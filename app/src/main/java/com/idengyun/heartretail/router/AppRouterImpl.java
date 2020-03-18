package com.idengyun.heartretail.router;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.commonmodule.beans.OrderListBean;
import com.idengyun.heartretail.R;
import com.idengyun.routermodule.AppRouter;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;

/**
 * @Title app模块的路由实现类
 * @Author: zhoubo
 * @CreateDate: 2020-03-18 10:59
 */
@Route(path = "/app/AppRouter", name = "AppRouter")
public class AppRouterImpl implements AppRouter {

    @Override
    public void init(Context context) {

    }
    @Override
    public void getOrderListAsyn(FragmentActivity activity,int status, int page, OnRequestOrderListListener onRequestOrderListListener) {
        Type type = new TypeToken<ApiBean<OrderListBean>>() {
        }.getType();
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderList())
                .activity(activity)
                .params("version", AppUtils.getAppVersionName())
                .params("page", page)
                .params("userId", HRUser.getId())
                .params("flag", status)
                .params("pageSize", 10)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean<OrderListBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderListBean>> response) {
                ApiBean<OrderListBean> body = response.body();
                if (null!=onRequestOrderListListener){
                    onRequestOrderListListener.onResultOrderList(body.data);
                }
            }

            @Override
            public void onError(Response<ApiBean<OrderListBean>> response) {
                super.onError(response);
                if (null!=onRequestOrderListListener){
                    onRequestOrderListListener.onError();
                }
            }
        });
    }


}
