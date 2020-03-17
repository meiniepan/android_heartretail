package com.idengyun.heartretail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dengyun.baselibrary.base.ApiBean;
import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.config.GlobalProperty;
import com.dengyun.baselibrary.net.NetApi;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.net.callback.JsonCallback;
import com.dengyun.baselibrary.net.constants.RequestMethod;
import com.dengyun.splashmodule.config.SpMainConfigConstants;
import com.google.gson.reflect.TypeToken;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.adapters.ShopListAdapter;
import com.idengyun.heartretail.beans.ShopListBean;
import com.idengyun.statusrecyclerviewlib.RefreshStatusRecyclerView;
import com.idengyun.usermodule.HRUser;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Title 店铺列表页面
 * @Author: zhoubo
 * @CreateDate: 2020-02-28 16:10
 */
public class ShopListActivity extends BaseActivity {
    public final static int TYPE_QUERY_WITH_LOCATION = 0;//根据定位查询
    public final static int TYPE_QUERY_WITH_NAME = 1;//根据名称查询


    ArrayList<ShopListBean> shopListBeans = new ArrayList<>();
    private ShopListAdapter shopListAdapter;

    /**
     * 通过定位查询列表
     */
    public static void startWithLocation(Context context) {
        Intent starter = new Intent(context, ShopListActivity.class);
        starter.putExtra("queryType", TYPE_QUERY_WITH_LOCATION);
        context.startActivity(starter);
    }

    /**
     * 通过店铺名查询列表
     */
    public static void startWithName(Context context, String shopName) {
        Intent starter = new Intent(context, ShopListActivity.class);
        starter.putExtra("queryType", TYPE_QUERY_WITH_NAME);
        starter.putExtra("shopName", shopName);
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

        shopListAdapter = new ShopListAdapter(R.layout.item_shoplist, shopListBeans);
        rrvShoplist.setLayoutManager(new LinearLayoutManager(this));
        rrvShoplist.setAdapter(shopListAdapter);
        shopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent i = new Intent();
                i.putExtra("result", shopListBeans.get(position).getShopName());
                setResult(3, i);
                finish();
            }
        });

        requestShopListData();
    }

    /**
     * 请求门店列表数据
     */
    private void requestShopListData() {
        int queryType = getIntent().getIntExtra("queryType", TYPE_QUERY_WITH_LOCATION);

        NetOption.Builder builder = NetOption.newBuilder(SpMainConfigConstants.shopList())
                .activity(this)
                .type(new TypeToken<ApiBean<List<ShopListBean>>>() {
                }.getType())
                .params("userId", HRUser.getId());
        //通过定位查找店铺列表
        if (queryType == TYPE_QUERY_WITH_LOCATION) {
            builder.params("latitude", GlobalProperty.getInstance().getLatitude())
                    .params("longitude", GlobalProperty.getInstance().getLongitude());
        } else if (queryType == TYPE_QUERY_WITH_NAME) {
            //通过店铺名称查找店铺列表
            String shopName = getIntent().getStringExtra("shopName");
            builder.params("shopName",shopName);
        }
        NetOption netOption = builder.build();
        NetApi.<ApiBean<List<ShopListBean>>>getData(RequestMethod.GET, netOption,
                new JsonCallback<ApiBean<List<ShopListBean>>>(netOption) {
            @Override
            public void onSuccess(Response<ApiBean<List<ShopListBean>>> response) {
                shopListBeans.clear();
                shopListBeans.addAll(response.body().getData());
                shopListAdapter.notifyDataSetChanged();

                /*注意：如果要加刷新、分页，刷新使用RRV的刷新*/
            }
        });

    }

}
