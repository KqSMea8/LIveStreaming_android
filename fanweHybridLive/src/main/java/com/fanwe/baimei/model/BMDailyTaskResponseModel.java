package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 包名: com.fanwe.baimei.model
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/31 10:14
 **/
public class BMDailyTaskResponseModel extends BaseActModel
{
    @Override
    public String toString() {
        return "BMDailyTaskResponseModel{" +
                "list=" + list +
                '}';
    }

    private List<BMDailyTasksListModel> list;

    public List<BMDailyTasksListModel> getList()
    {
        return list;
    }

    public void setList(List<BMDailyTasksListModel> list)
    {
        this.list = list;
    }

}
