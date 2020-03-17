package com.idengyun.heartretail.goods;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.dengyun.baselibrary.net.ImageApi;
import com.google.android.flexbox.FlexboxLayout;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.goods.helper.Cell;
import com.idengyun.heartretail.goods.helper.Converter;
import com.idengyun.heartretail.goods.helper.SKU;
import com.idengyun.heartretail.goods.helper.Section;
import com.idengyun.heartretail.model.response.GoodsDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-规格
 *
 * @author aLang
 */
public final class GoodsSpecFragment extends BaseFragment implements View.OnClickListener {

    private ImageView iv_goods_spec_logo;
    private TextView tv_goods_spec_price;
    private TextView tv_goods_spec_stock;
    private RecyclerView recycler_view;
    private TextView tv_purchase_quantity;

    private GoodsSpecAdapter mGoodsSpecAdapter;
    private final List<Section> mSectionList = new ArrayList<>();

    private Converter converter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_spec;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGoodsSpecAdapter = new GoodsSpecAdapter();
        recycler_view.setAdapter(mGoodsSpecAdapter);

        /*try {
            String s = new JSONObject(GoodsJson.json).toString();
            GoodsDetailBean detail = new Gson().fromJson(s, GoodsDetailBean.class);
            data = detail.data;
            updateUI(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        FragmentActivity activity = getActivity();
        assert activity != null;

        GDViewModel.observe(activity, this, new Observer<GoodsDetailBean.Data>() {
            @Override
            public void onChanged(@Nullable GoodsDetailBean.Data data) {
                if (data != null) updateUI(data);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (getView() == v) {
            HRActivity.hideFragment(getActivity(), getClass().getName());
            String specList = getGoodsSpecList();
            Fragment fragment = HRActivity.findFragmentByTag(getActivity(), GoodsSPUFragment.class.getName());
            if (fragment instanceof GoodsSPUFragment) {
                ((GoodsSPUFragment) fragment).setGoodsSpecList(specList);

            }
        }
    }

    @MainThread
    private void updateUI(GoodsDetailBean.Data data) {
        converter = new Converter(data);
        List<Section> sectionList = converter.getSectionList();
        SKU defaultSKU = converter.getDefaultSKU();

        mSectionList.clear();
        mSectionList.addAll(sectionList);

        /* 更新其他UI元素 */
        if (defaultSKU != null) updateUIBySku(defaultSKU);
    }

    private void updateUIBySku(@NonNull SKU sku) {
        String skuImgUrl = sku.skuImgUrl;
        String goodsPrice = sku.goodsPrice;
        int goodsCount = sku.goodsCount;
        int canBuyCount = sku.canBuyCount;

        iv_goods_spec_logo.setTag(skuImgUrl);
        ImageApi.displayImage(iv_goods_spec_logo.getContext(), iv_goods_spec_logo, skuImgUrl);
        tv_goods_spec_price.setText(goodsPrice);
        tv_goods_spec_stock.setText("" + goodsCount);
        tv_purchase_quantity.setText("" + canBuyCount);
    }

    /* 是否可以立即购买 */
    public boolean isCanBuy() {
        List<String> selectedSpecIDList = converter.getSelectedSpecIDList();
        return selectedSpecIDList != null && selectedSpecIDList.size() == mSectionList.size();
    }

    /* 确认订单界面使用 */
    public Bundle createExtras() {
        Bundle extras = new Bundle();
        extras.putString("goods_sku_img_url", iv_goods_spec_logo.getTag().toString());
        extras.putString("goods_sku_title", converter.getData().goodsTitle);
        extras.putString("goods_sku_spec_list", getGoodsSpecList());
        extras.putString("goods_sku_price", tv_goods_spec_price.getText().toString());
        extras.putString("goods_sku_count", tv_purchase_quantity.getText().toString());
        return extras;
    }

    private String getGoodsSpecList() {
        String specList = null;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (Section section : mSectionList) {
            for (Cell cell : section.cellList) {
                if (cell.checked) {
                    ++count;
                    sb.append(cell.specItemName).append("/");
                }
            }
        }
        if (count == mSectionList.size() && sb.length() > 0) {
            specList = sb.substring(0, sb.length() - 1);
        }
        return specList;
    }

    private void findViewById(@NonNull View view) {
        iv_goods_spec_logo = view.findViewById(R.id.iv_goods_spec_logo);
        tv_goods_spec_price = view.findViewById(R.id.tv_goods_spec_price);
        tv_goods_spec_stock = view.findViewById(R.id.tv_goods_spec_stock);
        recycler_view = view.findViewById(R.id.recycler_view);
        tv_purchase_quantity = view.findViewById(R.id.tv_purchase_quantity);

        view.setOnClickListener(this);
    }

    /* 商品规格适配器 */
    private class GoodsSpecAdapter extends RecyclerView.Adapter<GoodsSpecAdapter.GoodsSpecHolder> {
        private LayoutInflater inflater;

        @NonNull
        @Override
        public GoodsSpecHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_goods_spec_item, parent, false);
            return new GoodsSpecHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsSpecHolder holder, int position) {
            holder.updateUI(position);
        }

        @Override
        public int getItemCount() {
            return mSectionList.size();
        }

        class GoodsSpecHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tv_goods_spec_name;
            private FlexboxLayout flexbox_layout;

            public GoodsSpecHolder(@NonNull View itemView) {
                super(itemView);
                findViewById(itemView);
            }

            @Override
            public void onClick(View v) {
                Section tag = (Section) flexbox_layout.getTag();

                /* 一个选区遵守单选规则 */
                for (int i = 0; i < flexbox_layout.getChildCount(); i++) {
                    CheckBox child = flexbox_layout.getChildAt(i).findViewById(R.id.cb_goods_spec_value);
                    Cell c = (Cell) child.getTag();
                    if (!c.enabled) continue;
                    if (v == child) {
                        c.checked = !c.checked;
                        tag.checked = c.checked;
                    } else if (c.checked) c.checked = false;
                }

                converter.executeSpecMutex();
                notifyDataSetChanged();

                /* 更新其他UI元素 */
                List<String> selectedSpecIDList = converter.getSelectedSpecIDList();
                if (selectedSpecIDList != null && selectedSpecIDList.size() == getItemCount()) {
                    List<SKU> skuList = converter.getSKUList();
                    for (SKU sku : skuList) {
                        if (!selectedSpecIDList.retainAll(sku.specIDList)) {
                            updateUIBySku(sku);
                            break;
                        }
                    }
                }
            }

            @MainThread
            void updateUI(int position) {
                flexbox_layout.removeAllViews();

                Section section = mSectionList.get(position);
                List<Cell> cellList = section.cellList;
                flexbox_layout.setTag(section);
                tv_goods_spec_name.setText(section.skuName);
                for (int i = 0; i < cellList.size(); i++) {
                    Cell cell = cellList.get(i);
                    boolean enabled = cell.enabled;
                    boolean checked = cell.checked;
                    String specItemId = cell.specItemId;
                    String specItemName = cell.specItemName;

                    View child = inflater.inflate(R.layout.fragment_goods_spec_item_item, flexbox_layout, false);
                    flexbox_layout.addView(child);
                    CheckBox cb_goods_spec_value = child.findViewById(R.id.cb_goods_spec_value);
                    cb_goods_spec_value.setTag(cell);
                    cb_goods_spec_value.setText(specItemName);
                    cb_goods_spec_value.setEnabled(enabled);
                    cb_goods_spec_value.setChecked(checked);
                    if (enabled) cb_goods_spec_value.setOnClickListener(this);
                }
            }

            private void findViewById(@NonNull View itemView) {
                tv_goods_spec_name = itemView.findViewById(R.id.tv_goods_spec_name);
                flexbox_layout = itemView.findViewById(R.id.flexbox_layout);
            }
        }
    }
}
