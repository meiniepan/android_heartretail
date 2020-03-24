package com.dengyun.splashmodule.config;

import android.content.Context;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

import java.util.Set;

/**
 * @Title 主配置返回的规则本地存储
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 15:38
 */
public class SpRule {

    /* xml文件名 玩法规则 邀请规则 */
    public static final String spFileName = "SpRule";
    public static final String RULE_KEY_PLAY = "playRule";
    public static final String RULE_KEY_INVITE = "invite";

    /* 获取所有ruleKey */
    public static String getAllRuleKey() {
        Set<String> keySet = Utils.getApp().getSharedPreferences(spFileName, Context.MODE_PRIVATE).getAll().keySet();
        String s = keySet.toString();
        return s.substring(1, s.length() - 1).replaceAll(" ", "");
    }
}
