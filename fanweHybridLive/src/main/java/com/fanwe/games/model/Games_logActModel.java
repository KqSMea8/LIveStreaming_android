package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luodong on 2016/12/6.
 */

public class Games_logActModel extends BaseActModel
{

    private int[] data;

    //add
    private List<Integer> list;

    public int[] getData()
    {
        return data;
    }

    public void setData(int[] data)
    {
        this.data = data;
        list = new ArrayList<>();
        for (int i : data)
        {
            list.add(i);
        }
    }

    public List<Integer> getList()
    {
        return list;
    }
}
