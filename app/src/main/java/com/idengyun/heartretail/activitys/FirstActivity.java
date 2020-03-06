package com.idengyun.heartretail.activitys;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.MainActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.goods.GoodsDetailFragment;
import com.idengyun.heartretail.goods.GoodsSpecFragment;
import com.idengyun.usermodule.LoginActivity;
import com.idengyun.usermodule.ModifyPwdActivity;

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

    public void skipShopList(View view) {
        ModifyPwdActivity.start(this);
    }

    /* 开启商品详情界面 */
    public void skipGoodsDetail(View view) {
//        HRActivity.start(this, GoodsDetailFragment.class);
        HRActivity.start(this, GoodsSpecFragment.class);
    }

    /* 开启主界面 */
    public void skipMain(View view) {
        MainActivity.start(this);
    }
}
