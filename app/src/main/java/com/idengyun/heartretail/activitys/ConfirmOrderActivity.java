package com.idengyun.heartretail.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.phoneapp.AppUtils;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.commonmodule.beans.BaseGoodsBean;
import com.idengyun.commonmodule.beans.OrderStatusBean;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.OderStatusGoodsListAdapter;
import com.idengyun.heartretail.beans.CommentDetailBean;
import com.idengyun.heartretail.beans.ConfirmOrderReqBean;
import com.idengyun.heartretail.beans.ConfirmOrderRspBean;
import com.idengyun.heartretail.beans.OrderPriceBean;
import com.idengyun.heartretail.shop.ShopListActivity;
import com.idengyun.heartretail.utils.DecimalFormatUtil;
import com.idengyun.heartretail.widget.RecycleViewDivider;
import com.idengyun.statusrecyclerviewlib.StatusRecyclerView;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Burning
 * @description:确认订单
 * @date :2020-02-29 11:16
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;
    @BindView(R.id.ll_shop_choose)
    LinearLayout llShopChoose;
    @BindView(R.id.ll_no_shop)
    LinearLayout llNoShop;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.tv_total_pay1)
    TextView tvTotalPay1;
    @BindView(R.id.tv_order_freight1)
    TextView tvOrderFreight1;
    @BindView(R.id.tv_order_red_packet_balance)
    TextView tvOrderRedPacketBalance;
    @BindView(R.id.cb_red_packet_switch)
    CheckBox cbRedPacketSwitch;
    @BindView(R.id.tv_should_pay1)
    TextView tvShouldPay1;
    @BindView(R.id.cb_protocol)
    CheckBox cbProtocol;
    @BindView(R.id.tv_protocol1)
    TextView tvProtocol1;
    @BindView(R.id.tv_should_pay_bottom)
    TextView tvShouldPayBottom;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.sr_order_confirm)
    StatusRecyclerView recyclerView;
    @BindView(R.id.ll_proxy_tab)
    LinearLayout llProxyTab;
    @BindView(R.id.tv_self_get)
    TextView tvSelfGet;
    @BindView(R.id.tv_proxy_sale)
    TextView tvProxySale;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.ll_proxy_sale_protocol)
    LinearLayout llProxySaleProtocol;
    @BindView(R.id.ll_protocol)
    LinearLayout llProtocol;
    @BindView(R.id.cb_protocol_proxy_sale)
    CheckBox cbProtocolProxySale;

    int orderType;
    String order_confirm_goods_img_url;
    String order_confirm_goods_title;
    String order_confirm_goods_spec_list;
    String order_confirm_goods_price;
    int order_confirm_goods_count;
    String order_confirm_goods_type;
    private OrderStatusBean.GoodsBean goodsBean;
    private String orderId;
    private boolean isProxy;

    public static void start(Context context,@NonNull Bundle extras) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putExtras(extras);

        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order_retail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initData();
        initUI();
    }

    private void initUI() {
        if (orderType==1) {//1为零售
            initRetail();
        } else {
            initWholeSale();
        }
        rlShop.setVisibility(View.GONE);
        llNoShop.setVisibility(View.VISIBLE);
        initRecycler();

    }

    private void initWholeSale() {
        llProxyTab.setVisibility(View.VISIBLE);
        initSelfGet();
    }

    private void initRetail() {
        llProxyTab.setVisibility(View.GONE);
        llProtocol.setVisibility(View.VISIBLE);
        initSelfGet();
    }

    private void initData() {
        //todo 处理上个页面传递过来的数据
        order_confirm_goods_img_url=getIntent().getStringExtra("order_confirm_goods_img_url");
        order_confirm_goods_title=getIntent().getStringExtra("order_confirm_goods_title");
        order_confirm_goods_spec_list=getIntent().getStringExtra("order_confirm_goods_spec_list");
        order_confirm_goods_price=getIntent().getStringExtra("order_confirm_goods_price");
        order_confirm_goods_count=getIntent().getIntExtra("order_confirm_goods_count",1);
        orderType=getIntent().getIntExtra("order_confirm_goods_type",1);
        /*goodsId = extras.getInt("goodsId", goodsId);
        goodsType = extras.getInt("goodsType", goodsType);
        goodsName = extras.getString("goodsName");
        goodsTitle = extras.getString("goodsTitle");

        selectedSpecIDList = extras.getStringArrayList("selectedSpecIDList");

        specIDList = extras.getStringArrayList("specIDList");
        isDefault = extras.getInt("isDefault", -1);
        goodsCount = extras.getInt("goodsCount", -1);
        goodsSkuId = extras.getInt("goodsSkuId", -1);
        goodsPrice = extras.getString("goodsPrice");
        canBuyCount = extras.getInt("canBuyCount", -1);
        skuImgUrl = extras.getString("skuImgUrl");
        wholesaleFlag = extras.getInt("wholesaleFlag", -1);*/
        getShippingPrice();
        //todo 获取推荐店铺数据
//        orderType = "2";
    }

    private void initRecycler() {
        List<OrderStatusBean.GoodsBean> goodsBeanList = new ArrayList<>();
         goodsBean = new OrderStatusBean.GoodsBean();
        goodsBean.goodsName = order_confirm_goods_title;
        goodsBean.skuItemvalue = order_confirm_goods_spec_list;
        goodsBean.originalImg = order_confirm_goods_img_url;
        goodsBean.goodsPrice = order_confirm_goods_price;
        goodsBean.goodsNum = order_confirm_goods_count;
        goodsBeanList.add(goodsBean);
        OderStatusGoodsListAdapter adapter = new OderStatusGoodsListAdapter(R.layout.item_order_status_goods, goodsBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider divider = new RecycleViewDivider(SizeUtils.dp2px(1), getResources().getColor(R.color.lineColor));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.ll_shop_choose, R.id.tv_commit, R.id.tv_self_get, R.id.tv_proxy_sale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择商家
            case R.id.ll_shop_choose:
//                ShopListActivity.start(this);
                Intent intent = new Intent(this, ShopListActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_commit:
                doCommit();
                break;
            //自提tab
            case R.id.tv_self_get:
                tvSelfGet.setBackgroundResource(R.drawable.shape_white_tab_corner_rec);
                tvProxySale.setBackground(new BitmapDrawable());
                llProtocol.setVisibility(View.VISIBLE);
                initSelfGet();
                isProxy = false;
                break;
            //代销tab
            case R.id.tv_proxy_sale:
                queryProxyQualification();

                break;
        }
    }

    private void getShippingPrice() {
        Type type = new TypeToken<ApiBean<OrderPriceBean>>() {
        }.getType();
        HashMap map = new HashMap();
        //todo 请求参数
        map.put("version", AppUtils.getAppVersionName());
        map.put("userId", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getId());
        map.put("isUsePacket", "0");
        map.put("goodsType", orderType);
        map.put("orderType", 0);
        BaseGoodsBean goodsBean = new BaseGoodsBean();
        goodsBean.goodsId = 0;
        goodsBean.goodsSkuId = 0;
        goodsBean.goodsNum = order_confirm_goods_count;
        goodsBean.goodsType = orderType+"";
        List<BaseGoodsBean> list = new ArrayList<>();
        list.add(goodsBean);
        map.put("goodsSkuEntityList", list);
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.queryOrderPrice())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean<OrderPriceBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<OrderPriceBean>> response) {
                OrderPriceBean data = response.body().data;
                float totalPay = Float.parseFloat(data.goodsTotalPrice);
                float f2 = Float.parseFloat(data.shipping);
                float f3 = Float.parseFloat(data.payAmount);
                String stotalPay = "¥"+DecimalFormatUtil.getFormatDecimal("0.00",totalPay);
                String s2 = "¥"+DecimalFormatUtil.getFormatDecimal("0.00",f2);
                String s3 = "¥"+DecimalFormatUtil.getFormatDecimal("0.00",f3);
                tvTotalPay1.setText(stotalPay);
                tvShouldPay1.setText(s3);
                tvShouldPayBottom.setText(s3);
                tvOrderFreight1.setText(s2);
            }

            @Override
            public void onError(Response<ApiBean<OrderPriceBean>> response) {
                super.onError(response);
            }
        });
    }

    private void queryProxyQualification() {
        HashMap map = new HashMap();
        //todo 请求参数
        map.put("version", AppUtils.getAppVersionName());
        map.put("userId", TextUtils.isEmpty(HRUser.getId()) ? "1" : HRUser.getId());
        map.put("skuIds", "1");
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.checkProxyState())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .clazz(ApiBean.class)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean> response) {
                ApiBean<ConfirmOrderRspBean> body = response.body();
                ToastUtils.showShort("有代销资格");
                isProxy = true;
                tvProxySale.setBackgroundResource(R.drawable.shape_white_tab_corner_rec);
                tvSelfGet.setBackground(new BitmapDrawable());
                llProtocol.setVisibility(View.GONE);
                initProxySale();
            }

            @Override
            public void onError(Response<ApiBean> response) {
                super.onError(response);
            }
        });
    }

    private void initProxySale() {
        hideAllFrames();
        llProxySaleProtocol.setVisibility(View.VISIBLE);
        ivNext.setVisibility(View.GONE);
        llShopChoose.setEnabled(false);
    }

    private void initSelfGet() {
        hideAllFrames();
        ivNext.setVisibility(View.VISIBLE);
        llNoShop.setVisibility(View.VISIBLE);
        llShopChoose.setEnabled(true);
    }

    private void hideAllFrames() {
        llNoShop.setVisibility(View.GONE);
        rlShop.setVisibility(View.GONE);
        llProxySaleProtocol.setVisibility(View.GONE);

    }

    private void doCommit() {
        if (isProxy) {
            if (!cbProtocolProxySale.isChecked()) {
                ToastUtils.showShort("请先同意协议");
                return;
            }
        } else {
            if (!cbProtocol.isChecked()) {
                ToastUtils.showShort("请先同意协议");
                return;
            }
        }

        Type type = new TypeToken<ApiBean<ConfirmOrderRspBean>>() {
        }.getType();
        HashMap map = new HashMap();
        //todo 请求参数
        map.put("version", AppUtils.getAppVersionName());
        map.put("userId", HRUser.getId());
        map.put("userName", HRUser.getNickname());
        map.put("mobile", HRUser.getMobile());
        map.put("consignee", "");
        map.put("shopId", 0);
        map.put("shopMobile", "1");
        map.put("shopAddress",  "1");
        map.put("userRemark", "");
        map.put("isUsePacket", "0");
        map.put("orderType", 0);
        map.put("signProtocolId", 1);
        map.put("saleProxyProtocolId", 1);
        map.put("goodsType", 0);
        ConfirmOrderReqBean bean = new ConfirmOrderReqBean();
        //todo 取值
        bean.goodsId = 1;
        bean.goodsNum = 1;
        bean.goodsSkuId = 1;
        bean.goodsType = "1";
        map.put("goodsSkuEntityList",bean);
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.commitOrder())
                .activity(this)
                .params(map)
                .isShowDialog(true)
                .type(type)
                .build();

        NetApi.getData(netOption, new JsonCallback<ApiBean<ConfirmOrderRspBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<ConfirmOrderRspBean>> response) {
                ApiBean<ConfirmOrderRspBean> body = response.body();
                ToastUtils.showShort("提交成功");
                ChoosePayModeActivity.start(getContext(), body.data.orderId);
            }

            @Override
            public void onError(Response<ApiBean<ConfirmOrderRspBean>> response) {
                super.onError(response);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String name = data.getStringExtra("name");
            String address = data.getStringExtra("address");
            float distance = data.getFloatExtra("dis",0);
            hideAllFrames();
            rlShop.setVisibility(View.VISIBLE);
            tvTag3.setText(name);
            tvAddress.setText(address);
            String distanceStr = "";
            if (distance>=1000){
                distanceStr = "距离"+ DecimalFormatUtil.getFormatDecimal("0.0",distance/1000d)+"km";
            }else if (distance>1){
                distanceStr = "距离"+distance+"m";
            }else {
                distanceStr = "距离1m";
            }
            tvDistance.setText(distanceStr);
        }
    }
}
