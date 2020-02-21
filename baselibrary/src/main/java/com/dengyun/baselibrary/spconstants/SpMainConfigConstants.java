package com.dengyun.baselibrary.spconstants;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * @titile 主配置接口的sp常量
 * @desc Created by seven on 2018/5/17.
 */

public class SpMainConfigConstants {
    public static final String spFileName = "main";

    public static String versionCode() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "versionCode", "0");
    }

    public static String index() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "index", "");
    }

    public static String login() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "login", "");
    }

    public static String updateinterest() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updateinterest", "");
    }

    public static String delusercid() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "delusercid", "");
    }

    public static void saveIsExist(String isExist) {
        SharedPreferencesUtil.saveData(Utils.getApp(), spFileName, "is_exist", isExist);
    }

    public static String is_exist() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "is_exist", "");
    }

    public static String querynoapply() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querynoapply", "");
    }

    public static String queryproxy() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryproxy", "");
    }

    public static String queryAgreeContractOfficeList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryAgreeContractOfficeList", "");
    }

    public static String updateContractInfoAuditForAgree() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updateContractInfoAuditForAgree", "");
    }

    public static String queryAdvertShare() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryAdvertShare", "");
    }

    public static String PayAgreement() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "PayAgreement", "");
    }

    public static String getTitle() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getTitle", "");
    }

    public static String getQuestion() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getQuestion", "");
    }

    public static String nursingDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "nursingDetail", "");
    }

    public static String cidInsert() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "cidInsert", "");
    }

    public static String addordelCollect() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "addordelCollect", "");
    }

    public static String subscribeInsert() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "subscribeInsert", "");
    }

    public static String queryMember() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querymember", "");
    }

    public static String getGroupMessage() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getGroupMessage", "");
    }

    public static String getFriendList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getFriendList", "");
    }

    public static String getGroupUserList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getGroupUserList", "");
    }

    public static String getMemberList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getMemberList", "");
    }

    public static String getUserGroupByMap() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getUserGroupByMap", "");
    }

    public static String getUserInfo() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getUserInfo", "");
    }

    public static String getMemberInfo() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getMemberInfo", "");
    }

    public static String getCheckUserGroupRight() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "checkUserGroupRight", "");
    }

    public static String getSearchUserByTerms() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "searchUserByTerms", "");
    }

    public static String getSetFriendAlias() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "setFriendAlias", "");
    }

    public static String getQuitGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "quitGroup", "");
    }

    public static String getDismissGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "dismissGroup", "");
    }

    public static String getUploadFile() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "uploadFile", "");
    }

    public static String getUpdateGroupLogo() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updateGroupLogo", "");
    }

    public static String getCreateGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "createGroup", "");
    }

    public static String getNewFriendList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getNewFriendList", "");
    }

    public static String getAcceptFriendRequest() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "acceptFriendRequest", "");
    }

    public static String getRefreshGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "refreshGroup", "");
    }

    public static String getOrganization() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "organization", "");
    }

    public static String getQueryBookUserList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryBookUserList", "");
    }

    public static String getQueryBookOfficeList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryBookOfficeList", "");
    }

    public static String getAddTrainOrder() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "addTrainOrder", "");
    }

    public static String getCancelorder() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "cancelorder", "");
    }

    public static String getSavefavorites() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "savefavorites", "");
    }

    public static String getDelfavorites() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "delfavorites", "");
    }

    public static String getLessonDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "lessonDetail", "");
    }

    public static String getAskDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "askDetail", "");
    }

    public static String getAskCommentList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "askCommentList", "");
    }

    public static String getAskCommentInsert() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "askCommentInsert", "");
    }

    public static String getTopten() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "topten", "");
    }

    public static String getQueryBillDetailList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryBillDetailList", "");
    }
    public static String getQueryBillDetailForProxy() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryBillDetailForProxy", "");
    }

    public static String getAddInCarAgain() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "addInCarAgain", "");
    }

    public static String getCancelBill() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "cancelBill", "");
    }

    public static String getDelBill() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "delBill", "");
    }

    public static long getHttpNewsTypeListTime() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "httpNewsTypeListTime", 0L);
    }

    public static void saveHttpNewsTypeListTime(long value) {
        SharedPreferencesUtil.saveData(Utils.getApp(), spFileName, "httpNewsTypeListTime", value);
    }

    public static String getQuerynotifymsg() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querynotifymsg", "");
    }

    public static String getShareGoodsDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "shareGoodsDetail", "");
    }

    public static String getQueryGoodsDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryGoodsDetail", "");
    }

    public static String getAddOrDelUserGoods() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "addOrDelUserGoods", "");
    }

    public static String getQueryCarLen() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryCarLen", "");
    }

    public static String getQueryliveusernum() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryliveusernum", "");
    }

    public static String getRecordbrowsenumber() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "recordbrowsenumber", "");
    }

    public static String getQueryAnchordata() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryAnchordata", "");
    }

    public static String getUpdatelivestatus() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updatelivestatus", "");
    }

    public static String getLiveEnd() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "LiveEnd", "");
    }

    public static String getAssumeRole() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "assumeRole", "");
    }

    public static String getLivegiftto() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "livegiftto", "");
    }

    public static String getQuerynewcustomerlist() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querynewcustomerlist", "");
    }

    public static String getQuerytimelist() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querytimelist", "");
    }

    public static String getQuerymantimelist() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querymantimelist", "");
    }

    public static String getQueryfrequencynumberparticulars() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryfrequencynumberparticulars", "");
    }

    public static String getSendFriendRequest() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "sendFriendRequest", "");
    }

    public static String getJoinGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "joinGroup", "");
    }

    public static String getKickedGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "kickedGroup", "");
    }

    public static String getRegisterToken() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "registerToken", "");
    }

    public static String getUpdateGroupNotice() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updateGroupNotice", "");
    }

    public static String getUpdateGroupOwner() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "updateGroupOwner", "");
    }

    public static String getQueryLivecategorylist() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryLivecategorylist", "");
    }
    public static String getMakeSureCarForPersonal() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "makeSureCarForPersonal", "");
    }
    public static String getMakeSureCarForOffice() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "makeSureCarForOffice", "");
    }
    public static String getSaveBillForPersonal() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "saveBillForPersonal", "");
    }
    public static String getSaveBillForOffice() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "saveBillForOffice", "");
    }
    public static String getQueryUserRedEnvelope() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryUserRedEnvelope", "");
    }
    public static String getChooseRedEnvelope() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "chooseRedEnvelope", "");
    }
    public static String saveOrderFigure() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "saveOrderFigure", "");
    }public static String querynursesum() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querynursesum", "");
    }
    public static String getQueryPayForOrder() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryPayForOrder", "");
    }
    public static String queryVipByOfficeId() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryVipByOfficeId", "");
    }
    public static String queryVipOrderRecord() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryVipOrderRecord", "");
    }
    public static String saveVipOrderToCache() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "saveVipOrderToCache", "");
    }
    public static String proxyPayForVip() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "proxyPayForVip", "");
    }
    public static String queryArticleDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryArticleDetail", "");
    }
    public static String queryArticleDetails() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryArticleDetails", "");
    }
    public static String articleCommList() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "articleCommList", "");
    }
    public static String addFzxArticleLike() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "addFzxArticleLike", "");
    }
    public static String articleCollect() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "articleCollect", "");
    }
    public static String articleCommInsert() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "articleCommInsert", "");
    }
    public static String articleCommParise() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "articleCommParise", "");
    }
    public static String adduserpraise() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "adduserpraise", "");
    }
    public static String vipprotocol() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "vipprotocol", "");
    }
    public static String querybalance() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "querybalance", "");
    }
    public static String saveComplain() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "saveComplain", "");
    }
    public static String createTempGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "createTempGroup", "");
    }
    public static String joinTempGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "joinTempGroup", "");
    }
    public static String quitTempGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "quitTempGroup", "");
    }
    public static String dismissTempGroup() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "dismissTempGroup", "");
    }
    public static String getShareArticleDetail() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "shareArticleDetail", "");
    }
    public static String dellivenum() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "dellivenum", "");
    }
    public static String insertScoreLog() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "insertScoreLog", "");
    }
    public static String savestudytime() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "savestudytime", "");
    }
    public static String queryCourseCharge() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryCourseCharge", "");
    }
    public static String queryCourseOrderByPage() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "queryCourseOrderByPage", "");
    }
    public static String getRongyunToken() {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, "getRongyunToken", "");
    }
}
