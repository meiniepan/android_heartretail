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
import com.dengyun.baselibrary.net.upload.UploadListBean;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.TakePhotoUtil;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.ocr.BankCardBean;
import com.dengyun.baselibrary.utils.ocr.IdCardBackBean;
import com.dengyun.baselibrary.utils.ocr.IdCardFaceBean;
import com.dengyun.baselibrary.utils.ocr.OCRUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 步骤2
 *
 * @author aLang
 */
public final class Step2Fragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_REAL_BANK_CARD_TRUE = 2003;
    private static final int REQUEST_CODE_REAL_BANK_CARD_FALSE = 2004;

    private ImageView iv_bank_card_front;
    private ImageView iv_bank_card_back;
    private View tv_real_next_step_2;

    /* 身份证和银行卡正反面本地文件路径 */
    private String pathIdCardFront;
    private String pathIdCardBack;
    private String pathBankCardFront;
    private String pathBankCardBack;

    /* 身份证和银行卡正反面对应网络URL */
    private String urlIdCardFront;
    private String urlIdCardBack;
    private String urlBankCardFront;
    private String urlBankCardBack;

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
        pathIdCardFront = intent.getStringExtra("path_id_card_front");
        pathIdCardBack = intent.getStringExtra("path_id_card_back");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        List<LocalMedia> localMediaList = PictureSelector.obtainMultipleResult(data);
        if (ListUtils.isEmpty(localMediaList)) return;

        if (REQUEST_CODE_REAL_BANK_CARD_TRUE == requestCode) {
            pathBankCardFront = TakePhotoUtil.getResultPath(localMediaList.get(0));
            ImageApi.displayImage(iv_bank_card_front.getContext(), iv_bank_card_front, pathBankCardFront);
        } else if (REQUEST_CODE_REAL_BANK_CARD_FALSE == requestCode) {
            pathBankCardBack = TakePhotoUtil.getResultPath(localMediaList.get(0));
            ImageApi.displayImage(iv_bank_card_back.getContext(), iv_bank_card_back, pathBankCardBack);
        }

        boolean enabled = !(TextUtils.isEmpty(pathBankCardFront) || TextUtils.isEmpty(pathBankCardBack));
        tv_real_next_step_2.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        if (iv_bank_card_front == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 212, 136, 1, REQUEST_CODE_REAL_BANK_CARD_TRUE);
        } else if (iv_bank_card_back == v) {
            TakePhotoUtil.takePhotoWithItem(this, true, 212, 136, 1, REQUEST_CODE_REAL_BANK_CARD_FALSE);
        } else if (tv_real_next_step_2 == v) {
            uploadFileList();
        }
    }

    /**
     * 多图片上传
     */
    private void uploadFileList() {
        ArrayList<File> files = new ArrayList<>();
        if (!TextUtils.isEmpty(pathIdCardFront)) files.add(new File(pathIdCardFront));
        if (!TextUtils.isEmpty(pathIdCardBack)) files.add(new File(pathIdCardBack));
        if (!TextUtils.isEmpty(pathBankCardFront)) files.add(new File(pathBankCardFront));
        if (!TextUtils.isEmpty(pathBankCardBack)) files.add(new File(pathBankCardBack));
        if (files.size() != 4) {
            ToastUtils.showShort("图片不够四张");
            return;
        }


        OCRUtils.recoIdCardFace(true, pathIdCardFront, new OCRUtils.OnIdCardFaceResult() {
            @Override
            public void onResult(IdCardFaceBean idCardFaceBean) {

            }
        });

        OCRUtils.recoIdCardBack(true, pathIdCardBack, new OCRUtils.OnIdCardBackResult() {
            @Override
            public void onResult(IdCardBackBean idCardBackBean) {

            }
        });

        OCRUtils.recoBankCard(true, pathBankCardFront, new OCRUtils.OnBankCardResult() {
            @Override
            public void onResult(BankCardBean bankCardBean) {

            }
        });

        OCRUtils.recoBankCard(true, pathBankCardBack, new OCRUtils.OnBankCardResult() {
            @Override
            public void onResult(BankCardBean bankCardBean) {

            }
        });


        /*NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.uploads())
                .fragment(this)
                .clazz(UploadListBean.class)
                .isShowDialog(false)
                .build();
        NetApi.upFileListData(netOption, files, new JsonCallback<UploadListBean>(netOption) {
            @Override
            public void onSuccess(Response<UploadListBean> response) {
                //上传的图片和返回的图片时一一对应的，上传四张返回也是四张，一一对应
                List<String> returnUrls = response.body().data.filePathList;
                if (!ListUtils.isEmpty(returnUrls) && returnUrls.size() == 4) {
                    urlIdCardFront = returnUrls.get(0);
                    urlIdCardBack = returnUrls.get(1);
                    urlBankCardFront = returnUrls.get(2);
                    urlBankCardBack = returnUrls.get(3);

                    startOCRRecognize();
                }
            }
        });*/
    }

    /* 开始OCR识别 */
    private void startOCRRecognize() {
        System.out.println("urlIdCardFront= " + urlIdCardFront);
        System.out.println("urlIdCardBack= " + urlIdCardBack);
        System.out.println("urlBankCardFront= " + urlBankCardFront);
        System.out.println("urlBankCardBack= " + urlBankCardBack);
        /*OCRUtils.recoIdCardFace(false,urlIdCardFront, new OCRUtils.OnIdCardFaceResult() {
            @Override
            public void onResult(IdCardFaceBean idCardFaceBean) {
                System.out.println(idCardFaceBean);
            }
        });
        OCRUtils.recoIdCardBack(false,urlIdCardBack, new OCRUtils.OnIdCardBackResult() {
            @Override
            public void onResult(IdCardBackBean idCardBackBean) {
                System.out.println(idCardBackBean);
            }
        });*/
        OCRUtils.recoBankCard(false,urlBankCardFront, new OCRUtils.OnBankCardResult() {
            @Override
            public void onResult(BankCardBean bankCardBean) {
                System.out.println(bankCardBean);
            }
        });

        // startStep3Activity();
    }

    private void startStep3Activity() {
        Bundle extras = new Bundle();
        extras.putString("url_id_card_front", urlIdCardFront);
        extras.putString("url_id_card_back", urlIdCardBack);
        extras.putString("url_bank_card_front", urlBankCardFront);
        extras.putString("url_bank_card_back", urlBankCardBack);
        HRActivity.start(getContext(), extras, Step3Fragment.class);
    }

    private void findViewById(View view) {
        iv_bank_card_front = view.findViewById(R.id.iv_bank_card_front);
        iv_bank_card_back = view.findViewById(R.id.iv_bank_card_back);
        tv_real_next_step_2 = view.findViewById(R.id.tv_real_next_step_2);
        iv_bank_card_front.setOnClickListener(this);
        iv_bank_card_back.setOnClickListener(this);
        tv_real_next_step_2.setOnClickListener(this);
    }
}
