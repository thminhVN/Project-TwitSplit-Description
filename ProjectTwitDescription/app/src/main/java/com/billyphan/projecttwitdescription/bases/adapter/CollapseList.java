package com.billyphan.projecttwitdescription.bases.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy Phan on 4/8/2018.
 */

public class CollapseList {
    private List<Header> mItems;
    private BaseCollapseAdapter mAdapter;

    public CollapseList() {
        this.mItems = new ArrayList<>();
    }

    public void setAdapter(BaseCollapseAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public int getHeaderSize() {
        return mItems.size();
    }

    public Object getItem(int flatPosition) {
        int currentFlatPosition = -1;
        for (Header mItem : mItems) {
            currentFlatPosition++;
            if (currentFlatPosition == flatPosition)
                return mItem;
            if (!mItem.isCollapsed()) {
                for (int i = 0; i < mItem.size(); i++) {
                    currentFlatPosition++;
                    if (currentFlatPosition == flatPosition)
                        return mItem.get(i);
                }
            }
        }
        return null;
    }

    public void add(Header header) {
        this.mItems.add(header);
        header.setParent(this);
    }

    public int getFlatSize() {
        int size = mItems.size();
        for (Header mItem : mItems) {
            if (!mItem.isCollapsed())
                size += mItem.size();
        }
        return size;
    }

    public int getFlatPosition(Object item) {
        int currentFlatPosition = -1;
        for (Header mItem : mItems) {
            currentFlatPosition++;
            if (mItem == item) return currentFlatPosition;
            if (!mItem.isCollapsed()) {
                for (int i = 0; i < mItem.size(); i++) {
                    currentFlatPosition++;
                    if (mItem.get(i) == item) return currentFlatPosition;
                }
            }
        }
        return currentFlatPosition;
    }

    public static class Header {
        private final ArrayList<Item> mItems;
        private String mText;
        private RecyclerView.Adapter mAdapter;
        private boolean mCollapsed;
        private CollapseList mParent;

        public Header(List<String> parts) {
            this.mItems = new ArrayList<Item>();
            this.mText = "";
            this.load(parts);
            this.mCollapsed = true;
        }

        private void load(List<String> parts) {
            for (String part : parts) {
                this.mItems.add(new Item(part));
            }
        }

        public int size() {
            return this.mItems.size();
        }

        public void toogle() {
            int flatPosition = this.mParent.getFlatPosition(this);
            if (mCollapsed) {
                mAdapter.notifyItemRangeInserted(flatPosition + 1, size());
            } else {
                mAdapter.notifyItemRangeRemoved(flatPosition + 1, size());
            }
            mCollapsed = !mCollapsed;
        }

        public Item get(int position) {
            return mItems.get(position);
        }

        public boolean isCollapsed() {
            return mCollapsed;
        }

        @Override
        public String toString() {
            return mText;
        }

        public Header setParent(CollapseList parent) {
            this.mParent = parent;
            this.mAdapter = parent.mAdapter;
            return this;
        }

        public void setText(String text) {
            this.mText = text;
        }

        public void notifyInserted() {
            int flatPosition = this.mParent.getFlatPosition(this);
            if (isCollapsed()) {
                this.mAdapter.notifyItemRangeInserted(flatPosition, size() + 1);
            } else
                this.mAdapter.notifyItemInserted(flatPosition);
        }

        public int getFlatPositionOfLastItem() {
            int position = this.mParent.getFlatPosition(this);
            if (isCollapsed())
                return position;
            else
                return position + size();
        }
    }

    public static class Item {

        private final String mText;

        public Item(String part) {
            this.mText = part;
        }

        @Override
        public String toString() {
            return mText;
        }
    }
}
