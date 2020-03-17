package com.idengyun.heartretail.my.setting;

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
import com.idengyun.heartretail.model.response.ProtocolsBean;

/**
 * 用户协议
 *
 * @author aLang
 */
public final class AgreeDetailFragment extends BaseFragment {

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
        Intent intent = getActivity().getIntent();
        int protocolId = intent.getIntExtra("protocol_id", 0);
        String protocolName = intent.getStringExtra("protocol_name");
        String protocolContent = intent.getStringExtra("protocol_content");
        ProtocolsBean.Data data = new ProtocolsBean.Data();
        data.protocolId = protocolId;
        data.protocolName = protocolName;
        data.protocolContent = protocolContent;
        updateUI(data);
    }

    @MainThread
    private void updateUI(ProtocolsBean.Data data) {
        String protocolName = data.protocolName;
        String protocolContent = data.protocolContent;

        base_tool_bar.setTitle(protocolName);
        tv_agree_detail.setText(Html.fromHtml(protocolContent));
    }

    private void findViewById(View view) {
        base_tool_bar = view.findViewById(R.id.base_tool_bar);
        tv_agree_detail = view.findViewById(R.id.tv_agree_detail);
    }

}
