package com.idengyun.heartretail.setting.account.auth;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.PersonalDataBean;
import com.idengyun.heartretail.viewmodel.SettingViewModel;

/**
 * 银行卡信息
 *
 * @author aLang
 */
public final class BankCardInfoFragment extends BaseFragment {

    private ImageView iv_bank_card_icon;
    private TextView tv_bank_card_name;
    private TextView tv_bank_card_type;
    private TextView tv_bank_card_no;
    private TextView tv_bank_card_reserve_mobile;

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
        String userBankName = data.userBankName;
        String bankCardType = data.bankCardType;
        String cardType = data.cardType;
        String bankCardNo = data.bankCardNo;
        String bankMobile = data.bankMobile;

        // TODO: 2020/3/27
        ImageApi.displayImage(iv_bank_card_icon.getContext(), iv_bank_card_icon, "");
        tv_bank_card_name.setText(userBankName);
        tv_bank_card_type.setText(cardType);
        tv_bank_card_no.setText(bankCardNo);
        tv_bank_card_reserve_mobile.setText(bankMobile);
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
        iv_bank_card_icon = view.findViewById(R.id.iv_bank_card_icon);
        tv_bank_card_name = view.findViewById(R.id.tv_bank_card_name);
        tv_bank_card_type = view.findViewById(R.id.tv_bank_card_type);
        tv_bank_card_no = view.findViewById(R.id.tv_bank_card_no);
        tv_bank_card_reserve_mobile = view.findViewById(R.id.tv_bank_card_reserve_mobile);
    }
}
