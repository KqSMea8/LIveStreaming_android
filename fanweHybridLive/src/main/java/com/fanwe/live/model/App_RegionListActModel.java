package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.ArrayList;

/**
 * Created by shibx on 2016/7/13.
 */
public class App_RegionListActModel extends BaseActModel{

    private static final long serialVersionUID = 0L;

    private ArrayList<RegionModel> region_list ;

    private int region_versions;

    public int getRegion_versions() {
        return region_versions;
    }

    public void setRegion_versions(int region_versions) {
        this.region_versions = region_versions;
    }

    public ArrayList<RegionModel> getRegion_list() {
        return region_list;
    }

    public void setRegion_list(ArrayList<RegionModel> region_list) {
        this.region_list = region_list;
    }
}
