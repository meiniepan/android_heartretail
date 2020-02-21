package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 本地手艺人信息
 * @Author: zhoubo
 * @CreateDate: 2019-09-16 15:43
 */
public class SpCraftsmanConstants {
    private static final String FILE_NAME = "craftsman";
    private static final String CRAFTSMAN_NAME = "craftsman_name";
    private static final String CRAFTSMAN_GENDER = "craftsman_gender";
    private static final String CRAFTSMAN_AREA = "craftsman_area";
    private static final String CRAFTSMAN_ADDRESS = "craftsman_address";
    private static final String CRAFTSMAN_CARD = "craftsman_card";
    private static final String CRAFTSMAN_PHONE = "craftsman_phone";
    private static final String CRAFTSMAN_EMAIL = "craftsman_email";
    private static final String CRAFTSMAN_PROVINCE = "craftsman_province";
    private static final String CRAFTSMAN_CITY = "craftsman_city";
    private static final String CRAFTSMAN_DISTRICT = "craftsman_district";
    private static final String CRAFTSMAN_SPECIALLY = "craftsman_specially";
    private static final String CRAFTSMAN_YEAR = "craftsman_year";
    private static final String CRAFTSMAN_INCOME = "craftsman_income";
    private static final String CRAFTSMAN_FACE = "craftsman_face";
    private static final String CRAFTSMAN_INVERSE = "craftsman_inverse";
    private static final String BANK_SIZE = "bank_size";
    private static final String CRAFTSMAN_BANK_LIST = "craftsman_bank_list";
    private static final String ALIPAY_SIZE = "alipay_size";
    private static final String CRAFTSMAN_ALIPAY_LIST = "craftsman_alipay_list";
    private static final String WX_SIZE = "wx_size";
    private static final String CRAFTSMAN_WEIXIN_LIST = "craftsman_weixin_list";
    private static final String SHOW_FLAG = "show_flag";

    public static void saveCraftsmanName(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_NAME, value);
    }

    public static String getCraftsmanName() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_NAME, "");
    }

    public static void saveCraftsmanGender(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_GENDER, value);
    }

    public static String getCraftsmanGender() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_GENDER, "");
    }

    public static void saveCraftsmanArea(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_AREA, value);
    }

    public static String getCraftsmanArea() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_AREA, "");
    }

    public static void saveCraftsmanCard(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_CARD, value);
    }

    public static String getCraftsmanCard() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_CARD, "");
    }

    public static void saveCraftsmanPhone(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_PHONE, value);
    }

    public static String getCraftsmanPhone() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_PHONE, "");
    }

    public static void saveCraftsmanEmail(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_EMAIL, value);
    }

    public static String getCraftsmanEmail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_EMAIL, "");
    }

    public static void saveCraftsmanProvince(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_PROVINCE, value);
    }

    public static String getCraftsmanProvince() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_PROVINCE, "");
    }

    public static void saveCraftsmanCity(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_CITY, value);
    }

    public static String getCraftsmanCity() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_CITY, "");
    }

    public static void saveCraftsmanDistrict(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_DISTRICT, value);
    }

    public static String getCraftsmanDistrict() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_DISTRICT, "");
    }

    public static void saveCraftsmanSpecially(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_SPECIALLY, value);
    }

    public static String getCraftsmanSpecially() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_SPECIALLY, "");
    }

    public static void saveCraftsmanYear(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_YEAR, value);
    }

    public static String getCraftsmanYear() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_YEAR, "");
    }

    public static void saveCraftsmanIncome(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_INCOME, value);
    }

    public static String getCraftsmanIncome() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_INCOME, "");
    }

    public static void saveCraftsmanFace(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_FACE, value);
    }

    public static String getCraftsmanFace() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_FACE, "");
    }

    public static void saveCraftsmanInverse(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_INVERSE, value);
    }

    public static String getCraftsmanInverse() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_INVERSE, "");
    }

    public static void saveCraftsmanBankSize(int value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, BANK_SIZE, value);
    }

    public static int getCraftsmanBankSize() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, BANK_SIZE, -1);
    }

    public static void saveCraftsmanBankList(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_BANK_LIST, value);
    }

    public static String getCraftsmanBankList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_BANK_LIST, "");
    }

    public static void saveCraftsmanAlipaySize(int value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, ALIPAY_SIZE, value);
    }

    public static int getCraftsmanAlipaySize() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, ALIPAY_SIZE, -1);
    }

    public static void saveCraftsmanAlipayList(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_ALIPAY_LIST, value);
    }

    public static String getCraftsmanAlipayList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_ALIPAY_LIST, "");
    }

    public static void saveCraftsmanWxSize(int value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, WX_SIZE, value);
    }

    public static int getCraftsmanWxSize() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, WX_SIZE, -1);
    }

    public static void saveCraftsmanWxList(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_WEIXIN_LIST, value);
    }

    public static String getCraftsmanWxList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_WEIXIN_LIST, "");
    }

    public static void saveCraftsmanAddress(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, CRAFTSMAN_ADDRESS, value);
    }

    public static String getCraftsmanAddress() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, CRAFTSMAN_ADDRESS, "");
    }

    public static void saveCraftsmanShowFlag(int value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, SHOW_FLAG, value);
    }

    public static int getCraftsmanShowFlag() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, SHOW_FLAG, -1);
    }

    public static void removeAll(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }
}
