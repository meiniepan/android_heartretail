package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;

/**
 * 个人资料界面
 *
 * @author aLang
 */
public final class PersonalFragment extends BaseFragment implements View.OnClickListener {

    private View layout_personal_avatar;
    private View layout_personal_nickname;
    private View layout_personal_invite_code;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_data;
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
        /* 头像 昵称 邀请码 */
        if (layout_personal_avatar == v) {
            // TODO: 2020/3/7
            ToastUtils.showShort("待做...");
        } else if (layout_personal_nickname == v) {
            startNickModifyActivity();
        } else if (layout_personal_invite_code == v) {

        }
    }

    private void startNickModifyActivity() {
        HRActivity.start(getContext(), NicknameFragment.class);
    }

    private void findViewById(View view) {
        layout_personal_avatar = view.findViewById(R.id.layout_personal_avatar);
        layout_personal_nickname = view.findViewById(R.id.layout_personal_nickname);
        layout_personal_invite_code = view.findViewById(R.id.layout_personal_invite_code);

        layout_personal_avatar.setOnClickListener(this);
        layout_personal_nickname.setOnClickListener(this);
        layout_personal_invite_code.setOnClickListener(this);
    }
}
