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
import com.dengyun.splashmodule.beans.ProtocolConfigs;
import com.dengyun.splashmodule.beans.UrlConfigBean;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.dengyun.splashmodule.config.SpProtocol;
import com.dengyun.splashmodule.listeners.OnLoadMainUrlsListener;
import com.dengyun.splashmodule.utils.SaveMainConfigUtil;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import java.lang.reflect.Type;

public class SplashActivity extends BaseSplashActivity {
    @Override
    public void loadMainAndAdUrls(final OnLoadMainUrlsListener onLoadMainUrlsListener) {
        Type type = new TypeToken<ApiBean<MainConfig>>() {}.getType();
        NetOption netOption = NetOption.newBuilder(MainUrlConstants.MAINHTTP)
                .activity(this)
                .type(type)
                .build();
        NetApi.<ApiBean<MainConfig>>getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<MainConfig>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<MainConfig>> response) {
                //保存本地MainConfig配置url
                final MainConfig mainConfigApiBean = response.body().data;
                RxSchedulers.doOnIOThread(SplashActivity.this, new RxSchedulers.IOTask() {
                    @Override
                    public void doOnIOThread() {
                        if (null!=mainConfigApiBean && !ListUtils.isEmpty(mainConfigApiBean.urlConfig)){
                            SaveMainConfigUtil.saveMainBean(mainConfigApiBean);
                        }
                    }
                });
            }
        });
    }

}

