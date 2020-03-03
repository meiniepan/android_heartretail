package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.login.LoginFragment;
import com.idengyun.heartretail.shop.ShopListActivity;

@Route(path = (RouterPathConfig.app_FirstActivity))
public class FirstActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    public void skipShopList(View view) {
        VerifyDeviceActivity.start(this);
    }

    public void skipFist(View view) {
        HRActivity.start(this, LoginFragment.class);
    }
}
