package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by HSH on 2016/7/18.
 */
public class SettingsSecurityActModel extends BaseActModel
{
    private int is_security;
    private String mobile;

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public int getIs_security()
    {
        return is_security;
    }

    public void setIs_security(int is_security)
    {
        this.is_security = is_security;
    }
}
