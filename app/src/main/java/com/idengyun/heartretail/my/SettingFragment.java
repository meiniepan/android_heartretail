package com.idengyun.heartretail.my;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dengyun.baselibrary.base.fragment.BaseFragment;
import com.idengyun.heartretail.HRActivity;
import com.idengyun.heartretail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置界面
 *
 * @author aLang
 */
public final class SettingFragment extends BaseFragment implements View.OnClickListener {
    private final List<String> settingList = new ArrayList<>();
    private RecyclerView recycler_view;
    private View layout_logout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] names = {"个人资料", "账号管理", "当前版本", "用户协议"};
        for (int i = 0; i < names.length; i++) {
            settingList.add(names[i]);
        }
        SettingAdapter settingAdapter = new SettingAdapter();
        recycler_view.setAdapter(settingAdapter);
    }

    @Override
    public void onClick(View v) {
        /*
         * 0.个人资料
         * 1.账号管理
         * 2.当前版本
         * 3.用户协议
         * -1. 退出登录
         *  */
        int position = (int) v.getTag();
        if (0 == position) {
            startPersonDataActivity();
        } else if (1 == position) {
            startAccountActivity();
        } else if (2 == position) {
            startVersionActivity();
        } else if (3 == position) {
            startUserAgreeActivity();
        } else {
            logout();
        }
    }

    private void logout() {
        // TODO: 2020/3/6
    }

    private void startUserAgreeActivity() {
        // TODO: 2020/3/6
    }

    private void startVersionActivity() {
        // TODO: 2020/3/6
    }

    private void startAccountActivity() {
        HRActivity.start(getContext(), AccountManageFragment.class);
    }

    private void startPersonDataActivity() {
        HRActivity.start(getContext(), PersonDataFragment.class);
    }

    private void findViewById(View view) {
        recycler_view = view.findViewById(R.id.recycler_view);
        layout_logout = view.findViewById(R.id.layout_logout);

        layout_logout.setOnClickListener(this);
        layout_logout.setTag(-1);
    }

    private class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingHolder> {

        private LayoutInflater inflater;

        @NonNull
        @Override
        public SettingHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.fragment_setting_item, parent, false);
            return new SettingHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingHolder holder, int position) {
            holder.updateUI(position);
        }

        @Override
        public int getItemCount() {
            return settingList.size();
        }

        private class SettingHolder extends RecyclerView.ViewHolder {

            private TextView tv_setting_name;
            private View v_setting_divider;

            public SettingHolder(@NonNull View itemView) {
                super(itemView);
                tv_setting_name = itemView.findViewById(R.id.tv_setting_name);
                v_setting_divider = itemView.findViewById(R.id.v_setting_divider);
                itemView.setOnClickListener(SettingFragment.this);
            }

            @MainThread
            void updateUI(int position) {
                itemView.setTag(position);
                String s = settingList.get(position);
                tv_setting_name.setText(s);
                v_setting_divider.setVisibility(position + 1 == getItemCount() ? View.GONE : View.VISIBLE);
            }
        }
    }
}
