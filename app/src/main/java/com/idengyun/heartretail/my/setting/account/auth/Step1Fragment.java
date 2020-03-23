package com.idengyun.heartretail.my.setting.account.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.upload.UploadBean;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.TakePhotoUtil;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import static com.idengyun.heartretail.Constants.REQUEST_CODE_PERSONAL;

/**
 * 步骤3
 *
 * @author aLang
 */
public final class Step1Fragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_REAL_ID_CARD_TRUE = 2001;
    private static final int REQUEST_CODE_REAL_ID_CARD_FALSE = 2002;

    private ImageView iv_id_card_true;
    private ImageView iv_id_card_false;
    private View tv_real_next_step_1;

    /* 身份证正反面本地文件路径 */
    private String idCardTruePath;
    private String idCardFalsePath;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_real_step_1;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
        if (ListUtils.isEmpty(localMediaList)) return;

        if (REQUEST_CODE_REAL_ID_CARD_TRUE == requestCode) {
            idCardTruePath = TakePhotoUtil.getResultPath(localMediaList.get(0));
            System.out.println(idCardTruePath);
            ImageApi.displayImage(iv_id_card_true.getContext(), iv_id_card_true, idCardTruePath);
        } else if (REQUEST_CODE_REAL_ID_CARD_FALSE == requestCode) {
            idCardFalsePath = TakePhotoUtil.getResultPath(localMediaList.get(0));
            System.out.println(idCardFalsePath);
            ImageApi.displayImage(iv_id_card_false.getContext(), iv_id_card_false, idCardFalsePath);
        }

        boolean enabled = !(TextUtils.isEmpty(idCardTruePath) || TextUtils.isEmpty(idCardFalsePath));
        tv_real_next_step_1.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        if (iv_id_card_true == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, REQUEST_CODE_REAL_ID_CARD_TRUE);
        } else if (iv_id_card_false == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, REQUEST_CODE_REAL_ID_CARD_FALSE);
        } else if (tv_real_next_step_1 == v) {
            HRActivity.start(getContext(), Step2Fragment.class);
        }
    }

    private void findViewById(View view) {
        iv_id_card_true = view.findViewById(R.id.iv_id_card_true);
        iv_id_card_false = view.findViewById(R.id.iv_id_card_false);
        tv_real_next_step_1 = view.findViewById(R.id.tv_real_next_step_1);
        iv_id_card_true.setOnClickListener(this);
        iv_id_card_false.setOnClickListener(this);
        tv_real_next_step_1.setOnClickListener(this);
    }
}
