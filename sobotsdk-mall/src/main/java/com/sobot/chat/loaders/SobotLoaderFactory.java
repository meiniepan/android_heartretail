package com.sobot.chat.loaders;

/**
 * @Title 客服加载器工程类
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 16:24
 */
public class SobotLoaderFactory {
    public static SobotLoader createLoader(String fromWhere){
        SobotLoader sobotLoader = null;
        if ("mtmy".equals(fromWhere)) {//每天美耶
            sobotLoader = new MtmyLoader();
        } else if ("blln".equals(fromWhere)) {//蓓丽莲娜
            sobotLoader = new BllnLoader();
        } else if ("fzx".equals(fromWhere)) {

        } else if ("ym".equals(fromWhere)) {

        } else if ("xls".equals(fromWhere)) {
            sobotLoader = new XlsLoader();
        }
        return sobotLoader;
    }
}
