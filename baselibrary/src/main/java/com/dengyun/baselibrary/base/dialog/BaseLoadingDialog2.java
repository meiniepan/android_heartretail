package com.dengyun.baselibrary.base.dialog;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.base.dialog.listener.DialogViewHolder;
import com.dengyun.baselibrary.widgets.GraduallyTextView;

/**
 * @titile  请求网络时的缓冲进度圈
 * @desc Created by seven on 2018/3/24.
 */

public class BaseLoadingDialog2 extends BaseDialogFragment {

    GraduallyTextView mGraduallyTextView;
    private String mMessage;

    @Override
    public int intLayoutId() {
        return R.layout.base_dialog_loading2;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        mGraduallyTextView = holder.getView(R.id.graduallyTextView);
        if (!TextUtils.isEmpty(mMessage)) mGraduallyTextView.setText(mMessage);
        setWidthDp(150);
        setHeightDp(150);
    }

    //重写onresume方法，绑定activity的生命周期
    @Override
    public void onResume() {
        super.onResume();
        mGraduallyTextView.startLoading();
    }

    //重写onPause方法，绑定activity的生命周期
    @Override
    public void onPause() {
        super.onPause();
        mGraduallyTextView.stopLoading();
    }

    //设置关闭loadingview的方法
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        System.gc();
    }

    public BaseLoadingDialog2 setText(String message){
        this.mMessage = message;
        return this;
    }
}
