package com.idengyun.heartretail.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.ProtocolBean;

/**
 * 用户协议
 *
 * @author aLang
 */
public final class AgreeDetailFragment extends BaseFragment {

    private BaseToolBar base_tool_bar;
    private TextView tv_agree_detail;
    private WebView web_view;

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
        String protocolName = intent.getStringExtra("protocol_name");
        String protocolContent = intent.getStringExtra("protocol_content");
        ProtocolBean.Protocol protocol = new ProtocolBean.Protocol();
        protocol.protocolName = protocolName;
        protocol.protocolContent = protocolContent;
        updateUI(protocol);
    }

    @MainThread
    private void updateUI(ProtocolBean.Protocol protocol) {
        String protocolName = protocol.protocolName;
        String protocolContent = protocol.protocolContent;

        base_tool_bar.setTitle(protocolName);
        tv_agree_detail.setText(Html.fromHtml(protocolContent));
        // web_view.loadDataWithBaseURL(null, protocolContent, "text/html", "UTF-8", null);
    }

    private void findViewById(View view) {
        base_tool_bar = view.findViewById(R.id.base_tool_bar);
        tv_agree_detail = view.findViewById(R.id.tv_agree_detail);
        web_view = view.findViewById(R.id.web_view);
    }

}
