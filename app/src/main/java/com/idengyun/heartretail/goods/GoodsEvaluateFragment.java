package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-用户评价
 *
 * @author aLang
 */
public final class GoodsEvaluateFragment extends BaseFragment {

    private TextView tv_favorable_rate;
    private RecyclerView recycler_view;
    private EvaluationAdapter evaluationAdapter;

    private GoodsViewModel goodsViewModel;

    /* 分页 */
    private int totalPageSize = -1;
    private int totalPage = -1;
    private int pageSize = 10;
    private int page = 0;
    private boolean loadMore = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_evaluae;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv_favorable_rate = view.findViewById(R.id.tv_favorable_rate);
        recycler_view = view.findViewById(R.id.recycler_view);
        evaluationAdapter = new EvaluationAdapter();
        recycler_view.setAdapter(evaluationAdapter);
        recycler_view.addOnScrollListener(new LoadMore() {
            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                requestAPI();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        assert activity != null;
        if (goodsViewModel == null) {
            goodsViewModel = GoodsViewModel.getInstance(activity);
            goodsViewModel.getGoodsEvaluate().observe(this, new Observer<GoodsEvaluateBean>() {
                @Override
                public void onChanged(@Nullable GoodsEvaluateBean goodsEvaluateBean) {
                    updateUI(goodsEvaluateBean);
                }
            });
        }

        requestAPI();
    }

    private void requestAPI() {
        FragmentActivity activity = getActivity();
        assert activity != null;
        Intent intent = activity.getIntent();
        int goodsId = intent.getIntExtra("home_goods_id", -1);
        int goodsType = intent.getIntExtra("home_goods_type", -1);
        if (!loadMore) return;
        goodsViewModel.requestGoodsEvaluate(this, goodsId, page + 1, pageSize);
    }

    @MainThread
    private void updateUI(@Nullable GoodsEvaluateBean goodsEvaluateBean) {
        if (goodsEvaluateBean == null) return;
        GoodsEvaluateBean.Data data = goodsEvaluateBean.data;

        totalPageSize = data.total;
        totalPage = (int) Math.ceil(1D * totalPageSize / pageSize);
        loadMore = ++page < totalPage;

        int evaluationCounts = data.total;
        String praiseRate = data.praiseRate;
        List<GoodsEvaluateBean.Data.Evaluation> evaluationList = data.evaluationList;

        tv_favorable_rate.setText(evaluationCounts + "+条评论，" + praiseRate + "%好评率");
        evaluationAdapter.evaluationList.addAll(evaluationList);
        evaluationAdapter.notifyDataSetChanged();
    }

    private class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationHolder> {
        final ArrayList<GoodsEvaluateBean.Data.Evaluation> evaluationList = new ArrayList<>();
        /* 布局填充器 */
        private LayoutInflater inflater;

        @NonNull
        @Override
        public EvaluationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            if (inflater == null) inflater = LayoutInflater.from(viewGroup.getContext());
            View itemView = inflater.inflate(R.layout.fragment_goods_evaluate_item, viewGroup, false);
            return new EvaluationHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EvaluationHolder holder, int position) {
            GoodsEvaluateBean.Data.Evaluation evaluation = evaluationList.get(position);
            holder.updateUI(evaluation);
        }

        @Override
        public int getItemCount() {
            return evaluationList.size();
        }

        class EvaluationHolder extends RecyclerView.ViewHolder {
            ImageView iv_user_avatar;
            TextView tv_user_name;
            TextView tv_user_level;
            RatingBar rb_user_rating;
            TextView tv_user_evaluation_date;
            TextView tv_user_likes;
            TextView tv_user_evaluation_content;
            View tv_divider;
            TextView tv_no_more;

            EvaluationHolder(@NonNull View itemView) {
                super(itemView);
                findViewById(itemView);
            }

            @MainThread
            void updateUI(GoodsEvaluateBean.Data.Evaluation evaluation) {
                String userImgUrl = evaluation.userHeadImg;
                String userName = evaluation.userName;
                int userLevel = evaluation.userLevel;
                String commentTime = evaluation.evaluationDate;
                int commentStar = evaluation.commentStar;
                String contents = evaluation.evaluationContent;
                String orderId = evaluation.orderId;
                int isShow = evaluation.isShow;

                ImageApi.displayImage(itemView.getContext(), iv_user_avatar, userImgUrl);
                tv_user_name.setText(userName);
                tv_user_level.setText("LV" + userLevel);
                tv_user_evaluation_date.setText(commentTime);
                rb_user_rating.setNumStars(commentStar);
                tv_user_evaluation_content.setText(contents);


                if (getAdapterPosition() + 1 == totalPageSize) {
                    tv_no_more.setVisibility(View.VISIBLE);
                } else {
                    tv_no_more.setVisibility(View.GONE);
                }
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
                tv_no_more = itemView.findViewById(R.id.tv_no_more);

                //rb_user_rating.setMax();
//                rb_user_rating.setProgressDrawable(getResources().getDrawable(R.drawable.layer_list_ratingbar));
//                Drawable drawable = getResources().getDrawable(R.drawable.ic_start_16dp_bg);
//                Drawable drawable1 = getResources().getDrawable(R.drawable.ic_star_16dp);
//                Drawable pd = new LayerDrawable(new Drawable[]{drawable, new GradientDrawable(), drawable1});
//                rb_user_rating.setProgressDrawable(pd);
//                new BitmapDrawable().setTileModeXY();
            }
        }
    }
}

