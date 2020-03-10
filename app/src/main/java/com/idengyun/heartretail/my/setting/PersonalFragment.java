package com.idengyun.heartretail.my.setting;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.PersonalDataBean;
import com.idengyun.heartretail.my.setting.personal.AvatarFragment;
import com.idengyun.heartretail.my.setting.personal.NicknameFragment;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 个人资料界面
 *
 * @author aLang
 */
public final class PersonalFragment extends BaseFragment implements View.OnClickListener {

    private View layout_personal_avatar;
    private View layout_personal_nickname;
    private View layout_personal_invite_code;

    private ImageView iv_personal_avatar;
    private TextView tv_personal_nickname;
    private TextView tv_personal_invite_code;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_data;
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

    @Override
    public void onClick(View v) {
        /* 头像 昵称 邀请码 */
        if (layout_personal_avatar == v) {
            startAvatarActivity();
        } else if (layout_personal_nickname == v) {
            startNicknameActivity();
        } else if (layout_personal_invite_code == v) {
            startInvitationActivity();
        }
    }

    private void requestAPI() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryUserId())
                .fragment(this)
                .clazz(PersonalDataBean.class)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<PersonalDataBean>getData(RequestMethod.GET, netOption, new JsonCallback<PersonalDataBean>(netOption) {
            @Override
            public void onSuccess(Response<PersonalDataBean> response) {
                updateUI(response.body().data);
            }
        });
    }

    @MainThread
    private void updateUI(PersonalDataBean.Data data) {
        String headPic = data.headPic;
        String nickName = data.nickName;
        String invitationCode = data.invitationCode;

        ImageApi.displayImage(iv_personal_avatar.getContext(), iv_personal_avatar, headPic);
        tv_personal_nickname.setText(nickName);
        tv_personal_invite_code.setText(invitationCode);
    }

    private void startInvitationActivity() {
        // TODO: 2020/3/9
    }

    private void startAvatarActivity() {
        HRActivity.start(getContext(), AvatarFragment.class);
    }

    private void startNicknameActivity() {
        HRActivity.start(getContext(), NicknameFragment.class);
    }

    private void findViewById(View view) {
        iv_personal_avatar = view.findViewById(R.id.iv_personal_avatar);
        tv_personal_nickname = view.findViewById(R.id.tv_personal_nickname);
        tv_personal_invite_code = view.findViewById(R.id.tv_personal_invite_code);

        layout_personal_avatar = view.findViewById(R.id.layout_personal_avatar);
        layout_personal_nickname = view.findViewById(R.id.layout_personal_nickname);
        layout_personal_invite_code = view.findViewById(R.id.layout_personal_invite_code);

        layout_personal_avatar.setOnClickListener(this);
        layout_personal_nickname.setOnClickListener(this);
        layout_personal_invite_code.setOnClickListener(this);
    }
}
