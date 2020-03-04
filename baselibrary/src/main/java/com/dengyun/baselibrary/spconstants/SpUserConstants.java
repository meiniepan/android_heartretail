package com.dengyun.baselibrary.spconstants;

import android.text.TextUtils;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @Title 本地sp中user文件的存储常量
 * @Author: zhoubo
 * @CreateDate: 2019-08-28 14:01
 */
public class SpUserConstants {
    private static String FILE_NAME = "user";

    public static void removeAll(){
        SharedPreferencesUtil.removeAll(Utils.getApp(),FILE_NAME);
    }

    public static void saveUserBean(Object userBean){
        SharedPreferencesUtil.saveDataBean(Utils.getApp(), FILE_NAME, userBean);
    }

    public static void saveUserToken(String token){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_token", token);
    }

    public static void saveUserId(String token){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_token", token);
    }

    public static boolean isLogin(){
        String user_id = getUserId();
        return !TextUtils.isEmpty(user_id) && !user_id.equals("-1");
    }

    public static String getUserId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_id","");
    }

    public static String getUserToken() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "user_token", "");
    }

    public static int getInterest(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"interest",-1);
    }

    public static void saveInterest(int interest){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "interest", interest);
    }

    public static String getType(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"type","");
    }

    public static void saveType(String value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "type", value);
    }

    public static String appendUserId(String webUrl){
        if (!TextUtils.isEmpty(webUrl) && !webUrl.contains("user_id"))
            return webUrl + (webUrl.indexOf('?')<0  ? '?': '&')+"user_id=" + getUserId();
        return webUrl;
    }

    public static int getMtmyUserId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"mtmyUserId",-1);
    }

    public static String getUserPhoto(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_photo","");
    }
    public static void saveUserPhoto(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_photo", value);
    }

    public static void saveUserSex(int value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "sex", value);
    }
    public static int getUserSex(){
       return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "sex", -1);
    }

    public static String getNickname(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"nickname","");
    }
    public static void saveNickname(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "nickname", value);
    }
    public static String getServiceManifesto(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"service_manifesto","");
    }
    public static void saveServiceManifesto(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "service_manifesto", value);
    }
    public static String getSelfIntro(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"selfIntro","");
    }
    public static void saveSelfIntro(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "selfIntro", value);
    }
    public static String getIdCard(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"idcard","");
    }
    public static void saveIdCard(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "idcard", value);
    }
    public static int getNotifyNum(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"notifynum",-1);
    }
    public static void saveNotifyNum(int value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "notifynum", value);
    }
    public static String getSpecialties(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"specialitys","");
    }
    public static void saveSpecialties(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "specialitys", value);
    }
    public static String getModEname(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"mod_ename","");
    }
    public static void saveModEname(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "mod_ename", value);
    }
    public static String getLifeImgs(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"lifeImgs","");
    }
    public static void saveLifeImgs(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "lifeImgs", value);
    }
    public static String getPhoneNum(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"phone_num","");
    }
    public static void savePhoneNum(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "phone_num", value);
    }
    public static String getUserName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_name","");
    }
    public static void saveUserName(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_name", value);
    }
    public static int getPayType(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"pay_type",0);
    }

    /*------------------------------  用户组织机构  --------------------------------------------*/

    public static String getUserShopId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_shop_id","");
    }

    public static String getUserShopName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_shop_name","");
    }

    public static String getUserMarketId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_market_id","");
    }

    public static String getUserMarketName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_market_name","");
    }

    public static String getUserArmyId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_army_id","");
    }

    public static String getUserArmyName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_army_name","");
    }

    public static String getUserReaId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_rea_id","");
    }

    public static String getUserReaName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_rea_name","");
    }

    public static String getUserBussinessId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_bussiness_id","");
    }

    public static String getUserBussinessName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_bussiness_name","");
    }
    public static String getOfficeName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"office_name","");
    }
    public static void saveOfficeName(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "office_name", value);
    }
    public static String getOfficeId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"office_id","");
    }
    public static void saveOfficeId(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "office_id", value);
    }
    public static int getComPanyId(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"companyId",-1);
    }
    public static void saveComPanyId(int value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "companyId", value);
    }

    public static String getUserTypeName(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_type_name","");
    }

    public static String getWorkYear() {
        return SharedPreferencesUtil.getData(Utils.getApp(), FILE_NAME, "work_year", "");
    }
    public static void saveUserTypeName(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_type_name", value);
    }
    public static String getUserType(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"user_type","");
    }
    public static void saveUserType(String value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "user_type", value);
    }
    public static int getLevel(){
        return SharedPreferencesUtil.getData(Utils.getApp(),FILE_NAME,"level",0);
    }
    public static void saveLevel(int value){
        SharedPreferencesUtil.saveData(Utils.getApp(), FILE_NAME, "level", value);
    }
}
