package com.idengyun.heartretail.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.idengyun.heartretail.Constants;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.LogisticalStatusAdapter;
import com.idengyun.heartretail.adapters.LogisticalmsgAdapter;
import com.idengyun.heartretail.beans.LogisticalBean;
import com.idengyun.heartretail.beans.LogisticalMessageBean;
import com.idengyun.heartretail.beans.LogisticalStatusBean;
import com.idengyun.heartretail.beans.OrderStatusBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:
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
    @BindView(R.id.tv_order_status_shop_name)
    TextView tvOrderStatusShopName;
    @BindView(R.id.tv_order_status_status)
    TextView tvOrderStatusStatus;
    @BindView(R.id.rlv_logistical_status)
    RecyclerView rlvLogisticalStatus;
    @BindView(R.id.lv_logistical)
    RecyclerView lvLogistical;
    private String order_id = "";
    private int shipping_status; //发货状态(0:已发货、1:运输中、2:派件中、3:已签收、4:已提货
    private List<LogisticalStatusBean> logisticalStatusBeanList;
    private LogisticalStatusAdapter logisticalStatusAdapter;
    private LogisticalmsgAdapter adapter;
    private ArrayList<LogisticalMessageBean> list = new ArrayList<>();

    public static void start(Context context, String orderId) {
        Intent starter = new Intent(context, LogisticsDetailActivity.class);
        starter.putExtra(Constants.ORDER_ID, orderId);
        context.startActivity(starter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
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
        LogisticalMessageBean bean = new LogisticalMessageBean();
        bean.processinfo = "快件已在【亦庄开发区】签收，签收人：前台，如\n" +
                "有疑问请电联：13112345678";
        bean.upload_time="2019-12-07 14:31:44";
        list.add(bean);
        list.add(bean);
        list.add(bean);
        list.add(bean);
        adapter = new LogisticalmsgAdapter(this, list, R.layout.item_lv_logisticalmessage_layout);
        lvLogistical.setAdapter(adapter);
    }

    private void initLogisticalStatus() {
        logisticalStatusBeanList = new ArrayList<>();
        logisticalStatusBeanList.add(new LogisticalStatusBean("已发货", 0));
        logisticalStatusBeanList.add(new LogisticalStatusBean("运输中", 1));
        logisticalStatusBeanList.add(new LogisticalStatusBean("派件中", 0));
        logisticalStatusBeanList.add(new LogisticalStatusBean("已签收", 0));
        logisticalStatusBeanList.add(new LogisticalStatusBean("已提货", 0));
        rlvLogisticalStatus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        logisticalStatusAdapter = new LogisticalStatusAdapter(this, (ArrayList<LogisticalStatusBean>) logisticalStatusBeanList, R.layout.item_logistical_status);
        rlvLogisticalStatus.setAdapter(logisticalStatusAdapter);
        rlvLogisticalStatus.setNestedScrollingEnabled(false);

    }

    private void initViewData(LogisticalBean data) {

    }
}
