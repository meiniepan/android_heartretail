package com.dengyun.baselibrary.utils.ocr;

import android.util.Base64;

import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.ProjectType;
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

    private static String appcode = "";

    /**
     * 识别身份证(正面)
     *
     * @param localImagePath 本地图片路径
     */
    public static void recognitionIdCardFace(String localImagePath, OnIdCardFaceResult onIdCardFaceResult) {
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
                onIdCardFaceResult.onResult(response.body());
            }
        });
    }

    /**
     * 识别身份证(反面)
     *
     * @param localImagePath 本地图片路径
     */
    public static void recognitionIdCardBack(String localImagePath, OnIdCardBackResult onIdCardBackResult) {
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
                onIdCardBackResult.onResult(response.body());
            }
        });
    }


    /**
     * 正面返回结果：
     * {
     * "address"    : "浙江省杭州市余杭区文一西路969号",   #地址信息
     * "config_str" : "{\\\"side\\\":\\\"face\\\"}",    #配置信息，同输入configure
     * "face_rect":{       #人脸位置
     * "angle": -90,   #angle表示矩形顺时针旋转的度数
     * "center":{      #center表示人脸矩形中心坐标
     * "x" : 952,
     * "y" : 325.5
     * },
     * "size":{        #size表示人脸矩形长宽
     * "height":181.99,
     * "width":164.99
     * }
     * },
     * "card_region":[  #身份证区域位置，四个顶点表示，顺序是逆时针(左上、左下、右下、右上)
     * {"x":165,"y":657},
     * {"x":534,"y":658},
     * {"x":535,"y":31},
     * {"x":165,"y":30}
     * ],
     * "face_rect_vertices":[  #人脸位置，四个顶点表示
     * {
     * "x":1024.6600341796875,
     * "y":336.629638671875
     * },
     * {
     * "x":906.66107177734375,
     * "y":336.14801025390625
     * },
     * {
     * "x":907.1590576171875,
     * "y":214.1490478515625
     * },
     * {
     * "x":1025.157958984375,
     * "y":214.63067626953125
     * }
     * ],
     * "name" : "张三",                 #姓名
     * "nationality": "汉"，            #民族
     * "num" : "1234567890",            #身份证号
     * "sex" : "男",                    #性别
     * "birth" : "20000101",            #出生日期
     * "success" : true                 #识别结果，true表示成功，false表示失败
     * }
     * <p>
     * <p>
     * 反面返回结果:
     * {
     * "config_str" : "{\\\"side\\\":\\\"back\\\"}",  #配置信息，同输入configure
     * "start_date" : "19700101",       #有效期起始时间
     * "end_date" : "19800101",         #有效期结束时间
     * "issue" : "杭州市公安局",         #签发机关
     * "success" : true                 #识别结果，true表示成功，false表示失败
     * }
     */


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


}
