package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 其他的存储
 * @Author: zhoubo
 * @CreateDate: 2019-09-16 14:21
 */
public class SpOtherConstants {
    private static final String FILE_NAME = "share_data";

    public static void removeAll() {
        SharedPreferencesUtil.removeAll(Utils.getApp(), FILE_NAME);
    }

}
