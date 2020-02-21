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

    public static void removeRolePosition() {
        SharedPreferencesUtil.removeData(Utils.getApp(), FILE_NAME, "role_position");
    }

    public static void removeOfficePosition() {
        SharedPreferencesUtil.removeData(Utils.getApp(), FILE_NAME, "office_position");
    }

    public static void saveRolePosition(int position) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "role_position", position);
    }

    public static int getRolePosition() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "role_position", -1);
    }

    public static void saveOfficePosition(int position) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "office_position", position);
    }

    public static int getOfficePosition() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "office_position", -1);
    }

    public static void saveModleId(int modleId) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "modleId", modleId);
    }

    public static int getModleId() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "modleId", -1);
    }

    public static int hasClearOldErrorFile() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "hasClearOldErrorFile", 0);
    }

    public static void saveClearOldErrorFile() {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "hasClearOldErrorFile", 1);
    }

}
