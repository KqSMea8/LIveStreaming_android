package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

public class BMCheckVerifyActModel extends BaseActModel
{
    private int has_invitation_code;
    private String invitation_tip;
    private String default_code;

    public String getDefault_code()
    {
        return default_code;
    }

    public void setDefault_code(String default_code)
    {
        this.default_code = default_code;
    }

    public int getHas_invitation_code()
    {
        return has_invitation_code;
    }

    public void setHas_invitation_code(int has_invitation_code)
    {
        this.has_invitation_code = has_invitation_code;
    }

    public String getInvitation_tip()
    {
        return invitation_tip;
    }

    public void setInvitation_tip(String invitation_tip)
    {
        this.invitation_tip = invitation_tip;
    }
}
