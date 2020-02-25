package com.idengyun.heartretail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dengyun.baselibrary.base.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * 我的页面
 *
 * @author aLang
 */
public final class MyFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

final class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    ArrayList<String> items = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.fragment_main, viewGroup, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}

final class MyHolder extends RecyclerView.ViewHolder {
    MyHolder(@NonNull View itemView) {
        super(itemView);
    }
}
