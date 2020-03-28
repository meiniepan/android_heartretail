package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.imageview.RoundImageView;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020/3/24 0024 16:49
 */
public class EvaluateGoodsActivity extends BaseActivity {
    @BindView(R.id.iv_goods_icon22)
    RoundImageView ivGoodsIcon22;
    @BindView(R.id.tv_goods_title2)
    TextView tvGoodsTitle2;
    @BindView(R.id.tv_goods_spec2)
    TextView tvGoodsSpec2;
    @BindView(R.id.tv_quantity2)
    TextView tvQuantity2;
    @BindView(R.id.rb_evaluation2)
    AppCompatRatingBar rbEvaluation2;
    @BindView(R.id.et_content2)
    EditText etContent2;
    @BindView(R.id.tv_evaluate_confirm)
    TextView tvEvaluateConfirm;
    String orderId;

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, EvaluateGoodsActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_goods;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
    }

    private void doCommit() {
        HashMap map = new HashMap();
        map.put("userId", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getId());
        map.put("userIp", "1");
        map.put("goodsId", 1);
        map.put("skuId", "1");
        map.put("evaluationContent", etContent2.getText().toString());
        map.put("evaluationStar", rbEvaluation2.getRating());
        map.put("goodsType", 1);
        map.put("orderId", orderId);
        map.put("ogId", 1);

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.saveEvaluation())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ToastUtils.showShort("评价成功");

            }

            @Override
            public void onError(Response<ApiBean> response) {
                super.onError(response);
            }
        });
    }

    @OnClick(R.id.tv_evaluate_confirm)
    public void onViewClicked() {
        doCommit();
    }
}
