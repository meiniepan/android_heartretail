package com.idengyun.heartretail.my.other;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idengyun.heartretail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的模块-通用Adapter
 *
 * @author aLang
 */
public final class MyAdapter extends RecyclerView.Adapter<MyHolder> implements View.OnClickListener {

    private final List<MyModel> itemList = new ArrayList<>();
    private LayoutInflater inflater;
    private OnMyItemClickListener listener;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.fragment_setting_item, parent, false);
        itemView.setOnClickListener(this);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyModel s = itemList.get(position);
        holder.updateUI(s, getItemCount());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View v) {

    }
}
