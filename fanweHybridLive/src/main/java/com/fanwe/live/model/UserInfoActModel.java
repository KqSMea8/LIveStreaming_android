package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2017/5/18.
 */

public class UserInfoActModel extends BaseActModel
{
    private UserModel user_info;

    public UserModel getUser_info()
    {
        return user_info;
    }

    public void setUser_info(UserModel user_info)
    {
        this.user_info = user_info;
    }
}
