package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 庄家申请列表
 * Created by shibx on 2017/02/24.
 */

public class App_banker_listActModel extends BaseActModel
{
    private List<GameBankerModel> banker_list;

    public List<GameBankerModel> getBanker_list() {
        return banker_list;
    }

    public void setBanker_list(List<GameBankerModel> banker_list) {
        this.banker_list = banker_list;
    }
}
