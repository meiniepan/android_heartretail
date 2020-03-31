package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.ReplyListAdapter;
import com.idengyun.heartretail.beans.CommentDetailBean;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Burning
 * @description: 评价详情
 * @date :2020/3/6 0006 9:06
 */
public class EvaluateDetailActivity extends BaseActivity {
    @BindView(R.id.iv_goods_icon4)
    ImageView ivGoodsIcon;
    @BindView(R.id.tv_goods_title4)
    TextView tvGoodsTitle1;
    @BindView(R.id.tv_goods_spec4)
    TextView tvGoodsSpec1;
    @BindView(R.id.sr_evaluate_detail)
    StatusRecyclerView recyclerView;
    @BindView(R.id.iv_evaluate_avatar)
    ImageView ivEvaluateAvatar;
    @BindView(R.id.tv_evaluate_name)
    TextView tvEvaluateName;
    @BindView(R.id.tv_evaluate_time)
    TextView tvEvaluateTime;
    @BindView(R.id.rb_evaluation)
    AppCompatRatingBar rbEvaluation;
    @BindView(R.id.tv_evaluate_content)
    TextView tvEvaluateContent;
    private ReplyListAdapter adapter;
    List<CommentDetailBean> mData = new ArrayList<>();
    private int evaluationId;//评价id


    public static void start(Context context, int evaluationId) {
        Intent starter = new Intent(context, EvaluateDetailActivity.class);
        starter.putExtra(Constants.EVALUATION_ID, evaluationId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        evaluationId = getIntent().getIntExtra(Constants.EVALUATION_ID,0);
        getData();
    }

    private void initRecycler(List<CommentDetailBean.replyBean> replyList) {
        adapter = new ReplyListAdapter(R.layout.item_evaluate_detail, replyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Type type = new TypeToken<ApiBean<CommentDetailBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryMyEvaluationDetail())
                .activity(activity)
                .params("version", AppUtils.getAppVersionName())
                .params("evaluationId", evaluationId)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<CommentDetailBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<CommentDetailBean>> response) {
                CommentDetailBean data = response.body().data;
                if (data != null) {
                    initUI(data);
                }
            }

            @Override
            public void onError(Response<ApiBean<CommentDetailBean>> response) {
                super.onError(response);
            }
        });
    }

    private void initUI(CommentDetailBean data) {
        initRecycler(data.replyList);
        ImageApi.displayImage(this,ivGoodsIcon,data.goodsImg);
        ImageApi.displayImage(this,ivEvaluateAvatar,data.userHeadImg);
        tvGoodsTitle1.setText(data.goodsTitle);
        tvGoodsSpec1.setText(data.evaluationDate);
        tvEvaluateName.setText(data.userName);
        tvEvaluateTime.setText(data.evaluationDate);
        tvEvaluateContent.setText(data.evaluationContent);
        rbEvaluation.setRating(data.evaluationStar);
    }

}
