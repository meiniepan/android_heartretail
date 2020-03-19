package com.dengyun.baselibrary.utils.ocr;

import android.util.Base64;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * @Title 阿里ocr识别工具类
 * @Author: zhoubo
 * @CreateDate: 2020年03月12日09:15:50
 */
public class OCRUtils {

    private static String appcode = "c88ac3ef5117409e860c7958e72f376c";

    /**
     * 识别身份证(正面)
     *
     * @param localImagePath 本地图片路径
     */
    public static void recoIdCardFace(String localImagePath, OnIdCardFaceResult onIdCardFaceResult) {
        String url = "http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json";
        NetOption netOption = NetOption.newBuilder(url)
                .headers("Authorization", "APPCODE " + appcode) //你自己的AppCode
                .params("configure", "{\"side\":\"face\"}") // 身份证正反面类型:face/back
                .params("image", conventImageBase64(localImagePath)) // 图片二进制数据的base64编码/图片url
                .clazz(IdCardFaceBean.class)
                .projectType(ProjectType.NONE)
                .build();
        NetApi.<IdCardFaceBean>getData(netOption, new JsonCallback<IdCardFaceBean>(netOption) {
            @Override
            public void onSuccess(Response<IdCardFaceBean> response) {
                IdCardFaceBean idCardFaceBean = response.body();
                if (idCardFaceBean.isSuccess()){
                    onIdCardFaceResult.onResult(idCardFaceBean);
                }else {
                    ToastUtils.showShort("识别失败");
                }
            }
        });
    }

    /**
     * 识别身份证(反面)
     *
     * @param localImagePath 本地图片路径
     */
    public static void recoIdCardBack(String localImagePath, OnIdCardBackResult onIdCardBackResult) {
        String url = "http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json";
        NetOption netOption = NetOption.newBuilder(url)
                .headers("Authorization", "APPCODE " + appcode) //你自己的AppCode
                .params("configure", "{\"side\":\"back\"}") // 身份证正反面类型:face/back
                .params("image", conventImageBase64(localImagePath)) // 图片二进制数据的base64编码/图片url
                .clazz(IdCardBackBean.class)
                .projectType(ProjectType.NONE)
                .build();
        NetApi.<IdCardBackBean>getData(netOption, new JsonCallback<IdCardBackBean>(netOption) {
            @Override
            public void onSuccess(Response<IdCardBackBean> response) {
                IdCardBackBean idCardBackBean = response.body();
                if (idCardBackBean.isSuccess()){
                    onIdCardBackResult.onResult(idCardBackBean);
                }else {
                    ToastUtils.showShort("识别失败");
                }
            }
        });
    }

    /**
     * 识别银行卡
     *
     * @param localImagePath 本地图片路径
     */
    public static void recoBankCard(String localImagePath,OnBankCardResult onBankCardResult){
        String url = "http://bankocrb.shumaidata.com/getbankocrb";
        NetOption netOption = NetOption.newBuilder(url)
                .headers("Authorization", "APPCODE " + appcode) //你自己的AppCode
                .params("image", conventImageBase64(localImagePath)) // 图片二进制数据的base64编码/图片url
                .clazz(BankCardBean.class)
                .projectType(ProjectType.NONE)
                .build();
        NetApi.<BankCardBean>getData(netOption, new JsonCallback<BankCardBean>(netOption) {
            @Override
            public void onSuccess(Response<BankCardBean> response) {
                /*200	成功	成功
                400	参数错误	参数错误
                404	请求资源不存在	请求资源不存在
                500	系统内部错误，请联系服务商	系统内部错误，请联系服务商
                501	第三方服务异常	第三方服务异常
                604	接口停用	接口停用
                1001	服务异常，会返回具体的错误原因	服务异常，会返回具体的错误原因*/
                BankCardBean bankCardBean = response.body();
                if (bankCardBean.isSuccess()){
                    onBankCardResult.onResult(bankCardBean);
                }else {
                    ToastUtils.showShort("识别失败");
                }
            }
        });
    }


    // 对图像进行base64编码
    private static String conventImageBase64(String localImagePath) {
        String imgBase64Str = "";
        try {
            File file = new File(localImagePath);
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            try {
                imgBase64Str = Base64.encodeToString(content, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return imgBase64Str;
    }

    interface OnIdCardFaceResult {
        void onResult(IdCardFaceBean idCardFaceBean);
    }

    interface OnIdCardBackResult {
        void onResult(IdCardBackBean idCardBackBean);
    }

    interface OnBankCardResult {
        void onResult(BankCardBean bankCardBean);
    }


}
