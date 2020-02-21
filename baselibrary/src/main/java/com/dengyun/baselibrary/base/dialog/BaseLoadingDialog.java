package com.dengyun.baselibrary.base.dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.dengyun.baselibrary.R;
import com.dengyun.baselibrary.base.dialog.listener.DialogViewHolder;
import com.dengyun.baselibrary.net.NetOption;
import com.dengyun.baselibrary.utils.ObjectUtils;

import java.util.HashMap;

/**
 * @titile 请求网络时的缓冲进度圈
 * @desc Created by seven on 2018/3/24.
 */

public class BaseLoadingDialog extends BaseDialogFragment {
    private static final String APPEND_TAG = "BaseLoadingDialog";

    //每个页面的单例存在的dialog
    private static HashMap<String, BaseLoadingDialog> instanceDialogMap = new HashMap<>();

    public synchronized static BaseLoadingDialog getInstance(NetOption netOption) {
        String loadingTag = "";
        if (null != netOption.getActivity()) {
            loadingTag = getLoadingDialogTag(netOption.getActivity());
        } else if (null != netOption.getFragment()) {
            loadingTag = getLoadingDialogTag(netOption.getFragment());
        }
        if (TextUtils.isEmpty(loadingTag)) return null;
        if (instanceDialogMap.containsKey(loadingTag)) {
            return instanceDialogMap.get(loadingTag);
        } else {
            BaseLoadingDialog baseLoadingDialog = new BaseLoadingDialog();
            baseLoadingDialog.setLoadingTag(loadingTag);
            instanceDialogMap.put(loadingTag, baseLoadingDialog);
            return baseLoadingDialog;
        }
    }

    private static String getLoadingDialogTag(Object o) {
        return ObjectUtils.getClassPath(o) + APPEND_TAG;
    }

    private String mTag;


    @Override
    public int intLayoutId() {
        return R.layout.base_dialog_loading;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        setWidthDp(100);
        setHeightDp(100);
        setDimAmount(0);
        setOutCancel(false);
        setBackCancel(true);
    }

//    private String loadingTag;

    @Deprecated
    @Override
    public BaseDialogFragment show(FragmentManager manager) {
        return super.show(manager);
    }

    @Deprecated
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    /**
     * 设置显示加载圈的方法，挂载在activity，tag是 ：activity名称+BaseLoadingDialog
     */
    public BaseLoadingDialog show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), getLoadingDialogTag(activity));
        return this;
    }

    /**
     * 设置显示加载圈的方法，挂载在fragment，tag是 ：fragment名称+BaseLoadingDialog
     */
    public BaseLoadingDialog show(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getFragmentManager();
        if (fragmentManager == null) return this;
        show(fragmentManager, getLoadingDialogTag(fragment));
        return this;
    }

    @Override
    public void onDestroyView() {
        instanceDialogMap.remove(mTag);
        super.onDestroyView();
    }

    private void setLoadingTag(String tag){
        this.mTag = tag;
    }
}
