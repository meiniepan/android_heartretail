package com.idengyun.heartretail.my.other;

import android.view.View;

import com.idengyun.heartretail.my.other.MyModel;

/**
 * 我的模块-通用监听器
 *
 * @author aLang
 */
public interface OnMyItemClickListener {

    void onItemClick(View itemView, int position, MyModel model);
}
