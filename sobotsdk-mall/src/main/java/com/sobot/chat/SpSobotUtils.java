package com.sobot.chat;

import android.content.Context;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.idengyun.usermodule.HRUser;
import com.idengyun.usermodule.LoginActivity;

/**
 * @Title 从本地sp取的一些值
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 15:05
 */
public class SpSobotUtils {

    //是否登录
    public static boolean isLogin(){
        return HRUser.isLogin();
    }

    //获取userId
    public static String getUserId(){
//        return "3552585";
        return HRUser.getId();
    }

    //获取用户昵称
    public static String getUserNickName(){
        return HRUser.getAvatar();
    }

    //获取用户真实姓名
    public static String getUserRealName(){
        return "";
    }

    //获取用户qq号
    public static String getUserQQ(){
        return "";
    }

    //获取用户手机号
    public static String getUserPhone(){
        return HRUser.getMobile();
    }

    //获取用户备注信息
    public static String getUserRemark(){
        return "";
    }

    //获取用户头像
    public static String getUserHeadPic(){
        return HRUser.getAvatar();
    }

    //跳到登录页面
    public static void skipLogin(Context context){
        LoginActivity.start(context);
    }

    /*------------------------------获取主配置中的url------------------------------*/
    //智齿服务url
    public static String queryZCService(){
        return SharedPreferencesUtil.getData(Utils.getApp(), "main", "queryZcParam", "");
    }
}
