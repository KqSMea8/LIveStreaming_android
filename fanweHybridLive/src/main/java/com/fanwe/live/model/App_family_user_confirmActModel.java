package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 成员申请审核(家族长权限)
 * Created by Administrator on 2016/9/29.
 */

public class App_family_user_confirmActModel extends BaseActModel
{
    private int r_user_id;

    public int getR_user_id() {
        return r_user_id;
    }

    public void setR_user_id(int r_user_id) {
        this.r_user_id = r_user_id;
    }
}
