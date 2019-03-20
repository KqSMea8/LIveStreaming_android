package com.fanwe.live.model;

/**
 * 互动直播的创建数据
 */
public class CreateLiveData
{

    private int roomId; // 直播间房间号
    private int isClosedBack; //1：主播界面被强制关闭后回来
    private int sdkType; //0-腾讯云sdk；1-金山sdk

    public int getIsClosedBack()
    {
        return isClosedBack;
    }

    public void setIsClosedBack(int isClosedBack)
    {
        this.isClosedBack = isClosedBack;
    }

    public int getRoomId()
    {
        return roomId;
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public int getSdkType()
    {
        return sdkType;
    }

    public void setSdkType(int sdkType)
    {
        this.sdkType = sdkType;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("roomId:").append(roomId).append("\r\n");
        sb.append("isClosedBack:").append(isClosedBack).append("\r\n");
        return sb.toString();
    }
}
