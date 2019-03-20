package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by shibx on 2016/7/15.
 */
public class SelectLabelModel implements SDSelectManager.Selectable{

    private String label;
    private boolean isSelected;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SelectLabelModel(String label) {
        setLabel(label);
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
