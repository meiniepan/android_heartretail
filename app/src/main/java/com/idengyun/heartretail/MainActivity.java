package com.idengyun.heartretail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.main.MainFragment;

@Route(path = (RouterPathConfig.app_FirstActivity))
public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIHelper.applySystemUI(this);
        //setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new MainFragment())
                .commit();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
