package com.billyphan.projecttwitdescription.bases.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.billyphan.projecttwitdescription.functional.Bindable;

/**
 * Created by Billy Phan on 4/8/2018.
 */

public abstract class BaseCollapseAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private final LayoutInflater mInflater;
    protected final RecyclerView mView;
    protected CollapseList mItems;

    public BaseCollapseAdapter(RecyclerView recyclerView) {
        this.mView = recyclerView;
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(this);
        this.mInflater = LayoutInflater.from(recyclerView.getContext());
    }

    public void setItems(CollapseList mItems) {
        this.mItems = mItems;
        this.mItems.setAdapter(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.getItem(position) instanceof CollapseList.Header) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM)
            return onCreateItemHolder(parent);
        return onCreateHeaderHolder(parent);
    }

    protected abstract RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder onCreateItemHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Bindable)
            ((Bindable) holder).bind(mItems.getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.getFlatSize();
    }

    public View inflate(int layoutId, ViewGroup viewGroup) {
        return mInflater.inflate(layoutId, viewGroup, false);
    }

}
