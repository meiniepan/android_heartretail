package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.shop.ShopListActivity;

import butterknife.BindView;

/**
 * @author Burning
 * @description:
 * @date :2020-02-29 11:16
 */
public class ConfirmOrderActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order_retail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }
}
