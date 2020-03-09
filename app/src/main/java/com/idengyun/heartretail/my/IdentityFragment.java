package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

/**
 * 身份验证界面
 *
 * @author aLang
 */
public final class IdentityFragment extends BaseFragment implements View.OnClickListener {
    private View layout_logout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_id_verify;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    private void findViewById(View view) {
//        recycler_view = view.findViewById(R.id.recycler_view);
    }
}
