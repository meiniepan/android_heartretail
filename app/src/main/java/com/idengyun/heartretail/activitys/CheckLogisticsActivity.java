package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.usermodule.HRConst;
import com.idengyun.usermodule.beans.VerifyCodeBean;
import com.lzy.okgo.model.Response;

/**
 * @author Burning
 * @description:
 * @date :2020/3/6 0006 9:06
 */
public class CheckLogisticsActivity extends BaseActivity {
    private String orderId = "";

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, CheckLogisticsActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_logistics;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        orderId = getIntent().getStringExtra(Constants.ORDER_ID);
        getData();
    }

    private void getData() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryShippingOrderId())
                .activity(this)
                .params("orderId", orderId)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(RequestMethod.GET, netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ToastUtils.showLong("物流");
            }
        });
    }
}
