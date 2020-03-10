package com.dengyun.splashmodule.config;

import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;

/**
 * 请按照API文档分类和顺序加入URL
 *
 * @Title 主配置接口的sp常量
 * @Author: zhoubo
 * @CreateDate: 2020-03-04 09:18
 * @see {http://10.10.8.22:3000/project/39/interface/api}
 */
public class SpMainConfigConstants {
    public static final String spFileName = "main";

    /* 不要使用 */
    private static String getMainConfigUrl(String key) {
        return SharedPreferencesUtil.getData(Utils.getApp(), spFileName, key, "");
    }

    public static String index() {
        return getMainConfigUrl("index");
    }

    /* ================================== 公共分类 ================================== */

    /* 主配置 */
    public static String ___() {
        return getMainConfigUrl(null);
    }

    /* ================================== 协议 ================================== */

    /* 协议详情查询 */
    public static String todo4() {
        return getMainConfigUrl("todo4");
    }

    /* 协议状态更新 */
    public static String todo5() {
        return getMainConfigUrl("todo4");
    }

    /* ================================== 商品 ================================== */

    /* 商品详情 */
    public static String goodsDetail() {
        return getMainConfigUrl("goodsDetail");
    }

    /* 商品列表 */
    public static String goodsList() {
        return getMainConfigUrl("goodsList");
    }

    /* ================================== 评价 ================================== */

    /* 商品评价列表 */
    public static String evaluationList() {
        return getMainConfigUrl("evaluationList");
    }

    /* 我的评价列表 */
    public static String todo1() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo1");
    }

    /* 我的评价详情 */
    public static String todo2() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo2");
    }

    /* 评价订单商品 */
    public static String todo3() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo3");
    }

    /* ================================== 门店 ================================== */

    /* 门店列表 */
    public static String todo6() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo6");
    }

    /* 门店详情 */
    public static String todo7() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo7");
    }

    /* ================================== 订单 ================================== */

    /* 提交订单 */
    public static String todo8() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo8");
    }

    /* 支付方式查询-删除 */
    public static String todo9() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo9");
    }

    /* 查询用户代销资格 */
    public static String todo10() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo10");
    }

    /* 支付参数获取 */
    public static String todo11() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo11");
    }

    /* ================================== 推送消息 ================================== */

    /* 查询用户未读推送数量 */
    public static String todo12() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo12");
    }

    /* 更新用户阅读状态 */
    public static String todo13() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo13");
    }

    /* 推送消息列表查询 */
    public static String todo14() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo14");
    }

    /* ================================== 常见问题 ================================== */

    /* 常见问题分类 */
    public static String todo15() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo15");
    }

    /* 分类问题查询 */
    public static String todo16() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo16");
    }

    /* 问题详情 */
    public static String todo17() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo17");
    }

    /* ================================== 售后 ================================== */

    /* 申请售后 */
    public static String todo18() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo18");
    }

    /* 售后列表 */
    public static String todo19() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo19");
    }

    /* 订单售后详情 */
    public static String todo20() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo20");
    }

    /* 取消申请售后 */
    public static String todo21() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo21");
    }

    /* ================================== 设置 ================================== */

    /* 个人资料 */
    public static String queryUserId() {
        return getMainConfigUrl("queryUserId");
    }

    /* 修改昵称 */
    public static String changeNick() {
        return getMainConfigUrl("changeNick");
    }

    /* 更改头像 */
    public static String changeIcon() {
        return getMainConfigUrl("changeIcon");
    }

    /* 实名认证 */
    public static String authIdentity() {
        return getMainConfigUrl("authIdentity");
    }

    /* ================================= 登录注册 ================================= */

    /* 注册 */
    public static String register() {
        return getMainConfigUrl("register");
    }

    /* 新设备验证 */
    public static String addPhone() {
        return getMainConfigUrl("addPhone");
    }

    /* 获取验证码接口 */
    public static String getIdentifyCode() {
        return getMainConfigUrl("getIdentifyCode");
    }

    /* 修改/忘记密码 */
    public static String changePwd() {
        return getMainConfigUrl("changePwd");
    }

    /* 登录 */
    public static String login() {
        return getMainConfigUrl("login");
    }

    /* ================================= 我的订单 ================================= */

    /* 订单详情 */
    public static String queryOrderDetail() {
        return getMainConfigUrl("queryOrderDetail");
    }

    /* 订单列表 */
    public static String queryOrderList() {
        return getMainConfigUrl("queryOrderList");
    }

    /* 修改订单状态(取消订单) */
    public static String changeOrderState() {
        return getMainConfigUrl("changeOrderState");
    }

    /* ================================= 物流 ================================= */

    /* 查看物流) */
    public static String todo25() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo25");
    }

    /* 物流详情 */
    public static String todo26() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo26");
    }

    /* 运费接口 */
    public static String todo27() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo27");
    }

    /* ================================= 红包 ================================= */

    /* 查看红包 */
    public static String todo28() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo28");
    }

    /* 查看红包记录 */
    public static String todo29() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo29");
    }

    /* ================================= 余额 ================================= */

    /* 我的余额 */
    public static String todo30() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo30");
    }

    /* 查看账单记录 */
    public static String todo31() {
        // TODO: 2020/3/9
        return getMainConfigUrl("todo31");
    }

}
