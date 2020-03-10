package com.idengyun.heartretail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
    private static String lineSeparator = System.lineSeparator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemUIHelper.applySystemUI(this);
        // setContentView(R.layout.activity_hr);
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

            if (action == null) return;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String[] fnameArray = action.split(lineSeparator);
            for (String fname : fnameArray) {
                Fragment fragment = Fragment.instantiate(this, fname, extras);
                transaction.add(android.R.id.content, fragment, fname);
            }
            transaction.commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void start(@Nullable Context context, @NonNull Class<? extends Fragment> fragmentClass) {
        start(context, fragmentClass, null);
    }

    public static void start(@Nullable Context context, @NonNull Class<? extends Fragment> fragmentClass, @Nullable Bundle extras) {
        start(context, new Class[]{fragmentClass}, extras);
    }

    public static void start(@Nullable Context context, @NonNull Class<? extends Fragment>[] clazzArray, @Nullable Bundle extras) {
        if (context == null || clazzArray.length == 0) return;

        StringBuilder sb = new StringBuilder();
        for (Class<? extends Fragment> clazz : clazzArray)
            sb.append(clazz.getName()).append(lineSeparator);

        Intent starter = new Intent(context, HRActivity.class);
        String action = sb.toString();
        starter.setAction(action);
        if (extras != null) starter.putExtras(extras);
        context.startActivity(starter);
    }

    public static void hideFragment(@Nullable FragmentActivity activity, String tag) {
        if (activity == null) return;
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment != null) fm.beginTransaction().hide(fragment).commit();
    }

    public static void showFragment(@Nullable FragmentActivity activity, String tag) {
        if (activity == null) return;
        FragmentManager fm = activity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment != null) fm.beginTransaction().show(fragment).commit();
    }

    @Nullable
    public static Fragment findFragmentByTag(@Nullable FragmentActivity activity, String tag) {
        if (activity == null) return null;
        return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }
}
