package com.idengyun.heartretail.my.setting.account.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.upload.UploadBean;
import com.dengyun.baselibrary.net.upload.UploadListBean;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.TakePhotoUtil;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.idengyun.heartretail.Constants.REQUEST_CODE_PERSONAL;

/**
 * 步骤2
 *
 * @author aLang
 */
public final class Step2Fragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_REAL_BANK_CARD_TRUE = 2003;
    private static final int REQUEST_CODE_REAL_BANK_CARD_FALSE = 2004;

    private ImageView iv_bank_card_true;
    private ImageView iv_bank_card_false;
    private View tv_real_next_step_2;

    /* 身份证和银行卡正反面本地文件路径 */
    private String idCardTruePath;
    private String idCardFalsePath;
    private String bankCardTruePath;
    private String bankCardFalsePath;

    private String idCardTrueUrl;
    private String idCardFalseUrl;
    private String bankCardTrueUrl;
    private String bankCardFalseUrl;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_step_2;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity == null) return;
        Intent intent = activity.getIntent();
        idCardTruePath = intent.getStringExtra("id_card_true_path");
        idCardTruePath = intent.getStringExtra("id_card_false_path");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
        if (ListUtils.isEmpty(localMediaList)) return;

        if (REQUEST_CODE_REAL_BANK_CARD_TRUE == requestCode) {
            bankCardTruePath = TakePhotoUtil.getResultPath(localMediaList.get(0));
            ImageApi.displayImage(iv_bank_card_true.getContext(), iv_bank_card_true, bankCardTruePath);
        } else if (REQUEST_CODE_REAL_BANK_CARD_FALSE == requestCode) {
            bankCardFalsePath = TakePhotoUtil.getResultPath(localMediaList.get(0));
            ImageApi.displayImage(iv_bank_card_false.getContext(), iv_bank_card_false, bankCardFalsePath);
        }

        boolean enabled = !(TextUtils.isEmpty(bankCardTruePath) || TextUtils.isEmpty(bankCardFalsePath));
        tv_real_next_step_2.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        if (iv_bank_card_true == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 315, 215, 1, REQUEST_CODE_REAL_BANK_CARD_TRUE);
        } else if (iv_bank_card_false == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 315, 215, 1, REQUEST_CODE_REAL_BANK_CARD_FALSE);
        } else if (tv_real_next_step_2 == v) {
//            HRActivity.start(getContext(), Step3Fragment.class);
            uploadFileList();
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
//                avatarUrl = response.body().data.filePath;
//                modifyAvatar();
            }
        });
    }

    /**
     * 多图片上传
     */
    private void uploadFileList() {
        // TODO: 2020-03-24 身份证的照片没有传过来
        ArrayList<File> files = new ArrayList<>();
        if (!TextUtils.isEmpty(idCardTruePath)) files.add(new File(idCardTruePath));
        if (!TextUtils.isEmpty(idCardFalsePath)) files.add(new File(idCardFalsePath));
        if (!TextUtils.isEmpty(bankCardTruePath)) files.add(new File(bankCardTruePath));
        if (!TextUtils.isEmpty(bankCardFalsePath)) files.add(new File(bankCardFalsePath));
        if (files.size()!=4){
            ToastUtils.showShort("图片不够四张");
            return;
        }

        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.uploads())
                .fragment(this)
                .clazz(UploadListBean.class)
                .isShowDialog(false)
                .build();
        NetApi.<UploadListBean>upFileListData(netOption, files, new JsonCallback<UploadListBean>(netOption) {
            @Override
            public void onSuccess(Response<UploadListBean> response) {
                //上传的图片和返回的图片时一一对应的，上传四张返回也是四张，一一对应
                List<String> returnUrls = response.body().data.filePathList;
                if (!ListUtils.isEmpty(returnUrls) && returnUrls.size() == 4){
                    idCardTrueUrl = returnUrls.get(0);
                    idCardFalseUrl = returnUrls.get(1);
                    bankCardTrueUrl = returnUrls.get(2);
                    bankCardFalseUrl = returnUrls.get(3);
                }
            }
        });
    }

    private void findViewById(View view) {
        iv_bank_card_true = view.findViewById(R.id.iv_bank_card_true);
        iv_bank_card_false = view.findViewById(R.id.iv_bank_card_false);
        tv_real_next_step_2 = view.findViewById(R.id.tv_real_next_step_2);
        iv_bank_card_true.setOnClickListener(this);
        iv_bank_card_false.setOnClickListener(this);
        tv_real_next_step_2.setOnClickListener(this);
    }
}
