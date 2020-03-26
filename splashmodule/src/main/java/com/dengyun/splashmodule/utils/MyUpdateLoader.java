package com.dengyun.splashmodule.utils;

import android.support.v4.app.FragmentActivity;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.ProjectType;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.beans.CustomUpdateBean;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
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
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryVersion())
                .activity(activity)
                .type(new TypeToken<ApiBean<CustomUpdateBean>>() {
                }.getType())
                .isShowDialog(false)
                .build();
        NetApi.<ApiBean<CustomUpdateBean>>getData(RequestMethod.GET, netOption,
                new JsonCallback<ApiBean<CustomUpdateBean>>(netOption) {
                    @Override
                    public void onSuccess(Response<ApiBean<CustomUpdateBean>> response) {
                        CustomUpdateBean customUpdateBean = response.body().data;
                        UpdateBean updateBean = new UpdateBean();
                        if (null == customUpdateBean || customUpdateBean.isNewAppversion == 1) {
                            //升级的data没有信息 或者 没有新版本,  不升级
                            updateBean.setIsUpdate(0);
                            updateBean.setIsForce(0);
                        } else {
                            //设置升级字段--有新版本
                            updateBean.setIsUpdate(1);
                            ////设置升级字段--升级信息
                            updateBean.setVersionName(customUpdateBean.verNewno);
                            updateBean.setTitle(customUpdateBean.verDes);
                            updateBean.setMessage(customUpdateBean.verContent);
                            updateBean.setApkUrl(customUpdateBean.verUpdateurl);
                            updateBean.setApkSize("");
                            if (customUpdateBean.verStatus == 0) {
                                //设置升级字段--非强更
                                updateBean.setIsForce(0);
                            } else {
                                //设置升级字段--强更
                                updateBean.setIsForce(1);
                            }
                        }
                        callback.onSuccess(updateBean);
                    }

                    @Override
                    public void handleError(Response<ApiBean<CustomUpdateBean>> response) {
                        super.handleError(response);
                    }
                });
    }

    @Override
    public void exitApp() {
        AppUtils.exitApp();
    }
}
