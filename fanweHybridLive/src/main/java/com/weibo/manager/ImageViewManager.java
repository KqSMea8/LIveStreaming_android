package com.weibo.manager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

public class ImageViewManager {

    private Bitmap[] mBitmaps = null;
    private ImageView[] mItems = null;

    private int mCurrentIndex = -1;

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void setItems(ImageView[] items, Bitmap[] bitmaps) {
        if (items != null && items.length > 0) {
            mItems = items;
            mBitmaps = bitmaps;
            for (int i = 0; i < mItems.length; i++) {
                mItems[i].setId(i);
                mItems[i].setImageBitmap(mBitmaps[i]);
                mItems[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setSelectIndex(view, view.getId());
                    }
                });
            }
        }
    }

    public boolean setSelectIndex(View view, int index) {
        if (mItems != null && mItems.length > 0 && index < mItems.length) {
            if (index != mCurrentIndex) {
                mItems[index].setBackgroundColor(Color.YELLOW);
                if (mCurrentIndex != -1) {
                    mItems[mCurrentIndex].setBackgroundColor(Color.TRANSPARENT);
                }
                mCurrentIndex = index;
                if (mListener != null) {
                    mListener.onItemClick(view, index);
                }
                return true;
            }
        }
        return false;
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int index);
    }

}
