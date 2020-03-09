package com.idengyun.maplibrary.view;

import android.content.Context;
import android.util.AttributeSet;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;

/**
 * <com.idengyun.maplibrary.view.DyMapView
 *     android:id="@+id/map"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"/>
 *
 * 需要合理的管理地图生命周期，这非常的重要。
 *
 *
 * @Title 自定义mapview
 * @Author: zhoubo
 * @CreateDate: 2020-03-06 15:55
 */
public class DyMapView extends MapView {
    public DyMapView(Context context) {
        super(context);
    }

    public DyMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DyMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DyMapView(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
    }
}
