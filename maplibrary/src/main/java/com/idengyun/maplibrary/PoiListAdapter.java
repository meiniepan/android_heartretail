package com.idengyun.maplibrary;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @Title poi数据列表适配器
 * @Author: zhoubo
 * @CreateDate: 2020-03-09 15:46
 */
public class PoiListAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    private int currentIndex = 0;
    public PoiListAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_poi_title,item.getTitle());
        helper.setText(R.id.tv_poi_addr,item.getSnippet());
        TextView tvPoiCurrent = helper.getView(R.id.tv_poi_current);
        tvPoiCurrent.setVisibility(helper.getAdapterPosition()==currentIndex? View.VISIBLE:View.GONE);
    }

    public void setCurrentIndex(int currentIndex){
        this.currentIndex = currentIndex;
    }
}
