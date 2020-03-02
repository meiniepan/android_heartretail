package com.dengyun.splashmodule.utils;

import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.google.gson.reflect.TypeToken;
import com.idengyun.updatelib.bean.UpdateBean;
import com.idengyun.updatelib.listener.UpdateDataLoader;
import com.idengyun.updatelib.listener.UpdateDataLoaderCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;

/**
 * @Title 自定义更新信息请求加载器
 * @Author: zhoubo
 * @CreateDate: 2019-08-21 16:08
 */
public class MyUpdateLoader implements UpdateDataLoader {
    @Override
    public void requestUpdateData(FragmentActivity activity,
                                  String requestUrl,
                                  boolean isShowDialog,
                                  final UpdateDataLoaderCallback callback) {
        // TODO: 2020-03-02 临时方案先不请求更新接口
        UpdateBean updateBean = new UpdateBean();
        updateBean.setIsUpdate(0);
        updateBean.setIsForce(0);
        callback.onSuccess(updateBean);

        /*Type type = new TypeToken<ApiBean<UpdateBean>>() {}.getType();
        NetOption netOption = NetOption.newBuilder(requestUrl)
                .isShowDialog(isShowDialog)
                .projectType(ProjectType.NONE)
                .activity(activity)
                .type(type)
                .params("appFlag", "fzx")
                .params("client", "android")
                .params("versionName", AppUtils.getAppVersionName())
                .params("versionCode", String.valueOf(AppUtils.getAppVersionCode()))
                .build();
        NetApi.<ApiBean<UpdateBean>>getData(netOption, new JsonCallback<ApiBean<UpdateBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<UpdateBean>> response) {
                if (null != response && null != response.body() && null != response.body().getData()) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void handleError(Response<ApiBean<UpdateBean>> response) {
                //处理请求错误，这里去掉默认处理，重写此方法空实现
            }
        });*/
    }

    @Override
    public void exitApp() {
        AppUtils.exitApp();
    }
}
