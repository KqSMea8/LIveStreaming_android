package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActListModel;

import java.util.List;

/**
 * Created by luodong on 2016/10/17.
 */
public class App_RankContributionModel extends BaseActListModel
{
    private List<RankUserItemModel> list;

    public List<RankUserItemModel> getList() {
        return list;
    }

    public void setList(List<RankUserItemModel> list) {
        this.list = list;
    }

}
