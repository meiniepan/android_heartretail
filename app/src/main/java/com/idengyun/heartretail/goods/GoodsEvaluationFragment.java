package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.HRConfig;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.EvaluationListBean;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评价页
 *
 * @author aLang
 */
public final class GoodsEvaluationFragment extends BaseFragment {
    private int totalPageSize;
    private int totalPage;
    private int pageSize;
    private int page;
    private TextView tv_favorable_rate;
    private RecyclerView recycler_view;
    private EvaluationAdapter evaluationAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_evaluation;
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
                if (totalPageSize == 0) return;

                if ((page + 1) > totalPage) {
                    // 已经是最后一页了
                    return;
                }

                requestAPI();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestAPI();
    }

    private void requestAPI() {
        String url = HRConfig.getGoodsEvaluationUrl();
        url = "http://10.10.8.22:3000/mock/39/evaluation/query/list";
        NetOption netOption = NetOption.newBuilder(url)
                .fragment(this)
                .type(new TypeToken<ApiBean<EvaluationListBean>>() {
                }.getType())
                .params("goodsId", "")
                .params("page", page + 1)
                .params("pageSize", pageSize)
                .build();
        NetApi.<ApiBean<EvaluationListBean>>getData(netOption, new JsonCallback<ApiBean<EvaluationListBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<EvaluationListBean>> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(EvaluationListBean model) {
        String commentCounts = model.commentCounts;
        String praiseRate = model.praiseRate;
        List<EvaluationListBean.Evaluation> evaluationList = model.evaluationList;

        totalPageSize = Integer.parseInt(commentCounts);
        totalPage = (int) Math.ceil(1D * totalPageSize / pageSize);
        pageSize = 100;
        ++page;

        tv_favorable_rate.setText(commentCounts + "+条评论，" + praiseRate + "%好评率");
        evaluationAdapter.evaluationList.addAll(evaluationList);
        evaluationAdapter.notifyDataSetChanged();
    }

    private class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationHolder> {
        final ArrayList<EvaluationListBean.Evaluation> evaluationList = new ArrayList<>();
        /* 布局填充器 */
        private LayoutInflater inflater;

        @NonNull
        @Override
        public EvaluationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            if (inflater == null) inflater = LayoutInflater.from(viewGroup.getContext());
            View itemView = inflater.inflate(R.layout.fragment_goods_evaluation_item, viewGroup, false);
            return new EvaluationHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull EvaluationHolder holder, int position) {
            EvaluationListBean.Evaluation evaluation = evaluationList.get(position);
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
            void updateUI(EvaluationListBean.Evaluation evaluation) {
                String userImgUrl = evaluation.userImgUrl;
                String userName = evaluation.userName;
                int userLevel = evaluation.userLevel;
                String commentTime = evaluation.commentTime;
                int commentStar = evaluation.commentStar;
                String contents = evaluation.contents;
                String orderId = evaluation.orderId;
                int isShow = evaluation.isShow;

                ImageApi.displayImage(itemView.getContext(), iv_user_avatar, userImgUrl);
                tv_user_name.setText(userName);
                tv_user_level.setText("LV" + userLevel);
                tv_user_evaluation_date.setText(commentTime);
                rb_user_rating.setNumStars(commentStar);
                tv_user_evaluation_content.setText(contents);

                if (getAdapterPosition() == totalPageSize - 1)
                    tv_no_more.setVisibility(View.VISIBLE);
                else tv_no_more.setVisibility(View.GONE);
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

