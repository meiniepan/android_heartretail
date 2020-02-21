package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 本地的进度缓存
 * @Desc: mp4进度、pdf进度
 * @Author: zhoubo
 * @CreateDate: 2019-09-16 15:33
 */
public class SpProgressConstants {
    private static String FILE_NAME = "progress";

    public static void removeAll(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }

    /**
     * 保存pdf进度
     * @param pdfName   pdf名称（带后缀）
     * @param progressPage pdf进度页数
     */
    public static void savePdfProgress(String pdfName,int progressPage){
        SharedPreferencesUtil.saveData(Utils.getApp(),FILE_NAME,pdfName,progressPage);
    }

    /**
     * 获取本地保存的pdf阅读的进度
     * @param pdfName pdf的名称（带后缀）
     * @return pdf进度页数
     */
    public static int getPdfProgress(String pdfName){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,pdfName,0);
    }


}
