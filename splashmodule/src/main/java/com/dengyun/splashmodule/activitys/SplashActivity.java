package com.dengyun.splashmodule.activitys;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.net.rx.RxSchedulers;
import com.dengyun.baselibrary.utils.GsonConvertUtil;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.SharedPreferencesUtil;
import com.dengyun.baselibrary.utils.Utils;
import com.dengyun.splashmodule.beans.MainConfig;
import com.dengyun.splashmodule.beans.MainUrlConstants;
import com.dengyun.splashmodule.beans.UrlConfigBean;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.dengyun.splashmodule.listeners.OnLoadMainUrlsListener;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import java.lang.reflect.Type;

public class SplashActivity extends BaseSplashActivity {
    @Override
    public void loadMainAndAdUrls(final OnLoadMainUrlsListener onLoadMainUrlsListener) {
        // TODO: 2020-03-02 先注掉主配置请求

        Type type = new TypeToken<ApiBean<MainConfig>>() {}.getType();
        NetOption netOption = NetOption.newBuilder(MainUrlConstants.MAINHTTP)
                .activity(this)
                .type(type)
                .build();
        NetApi.<ApiBean<MainConfig>>getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<MainConfig>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<MainConfig>> response) {
                //保存本地MainConfig配置url
                saveLocalMainConfig(response.body().data);
            }
        });
    }

    /**
     * 将主配置保存在本地sp中
     */
    private void saveLocalMainConfig(final MainConfig mainConfigApiBean){
        RxSchedulers.doOnIOThread(this, new RxSchedulers.IOTask() {
            @Override
            public void doOnIOThread() {
                if (null!=mainConfigApiBean && !ListUtils.isEmpty(mainConfigApiBean.urlConfig)){
                    saveMainBean(mainConfigApiBean);
                }
            }
        });
    }

    private void saveMainBean(MainConfig mainConfig){
        for (int i = 0; i < mainConfig.urlConfig.size(); i++) {
            UrlConfigBean urlConfigBean = mainConfig.urlConfig.get(i);
            SharedPreferencesUtil.saveData(Utils.getApp(),
                    SpMainConfigConstants.spFileName,
                    urlConfigBean.urlCode,
                    urlConfigBean.urlHead+urlConfigBean.urlTail);
        }
    }

}

