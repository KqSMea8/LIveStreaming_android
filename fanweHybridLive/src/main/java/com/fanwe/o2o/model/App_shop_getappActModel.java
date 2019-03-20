package com.fanwe.o2o.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.model.UserModel;

/**
 * Created by Administrator on 2016/9/24.
 */

public class App_shop_getappActModel extends BaseActModel
{
    private UserModel user;
    private String session_id;

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }
}
