package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-11 下午5:18:00 类说明
 */
@SuppressWarnings("serial")
public class App_user_homeActModel extends BaseActModel
{
    private int has_focus; // 0:未关注;1:已关注
    private int has_black;// 0:未拖黑;1:已拖黑
    private List<UserModel> cuser_list;//贡献前3名

    //add
    private LiveRoomModel video;//该用户正在直播的房间信息

    public LiveRoomModel getVideo() {
        return video;
    }

    public void setVideo(LiveRoomModel video) {
        this.video = video;
    }

    public List<UserModel> getCuser_list()
    {
        return cuser_list;
    }

    public void setCuser_list(List<UserModel> cuser_list)
    {
        this.cuser_list = cuser_list;
    }

    public int getHas_focus()
    {
        return has_focus;
    }

    public void setHas_focus(int has_focus)
    {
        this.has_focus = has_focus;
    }

    public int getHas_black()
    {
        return has_black;
    }

    public void setHas_black(int has_black)
    {
        this.has_black = has_black;
    }

    private UserModel user;

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

}
