package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;

/**
 * @author Burning
 * @description:
 * @date :2020/3/24 0024 16:49
 */
public class EvaluateGoodsActivity extends BaseActivity {
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

    }
}
