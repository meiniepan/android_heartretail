package com.idengyun.heartretail.my.setting;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.splashmodule.config.SpProtocol;
import com.idengyun.heartretail.HRSession;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.ProtocolsBean;

import java.util.List;

/**
 * 用户协议
 *
 * @author aLang
 */
public final class AgreementsFragment extends BaseFragment {

    private TextView tv_user_agree_title;
    private TextView tv_user_agree_content;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_agree;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestAPI();
    }

    private void requestAPI() {
        HRSession.session_08(this, new int[]{SpProtocol.getUserProtocolId()}, new Observer<List<ProtocolsBean.Data>>() {
            @Override
            public void onChanged(@Nullable List<ProtocolsBean.Data> data) {
                updateUI(data);
            }
        });
    }

    @MainThread
    private void updateUI(List<ProtocolsBean.Data> data) {
        if (!data.isEmpty()) {
            ProtocolsBean.Data data1 = data.get(0);
            tv_user_agree_title.setText(Html.fromHtml(data1.protocolName));
            tv_user_agree_content.setText(Html.fromHtml(data1.protocolContent));
        }
    }

    private void findViewById(View view) {
        tv_user_agree_title = view.findViewById(R.id.tv_user_agree_title);
        tv_user_agree_content = view.findViewById(R.id.tv_user_agree_content);
    }
}
