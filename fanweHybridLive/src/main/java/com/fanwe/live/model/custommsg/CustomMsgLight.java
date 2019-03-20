package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgLight extends CustomMsg
{
    private String imageName;
    private int showMsg;

    public void setShowMsg(int showMsg)
    {
        this.showMsg = showMsg;
    }

    public int getShowMsg()
    {
        return showMsg;
    }

    public CustomMsgLight()
    {
        super();
        setType(CustomMsgType.MSG_LIGHT);
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

}
