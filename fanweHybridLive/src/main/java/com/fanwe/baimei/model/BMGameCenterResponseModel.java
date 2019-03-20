package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 包名: com.fanwe.baimei.model
 * 描述: 游戏主页返回实体类
 * 作者: Su
 * 创建时间: 2017/5/19 9:58
 **/
public class BMGameCenterResponseModel extends BaseActModel
{

    /**
     * status : 1
     * error :
     * head_image : http://liveimage.fanwe.net/public/attachment/201704/167314/1492678147021.png
     * diamonds : 0
     * online_user : 762
     * list : [{"id":"1","child_id":"1","name":"炸金花","image":"http://liveimage.fanwe.net/public/attachment/201612/09/14/584a4d96d8ebd.jpg","type":"2","class_name":"game"},{"id":"2","child_id":"2","name":"斗牛","image":"http://liveimage.fanwe.net/public/attachment/201612/08/18/5849319a4049a.png","type":"2","class_name":"game"},{"id":"8","child_id":"3","name":"德州扑克","image":"http://liveimage.fanwe.net/public/images/5859e6513a3c7.png","type":"2","class_name":"game"},{"id":"9","child_id":"4","name":"摇色子","image":"http://liveimage.fanwe.net/public/images/5859e6513a3c7.png","type":"2","class_name":"game"}]
     * focus_list : [{"room_id":"1"},{"room_id":"2"},{"room_id":"3"},{"room_id":"4"}]
     * init_version : 2017042906
     * act : index
     * ctl : games
     */

    private String head_image;
    private String diamonds;
    private String coins;
    private int online_user;
    private String act;
    private String ctl;
    private List<BMGameCenterGameModel> list;
    private List<BMGameFriendsRoomModel> focus_list;
    private int has_room;

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public String getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(String diamonds)
    {
        this.diamonds = diamonds;
    }

    public int getOnline_user()
    {
        return online_user;
    }

    public void setOnline_user(int online_user)
    {
        this.online_user = online_user;
    }

    public String getAct()
    {
        return act;
    }

    public void setAct(String act)
    {
        this.act = act;
    }

    public String getCtl()
    {
        return ctl;
    }

    public void setCtl(String ctl)
    {
        this.ctl = ctl;
    }

    public List<BMGameCenterGameModel> getList()
    {
        return list;
    }

    public void setList(List<BMGameCenterGameModel> list)
    {
        this.list = list;
    }

    public List<BMGameFriendsRoomModel> getFocus_list()
    {
        return focus_list;
    }

    public void setFocus_list(List<BMGameFriendsRoomModel> focus_list)
    {
        this.focus_list = focus_list;
    }

    public int getHas_room()
    {
        return has_room;
    }

    public void setHas_room(int has_room)
    {
        this.has_room = has_room;
    }

    public String getCoins()
    {
        return coins;
    }

    public void setCoins(String coins)
    {
        this.coins = coins;
    }
}
