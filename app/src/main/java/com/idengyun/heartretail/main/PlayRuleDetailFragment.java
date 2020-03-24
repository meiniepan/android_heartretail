package com.idengyun.heartretail.main;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.ProtocolBean;
import com.idengyun.heartretail.model.response.RuleBean;
import com.idengyun.heartretail.viewmodel.RuleViewModel;

/**
 * 玩法规则
 *
 * @author aLang
 */
public final class PlayRuleDetailFragment extends BaseFragment {

    private BaseToolBar base_tool_bar;
    private TextView tv_agree_detail;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_agree_detail;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RuleViewModel ruleViewModel = RuleViewModel.getInstance(this);
        ruleViewModel.getPlayRule().observe(this, new Observer<RuleBean.Rule>() {
            @Override
            public void onChanged(@Nullable RuleBean.Rule rule) {
                updateUI(rule);
            }
        });
        ruleViewModel.requestPlayRule(this);
    }

    @MainThread
    private void updateUI(@Nullable RuleBean.Rule rule) {
        if (rule == null) return;

        String title = rule.title;
        String content = rule.content;

        base_tool_bar.setTitle(title);
        tv_agree_detail.setText(Html.fromHtml(content));
    }

    private void findViewById(View view) {
        base_tool_bar = view.findViewById(R.id.base_tool_bar);
        tv_agree_detail = view.findViewById(R.id.tv_agree_detail);
    }

}
