package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.SystemUIHelper;

/**
 * @author Burning
 * @description:
 * @date :2020/3/20 0020 11:28
 */
public class MyBalanceActivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, MyBalanceActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_balance;
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        SystemUIHelper.applySystemUI(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
