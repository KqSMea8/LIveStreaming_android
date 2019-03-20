package com.fanwe.auction.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by shibx on 2016/8/9.
 */
public class GoodsTagsModel implements SDSelectManager.Selectable{

    private int id;
    private String name;
    private boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}
