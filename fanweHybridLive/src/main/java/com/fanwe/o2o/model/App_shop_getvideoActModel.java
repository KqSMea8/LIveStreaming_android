package com.fanwe.o2o.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.model.UserModel;

/**
 * Created by Administrator on 2016/9/24.
 */

public class App_shop_getvideoActModel extends BaseActModel
{
    private UserModel user;
    private int user_id;
    private String session_id;
    private int is_small_screen;
    private int roomId;
    private String groupId;
    private String createrId;
    private String loadingVideoImageUrl;

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }

    public int getIs_small_screen()
    {
        return is_small_screen;
    }

    public void setIs_small_screen(int is_small_screen)
    {
        this.is_small_screen = is_small_screen;
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
}
