package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.google.android.flexbox.FlexboxLayout;
import com.idengyun.heartretail.R;

/**
 * 商品详情-服务
 *
 * @author aLang
 */
public final class GoodsServiceFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_spec;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FlexboxLayout flexbox_layout = view.findViewById(R.id.flexbox_layout);
        flexbox_layout.removeAllViews();
        String[] texts = new String[]{"20ml装", "20ml装(2020春节限定)", "50ml装", "20ml装(2020春节限定款)", "100ml装", "20ml装(2020情人节限定款)", "20ml装(2020元旦限定款)"};
        for (int i = 0; i < 7; i++) {
            View inflate = View.inflate(getContext(), R.layout.fragment_goods_spec_item_item, null);
            TextView textView = inflate.findViewById(R.id.tv_goods_spec);
            textView.setText(texts[i]);
            flexbox_layout.addView(inflate);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
