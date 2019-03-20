package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActListModel;

import java.util.List;

/**
 * Created by HSH on 2016/7/26.
 */
public class IndexSearch_areaActModel extends BaseActListModel
{
    private List<SelectCityModel> list;

    public List<SelectCityModel> getList()
    {
        return list;
    }

    public void setList(List<SelectCityModel> list)
    {
        this.list = list;
    }
}
