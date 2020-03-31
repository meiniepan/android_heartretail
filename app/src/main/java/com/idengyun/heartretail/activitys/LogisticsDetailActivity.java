package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.LogisticalStatusAdapter;
import com.idengyun.heartretail.adapters.LogisticalmsgAdapter;
import com.idengyun.heartretail.beans.LogisticalBean;
import com.idengyun.heartretail.beans.LogisticalStatusBean;
import com.idengyun.heartretail.beans.ShippingListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description: 物流详情
 * @date :2020/3/6 0006 9:06
 */
public class LogisticsDetailActivity extends BaseActivity {
    @BindView(R.id.iv_goods_3)
    ImageView ivGoods3;
    @BindView(R.id.tv_logistics_company_name)
    TextView tvLogisticsCompanyName;
    @BindView(R.id.tv_logistics_id)
    TextView tvLogisticsId;
    @BindView(R.id.tv_logistics_id_copy)
    TextView tvLogisticsIdCopy;
    @BindView(R.id.tv_logistics_phone)
    TextView tvLogisticsPhone;

    @BindView(R.id.rlv_logistical_status)
    RecyclerView rlvLogisticalStatus;
    @BindView(R.id.lv_logistical)
    RecyclerView lvLogistical;
    private String orderId = "";
    private int shipping_status; //发货状态(0:已发货、1:运输中、2:派件中、3:已签收、4:已提货
    private List<LogisticalStatusBean> logisticalStatusBeanList;
    private LogisticalStatusAdapter logisticalStatusAdapter;
    private LogisticalmsgAdapter adapter;
    private ArrayList<ShippingListBean.LogisticsTraces> list = new ArrayList<>();
    ShippingListBean.ShippingBean data;

    public static void start(Context context, ShippingListBean.ShippingBean bean) {
        Intent starter = new Intent(context, LogisticsDetailActivity.class);
        starter.putExtra(Constants.SHIPPING_BEAN, bean);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        data = getIntent().getParcelableExtra(Constants.SHIPPING_BEAN);
        initUI();
        initAdapter();
        initLogisticalStatus();
    }

    private void initUI() {
        ImageApi.displayImage(this, ivGoods3, data.goods.get(0).originalImg);
        tvLogisticsCompanyName.setText(data.shippingName);
        tvLogisticsId.setText(data.shippingCode);
        initAdapter();
        initLogisticalStatus();
    }

    @OnClick({R.id.tv_logistics_id_copy, R.id.tv_logistics_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_logistics_id_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvLogisticsId.getText());
                ToastUtils.showShort("复制成功");
                break;
            case R.id.tv_logistics_phone:
                break;
        }
    }

    private void initAdapter() {
        lvLogistical.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogisticalmsgAdapter(this, data.logisticsTraces, R.layout.item_lv_logisticalmessage_layout);
        lvLogistical.setAdapter(adapter);
    }

    private void initLogisticalStatus() {
        int status_1 = 0;
        int status_2 = 0;
        int status_3 = 0;
        int status_4 = 0;
        int status_5 = 0;
        String action = data.logisticsTraces.get(data.logisticsTraces.size() - 1).Action;
        if (!TextUtils.isEmpty(action)) {
            if (action.equals("1")) {
                status_1 = 1;
            } else if (action.equals("2")) {
                status_2 = 1;
            } else if (action.equals("201")) {
                status_2 = 1;
            } else if (action.equals("202")) {
                status_3 = 1;
            } else if (action.equals("301")) {
                status_4 = 1;
            } else if (action.equals("302")) {
                status_4 = 1;
            } else if (action.equals("304")) {
                status_4 = 1;
            }
        }
        logisticalStatusBeanList = new ArrayList<>();
        logisticalStatusBeanList.add(new LogisticalStatusBean("已发货", status_1));
        logisticalStatusBeanList.add(new LogisticalStatusBean("运输中", status_2));
        logisticalStatusBeanList.add(new LogisticalStatusBean("派件中", status_3));
        logisticalStatusBeanList.add(new LogisticalStatusBean("已签收", status_4));
        logisticalStatusBeanList.add(new LogisticalStatusBean("已提货", status_5));
        rlvLogisticalStatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        logisticalStatusAdapter = new LogisticalStatusAdapter(this, (ArrayList<LogisticalStatusBean>) logisticalStatusBeanList, R.layout.item_logistical_status);
        rlvLogisticalStatus.setAdapter(logisticalStatusAdapter);
        rlvLogisticalStatus.setNestedScrollingEnabled(false);

    }

}
