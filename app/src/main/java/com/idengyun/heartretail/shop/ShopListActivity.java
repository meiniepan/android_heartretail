package com.idengyun.heartretail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.ShopListAdapter;
import com.idengyun.heartretail.beans.ShopListBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Title 店铺列表页面
 * @Author: zhoubo
 * @CreateDate: 2020-02-28 16:10
 */
public class ShopListActivity extends BaseActivity {

    private String tempImgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582971338042&di=a2a7879ee98d6f9c50d5f1ccdeeccd73&imgtype=0&src=http%3A%2F%2Fdik.img.kttpdq.com%2Fpic%2F142%2F99386%2F5bdc6bc2302e4423_1440x900.jpg";

    public static void start(Context context) {
        Intent starter = new Intent(context, ShopListActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.rrv_shoplist)
    RefreshStatusRecyclerView rrvShoplist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ArrayList<ShopListBean> shopListBeans = new ArrayList<>();
        ShopListBean shopListBean = new ShopListBean();
        shopListBean.setShopName("可诺丹婷大族广场店1");
        shopListBean.setDistance(1500);
        shopListBean.setShopAddress("北京市大兴区荣京东街12号");
        shopListBean.setIsUsed(1);
        shopListBean.setIsNear(0);
        shopListBean.setShopId(1);
        shopListBean.setShopPhoto(tempImgUrl);
        shopListBeans.add(shopListBean);

        ShopListBean shopListBean1 = new ShopListBean();
        shopListBean1.setShopName("可诺丹婷大族广场店2");
        shopListBean1.setDistance(700);
        shopListBean1.setShopAddress("北京市大兴区荣京东街12号");
        shopListBean1.setIsUsed(0);
        shopListBean1.setIsNear(1);
        shopListBean1.setShopId(2);
        shopListBean1.setShopPhoto(tempImgUrl);
        shopListBeans.add(shopListBean1);

        for (int i = 0; i < 10; i++) {
            ShopListBean shopListBean2 = new ShopListBean();
            shopListBean2.setShopName("可诺丹婷大族广场店"+(i+3));
            shopListBean2.setDistance(700);
            shopListBean2.setShopAddress("北京市大兴区荣京东街12号");
            shopListBean2.setIsUsed(0);
            shopListBean2.setIsNear(0);
            shopListBean2.setShopId(i+3);
            shopListBean2.setShopPhoto(tempImgUrl);
            shopListBeans.add(shopListBean2);
        }
        ShopListAdapter shopListAdapter = new ShopListAdapter(R.layout.item_shoplist,shopListBeans);
        rrvShoplist.setAdapter(shopListAdapter);
    }

}
