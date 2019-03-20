package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

public class LiveCreaterMenuModel implements SDSelectManager.Selectable
{

    private int imageResIdNormal;
    private int imageResIdSelected;
    private int textColorResIdNormal;
    private int textColorResIdSelected;
    private String textNormal;
    private String textSelected;
    private boolean visible = true;
    private boolean enable = true;

    private boolean selected;

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public int getTextColorResIdNormal()
    {
        return textColorResIdNormal;
    }

    public void setTextColorResIdNormal(int textColorResIdNormal)
    {
        this.textColorResIdNormal = textColorResIdNormal;
    }

    public int getTextColorResIdSelected()
    {
        return textColorResIdSelected;
    }

    public void setTextColorResIdSelected(int textColorResIdSelected)
    {
        this.textColorResIdSelected = textColorResIdSelected;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public int getImageResIdNormal()
    {
        return imageResIdNormal;
    }

    public void setImageResIdNormal(int imageResIdNormal)
    {
        this.imageResIdNormal = imageResIdNormal;
    }

    public int getImageResIdSelected()
    {
        return imageResIdSelected;
    }

    public void setImageResIdSelected(int imageResIdSelected)
    {
        this.imageResIdSelected = imageResIdSelected;
    }

    public String getTextNormal()
    {
        return textNormal;
    }

    public void setTextNormal(String textNormal)
    {
        this.textNormal = textNormal;
    }

    public String getTextSelected()
    {
        return textSelected;
    }

    public void setTextSelected(String textSelected)
    {
        this.textSelected = textSelected;
    }

    public boolean isEnable()
    {
        return enable;
    }

    public void setEnable(boolean enable)
    {
        this.enable = enable;
    }

}
