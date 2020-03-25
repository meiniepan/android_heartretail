package com.idengyun.heartretail.goods.helper;

import com.idengyun.heartretail.model.response.GoodsDetailBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 执行商品规格互斥助手
 *
 * @author aLang
 */
public final class Converter {

    private final GoodsDetailBean.Data mData;

    private final List<SKU> mSKUList;
    private final List<Section> mSectionList;
    private final List<Section> mUnSelectedSectionList;
    private final List<Section> mSelectedSectionList;
    private final List<String> mSelectedSpecIDList;

    private SKU mDefaultSKU;
    private SKU mSelectedSKU;

    public Converter(@NonNull GoodsDetailBean.Data data) {
        mData = data;

        mSKUList = new ArrayList<>();
        mSectionList = new ArrayList<>();
        mUnSelectedSectionList = new ArrayList<>();
        mSelectedSectionList = new ArrayList<>();
        mSelectedSpecIDList = new ArrayList<>();

        List<GoodsDetailBean.Data.GoodsSpec> goodsSpecList = data.goodsSpecList;
        List<GoodsDetailBean.Data.GoodsSku> goodsSkuList = data.goodsSkuList;

        for (final GoodsDetailBean.Data.GoodsSku goodsSku : goodsSkuList) {
            SKU sku = goodsSkuToSKU(goodsSku);
            // println(sku.specList);
            // if (sku.goodsCount > 0 && sku.canBuyCount > 0 && sku.wholesaleFlag == 1) {
            if (sku.goodsCount > 0 && sku.canBuyCount > 0) {
                mSKUList.add(sku);
                /* 默认SKU */
                if (sku.isDefault == 1) mDefaultSKU = sku;
            }
        }

        /* DataModel to ViewModel */
        for (int i = 0; i < goodsSpecList.size(); i++) {
            GoodsDetailBean.Data.GoodsSpec goodsSpec = goodsSpecList.get(i);

            Section section = goodsSpecToSection(i, goodsSpec);
            List<GoodsDetailBean.Data.GoodsSpec.SkuValue> skuValueList = goodsSpec.skuValueList;
            for (int j = 0; j < skuValueList.size(); j++) {
                final GoodsDetailBean.Data.GoodsSpec.SkuValue skuValue = skuValueList.get(j);
                Cell cell = skuValueToCell(j, skuValue);
                section.cellList.add(cell);
                /* 设置默认规格 */
                if (mDefaultSKU != null && mDefaultSKU.specIDList.contains(cell.specItemId)) {
                    cell.enabled = true;
                    cell.checked = true;
                    section.checked = true;
                }
            }
            mSectionList.add(section);
        }

        executeSpecMutex();
    }

    @NonNull
    public GoodsDetailBean.Data getData() {
        return mData;
    }

    public int getGoodsType() {
        return mData.goodsType;
    }

    @NonNull
    public List<? extends SKU> getSKUList() {
        return mSKUList;
    }

    @NonNull
    public List<? extends Section> getSectionList() {
        return mSectionList;
    }

    @NonNull
    public List<? extends Section> getUnSelectedSectionList() {
        return mUnSelectedSectionList;
    }

    @NonNull
    public List<? extends Section> getSelectedSectionList() {
        return mSelectedSectionList;
    }

    @NonNull
    public List<? extends String> getSelectedSpecIDList() {
        return mSelectedSpecIDList;
    }

    @Nullable
    public SKU getDefaultSKU() {
        return mDefaultSKU;
    }

    @Nullable
    public SKU getSelectedSKU() {
        return mSelectedSKU;
    }

    public void executeSpecMutex() {
        /* 1.搜索未被选中的和已被选中的 */
        mUnSelectedSectionList.clear();
        mSelectedSectionList.clear();
        for (Section section : mSectionList) {
            if (!section.checked) mUnSelectedSectionList.add(section);
            else mSelectedSectionList.add(section);
        }

        mSelectedSpecIDList.clear();
        mSelectedSKU = null;
        if (mSelectedSectionList.size() == mSectionList.size()) {
            /* 被选中的specItemIdList */
            for (Section section : mSelectedSectionList) {
                for (Cell cell : section.cellList) {
                    if (cell.checked) {
                        mSelectedSpecIDList.add(cell.specItemId);
                        break;
                    }
                }
            }

            /* 被选中的SKU */
            if (!mSelectedSpecIDList.isEmpty()) {
                for (SKU sku : mSKUList) {
                    if (sku.specIDList.containsAll(mSelectedSpecIDList)) {
                        mSelectedSKU = sku;
                        break;
                    }
                }
            }
        }

        /* 2.遍历未被选中选区，设置是否启用 */
        for (Section section : mUnSelectedSectionList) {
            for (Cell cell : section.cellList) {
                cell.enabled = isEnabled(mSelectedSectionList, cell);
            }
        }

        /* 3.遍历被选中选区，假设被选中的中有一个是未选中的，模仿步骤2 */
        for (int i = 0; i < mSelectedSectionList.size(); i++) {
            Section unSelectedSection = mSelectedSectionList.get(i);
            ArrayList<Section> selectedSections = new ArrayList<>(mSelectedSectionList);
            selectedSections.remove(unSelectedSection);

            for (Cell cell : unSelectedSection.cellList) {
                cell.enabled = isEnabled(selectedSections, cell);
            }
        }
    }

    private boolean isEnabled(List<Section> selectedSectionList, Cell target) {
        /* 搜索被选中的规格ID */
        ArrayList<String> selectedSpecIdList = new ArrayList<>();
        for (Section section : selectedSectionList) {
            for (Cell cell : section.cellList) {
                if (cell.checked) selectedSpecIdList.add(cell.specItemId);
            }
        }

        /* 搜索有效的SKU列表 */
        ArrayList<SKU> selectedSKUList = new ArrayList<>();
        for (SKU sku : mSKUList) {
            if (sku.specIDList.containsAll(selectedSpecIdList)) selectedSKUList.add(sku);
        }

        /* 是否启用 */
        for (SKU sku : selectedSKUList) {
            if (sku.specIDList.contains(target.specItemId)) return true;
        }

        return false;
    }

    private Section goodsSpecToSection(final int index,
                                       final GoodsDetailBean.Data.GoodsSpec goodsSpec) {
        return new Section() {
            {
                position = index;
                skuName = goodsSpec.skuName;
                checked = false;
                cellList = new ArrayList<>();
            }
        };
    }

    private Cell skuValueToCell(final int index,
                                final GoodsDetailBean.Data.GoodsSpec.SkuValue skuValue) {
        return new Cell() {
            {
                position = index;
                enabled = false;
                checked = false;
                specItemId = String.valueOf(skuValue.specItemId);
                specItemName = skuValue.specItemName;
            }
        };
    }

    private SKU goodsSkuToSKU(final GoodsDetailBean.Data.GoodsSku goodsSku) {
        return new SKU() {
            {
                specIDList = Arrays.asList(goodsSku.skuCombinationCode.split("_"));
                isDefault = goodsSku.isDefault;
                goodsCount = goodsSku.goodsCount;
                goodsSkuId = goodsSku.goodsSkuId;
                goodsPrice = goodsSku.goodsPrice;
                canBuyCount = goodsSku.canBuyCount;
                skuImgUrl = goodsSku.skuImgUrl;
                wholesaleFlag = goodsSku.wholesaleFlag;
            }
        };
    }

    private void println(Object x) {
        System.out.println(x);
    }

    private void print(Object x) {
        System.out.print(x);
    }
}
