package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 本地企业数据
 * @Author: zhoubo
 * @CreateDate: 2019-09-16 15:44
 */
public class SpEnterpriseConstants {
    private static final String FILE_NAME = "enterprise";
    private static final String ENTERPRISE_NAME = "enterprise_name";
    private static final String ENTERPRISE_LICENSE = "enterprise_license";
    private static final String ENTERPRISE_AREA = "enterprise_area";
    private static final String ENTERPRISE_ADDRESS = "enterprise_address";
    private static final String ENTERPRISE_LEGAL_PERSON = "enterprise_legal_person";
    private static final String ENTERPRISE_NUMBER = "enterprise_number";
    private static final String ENTERPRISE_PHONE = "enterprise_phone";
    private static final String ENTERPRISE_TYPE = "enterprise_type";
    private static final String ENTERPRISE_TIME = "enterprise_time";
    private static final String ENTERPRISE_PROVINCE = "enterprise_province";
    private static final String ENTERPRISE_CITY = "enterprise_city";
    private static final String ENTERPRISE_DISTRICT = "enterprise_district";
    private static final String ENTERPRISE_FACE = "enterprise_face";
    private static final String ENTERPRISE_INVERSE = "enterprise_inverse";
    private static final String ENTERPRISE_LICENSE_PHOTO = "enterprise_license_photo";

    public static void saveEnterpriseName(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_NAME, value);
    }

    public static String getEnterpriseName() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_NAME, "");
    }

    public static void saveEnterpriseLicense(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_LICENSE, value);
    }

    public static String getEnterpriseLicense() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_LICENSE, "");
    }

    public static void saveEnterpriseArea(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_AREA, value);
    }

    public static String getEnterpriseArea() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_AREA, "");
    }

    public static void saveEnterpriseAddress(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_ADDRESS, value);
    }

    public static String getEnterpriseAddress() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_ADDRESS, "");
    }

    public static void saveEnterpriseLegalPerson(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_LEGAL_PERSON, value);
    }

    public static String getEnterpriseLegalPerson() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_LEGAL_PERSON, "");
    }

    public static void saveEnterpriseNumber(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_NUMBER, value);
    }

    public static String getEnterpriseNumber() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_NUMBER, "");
    }

    public static void saveEnterprisePhone(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_PHONE, value);
    }

    public static String getEnterprisePhone() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_PHONE, "");
    }

    public static void saveEnterpriseType(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_TYPE, value);
    }

    public static String getEnterpriseType() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_TYPE, "");
    }

    public static void saveEnterpriseTime(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_TIME, value);
    }

    public static String getEnterpriseTime() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_TIME, "");
    }

    public static void saveEnterpriseProvince(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_PROVINCE, value);
    }

    public static String getEnterpriseProvince() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_PROVINCE, "");
    }

    public static void saveEnterpriseCity(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_CITY, value);
    }

    public static String getEnterpriseCity() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_CITY, "");
    }

    public static void saveEnterpriseDistrict(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_DISTRICT, value);
    }

    public static String getEnterpriseDistrict() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_DISTRICT, "");
    }

    public static void saveEnterpriseFace(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_FACE, value);
    }

    public static String getEnterpriseFace() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_FACE, "");
    }

    public static void saveEnterpriseInverse(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_INVERSE, value);
    }

    public static String getEnterpriseInverse() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_INVERSE, "");
    }

    public static void saveEnterpriseLicensePhoto(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ENTERPRISE_LICENSE_PHOTO, value);
    }

    public static String getEnterpriseLicensePhoto() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ENTERPRISE_LICENSE_PHOTO, "");
    }

    public static void removeAll(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }
}
