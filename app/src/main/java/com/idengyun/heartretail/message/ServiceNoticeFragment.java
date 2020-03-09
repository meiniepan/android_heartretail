package com.idengyun.heartretail.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.idengyun.heartretail.bases.PagerChildFragment;

/**
 * 服务通知
 *
 * @author aLang
 */
public final class ServiceNoticeFragment extends PagerChildFragment {

    @Override
    public int getLayoutId() {
        return android.R.layout.browser_link_context_header;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
