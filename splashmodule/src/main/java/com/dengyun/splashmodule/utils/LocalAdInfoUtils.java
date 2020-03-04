package com.dengyun.splashmodule.utils;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;

/**
 * @Title 本地广告信息的工具类
 * @Author: zhoubo
 * @CreateDate: 2019-07-29 16:58
 */
public class LocalAdInfoUtils {
    public static void saveAdInfo(String adPic,String adSkipUrl){
        SharedPreferencesUtil.saveData(Utils.getApp(), SpMainConfigConstants.spFileName,"img_url",adPic);
        SharedPreferencesUtil.saveData(Utils.getApp(), SpMainConfigConstants.spFileName,"redirect_url",adSkipUrl);
    }

    public static void removeAdInfo(){
        SharedPreferencesUtil.removeData(Utils.getApp(), SpMainConfigConstants.spFileName, "img_url");
        SharedPreferencesUtil.removeData(Utils.getApp(), SpMainConfigConstants.spFileName, "redirect_url");
    }

    public static String getAdPic(){
        return SharedPreferencesUtil.getData(Utils.getApp(), SpMainConfigConstants.spFileName,"img_url","");
    }

    public static String getAdSkipUrl(){
        return SharedPreferencesUtil.getData(Utils.getApp(), SpMainConfigConstants.spFileName,"redirect_url","");
    }
}
