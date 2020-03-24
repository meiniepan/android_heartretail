package com.dengyun.splashmodule.config;

import android.content.Context;

import com.dengyun.baselibrary.utils.Utils;

import java.util.Set;

/**
 * @Title 主配置返回的协议本地存储
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 15:38
 */
public class SpProtocol {

    /* xml文件名 用户协议 代签协议 代销协议 */
    public static final String spFileName = "SpProtocol";
    public static String PROTOCOL_KEY_USER = "user";
    public static String PROTOCOL_KEY_ALLOGRAPH = "allograph";
    public static String PROTOCOL_KEY_PROXYSALES = "proxySales";

    /* 获取所有protocolKey */
    public static Set<String> getAllProtocolKey() {
        return Utils.getApp().getSharedPreferences(spFileName, Context.MODE_PRIVATE).getAll().keySet();
    }
}
