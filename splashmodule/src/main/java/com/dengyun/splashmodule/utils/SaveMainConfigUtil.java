package com.dengyun.splashmodule.utils;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.splashmodule.beans.MainConfig;
import com.dengyun.splashmodule.beans.ProtocolConfigs;
import com.dengyun.splashmodule.beans.UrlConfigBean;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.dengyun.splashmodule.config.SpProtocol;
import com.dengyun.splashmodule.config.SpRule;

/**
 * @Title 保存主配置的工具
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 16:03
 */
public class SaveMainConfigUtil {

    public static void saveMainBean(MainConfig mainConfig) {
        //保存url
        for (int i = 0; i < mainConfig.urlConfig.size(); i++) {
            UrlConfigBean urlConfigBean = mainConfig.urlConfig.get(i);
            SharedPreferencesUtil.saveData(Utils.getApp(),
                    SpMainConfigConstants.spFileName,
                    urlConfigBean.urlCode,
                    urlConfigBean.urlHead + urlConfigBean.urlTail);
        }

        //保存协议
        for (int i = 0; i < mainConfig.protocolList.size(); i++) {
            ProtocolConfigs protocolConfigs = mainConfig.protocolList.get(i);
            SharedPreferencesUtil.saveData(Utils.getApp(), SpProtocol.spFileName, protocolConfigs.key, protocolConfigs.value);
        }

        //保存规则
        for (int i = 0; i < mainConfig.ruleList.size(); i++) {
            ProtocolConfigs protocolConfigs = mainConfig.ruleList.get(i);
            SharedPreferencesUtil.saveData(Utils.getApp(), SpRule.spFileName, protocolConfigs.key, protocolConfigs.value);
        }
    }
}
