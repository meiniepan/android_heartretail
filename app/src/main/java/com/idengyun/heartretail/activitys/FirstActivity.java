package com.idengyun.heartretail.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.R;
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
        ConfirmOrderActivity.start(this);
    }

    public void skipFist(View view) {
        ToastUtils.showShort("跳转到首页");
    }
}
