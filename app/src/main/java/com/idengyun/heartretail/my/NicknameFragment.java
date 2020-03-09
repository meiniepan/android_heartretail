package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

/**
 * 修改昵称界面
 *
 * @author aLang
 */
public final class NicknameFragment extends BaseFragment implements View.OnClickListener {

    private EditText et_nick_name;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_nick_modify;
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
        et_nick_name = view.findViewById(R.id.et_nick_name);
    }
}
