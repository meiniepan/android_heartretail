package com.idengyun.heartretail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.activity.BaseActivity;
import com.dengyun.baselibrary.net.ImageApi;
import com.dengyun.baselibrary.utils.ImageUtils;
import com.dengyun.baselibrary.utils.ToastUtils;
import com.dengyun.baselibrary.widgets.imageview.RoundImageView;
import com.dengyun.baselibrary.widgets.toolbar.BaseToolBar;
import com.idengyun.heartretail.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_shop_detail)
    BaseToolBar toolbarShopDetail;
    @BindView(R.id.riv_photo1)
    RoundImageView rivPhoto1;
    @BindView(R.id.riv_photo2)
    RoundImageView rivPhoto2;
    @BindView(R.id.riv_photo3)
    RoundImageView rivPhoto3;
    @BindView(R.id.riv_photo4)
    RoundImageView rivPhoto4;
    @BindView(R.id.riv_photo5)
    RoundImageView rivPhoto5;
    @BindView(R.id.riv_photo6)
    RoundImageView rivPhoto6;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_time)
    TextView tvShopTime;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.tv_shop_tele)
    TextView tvShopTele;
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

        ImageApi.displayImage(this, rivPhoto1, tempImgUrls.get(0));
        ImageApi.displayImage(this, rivPhoto2, tempImgUrls.get(1));
        ImageApi.displayImage(this, rivPhoto3, tempImgUrls.get(2));
        ImageApi.displayImage(this, rivPhoto4, tempImgUrls.get(3));
        ImageApi.displayImage(this, rivPhoto5, tempImgUrls.get(4));
        ImageApi.displayImage(this, rivPhoto6, tempImgUrls.get(5));
    }

    @OnClick({R.id.riv_photo1, R.id.riv_photo2, R.id.riv_photo3, R.id.riv_photo4, R.id.riv_photo5, R.id.riv_photo6, R.id.iv_shop_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.riv_photo1:
                ImageUtils.skipToBigPic(this,0,tempImgUrls);
                break;
            case R.id.riv_photo2:
                ImageUtils.skipToBigPic(this,1,tempImgUrls);
                break;
            case R.id.riv_photo3:
                ImageUtils.skipToBigPic(this,2,tempImgUrls);
                break;
            case R.id.riv_photo4:
                ImageUtils.skipToBigPic(this,3,tempImgUrls);
                break;
            case R.id.riv_photo5:
                ImageUtils.skipToBigPic(this,4,tempImgUrls);
                break;
            case R.id.riv_photo6:
                ImageUtils.skipToBigPic(this,5,tempImgUrls);
                break;
            case R.id.iv_shop_navigation:
                ToastUtils.showShort("点击导航按钮");
                break;
        }
    }
}
