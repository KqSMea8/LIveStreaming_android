package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/7/14.
 */
public class App_userEditActModel extends BaseActModel
{

    private UserModel user;
    private String nick_info;//昵称编辑栏描述文字

    public String getNick_info()
    {
        return nick_info;
    }

    public void setNick_info(String nick_info)
    {
        this.nick_info = nick_info;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }


}
