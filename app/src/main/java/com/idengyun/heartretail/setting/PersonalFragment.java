package com.idengyun.heartretail.setting;

import android.arch.lifecycle.Observer;
import android.content.Intent;
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
import com.dengyun.baselibrary.net.upload.UploadBean;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.TakePhotoUtil;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.beans.UserAvatarBean;
import com.idengyun.heartretail.setting.personal.InviteCodeFragment;
import com.idengyun.heartretail.setting.personal.NicknameFragment;
import com.idengyun.heartretail.viewmodel.SettingViewModel;
import com.idengyun.usermodule.HRUser;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.idengyun.heartretail.Constants.REQUEST_CODE_PERSONAL;

/**
 * 个人资料
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

    private SettingViewModel settingViewModel;

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
        observe();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onClick(View v) {
        /* 头像 昵称 邀请码 */
        if (layout_personal_avatar == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, REQUEST_CODE_PERSONAL);
        } else if (layout_personal_nickname == v) {
            HRActivity.start(getContext(), NicknameFragment.class);
        } else if (layout_personal_invite_code == v) {
            HRActivity.start(getContext(), InviteCodeFragment.class);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PERSONAL && resultCode == RESULT_OK) {
            List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
            if (!ListUtils.isEmpty(localMediaList)) {
                String imgPath = TakePhotoUtil.getResultPath(localMediaList.get(0));
                uploadFilePath(imgPath);
            }
        }
    }

    private void uploadFilePath(String imgPath) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.upload())
                .fragment(this)
                .clazz(UploadBean.class)
                .isShowDialog(false)
                .build();
        NetApi.upFileData(netOption, new File(imgPath), new JsonCallback<UploadBean>(netOption) {
            @Override
            public void onSuccess(Response<UploadBean> response) {
                avatarUrl = response.body().data.filePath;
                modifyAvatar();
            }
        });
    }

    private String avatarUrl;

    private void modifyAvatar() {
        if (settingViewModel == null) return;
        settingViewModel.requestModifyAvatar(this, avatarUrl);
    }

    @MainThread
    private void updateUI() {
        ImageApi.displayImage(iv_personal_avatar.getContext(), iv_personal_avatar, HRUser.getAvatar());
        tv_personal_nickname.setText(HRUser.getNickname());
        tv_personal_invite_code.setText(HRUser.getInviteCode());
    }

    private void observe() {
        if (settingViewModel == null) {
            settingViewModel = SettingViewModel.getInstance(this);
            settingViewModel.getModifyAvatar().observe(this, new Observer<UserAvatarBean>() {
                @Override
                public void onChanged(@Nullable UserAvatarBean userAvatarBean) {
                    HRUser.saveAvatar(avatarUrl);
                    ImageApi.displayImage(iv_personal_avatar.getContext(), iv_personal_avatar, avatarUrl);
                }
            });
        }
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
