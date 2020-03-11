package com.idengyun.maplibrary.citylist;

import android.support.annotation.Nullable;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idengyun.maplibrary.R;

import java.util.List;

/**
 * @Title 选择定位地址的列表适配器
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 09:21
 */
public class ChooseAddrAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {
    public ChooseAddrAdapter(int layoutResId, @Nullable List<Tip> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.tv_address,item.getName());
    }
}
