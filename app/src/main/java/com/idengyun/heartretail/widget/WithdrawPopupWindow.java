package com.idengyun.heartretail.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.idengyun.heartretail.R;


/**
 * @author Burning
 * @description:我要提现弹窗
 * @date :2020/03/1614:26
 */
public class WithdrawPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mPopView;

    public WithdrawPopupWindow(Context context) {
        super(context);
        init(context);
        setPopupWindow();
    }

    private void init(Context context) {
        mPopView = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw, null);
    }

    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setTouchable(true);
        this.setFocusable(true);
    }

    @Override
    public void onClick(View v) {

    }
}
