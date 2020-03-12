package com.idengyun.heartretail.my.setting.personal;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.upload.UploadBean;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.UserAvatarBean;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

/**
 * 修改头像界面
 *
 * @author aLang
 */
public final class AvatarFragment extends BaseFragment implements View.OnClickListener {

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
//        uploadImage();
    }

    private void uploadImage() {
        NetOption netOption = NetOption.newBuilder("")
                .clazz(UploadBean.class)
                .fragment(this)
                .isShowDialog(false)
                .build();
        NetApi.upFileData(netOption, null, new JsonCallback<UploadBean>(netOption) {
            @Override
            public void onSuccess(Response<UploadBean> response) {
                UploadBean uploadBean = response.body();
                String url = uploadBean.getFile_url();
                requestAPI(url);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void requestAPI(String url) {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.changeIcon())
                .fragment(this)
                .clazz(UserAvatarBean.class)
                .params("url", url)
                .params("userId", HRUser.getId())
                .build();
        NetApi.<UserAvatarBean>getData(netOption, new JsonCallback<UserAvatarBean>(netOption) {
            @Override
            public void onSuccess(Response<UserAvatarBean> response) {
                updateUI(response.body().data);
            }
        });
    }


    @MainThread
    private void updateUI(UserAvatarBean.Data data) {

    }

    private void findViewById(View view) {
        et_nick_name = view.findViewById(R.id.et_nick_name);
    }
}
