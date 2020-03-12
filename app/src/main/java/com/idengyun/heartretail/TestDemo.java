package com.idengyun.heartretail;

import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.config.ShareChannelConstants;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;

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
                .shareUrl("分享的链接地址")
                .shareChannel("1_4_0")//分享的渠道：0：新浪；1：微信；2：朋友圈；4：qq；13：短信
                .build();
        ShareUtil.shareWithPermission(shareOptions, new OnShareResult() {
            @Override
            public void onShareSuccess(ShareOptions shareOptions, String shareChannel) {
                ToastUtils.showShort("分享成功 : " + shareChannel);
            }
        });
    }
}
