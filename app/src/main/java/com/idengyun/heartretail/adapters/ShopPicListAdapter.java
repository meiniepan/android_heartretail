package com.idengyun.heartretail.adapters;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.net.ImageApi;
import com.idengyun.heartretail.R;

import java.util.List;

/**
 * @Title 店铺详情的图片列表适配器
 * @Author: zhoubo
 * @CreateDate: 2020-03-17 09:43
 */
public class ShopPicListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ShopPicListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPhoto = helper.getView(R.id.riv_shop_photo_list);
        ImageApi.displayImage(mContext,ivPhoto,item);
    }
}
