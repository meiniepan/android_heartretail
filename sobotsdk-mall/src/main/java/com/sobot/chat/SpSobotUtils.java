package com.sobot.chat;

import android.content.Context;

import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.idengyun.usermodule.LoginActivity;

/**
 * @Title 标题
 * @Desc: 描述
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:05
 */
public class SpSobotUtils {

    //是否登录
    public static boolean isLogin(){
        return true;
    }

    //获取userId
    public static String getUserId(){
        return "";
    }

    //获取token
    public static String getUserToken(){
        return "";
    }

    //获取用户头像
    public static String getUserHeadPic(){
        return "";
    }

    //获取版本号
    public static String getVersionName(){
        return AppUtils.getAppVersionName();
    }



    public static void skipLogin(Context context){
        LoginActivity.start(context);
    }


    /*------------------------------获取主配置中的url------------------------------*/
    //智齿服务url
    public static String queryZCService(){
        return "";
    }

    public static String getOrderListUrl(){
        return "";
    }
}
