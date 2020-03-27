package com.idengyun.routermodule;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @Title 意图跳转的路由工具类
 * @Desc: 指简单的跳转，例如跳转到登录页、通用网页之类的
 * @Author: zhoubo
 * @CreateDate: 2020-03-27 16:09
 */
public class IntentRouterUtils {
    /**
     * 跳到通用网页
     * @param url
     */
    public static void skipToSomeWeb(String url){
        ARouter.getInstance().build(RouterPathConfig.app_SomewebActivity).withString("url",url).navigation();
    }

    /**
     * 跳到首页
     */
    public static void skipToFirstActivity(){
        ARouter.getInstance().build(RouterPathConfig.app_FirstActivity).navigation();
    }
}
