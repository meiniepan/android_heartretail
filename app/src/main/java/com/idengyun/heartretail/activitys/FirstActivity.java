package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.shop.ShopListActivity;
import com.idengyun.usermodule.LoginActivity;

@Route(path = (RouterPathConfig.app_FirstActivity))
public class FirstActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    /* 开启登录界面 */
    public void skipLogin(View view) {
        LoginActivity.start(this);
    }

    /* 开启主界面 */
    public void skipMain(View view) {
        MainActivity.start(this);
    }

    /* 跳到店铺列表 */
    public void skipShopList(View view) {
        ShopListActivity.start(this);
    }

    /* 跳到订单列表 */
    public void skipOrderList(View view) {
        OrderListActivity.start(this, 1);
    }
}
