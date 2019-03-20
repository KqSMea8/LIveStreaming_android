package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/9/14.
 */
public class App_auction_upload_imageActModel extends BaseActModel
{
    private String server_full_path;
    private String server_path;

    public String getServer_full_path()
    {
        return server_full_path;
    }

    public void setServer_full_path(String server_full_path)
    {
        this.server_full_path = server_full_path;
    }

    public String getServer_path()
    {
        return server_path;
    }

    public void setServer_path(String server_path)
    {
        this.server_path = server_path;
    }
}
