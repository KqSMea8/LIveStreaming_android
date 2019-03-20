package com.fanwe.live.model;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by shibx on 2016/7/13.
 */
public class RegionModel implements IPickerViewData, Serializable
{
    private static final long serialVersionUID = 0L;

    private int id;
    private int pid;
    private String name;
    private int region_level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegion_level() {
        return region_level;
    }

    public void setRegion_level(int region_level) {
        this.region_level = region_level;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
