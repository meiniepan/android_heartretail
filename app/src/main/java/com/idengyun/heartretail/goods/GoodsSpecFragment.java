package com.idengyun.heartretail.goods;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.BGoodsDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-规格
 *
 * @author aLang
 */
public final class GoodsSpecFragment extends BaseFragment {

    private RecyclerView recycler_view;
    private GoodsSpecAdapter goodsSpecAdapter;
    private List<BGoodsDetail.Data.GoodsSku> goodsSkuList;
    private List<BGoodsDetail.Data.GoodsSpec> goodsSpecList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_goods_spec;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goodsSpecAdapter = new GoodsSpecAdapter();
        recycler_view.setAdapter(goodsSpecAdapter);

        try {
            String s = new JSONObject(GoodsJson.json).toString();
            BGoodsDetail detail = new Gson().fromJson(s, BGoodsDetail.class);
            updateUI(detail.data.goodsSpecList, detail.data.goodsSkuList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @MainThread
    private void updateUI(List<BGoodsDetail.Data.GoodsSpec> goodsSpecList, List<BGoodsDetail.Data.GoodsSku> goodsSkuList) {
        this.goodsSpecList = goodsSpecList;
        this.goodsSkuList = goodsSkuList;

        /* 数据模型转视图模型 */
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0; i < goodsSpecList.size(); i++) {
            BGoodsDetail.Data.GoodsSpec goodsSpec = goodsSpecList.get(i);
            Section section = new Section();
            section.skuName = goodsSpec.skuName;
            List<Section.Cell> cellList = new ArrayList<>();
            for (BGoodsDetail.Data.GoodsSpec.SkuValue skuValue : goodsSpec.skuValueList) {
                Section.Cell cell = new Section.Cell();
                cell.position = i;
                cell.specItemId = String.valueOf(skuValue.specItemId);
                cell.specItemName = skuValue.specItemName;
                cell.enabled = false;
                cell.checked = false;
                cellList.add(cell);
            }
            section.cellList = cellList;
            sectionList.add(section);
        }

        // boolean nonNull = goodsSkuList != null && !goodsSkuList.isEmpty();

        /* 遍历所有Checkbox，设置每个Checkbox是否被默认启用 */
        switchEnabledStatus(sectionList, goodsSkuList);

        /* 搜索默认规格 */
        String skuCombinationCode = searchDefaultSkuCombinationCode(goodsSkuList);

        /* 遍历所有Checkbox，设置每个Checkbox是否被默认选中 */
        if (skuCombinationCode != null) {
            for (int i = 0; i < sectionList.size(); i++) {
                Section section = sectionList.get(i);
                for (Section.Cell cell : section.cellList) {
                    if (skuCombinationCode.contains(cell.specItemId)) {
                        /* 选中默认规格 */
                        cell.checked = true;

                        /* 子规格 */
                        ArrayList<BGoodsDetail.Data.GoodsSku> subSkuList = new ArrayList<>();
                        for (BGoodsDetail.Data.GoodsSku goodsSku : goodsSkuList) {
                            if (goodsSku.skuCombinationCode.contains(cell.specItemId)) {
                                subSkuList.add(goodsSku);
                            }
                        }

                        /* 子选区 */
                        ArrayList<Section> subSectionList = new ArrayList<>(sectionList);
                        subSectionList.remove(section);

                        /* 更新启用状态 */
                        switchEnabledStatus(subSectionList, subSkuList);

                        break;
                    }
                }
            }
        }

        goodsSpecAdapter.goodsSpecItems.clear();
        goodsSpecAdapter.goodsSpecItems.addAll(sectionList);
        goodsSpecAdapter.notifyDataSetChanged();
    }

    /* 切换启用状态 */
    private void switchEnabledStatus(List<Section> sectionList, List<BGoodsDetail.Data.GoodsSku> goodsSkuList) {
        for (Section section : sectionList) {
            for (Section.Cell cell : section.cellList) {
                cell.enabled = false;
                for (BGoodsDetail.Data.GoodsSku goodsSku : goodsSkuList) {
                    if (goodsSku.skuCombinationCode.contains(cell.specItemId)) {
                        cell.enabled = true;
                        break;
                    }
                }
            }
        }
    }

    /* 搜索默认规格 */
    private String searchDefaultSkuCombinationCode(List<BGoodsDetail.Data.GoodsSku> goodsSkuList) {
        for (BGoodsDetail.Data.GoodsSku goodsSku : goodsSkuList) {
            if (goodsSku.isDefault == 1) return goodsSku.skuCombinationCode;
        }
        return null;
    }

    /* 商品规格适配器 */
    private class GoodsSpecAdapter extends RecyclerView.Adapter<GoodsSpecAdapter.GoodsSpecHolder> {
        private LayoutInflater inflater;
        final List<Section> goodsSpecItems = new ArrayList<>();

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
            return goodsSpecItems.size();
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
                Section clickedSection = (Section) flexbox_layout.getTag();
                Section.Cell clickedCell = (Section.Cell) v.getTag();

                /* 一个选区遵守单选规则 */
                for (int i = 0; i < flexbox_layout.getChildCount(); i++) {
                    CheckBox child = flexbox_layout.getChildAt(i).findViewById(R.id.cb_goods_spec_value);
                    Section.Cell cell = (Section.Cell) child.getTag();
                    if (child == v) {
                        cell.checked = !cell.checked;
                    } else {
                        cell.checked = false;
                    }
                }

                if (clickedCell.checked) {
                    /* 子选区 */
                    ArrayList<Section> subSectionList = new ArrayList<>(goodsSpecItems);
                    subSectionList.remove(clickedSection);

                    /* 子规格 */
                    ArrayList<BGoodsDetail.Data.GoodsSku> subSkuList = new ArrayList<>();
                    for (BGoodsDetail.Data.GoodsSku goodsSku : goodsSkuList) {
                        if (goodsSku.skuCombinationCode.contains(clickedCell.specItemId)) {
                            subSkuList.add(goodsSku);
                        }
                    }

                    /* 更新启用状态 */
                    switchEnabledStatus(subSectionList, subSkuList);
                } else {
                    /* 子选区 */
                    ArrayList<Section> subSectionList = new ArrayList<>(goodsSpecItems);
                    subSectionList.remove(clickedSection);

                    /* 更新启用状态 */
                    switchEnabledStatus(subSectionList, goodsSkuList);
                }

                /* 更新数据 */
                notifyDataSetChanged();
            }

            @MainThread
            void updateUI(int position) {
                flexbox_layout.removeAllViews();

                Section section = goodsSpecItems.get(position);
                List<Section.Cell> cellList = section.cellList;
                flexbox_layout.setTag(section);
                tv_goods_spec_name.setText(section.skuName);
                for (int i = 0; i < cellList.size(); i++) {
                    Section.Cell cell = cellList.get(i);
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

    /* 商品规格视图模型 */
    private static class Section {
        String skuName;
        List<Cell> cellList;

        static class Cell {
            int position;
            boolean enabled;
            boolean checked;
            String specItemId;
            String specItemName;
        }
    }
}
