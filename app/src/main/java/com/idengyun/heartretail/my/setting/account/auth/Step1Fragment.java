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
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.TakePhotoUtil;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 步骤3
 *
 * @author aLang
 */
public final class Step1Fragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_REAL_ID_CARD_TRUE = 2001;
    private static final int REQUEST_CODE_REAL_ID_CARD_FALSE = 2002;

    private ImageView iv_id_card_front;
    private ImageView iv_id_card_back;
    private View tv_real_next_step_1;

    /* 身份证正反面本地文件路径 */
    private String pathIdCardFront;
    private String pathIdCardBack;

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
            pathIdCardFront = TakePhotoUtil.getResultPath(localMediaList.get(0));
            System.out.println(pathIdCardFront);
            ImageApi.displayImage(iv_id_card_front.getContext(), iv_id_card_front, pathIdCardFront);
        } else if (REQUEST_CODE_REAL_ID_CARD_FALSE == requestCode) {
            pathIdCardBack = TakePhotoUtil.getResultPath(localMediaList.get(0));
            System.out.println(pathIdCardBack);
            ImageApi.displayImage(iv_id_card_back.getContext(), iv_id_card_back, pathIdCardBack);
        }

        boolean enabled = !(TextUtils.isEmpty(pathIdCardFront) || TextUtils.isEmpty(pathIdCardBack));
        tv_real_next_step_1.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        if (iv_id_card_front == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 315, 215, 1, REQUEST_CODE_REAL_ID_CARD_TRUE);
        } else if (iv_id_card_back == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 315, 215, 1, REQUEST_CODE_REAL_ID_CARD_FALSE);
        } else if (tv_real_next_step_1 == v) {
            Bundle extras = new Bundle();
            extras.putString("path_id_card_front", pathIdCardFront);
            extras.putString("path_id_card_back", pathIdCardBack);
            HRActivity.start(getContext(), extras, Step2Fragment.class);
        }
    }

    private void findViewById(View view) {
        iv_id_card_front = view.findViewById(R.id.iv_id_card_front);
        iv_id_card_back = view.findViewById(R.id.iv_id_card_back);
        tv_real_next_step_1 = view.findViewById(R.id.tv_real_next_step_1);
        iv_id_card_front.setOnClickListener(this);
        iv_id_card_back.setOnClickListener(this);
        tv_real_next_step_1.setOnClickListener(this);
    }
}
