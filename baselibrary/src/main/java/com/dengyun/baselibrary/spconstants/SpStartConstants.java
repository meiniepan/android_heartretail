package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;

/**
 * @Title 本地sp中start文件的存储常量
 * @Author: zhoubo
 * @CreateDate: 2019-08-28 13:59
 */
public class SpStartConstants {
    private static String FILE_NAME = "start";

    public static void clearStartFile(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }

    public static boolean isFirstStart(){
        return  SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "isfirststart", true);
    }

    public static void saveIsFirstStart(){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "isfirststart", false);
    }

    public static String getLocalVersionName(){
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "ver_num", "");
    }

    public static void saveLocalVersionName(){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "ver_num", AppUtils.getAppVersionName());
    }

}
