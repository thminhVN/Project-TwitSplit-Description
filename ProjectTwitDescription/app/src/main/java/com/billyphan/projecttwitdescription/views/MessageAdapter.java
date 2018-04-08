package com.billyphan.projecttwitdescription.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billyphan.projecttwitdescription.R;
import com.billyphan.projecttwitdescription.bases.adapter.BaseCollapseAdapter;
import com.billyphan.projecttwitdescription.bases.adapter.CollapseList;
import com.billyphan.projecttwitdescription.functional.Bindable;

/**
 * Created by Billy Phan on 4/8/2018.
 */

public class MessageAdapter extends BaseCollapseAdapter {

    public MessageAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new GroupHolder(parent);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemHolder(ViewGroup parent) {
        return new Holder(parent);
    }

    public void addGroup(CollapseList.Header header) {
        header.setText("Message " + mItems.getHeaderSize());
        mItems.add(header);
        header.notifyInserted();
    }

    class GroupHolder extends RecyclerView.ViewHolder implements Bindable<CollapseList.Header> {

        private final TextView mTextView;
        private final View mDropDown;

        public GroupHolder(ViewGroup viewGroup) {
            super(inflate(R.layout.item_trunk_header, viewGroup));
            this.mTextView = itemView.findViewById(R.id.txt_trunk_header);
            this.mDropDown = itemView.findViewById(R.id.img_drop_down);
            this.itemView.setOnClickListener(view -> {
                CollapseList.Header header = (CollapseList.Header) view.getTag();
                header.toogle();
                mDropDown.setSelected(!mDropDown.isSelected());
                mView.smoothScrollToPosition(header.getFlatPositionOfLastItem());
            });
        }

        @Override
        public void bind(CollapseList.Header item) {
            itemView.setTag(item);
            mTextView.setText(item.toString());
        }
    }

    class Holder extends RecyclerView.ViewHolder implements Bindable<CollapseList.Item> {

        private final TextView mTextView;

        public Holder(ViewGroup viewGroup) {
            super(inflate(R.layout.item_trunk, viewGroup));
            this.mTextView = itemView.findViewById(R.id.txt_trunk);
        }

        @Override
        public void bind(CollapseList.Item item) {
            mTextView.setText(item.toString());
        }
    }
}
