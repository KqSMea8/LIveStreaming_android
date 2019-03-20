package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/7/5.
 */
public class App_is_user_verifyActModel extends BaseActModel {
    private String verify_url;

    public String getVerify_url() {
        return verify_url;
    }

    public void setVerify_url(String verify_url) {
        this.verify_url = verify_url;
    }
}
