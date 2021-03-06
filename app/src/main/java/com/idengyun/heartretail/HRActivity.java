package com.idengyun.heartretail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dengyun.baselibrary.base.activity.BaseActivity;

import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 简单容器类Activity
 * 通用Activity
 *
 * @author aLang
 */
public class HRActivity extends BaseActivity {

    public static void start(Context context, Bundle extras, @NonNull List<Class<? extends Fragment>> fClassList) {
        if (context == null || fClassList.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for (Class<? extends Fragment> clazz : fClassList)
            sb.append(clazz.getName()).append(lineSeparator);

        Intent starter = new Intent(context, HRActivity.class);
        String action = sb.toString();
        starter.setAction(action);
        if (extras != null) starter.putExtras(extras);
        context.startActivity(starter);
    }

    public static void start(Context context, Bundle extras, @NonNull Class<? extends Fragment> fClass) {
        start(context, extras, Collections.singletonList(fClass));
    }

    public static void start(Context context, Class<? extends Fragment> fClass) {
        start(context, null, fClass);
    }

    public static void startForResult(Activity activity, Bundle extras, @NonNull List<Class<? extends Fragment>> fClassList, int requestCode) {
        if (activity == null || fClassList.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for (Class<? extends Fragment> clazz : fClassList)
            sb.append(clazz.getName()).append(lineSeparator);

        Intent starter = new Intent(activity, HRActivity.class);
        String action = sb.toString();
        starter.setAction(action);
        if (extras != null) starter.putExtras(extras);
        activity.startActivityForResult(starter, requestCode);
    }

    public static void startForResult(Activity activity, Bundle extras, @NonNull Class<? extends Fragment> fClass, int requestCode) {
        startForResult(activity, extras, Collections.singletonList(fClass), requestCode);
    }

    public static void startForResult(Activity activity, Class<? extends Fragment> fClass, int requestCode) {
        startForResult(activity, null, fClass, requestCode);
    }

    public static void finish(@Nullable FragmentActivity fActivity) {
        if (fActivity != null) fActivity.finish();
    }

    public static void onBackPressed(@Nullable FragmentActivity fActivity) {
        if (fActivity != null) fActivity.onBackPressed();
    }

    public static void showFragment(@Nullable FragmentActivity fActivity, String... tags) {
        setUserVisible(fActivity, true, tags);
    }

    public static void hideFragment(@Nullable FragmentActivity fActivity, String... tags) {
        setUserVisible(fActivity, false, tags);
    }

    private static void setUserVisible(@Nullable FragmentActivity fActivity, boolean isVisibleToUser, String... tags) {
        if (fActivity == null) return;
        FragmentManager fManager = fActivity.getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        for (String tag : tags) {
            Fragment fragment = fManager.findFragmentByTag(tag);
            if (fragment != null) {
                fragment.setUserVisibleHint(isVisibleToUser);
                if (isVisibleToUser) fTransaction.show(fragment);
                else fTransaction.hide(fragment);
            }
        }
        fTransaction.commit();
    }

    @Nullable
    public static Fragment findFragmentByTag(@Nullable FragmentActivity fActivity, String tag) {
        if (fActivity == null) return null;
        return fActivity.getSupportFragmentManager().findFragmentByTag(tag);
    }

    private static String lineSeparator = System.lineSeparator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HRUIHelper.applySystemUI(this);
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

            FragmentManager fManager = getSupportFragmentManager();
            FragmentTransaction fTransaction = fManager.beginTransaction();
            String[] fnameArray = action.split(lineSeparator);
            for (String fname : fnameArray) {
                Fragment fragment = Fragment.instantiate(this, fname, extras);
                fTransaction.add(android.R.id.content, fragment, fname);
                /*boolean isVisibleToUser = fragment.getUserVisibleHint();
                if (isVisibleToUser) fTransaction.show(fragment);
                else fTransaction.hide(fragment);*/
            }
            fTransaction.commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
