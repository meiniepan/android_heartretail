package com.idengyun.heartretail;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dengyun.baselibrary.base.activity.BaseActivity;

import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIHelper.applySystemUI(this);
        //setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new MainFragment())
                .commit();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                HRNetApi.demo();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
