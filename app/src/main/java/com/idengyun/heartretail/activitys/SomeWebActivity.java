package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.activity.BaseSomewebActivity;
import com.dengyun.baselibrary.utils.webview.WebJsBean;
import com.dengyun.sharelibrary.utils.ShareUtil;
import com.idengyun.heartretail.beans.MyWebJsBean;
import com.idengyun.heartretail.widget.webset.SomeWebJsControlImpl;
import com.idengyun.routermodule.RouterPathConfig;
import com.tencent.smtt.sdk.WebView;

@Route(path = (RouterPathConfig.app_SomewebActivity))
public class SomeWebActivity extends BaseSomewebActivity {

    private String weburl;

    public static void start(Context context, String weburl, String title) {
        Intent starter = new Intent(context, SomeWebActivity.class);
        starter.putExtra("url", weburl);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }

    @Override
    public WebJsBean getWebJsBean() {
        weburl =  getIntent().getStringExtra("url");
        return new MyWebJsBean(this,new SomeWebJsControlImpl(this,weburl));
    }

    @Override
    public void setDataBeforLoadUrl() {

    }

    @Override
    public String getWebUrl() {
        return weburl;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("titls");
        getBaseToolbar().setTitle(title);
    }

    @Override
    public void setLoadUrlMethod(WebView webView, String url) {
        super.setLoadUrlMethod(webView,url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareUtil.onActivityResult(this, requestCode, resultCode, data);
    }
}
