package com.dengyun.splashmodule.utils;

import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dengyun.baselibrary.config.RouterPathConfig;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 跳转App首页/登录
 * @Author: zhoubo
 * @CreateDate: 2020-03-12 10:12
 */
public class ArouterToFirstUtil {
    public static void  skipToFirst(){
        // TODO: 2020-03-12
        String userId = SharedPreferencesUtil.getData(Utils.getApp(),"hr_user_info","id","");
        if (!TextUtils.isEmpty(userId)){
            ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).navigation();
        }else {
            ARouter.getInstance().build(RouterPathConfig.user_LoginActivity).navigation();
        }
    }
}
