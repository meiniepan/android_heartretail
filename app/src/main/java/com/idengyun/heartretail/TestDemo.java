package com.idengyun.heartretail;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.config.ShareChannelConstants;
import com.dengyun.sharelibrary.utils.ShareCallbackType;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.idengyun.paylibrary.constants.PayResultCodeConstants;
import com.idengyun.paylibrary.payutil.PayCallBackListener;
import com.idengyun.paylibrary.payutil.PayUtils;

/**
 * @Title 一些功能测试的方法
 * @Desc: 不重要
 * @Author: zhoubo
 * @CreateDate: 2020-03-12 14:22
 */
public class TestDemo {
    public static void share(FragmentActivity activity) {
        ShareOptions shareOptions = ShareOptions.newBuilder(activity)
                .shareTitle("分享标题")
                .shareMsg("分享的内容")
                .shareUrl("http://www.baidu.com")
                .shareCallbackType(ShareCallbackType.BOARD_CLICK_RESULT)
                .shareChannel("1_4_0")//分享的渠道：0：新浪；1：微信；2：朋友圈；4：qq；13：短信
                .build();
        ShareUtil.shareWithPermission(shareOptions, new OnShareResult() {
            @Override
            public void onShareSuccess(ShareOptions shareOptions, String shareChannel) {
                ToastUtils.showShort("分享成功 : " + shareChannel);
            }
        });
    }

    public static void testWXPay(Activity activity,
                                 String appId,
                                 String partnerId,
                                 String prepayId,
                                 String nonceStr,
                                 String timeStamp,
                                 String sign,
                                 String packageValue) {
        PayUtils payUtils = new PayUtils();
        // 这些参数都是请求订单支付的时候后台返回的
        payUtils.startWxPay(activity, appId, partnerId, prepayId, nonceStr, timeStamp, sign, packageValue, new PayCallBackListener() {
            @Override
            public void onPayCallBack(int status) {
                //支付结果在这儿；
                if (status == PayResultCodeConstants.PAY_SUCCESS_WX) {
                    //微信支付成功
                } else if (status == PayResultCodeConstants.PAY_CANCLE_WX) {
                    //微信支付取消
                } else if (status == PayResultCodeConstants.PAY_ERR_WX) {
                    //微信支付失败
                } else if (status == PayResultCodeConstants.PAY_INVALID_WX) {
                    //微信没有安装客户端
                }
            }
        });
    }

    public static void testAliPay(Activity activity, String orderInfo) {
        PayUtils payUtils = new PayUtils();
        // orderInfo是请求后台支付的时候后台返回的
        payUtils.startAlipay(activity, orderInfo, new PayCallBackListener() {
            @Override
            public void onPayCallBack(int status) {
                //支付结果在这儿；
                if (status == PayResultCodeConstants.PAY_SUCCESS_ALIPAY) {
                    //支付宝支付成功
                } else if (status == PayResultCodeConstants.PAY_CANCLE_ALIPAY) {
                    //支付宝支付取消
                } else if (status == PayResultCodeConstants.PAY_ERR_ALIPAY) {
                    //支付宝支付失败
                } else if (status == PayResultCodeConstants.PAY_WAIT_ALIPAY) {
                    //支付宝支付确认中
                }
            }
        });
    }
}
