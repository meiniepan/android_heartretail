package com.dengyun.baselibrary.widgets.tablayout;

import android.support.annotation.DrawableRes;

/**
 * @Title 自定义tablayout的tab实体
 * @Desc: 标题，选中，未选中图片
 * @Author: zhoubo
 * @CreateDate: 2018/11/14 6:43 PM
 */
public class CustomTabEntity {
    private String title;
    private @DrawableRes int tabSelectedIcon;
    private @DrawableRes int tabUnselectedIcon;
    private String tabSelectedIconUrl;
    private String tabUnselectedIconUrl;

    public CustomTabEntity(String title, int tabSelectedIcon, int tabUnselectedIcon, String tabSelectedIconUrl, String tabUnselectedIconUrl) {
        this.title = title;
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
        this.tabSelectedIconUrl = tabSelectedIconUrl;
        this.tabUnselectedIconUrl = tabUnselectedIconUrl;
    }

    public CustomTabEntity(String title, @DrawableRes int tabSelectedIcon, @DrawableRes int tabUnselectedIcon) {
        this.title = title;
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    public void setTabSelectedIcon(@DrawableRes int tabSelectedIcon) {
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }

    public void setTabUnselectedIcon(@DrawableRes int tabUnselectedIcon) {
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    public String getTabSelectedIconUrl() {
        return tabSelectedIconUrl;
    }

    public void setTabSelectedIconUrl(String tabSelectedIconUrl) {
        this.tabSelectedIconUrl = tabSelectedIconUrl;
    }

    public String getTabUnselectedIconUrl() {
        return tabUnselectedIconUrl;
    }

    public void setTabUnselectedIconUrl(String tabUnselectedIconUrl) {
        this.tabUnselectedIconUrl = tabUnselectedIconUrl;
    }
}
