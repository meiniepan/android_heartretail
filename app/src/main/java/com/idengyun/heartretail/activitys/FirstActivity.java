package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.ModifyPwdActivity;
import com.idengyun.usermodule.VerifyDeviceActivity;

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
        ModifyPwdActivity.start(this);
    }

    public void skipFist(View view) {
        LoginActivity.start(this);
    }
}
