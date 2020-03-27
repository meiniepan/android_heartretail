package com.idengyun.heartretail.setting.account.auth;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.PersonalDataBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;

/**
 * 银行卡信息
 *
 * @author aLang
 */
public final class BankCardInfoFragment extends BaseFragment {

    private SettingViewModel settingViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bank_card_info;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observe();
        requestAPI();
    }

    @MainThread
    private void updateUI(@Nullable PersonalDataBean personalDataBean) {
        if (personalDataBean == null) return;
        PersonalDataBean.Data data = personalDataBean.data;
        String name = data.name;
        String sex = data.sex;
        String nation = data.nation;
        String userBirthday = data.userBirthday;
        String address = data.address;
        String number = data.number;
        String authority = data.authority;
        String timeLimit = data.timeLimit;

        /*tv_id_card_value_1.setText(name);
        tv_id_card_value_2.setText(sex);
        tv_id_card_value_3.setText(nation);
        tv_id_card_value_4.setText(userBirthday);
        tv_id_card_value_5.setText(address);
        tv_id_card_value_6.setText(number);
        tv_id_card_value_7.setText(authority);
        tv_id_card_value_8.setText(timeLimit);*/
    }

    private void observe() {
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(this);
            settingViewModel.getUserInfo().observe(this, new Observer<PersonalDataBean>() {
                @Override
                public void onChanged(@Nullable PersonalDataBean personalDataBean) {
                    updateUI(personalDataBean);
                }
            });
        }
    }

    private void requestAPI() {
        if (settingViewModel == null) return;
        settingViewModel.requestUserInfo(this);
    }

    private void findViewById(View view) {
    }
}
