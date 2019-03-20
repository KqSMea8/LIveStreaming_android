package com.fanwe.live.model.custommsg;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.live.LiveConstant;

/**
 * Created by Administrator on 2017/1/22.
 */

public class CustomMsgData extends CustomMsg
{
    private int data_type;
    private String data;

    public CustomMsgData()
    {
        setType(LiveConstant.CustomMsgType.MSG_DATA);
    }

    public int getData_type()
    {
        return data_type;
    }

    public void setData_type(int data_type)
    {
        this.data_type = data_type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public <T> T parseData(Class<T> clazz)
    {
        try
        {
            return SDJsonUtil.json2Object(this.data, clazz);
        } catch (Exception e)
        {
            if (ApkConstant.DEBUG)
            {
                e.printStackTrace();
                LogUtil.i("parseData error:" + e.toString());
            }
        }
        return null;
    }
}
