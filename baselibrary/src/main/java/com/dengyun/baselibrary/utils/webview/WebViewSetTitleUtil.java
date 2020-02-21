package com.dengyun.baselibrary.utils.webview;

import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;

/**
 * Created by seven on 2017/3/31.
 */

public class WebViewSetTitleUtil {
    public static void setWVTitle(BaseToolBar toolBar, String title){
        if(null==title||title.startsWith("http://")
                ||title.startsWith("https://")
                ||title.startsWith("kehu.idengyun")){
            toolBar.setTitle("");
        }else {
            toolBar.setTitle(title);
        }
    }
}
