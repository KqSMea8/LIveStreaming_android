package com.fanwe.live.model;

public class JoinLiveData
{
    private int roomId;
    private String groupId;
    private String createrId;
    private String loadingVideoImageUrl;
    private String privateKey;
    private int is_small_screen;//是否小屏
    private int sdkType; //0-腾讯sdk；1-金山sdk
    //add
    private int create_type; //直播类型 0：移动端；1:PC直播

    public int getCreate_type()
    {
        return create_type;
    }

    public void setCreate_type(int create_type)
    {
        this.create_type = create_type;
    }

    public void setSdkType(int sdkType)
    {
        this.sdkType = sdkType;
    }

    public int getSdkType()
    {
        return sdkType;
    }

    public int getRoomId()
    {
        return roomId;
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getCreaterId()
    {
        return createrId;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public String getLoadingVideoImageUrl()
    {
        return loadingVideoImageUrl;
    }

    public void setLoadingVideoImageUrl(String loadingVideoImageUrl)
    {
        this.loadingVideoImageUrl = loadingVideoImageUrl;
    }

    public String getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(String privateKey)
    {
        this.privateKey = privateKey;
    }

    public int getIs_small_screen()
    {
        return is_small_screen;
    }

    public void setIs_small_screen(int is_small_screen)
    {
        this.is_small_screen = is_small_screen;
    }
}
