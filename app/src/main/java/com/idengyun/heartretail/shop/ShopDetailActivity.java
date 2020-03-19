package com.idengyun.heartretail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.ListUtils;
import com.dengyun.baselibrary.utils.SizeUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.utils.ViewUtil;
import com.dengyun.baselibrary.utils.bar.StatusBarUtil;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.ShopPicListAdapter;
import com.idengyun.heartretail.beans.ShopDetailBean;
import com.idengyun.maplibrary.SingleMapActivity;
import com.idengyun.statusrecyclerviewlib.RecycleViewDivider;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Title 店铺详情页面
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 09:43
 */
public class ShopDetailActivity extends BaseActivity {

    @BindView(R.id.iv_shop_cover)
    ImageView ivShopCover;
    @BindView(R.id.toolbar_shop_detail)
    BaseToolBar toolbarShopDetail;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_time)
    TextView tvShopTime;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.tv_shop_tele)
    TextView tvShopTele;
    @BindView(R.id.rv_shop_pic)
    RecyclerView rvShopPic;
    @BindView(R.id.tv_shop_info)
    TextView tvShopInfo;

    private int shopId;
    private ShopDetailBean shopDetailBean;

    public static void start(Context context, int shopId) {
        Intent starter = new Intent(context, ShopDetailActivity.class);
        starter.putExtra("shopId", shopId);
        context.startActivity(starter);
    }

    @Override
    public void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ViewUtil.setMargins(toolbarShopDetail, 0, StatusBarUtil.getStatusBarHeight(), 0, 0);
        shopId = getIntent().getIntExtra("shopId", 0);
        requestShopDetail();
    }

    /**
     * 请求店铺详情的信息
     */
    private void requestShopDetail() {
        NetOption netOption = NetOption.newBuilder(SpMainConfigConstants.shopDetail())
                .activity(this)
                .type(new TypeToken<ApiBean<ShopDetailBean>>() {
                }.getType())
                .params("shopId", shopId)
                .build();
        NetApi.<ApiBean<ShopDetailBean>>getData(RequestMethod.GET,netOption, new JsonCallback<ApiBean<ShopDetailBean>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<ShopDetailBean>> response) {
                shopDetailBean = response.body().data;
                setViewData();
            }
        });
    }

    @OnClick(R.id.rl_shop_address)
    public void onViewClicked() {
        try {
            if (!TextUtils.isEmpty(shopDetailBean.latitude) && !TextUtils.isEmpty(shopDetailBean.longitude)){
                double latitude = Double.parseDouble(shopDetailBean.latitude);
                double longitude = Double.parseDouble(shopDetailBean.longitude);
                SingleMapActivity.start(this, latitude, longitude,shopDetailBean.shopName,shopDetailBean.shopDetailAddress );
            }
        }catch (NumberFormatException e){
            ToastUtils.showShort("经纬度解析错误");
            e.printStackTrace();
        }

    }

    private void setViewData() {
        //封面图
        if (!ListUtils.isEmpty(shopDetailBean.images)) {
            ImageApi.displayImage(this, ivShopCover, shopDetailBean.images.get(0));
        }
        //标题
        toolbarShopDetail.setTitle(shopDetailBean.shopName);
        //店名
        tvShopName.setText(shopDetailBean.shopName);
        //营业时间
        if (TextUtils.isEmpty(shopDetailBean.businessHoursStart))
            shopDetailBean.businessHoursStart = "--";
        if (TextUtils.isEmpty(shopDetailBean.businessHoursEnd))
            shopDetailBean.businessHoursEnd = "--";
        tvShopTime.setText(String.format("%1$s - %1$s", shopDetailBean.businessHoursStart, shopDetailBean.businessHoursEnd));
        //地址
        tvShopAddress.setText(shopDetailBean.shopDetailAddress);
        //电话
        tvShopTele.setText(shopDetailBean.shopTelephone);
        //描述
        tvShopInfo.setText(shopDetailBean.shopIntroduction);
        //店铺图片列表
        setShopImgList();
    }

    private void setShopImgList() {
        ShopPicListAdapter picListAdapter = new ShopPicListAdapter(R.layout.item_shop_piclist, shopDetailBean.images);
        rvShopPic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvShopPic.addItemDecoration(new RecycleViewDivider(this, SizeUtils.dp2px(4)));
        rvShopPic.setAdapter(picListAdapter);
        picListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageUtils.skipToBigPic(ShopDetailActivity.this,position,shopDetailBean.images);
            }
        });
    }
}
