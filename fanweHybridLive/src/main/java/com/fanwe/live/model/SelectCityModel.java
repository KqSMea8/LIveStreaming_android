package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

/**
 * Created by HSH on 2016/7/26.
 */
public class SelectCityModel implements SDSelectManager.Selectable
{
    private String city;
    private String number;

    //add
    private boolean selected;

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }


    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
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
}
