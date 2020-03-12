package com.idengyun.heartretail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.idengyun.heartretail.main.HomeFragment;
import com.idengyun.heartretail.main.MyFragment;
import com.idengyun.heartretail.main.RedPacketFragment;

/**
 * APP主界面
 *
 * @author aLang
 */
@Route(path = (RouterPathConfig.app_FirstActivity))
public final class MainActivity extends BaseActivity {
    private HomeFragment homeFragment;
    private RedPacketFragment redPacketFragment;
    private MyFragment myFragment;


    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    private void init() {
        SystemUIHelper.applySystemUI(this);

        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        redPacketFragment = new RedPacketFragment();
        myFragment = new MyFragment();

        /*homeFragment.setUserVisibleHint(false);
        redPacketFragment.setUserVisibleHint(false);
        myFragment.setUserVisibleHint(false);*/

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, homeFragment, HomeFragment.class.getName())
                .add(R.id.content, redPacketFragment, RedPacketFragment.class.getName())
                .add(R.id.content, myFragment, MyFragment.class.getName())
                .hide(homeFragment)
                .hide(redPacketFragment)
                .hide(myFragment)
                .commit();

        RadioGroup rg_navigation = findViewById(R.id.rg_navigation);
        RadioButton rb_home = findViewById(R.id.rb_home);
        RadioButton rb_red_packet = findViewById(R.id.rb_red_packet);
        RadioButton rb_my = findViewById(R.id.rb_my);
        rg_navigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.rb_home == checkedId) {
                    showHomeFragment();
                } else if (R.id.rb_red_packet == checkedId) {
                    showRedPacketFragment();
                } else if (R.id.rb_my == checkedId) {
                    showMyFragment();
                }
            }
        });

        rg_navigation.check(R.id.rb_home);
    }

    private void showMyFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(myFragment)
                .hide(homeFragment)
                .hide(redPacketFragment)
                .commit();

        homeFragment.setUserVisibleHint(false);
        redPacketFragment.setUserVisibleHint(false);
        myFragment.setUserVisibleHint(true);
    }

    private void showRedPacketFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(redPacketFragment)
                .hide(homeFragment)
                .hide(myFragment)
                .commit();

        homeFragment.setUserVisibleHint(false);
        redPacketFragment.setUserVisibleHint(true);
        myFragment.setUserVisibleHint(false);
    }

    private void showHomeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .show(homeFragment)
                .hide(redPacketFragment)
                .hide(myFragment)
                .commit();

        homeFragment.setUserVisibleHint(true);
        redPacketFragment.setUserVisibleHint(false);
        myFragment.setUserVisibleHint(false);
    }
}
