package com.idengyun.heartretail.my.other;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idengyun.heartretail.R;

/**
 * 我的模块-通用ViewHolder
 *
 * @author aLang
 */
public final class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_setting_name;
    private ImageView iv_setting_avatar;
    private TextView tv_setting_version;
    private View iv_setting_arrow;
    private View v_setting_divider;
    private OnMyItemClickListener listener;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        findViewById(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onItemClick(v, getAdapterPosition(), (MyModel) v.getTag());
    }

    @MainThread
    public void updateUI(MyModel model, int itemCount) {
        int adapterPosition = getAdapterPosition();
        itemView.setTag(model);
        tv_setting_name.setText(model.name);
        iv_setting_avatar.setImageDrawable(null);
        tv_setting_version.setText(null);
        v_setting_divider.setVisibility(adapterPosition + 1 == itemCount ? View.GONE : View.VISIBLE);
    }

    private void findViewById(@NonNull View view) {
        tv_setting_name = view.findViewById(R.id.tv_setting_name);
        iv_setting_avatar = view.findViewById(R.id.iv_setting_avatar);
        tv_setting_version = view.findViewById(R.id.tv_setting_version);
        iv_setting_arrow = view.findViewById(R.id.iv_setting_arrow);
        v_setting_divider = view.findViewById(R.id.v_setting_divider);
    }
}
