package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 16:00
 */
public class HelpCenterActivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, HelpCenterActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
