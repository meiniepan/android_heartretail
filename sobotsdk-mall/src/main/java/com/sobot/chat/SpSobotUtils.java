package com.sobot.chat;

import android.content.Context;

import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.idengyun.usermodule.LoginActivity;

/**
 * @Title 从本地sp取的一些值
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
        // TODO: 2020-03-18 从用户信息中获取，这儿先写死每天美耶的阿强的UserId测试用
        return "3552585";
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
        // TODO: 2020-03-18 从主配置取地址，这儿先写死每天美耶的生产客服地址测试用
        return "https://jiekou2.idengyun.com/mtmy-app/queryZCService.do";
    }

    public static String getOrderListUrl(){
        return "";
    }
}
