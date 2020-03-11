package com.idengyun.maplibrary.citylist;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dengyun.baselibrary.utils.SpanUtils;
import com.idengyun.maplibrary.R;

import java.util.List;

/**
 * @Title 选择定位地址的列表适配器
 * @Author: zhoubo
 * @CreateDate: 2020-03-11 09:21
 */
public class ChooseAddrAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {
    //搜索的字符
    private String searchStr;

    public ChooseAddrAdapter(int layoutResId, @Nullable List<Tip> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        TextView tvName = helper.getView(R.id.tv_address);
        String name = item.getName();
        if (TextUtils.isEmpty(searchStr) || null == name || !name.contains(searchStr)) {
            tvName.setText(name);
        } else {
            int searchIndex = name.indexOf(searchStr);
            SpannableString spannableString = new SpannableString(name);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.gray333));
            spannableString.setSpan(colorSpan, searchIndex, searchIndex+searchStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvName.setText(spannableString);
        }
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }
}
