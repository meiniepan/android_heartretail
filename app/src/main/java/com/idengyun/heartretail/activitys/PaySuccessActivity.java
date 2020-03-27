package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.goods.GoodsDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:支付完成
 * @date :2020/3/3 0003 10:31
 */
public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_check_order)
    TextView tvCheckOrder;
    @BindView(R.id.tv_back_goods_detail)
    TextView tvBackGoodsDetail;
    String orderId = "";

    public static void start(Context context) {
        Intent starter = new Intent(context, PaySuccessActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }


    @OnClick({R.id.tv_check_order, R.id.tv_back_goods_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_order:
                OrderDetailActivity.start(this,orderId);
                break;
            case R.id.tv_back_goods_detail:

                break;
        }
    }
}
