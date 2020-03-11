package com.idengyun.heartretail.utils;

import android.text.TextUtils;

import com.dengyun.sharelibrary.utils.ShareCallbackType;

/**
 * @Title wap端传的回调类型转为分享框架使用的回调类型
 * @Author: zhoubo
 * @CreateDate: 2019-06-29 17:37
 */
public class ShareCallbackTypeConvent {
    public static @ShareCallbackType
    int conventShareCallbackType(String blockType) {
        if (TextUtils.isEmpty(blockType)) return ShareCallbackType.DEFULT_RESULT;
        switch (blockType) {
            case "0":   //不要回调
                return ShareCallbackType.NONE;
            case "1":   //点击面板回调
                return ShareCallbackType.BOARD_CLICK_RESULT;
            case "2":   //默认友盟回调
                return ShareCallbackType.DEFULT_RESULT;
        }
        return ShareCallbackType.DEFULT_RESULT;
    }
}
