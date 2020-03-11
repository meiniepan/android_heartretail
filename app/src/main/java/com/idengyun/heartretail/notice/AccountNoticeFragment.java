package com.idengyun.heartretail.notice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.idengyun.heartretail.bases.PagerChildFragment;

/**
 * 账户通知
 *
 * @author aLang
 */
public final class AccountNoticeFragment extends PagerChildFragment {

    @Override
    public int getLayoutId() {
        return android.R.layout.two_line_list_item;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
