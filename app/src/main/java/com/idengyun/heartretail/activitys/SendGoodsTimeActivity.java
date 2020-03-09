package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
 * @date :2020-03-08 17:24
 */
public class SendGoodsTimeActivity extends BaseActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, SendGoodsTimeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fh_time;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }


    @OnClick({R.id.ll_do_1, R.id.ll_do_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_do_1:
                SendGoodsTimeQ1Activity.start(this);
                break;
            case R.id.ll_do_2:
                break;
        }
    }
}
