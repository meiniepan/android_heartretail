package com.sobot.chat.dengy.adapter;

import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.swipe.BaseSwipeAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by liup on 16/6/7.
 */

public class SimpleSwipeableAdapter<T, VH extends BaseSwipeAdapter.BaseSwipeableViewHolder> extends BaseSwipeAdapter<VH> {

    private List<T> mList;


    public SimpleSwipeableAdapter(List<T> list) {
        mList = list != null ? list : new ArrayList<T>();
    }

    public List<T> getList() {
        return mList;
    }

    public void addAll(Collection<? extends T> collection) {
        closeAllExcept(null);
        int oldSize = mList.size();
        mList.addAll(collection);
        notifyItemRangeInserted(oldSize, collection.size());
    }

    public void replace(Collection<? extends T> collection) {
        closeAllExcept(null);
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
    }

    public void add(T item) {
        closeAllExcept(null);
        mList.add(item);
        notifyItemInserted(mList.size() - 1);
    }

    public void set(int position, T item) {
        mList.set(position, item);
        notifyItemChanged(position);
    }

    public T remove(int position) {
        T item = mList.remove(position);
        notifyItemRemoved(position);
        closeItem(position);
        return item;
    }

    public void clear() {
        closeAllExcept(null);
        int oldSize = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public T getLastItem() {
        int index = mList.size() - 1;
        if (index > 0){
            return getItem(index);
        }
        return null;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
