package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsEvaluateBean;
import com.idengyun.heartretail.viewmodel.GoodsViewModel;
import com.idengyun.msgmodule.RVLoadMore;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-用户评价
 *
 * @author aLang
 */
public final class GoodsEvaluateFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private TextView tv_favorable_rate;
    private RecyclerView recycler_view;
    private EvaluationAdapter evaluationAdapter;

    private GoodsViewModel goodsViewModel;

    /* 分页加载 */
    private int totalSize = 0;
    private int totalPage = 0;
    private int pageSize = 0;
    private int currentPage = 0;

    private final RVLoadMore onScrollListener = new RVLoadMore() {
        @Override
        public void onLoadingMore(RVLoadMore listener) {
            onLoadMore();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_evaluae;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv_favorable_rate = view.findViewById(R.id.tv_favorable_rate);
        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.addOnScrollListener(onScrollListener);
        evaluationAdapter = new EvaluationAdapter();
        recycler_view.setAdapter(evaluationAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        totalSize = 0;
        totalPage = 0;
        pageSize = 10;
        currentPage = 0;
        onScrollListener.setCanLoadMore(true);
        onLoadMore();
    }

    private void observe() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(activity);
            goodsViewModel.getGoodsEvaluate().observe(this, new Observer<GoodsEvaluateBean>() {
                @Override
                public void onChanged(@Nullable GoodsEvaluateBean goodsEvaluateBean) {
                    updateUI(goodsEvaluateBean);
                }
            });
        }
    }

    private void onLoadMore() {
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        Intent intent = activity.getIntent();
        int goodsId = intent.getIntExtra("home_goods_id", -1);
        int goodsType = intent.getIntExtra("home_goods_type", -1);
        if (goodsViewModel == null) return;
        goodsViewModel.requestGoodsEvaluate(this, goodsId, currentPage + 1, pageSize);
    }

    @MainThread
    private void updateUI(@Nullable GoodsEvaluateBean goodsEvaluateBean) {
        onScrollListener.setLoadingMore(false);
        if (goodsEvaluateBean == null) return;
        GoodsEvaluateBean.Data data = goodsEvaluateBean.data;
        List<GoodsEvaluateBean.Data.Evaluation> evaluationList = data.evaluationList;

        totalSize = data.total;
        totalPage = data.pages;
        currentPage = data.current;
        onScrollListener.setCanLoadMore(currentPage < totalPage);

        int evaluationCounts = data.total;
        String praiseRate = data.praiseRate;
        String count = evaluationCounts > 999 ? 999 + "+" : evaluationCounts + "";
        tv_favorable_rate.setText(count + "条评论，" + praiseRate + "%好评率");

        if (currentPage == 1) evaluationAdapter.evaluationList.clear();
        evaluationAdapter.evaluationList.addAll(evaluationList);
        if (!onScrollListener.isCanLoadMore()) {
            GoodsEvaluateBean.Data.Evaluation evaluation = new GoodsEvaluateBean.Data.Evaluation();
            evaluation.evaluationId = -1;
            evaluationAdapter.evaluationList.add(evaluation);
        }
        evaluationAdapter.notifyDataSetChanged();
    }

    private static class EvaluationAdapter extends RecyclerView.Adapter {
        final ArrayList<GoodsEvaluateBean.Data.Evaluation> evaluationList = new ArrayList<>();
        /* 布局填充器 */
        private LayoutInflater inflater;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (inflater == null) inflater = LayoutInflater.from(viewGroup.getContext());
            if (viewType == -1) {
                View itemView = inflater.inflate(R.layout.view_type_no_more, viewGroup, false);
                return new RecyclerView.ViewHolder(itemView) {
                };
            }
            View itemView = inflater.inflate(R.layout.fragment_goods_evaluate_item, viewGroup, false);
            return new EvaluationHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) != -1) {
                GoodsEvaluateBean.Data.Evaluation evaluation = evaluationList.get(position);
                EvaluationHolder evaluationHolder = (EvaluationHolder) holder;
                evaluationHolder.updateUI(evaluation, position, getItemCount());
            }
        }

        @Override
        public int getItemCount() {
            return evaluationList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (evaluationList.get(position).evaluationId == -1) return -1;
            return super.getItemViewType(position);
        }
    }

    private static class EvaluationHolder extends RecyclerView.ViewHolder {
        private ImageView iv_user_avatar;
        private TextView tv_user_name;
        private TextView tv_user_level;
        private RatingBar rb_user_rating;
        private TextView tv_user_evaluation_date;
        private TextView tv_user_likes;
        private TextView tv_user_evaluation_content;
        private View tv_divider;

        EvaluationHolder(@NonNull View itemView) {
            super(itemView);
            findViewById(itemView);
        }

        @MainThread
        void updateUI(GoodsEvaluateBean.Data.Evaluation evaluation, int position, int itemCount) {
            String userImgUrl = evaluation.userHeadImg;
            String userName = evaluation.userName;
            int userLevel = evaluation.userLevel;
            String evaluationDate = evaluation.evaluationDate;
            int evaluationStar = evaluation.evaluationStar;
            String contents = evaluation.evaluationContent;
            String orderId = evaluation.orderId;
            int isShow = evaluation.isShow;
            String[] split = evaluationDate.split(" ");
            String date = split.length > 0 ? split[0] : "";

            ImageApi.displayImage(itemView.getContext(), iv_user_avatar, userImgUrl);
            tv_user_name.setText(userName);
            tv_user_level.setText("LV" + userLevel);
            tv_user_evaluation_date.setText(date);
            rb_user_rating.setRating(evaluationStar);
            tv_user_evaluation_content.setText(contents);
            tv_divider.setVisibility(position == itemCount - 1 ? View.GONE : View.VISIBLE);
        }

        private void findViewById(@NonNull View itemView) {
            iv_user_avatar = itemView.findViewById(R.id.iv_user_avatar);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_user_level = itemView.findViewById(R.id.tv_user_level);
            rb_user_rating = itemView.findViewById(R.id.rb_user_rating);
            tv_user_evaluation_date = itemView.findViewById(R.id.tv_user_evaluation_date);
            tv_user_likes = itemView.findViewById(R.id.tv_user_likes);
            tv_user_evaluation_content = itemView.findViewById(R.id.tv_user_evaluation_content);
            tv_divider = itemView.findViewById(R.id.tv_divider);
        }
    }
}

