package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @titile 主配置接口的sp常量
 * @desc Created by seven on 2018/5/17.
 */

public class SpMainConfigConstants {
    public static final String spFileName = "main";

    public static String index() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "index", "");
    }

    public static String evaluationList(){
        return "http://10.10.8.22:3000/mock/39/evaluation/query/list";
    }

}
