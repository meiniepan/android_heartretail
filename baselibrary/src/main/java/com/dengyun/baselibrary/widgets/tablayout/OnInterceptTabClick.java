package com.dengyun.baselibrary.widgets.tablayout;

/**
 * @Title 拦截tab点击
 * @Author: zhoubo
 * @CreateDate: 2019/3/28 6:17 PM
 */
public abstract class OnInterceptTabClick {
    public abstract boolean onInterceptTab(int position,boolean isCurrentPosition);
}
