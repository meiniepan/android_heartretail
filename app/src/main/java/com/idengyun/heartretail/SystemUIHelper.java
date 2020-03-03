package com.idengyun.heartretail;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

/**
 * @author aLang
 */
public final class SystemUIHelper {

    public static void applySystemUI(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            new Theme(activity);
        }
    }

    private SystemUIHelper() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static class Theme implements View.OnApplyWindowInsetsListener, View.OnSystemUiVisibilityChangeListener {
        private static final int STYLE_LIGHT = 0;
        private static final int STYLE_DARK = 1;

        private int style = STYLE_LIGHT;

        private Window window;
        private View decorView;
        private android.app.ActionBar actionBar;
        private android.support.v7.app.ActionBar supportActionBar;

        public Theme(@NonNull Activity activity) {
            this.window = activity.getWindow();
            this.decorView = window.getDecorView();
            this.actionBar = activity.getActionBar();
            if (activity instanceof AppCompatActivity) {
                this.supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            }

            init();
        }

        private void init() {
            decorView.setOnApplyWindowInsetsListener(this);
            decorView.setOnSystemUiVisibilityChangeListener(this);

            hideActionBar();

            if (style == STYLE_LIGHT) {
                setLightTheme();
            }
        }

        @Override
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (style == STYLE_LIGHT) {
                    v.setBackground(new ColorDrawable(Color.WHITE));
                } else {
                    v.setBackground(new ColorDrawable(Color.BLACK));
                }
                int bottom = insets.getStableInsetBottom();
                v.setPadding(0, 0, 0, bottom);
                return insets.replaceSystemWindowInsets(0, 0, 0, 0);
            }
            return insets;
        }

        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            /*if (decorView == null || window == null) return;
            if (style == STYLE_LIGHT) setLightTheme();*/
        }

        private void setLightTheme() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        private void hideActionBar() {
            if (actionBar != null) actionBar.hide();
            if (supportActionBar != null) supportActionBar.hide();
        }

    }
}
