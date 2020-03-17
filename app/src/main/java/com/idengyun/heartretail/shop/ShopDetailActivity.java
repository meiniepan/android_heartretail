package com.idengyun.heartretail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.idengyun.heartretail.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private String tempImgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582971338042&di=a2a7879ee98d6f9c50d5f1ccdeeccd73&imgtype=0&src=http%3A%2F%2Fdik.img.kttpdq.com%2Fpic%2F142%2F99386%2F5bdc6bc2302e4423_1440x900.jpg";

    private ArrayList<String> tempImgUrls = new ArrayList<>();

    public static void start(Context context, int shopId) {
        Intent starter = new Intent(context, ShopDetailActivity.class);
        starter.putExtra("shopId", shopId);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        int shopId = getIntent().getIntExtra("shopId", 0);
        ToastUtils.showShort("shopId 等于 = " + shopId);
        toolbarShopDetail.setTitle("可诺丹婷大族广场店");
        tempImgUrls.add(tempImgUrl);
        tempImgUrls.add(tempImgUrl);
        tempImgUrls.add(tempImgUrl);
        tempImgUrls.add(tempImgUrl);
        tempImgUrls.add(tempImgUrl);
        tempImgUrls.add(tempImgUrl);
    }

    /*@OnClick(R.id.rl_shop_address)
    public void onViewClicked() {
    }*/
}
