package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

/**
 * @author Burning
 * @description:
 * @date :2020-03-08 17:24
 */
public class SendGoodsTimeQ1Activity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, SendGoodsTimeQ1Activity.class);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_fh_time;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
