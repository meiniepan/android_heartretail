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
import android.widget.ImageView;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.idengyun.heartretail.R;
import com.idengyun.heartretail.model.response.GoodsDetailBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品详情-规格
 *
 * @author aLang
 */
public final class GoodsSpecFragment extends BaseFragment {

    private ImageView iv_goods_spec_logo;
    private TextView tv_goods_spec_price;
    private TextView tv_goods_spec_stock;
    private RecyclerView recycler_view;

    private GoodsSpecAdapter mGoodsSpecAdapter;
    private final List<Section> mSectionList = new ArrayList<>();
    private GoodsDetailBean model;
    private List<GoodsDetailBean.Data.GoodsSku> mGoodsSkuList;
    private List<GoodsDetailBean.Data.GoodsSpec> mGoodsSpecList;

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

        try {
            String s = new JSONObject(GoodsJson.json).toString();
            GoodsDetailBean detail = new Gson().fromJson(s, GoodsDetailBean.class);
            this.mGoodsSpecList = detail.data.goodsSpecList;
            this.mGoodsSkuList = detail.data.goodsSkuList;
            model = detail;
            updateUI();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragment() instanceof GoodsDetailFragment) {
                    ((GoodsDetailFragment) getParentFragment()).showGoodsSpecFragment(false);
                }
            }
        });
    }

    private void findViewById(@NonNull View view) {
        iv_goods_spec_logo = view.findViewById(R.id.iv_goods_spec_logo);
        tv_goods_spec_price = view.findViewById(R.id.tv_goods_spec_price);
        tv_goods_spec_stock = view.findViewById(R.id.tv_goods_spec_stock);
        recycler_view = view.findViewById(R.id.recycler_view);
    }

    @MainThread
    private void updateUI() {
        GoodsDetailBean.Data data = model.data;
        iv_goods_spec_logo.setImageDrawable(null);
        tv_goods_spec_price.setText("110");
        tv_goods_spec_stock.setText("1024");

        List<GoodsDetailBean.Data.GoodsSpec> goodsSpecList = mGoodsSpecList;
        List<GoodsDetailBean.Data.GoodsSku> goodsSkuList = mGoodsSkuList;

        /* DataModel to ViewModel */
        List<Section> sectionList = new ArrayList<>();
        for (int i = 0; i < goodsSpecList.size(); i++) {
            GoodsDetailBean.Data.GoodsSpec goodsSpec = goodsSpecList.get(i);
            Section section = new Section();
            section.skuName = goodsSpec.skuName;
            List<Section.Cell> cellList = new ArrayList<>();
            for (GoodsDetailBean.Data.GoodsSpec.SkuValue skuValue : goodsSpec.skuValueList) {
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
                        break;
                    }
                }
            }
        }

        /* 数据相关操作 */
        mSectionList.clear();
        mSectionList.addAll(sectionList);
        executeSpecMutex();

        mGoodsSpecAdapter.notifyDataSetChanged();
    }

    /* 搜索默认规格 */
    private String searchDefaultSkuCombinationCode(List<GoodsDetailBean.Data.GoodsSku> goodsSkuList) {
        for (GoodsDetailBean.Data.GoodsSku goodsSku : goodsSkuList) {
            if (goodsSku.isDefault == 1) return goodsSku.skuCombinationCode;
        }
        return null;
    }

    /* 搜索被选中选框 */
    private List<String> searchCheckedIdList() {
        ArrayList<String> checkedSpecItemIdList = new ArrayList<>();

        for (Section section : mSectionList) {
            for (Section.Cell cell : section.cellList) {
                if (cell.checked) {
                    checkedSpecItemIdList.add(cell.specItemId);
                    break;
                }
            }
        }

        return checkedSpecItemIdList;
    }

    /* 筛选有效的规格 */
    private List<GoodsDetailBean.Data.GoodsSku> searchValidSkuList() {
        ArrayList<GoodsDetailBean.Data.GoodsSku> validSkuList = new ArrayList<>();

        List<String> checkedIdList = searchCheckedIdList();
        for (GoodsDetailBean.Data.GoodsSku goodsSku : mGoodsSkuList) {
            if (Arrays.asList(goodsSku.skuCombinationCode.split("_")).containsAll(checkedIdList)) {
                validSkuList.add(goodsSku);
            }
        }

        /* 打印日志 */
        System.out.println(checkedIdList.toString());

        return validSkuList;
    }

    /* 切换启用状态 */
    private void switchEnabledStatus(List<Section> sectionList, List<GoodsDetailBean.Data.GoodsSku> goodsSkuList) {
        for (Section section : sectionList) {
            for (Section.Cell cell : section.cellList) {
                cell.enabled = false;
                for (GoodsDetailBean.Data.GoodsSku goodsSku : goodsSkuList) {
                    if (Arrays.asList(goodsSku.skuCombinationCode.split("_")).contains(cell.specItemId)) {
                        cell.enabled = true;
                        break;
                    }
                }
            }
        }
    }

    /* 执行规格间互斥性 高复杂度逻辑操作 修改请慎重 */
    private void executeSpecMutex() {
        /* 筛选有效的规格 */
        List<GoodsDetailBean.Data.GoodsSku> validSkuList = searchValidSkuList();

        /* 打印日志 */
        StringBuilder sb = new StringBuilder();
        for (GoodsDetailBean.Data.GoodsSku gs : validSkuList) {
            sb.append(gs.skuCombinationCode).append(", ");
        }
        System.out.println(sb.toString());

        /* 更新启用状态 */
        switchEnabledStatus(mSectionList, validSkuList);
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
                /*Section clickedSection = (Section) flexbox_layout.getTag();
                Section.Cell clickedCell = (Section.Cell) v.getTag();*/

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

                /* 执行规格间互斥性 */
                executeSpecMutex();

                /* 解决单个规格被选中时的bug */
                List<String> checkedSpecItemIdList = searchCheckedIdList();
                if (checkedSpecItemIdList.size() == 1) {
                    Section tmp = null;
                    for (Section section : mSectionList) {
                        for (Section.Cell cell : section.cellList) {
                            if (cell.checked) {
                                tmp = section;
                                break;
                            }
                        }
                    }

                    if (tmp != null) {
                        for (Section.Cell cell : tmp.cellList) {
                            cell.enabled = false;
                            for (GoodsDetailBean.Data.GoodsSku goodsSku : mGoodsSkuList) {
                                if (Arrays.asList(goodsSku.skuCombinationCode.split("_")).contains(cell.specItemId)) {
                                    cell.enabled = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                /* 更新数据 */
                notifyDataSetChanged();
            }

            @MainThread
            void updateUI(int position) {
                flexbox_layout.removeAllViews();

                Section section = mSectionList.get(position);
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
