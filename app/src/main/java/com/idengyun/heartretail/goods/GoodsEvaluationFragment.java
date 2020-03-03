package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.idengyun.heartretail.HRConst;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.request.KVEvaluation;
import com.idengyun.heartretail.model.response.BEvaluation;
import com.lzy.okgo.model.Response;

import java.util.Map;

/**
 * 商品评价
 *
 * @author aLang
 */
public final class GoodsEvaluationFragment extends BaseFragment {


    class LoadMore {
        int totalSize;
        int totalPage;
        int pageSize;
        int page;

        public LoadMore() {


            totalPage = (int) Math.ceil(1D * totalSize / pageSize);

            if (page > totalPage) {
                // 已经是最后一页了
                return;
            }

            if ((page + 1) > totalPage) {
                // 已经是最后一页了

            } else {
                int newPage = page + 1;

            }

            // clalback success page+=1 fail=page;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_evaluation;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recycler_view = view.findViewById(R.id.recycler_view);
        EvaluationAdapter adapter = new EvaluationAdapter();
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Map<String, Object> map = new KVEvaluation(
                HRConst.VERSION,
                "",
                1,
                100,
                HRConst.PLATFORM
        ).toMap();

        NetOption netOption = NetOption.newBuilder("http://10.10.8.22:3000/mock/39/user/register")
                .activity(getActivity())
                .isShowDialog(true)
                .params(map)
                .clazz(BEvaluation.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<BEvaluation>(netOption) {
            @Override
            public void onSuccess(Response<BEvaluation> response) {
                if (response.code() != 200) {
                    return;
                }

                BEvaluation body = response.body();
                if (body == null) {
                    return;
                }

                if (!"200".equals(body.code)) {
                    return;
                }

            }
        });
    }

    private class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationHolder> {
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
            if (position == getItemCount() - 1) holder.tv_no_more.setVisibility(View.VISIBLE);
            else holder.tv_no_more.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return 6;
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

