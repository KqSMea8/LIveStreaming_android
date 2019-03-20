package com.fanwe.o2o.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemAdapterMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes.Mode;
import com.fanwe.library.adapter.SDSimpleAdapter;

import java.util.List;

public abstract class SDBaseSwipeAdapter<T> extends SDSimpleAdapter<T> implements SwipeItemMangerInterface, SwipeAdapterInterface
{
    protected SwipeItemAdapterMangerImpl mItemManger = new SwipeItemAdapterMangerImpl(this);

    public SDBaseSwipeAdapter(List<T> listModel, Activity activity) {
        super(listModel, activity);
    }


    public abstract int getSwipeLayoutResourceId(int var1);

    public abstract View generateView(int var1, ViewGroup var2);

    public abstract void fillValues(int var1, View var2);

    public final View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null) {
            v = this.generateView(position, parent);
            this.mItemManger.initialize(v, position);
        } else {
            this.mItemManger.updateConvertView(convertView, position);
        }

        this.fillValues(position, v);
        return v;
    }

    public void openItem(int position) {
        this.mItemManger.openItem(position);
    }

    public void closeItem(int position) {
        this.mItemManger.closeItem(position);
    }

    public void closeAllExcept(SwipeLayout layout) {
        this.mItemManger.closeAllExcept(layout);
    }

    public void closeAllItems() {
        this.mItemManger.closeAllItems();
    }

    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    public List<SwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    public void removeShownLayouts(SwipeLayout layout) {
        this.mItemManger.removeShownLayouts(layout);
    }

    public boolean isOpen(int position) {
        return this.mItemManger.isOpen(position);
    }

    public Mode getMode() {
        return this.mItemManger.getMode();
    }

    public void setMode(Mode mode) {
        this.mItemManger.setMode(mode);
    }
}
