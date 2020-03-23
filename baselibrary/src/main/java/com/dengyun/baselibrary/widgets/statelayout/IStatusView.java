package com.dengyun.baselibrary.widgets.statelayout;

import android.view.View;

import java.util.List;

/**
 * @Title 状态布局的接口类，用于在自定义的状态布局中实现某些状态逻辑
 * @Author: zhoubo
 * @CreateDate: 2020-03-23 10:53
 */
public interface IStatusView {
    void setLayoutParamsWithToolbar(int toolbarId);
    void addInnerStatusView(View innerStatusView);
}
