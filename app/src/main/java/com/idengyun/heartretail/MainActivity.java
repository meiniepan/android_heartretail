package com.idengyun.heartretail;


import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.main.MainFragment;

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
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
