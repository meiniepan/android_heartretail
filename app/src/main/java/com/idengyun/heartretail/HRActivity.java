package com.idengyun.heartretail;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.dengyun.baselibrary.base.activity.BaseActivity;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 简单容器类Activity
 * 通用Activity
 *
 * @author aLang
 */
public class HRActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIHelper.applySystemUI(this);
        //setContentView(R.layout.activity_hr);
        instantiateFragment();
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    private void instantiateFragment() {
        try {
            Intent intent = getIntent();
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            Bundle args = (extras != null) ? extras : new Bundle();
            Fragment fragment = Fragment.instantiate(this, action, args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, fragment, null)
                    .commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void start(@NonNull Context context, @NonNull Class<? extends Fragment> fragmentClass) {
        start(context, fragmentClass, null);
    }

    public static void start(@NonNull Context context, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle extras) {
        Intent starter = new Intent(context, HRActivity.class);
        starter.setAction(fragmentClass.getName());
        if (extras != null) starter.putExtras(extras);
        context.startActivity(starter);
    }

    private static void start(@NonNull Activity activity, @NonNull Class<? extends Fragment> fragmentClass) {
        Intent starter = new Intent(activity, HRActivity.class);
        //starter.putExtra();
        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) starter.putExtras(extras);
        starter.setAction(fragmentClass.getName());
        activity.startActivity(starter);
    }
}
