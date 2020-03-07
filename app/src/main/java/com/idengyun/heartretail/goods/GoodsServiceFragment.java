package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.R;

/**
 * 商品详情-服务
 *
 * @author aLang
 */
public final class GoodsServiceFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_service;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() instanceof GoodsDetailFragment) {
                    ((GoodsDetailFragment) getParentFragment()).showGoodsServiceFragment(false);
                }
            }
        });
    }
}
