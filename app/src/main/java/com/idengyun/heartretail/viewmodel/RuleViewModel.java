package com.idengyun.heartretail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.dengyun.splashmodule.config.SpRule;
import com.idengyun.heartretail.beans.RuleBean;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 规则模块API
 *
 * @author aLang
 */
public final class RuleViewModel extends ViewModel {

    public static RuleViewModel getInstance(@NonNull FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(RuleViewModel.class);
    }

    public static RuleViewModel getInstance(@NonNull Fragment fragment) {
        return ViewModelProviders.of(fragment).get(RuleViewModel.class);
    }

    private final MutableLiveData<RuleBean> ruleListLiveData;
    private final MutableLiveData<RuleBean.Rule> playRuleLiveData;
    private final MutableLiveData<RuleBean.Rule> inviteRuleLiveData;

    public RuleViewModel() {
        super();
        ruleListLiveData = new MutableLiveData<>();
        playRuleLiveData = new MutableLiveData<>();
        inviteRuleLiveData = new MutableLiveData<>();
    }

    /* 玩法规则 */
    public void requestPlayRule(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.ruleQueryDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RuleBean.class)
                .params("ruleKeys", SpRule.RULE_KEY_PLAY)
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<RuleBean>(netOption) {
            @Override
            public void onSuccess(Response<RuleBean> response) {
                RuleBean ruleBean = response.body();
                List<RuleBean.Rule> ruleList = ruleBean.data;
                if (ruleList.isEmpty()) return;
                RuleBean.Rule rule = ruleList.get(0);
                playRuleLiveData.setValue(rule);
            }
        });
    }

    /* 邀请规则 */
    public void requestInviteRule(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.ruleQueryDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RuleBean.class)
                .params("ruleKeys", SpRule.RULE_KEY_INVITE)
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<RuleBean>(netOption) {
            @Override
            public void onSuccess(Response<RuleBean> response) {
                RuleBean ruleBean = response.body();
                List<RuleBean.Rule> ruleList = ruleBean.data;
                if (ruleList.isEmpty()) return;
                RuleBean.Rule rule = ruleList.get(0);
                inviteRuleLiveData.setValue(rule);
            }
        });
    }

    /* 查询规则列表 */
    private void requestRuleList(Fragment fragment) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.ruleQueryDetail())
                .fragment(fragment)
                .isShowDialog(true)
                .clazz(RuleBean.class)
                .params("ruleKeys", SpRule.getAllRuleKey())
                .build();
        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<RuleBean>(netOption) {
            @Override
            public void onSuccess(Response<RuleBean> response) {
                ruleListLiveData.setValue(response.body());
            }
        });
    }

    private LiveData<RuleBean> getRuleList() {
        return ruleListLiveData;
    }

    public LiveData<RuleBean.Rule> getPlayRule() {
        return playRuleLiveData;
    }

    public LiveData<RuleBean.Rule> getInviteRule() {
        return inviteRuleLiveData;
    }
}
