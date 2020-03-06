package com.dengyun.baselibrary.config;

/**
 * @titile  全局的配置类，配置debug模式
 * @desc Created by seven on 2018/4/12.
 */

public class AppConfig {

    //是否是debug模式
    //上线时需要改成false
    public static boolean isDebug = true;
    /*  isDebug可以控制：
     *   网络日志 （debug模式下不打印） -----使用wwwwww过滤日志查看
     *   Log开关 （debug模式下不打印）
     *   埋点地址 （debug模式下为测试地址）
     *   app更新 （debug模式下不更新）*/


    //是否开启神策统计
    //上线时需要改成true
    public static boolean isSAPush = true;

    //是否开启友盟统计
    //上线时需要改成true
    public static boolean isUmengPush = true;

    //是否开启activity的生命周期打印log--使用aaaaaa过滤日志查看
    //上线时需要改成false
    public static boolean isActivityLCLog = true;

    //是否开启fragment的生命周期打印log--使用ffffff过滤日志查看
    //上线时需要改成false
    public static boolean isFragmentLCLog = true;


}
