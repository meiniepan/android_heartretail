package com.idengyun.heartretail.widget.webset;

import com.dengyun.baselibrary.base.activity.BaseCustomSomewebActivity;
import com.dengyun.baselibrary.utils.HandleUtil;
import com.dengyun.baselibrary.utils.StringUtils;
import com.dengyun.baselibrary.widgets.toolbar.BaseOneRightToolBar;
import com.dengyun.sharelibrary.callback.OnShareResult;
import com.dengyun.sharelibrary.utils.ShareCallbackType;
import com.dengyun.sharelibrary.utils.ShareOptions;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.idengyun.heartretail.utils.ShareCallbackTypeConvent;
import com.orhanobut.logger.Logger;

/**
 * @Title SomeWeb页面的页面控制实现类
 * @Author: zhoubo
 * @CreateDate: 2020年03月11日17:01:22
 */
public class SomeWebJsControlImpl implements JsControlPageInterface {
    private BaseCustomSomewebActivity somewebActivity;
    private String mNormalUrl;

    protected String shareTitle;//分享的标题
    protected String describe;//分享的描述
    protected String shareUrl;//分享的链接
    protected String shareChannel;//分享的渠道
    protected String imgUrl;//分享的图片url
    protected String isBlock;//分享是否要成功回调

    public SomeWebJsControlImpl(BaseCustomSomewebActivity somewebActivity, String normalUrl) {
        this.somewebActivity = somewebActivity;
        this.mNormalUrl = normalUrl;
    }

    @Override
    public void viewDetailReload() {
        Logger.d("-----js = viewDetailReload");
        if (null == somewebActivity) return;
        HandleUtil.getMainThreadHandler().post(() -> {
            somewebActivity.reloadRefresh();
        });
    }

    @Override
    public void viewDetailCanclePull() {
        Logger.d("-----js = viewDetailCanclePull");
        if (null == somewebActivity) return;
        HandleUtil.getMainThreadHandler().post(() -> {
            if (null != somewebActivity.getWebViewOptions().getRefreshLayout()) {
                somewebActivity.getWebViewOptions().getRefreshLayout().setEnableRefresh(false);
            }
        });
    }

    @Override
    public void viewShareConfig(String shareChannel, String isBlock, String title, String describe, String url, String imgUrl) {
        Logger.d("-----js = viewShareConfig");
        if (null == somewebActivity) return;
        if (null != somewebActivity.getBaseToolbar() &&
                somewebActivity.getBaseToolbar() instanceof BaseOneRightToolBar) {
            HandleUtil.getMainThreadHandler().post(() -> {
                ((BaseOneRightToolBar) somewebActivity.getBaseToolbar()).setRightButtonText("分享");
                ((BaseOneRightToolBar) somewebActivity.getBaseToolbar()).setOnRightButtonClickLinster(v -> setRightListener());
            });
        }

        //文章标题
        this.shareTitle = StringUtils.stringFromHtml(title).toString();
        //文章描述
        this.describe = StringUtils.stringFromHtml(describe).toString();
        this.shareUrl = url;
        this.shareChannel = shareChannel;
        this.imgUrl = imgUrl;
        this.isBlock = isBlock;
    }

    @Override
    public void viewShareButtonConfig(String shareChannel, String isBlock, String title, String describe, String url, String imgUrl) {
        Logger.d("-----js = viewShareButtonConfig");
        toShare(StringUtils.stringFromHtml(describe).toString(),
                StringUtils.stringFromHtml(title).toString(),
                url, imgUrl, shareChannel, ShareCallbackTypeConvent.conventShareCallbackType(isBlock));
    }

    public void setRightListener() {
        toShare(describe, shareTitle, shareUrl, imgUrl, shareChannel, ShareCallbackTypeConvent.conventShareCallbackType(isBlock));
    }

    public void toShare(String describe, String title, String url, String imgUrl, String shareChannel, @ShareCallbackType int shareCallbackType) {
        if (null == somewebActivity) return;
        HandleUtil.getMainThreadHandler().post(() -> {
            ShareOptions shareOptions = ShareOptions.newBuilder(somewebActivity)
                    .shareTitle(title)
                    .shareMsg(describe)
                    .shareUrl(url)
                    .shareImgUrl(imgUrl)
                    .shareChannel(shareChannel)
                    .shareCallbackType(shareCallbackType)
                    .build();
            ShareUtil.shareWithPermission(shareOptions, new OnShareResult() {
                @Override
                public void onShareSuccess(ShareOptions shareOptions, String shareChannel) {
                    final String js = "javascript:viewShareButtonCallback(\"" + shareChannel + "\",true)";
                    if (null != somewebActivity && null != somewebActivity.getWebView()) {
                        somewebActivity.getWebView().loadUrl(js);
                    }
                }
            });
        });

    }

}