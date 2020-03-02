package com.idengyun.routermodule;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @Title 路由代理示例
 * @Author: zhoubo
 * @CreateDate: 2019-12-11 15:33
 */
public class ARouterInstance {
    private static AppRouter appRouter;
    private static UserRoleRouter userRoleRouter;

    public static AppRouter getAppRouter() {
        if (null == appRouter) {
            appRouter = ARouter.getInstance().navigation(AppRouter.class);
        }
        return appRouter;
    }

    public static UserRoleRouter getUserRoleRouter() {
        if (null == userRoleRouter) {
            userRoleRouter = ARouter.getInstance().navigation(UserRoleRouter.class);
        }
        return userRoleRouter;
    }
}
