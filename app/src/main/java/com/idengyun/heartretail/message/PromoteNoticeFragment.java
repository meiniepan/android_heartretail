package com.idengyun.heartretail.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.idengyun.heartretail.bases.PagerChildFragment;

/**
 * 促销优惠
 *
 * @author aLang
 */
public final class PromoteNoticeFragment extends PagerChildFragment {

    @Override
    public int getLayoutId() {
        return android.R.layout.activity_list_item;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
