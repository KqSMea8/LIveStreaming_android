package com.weibo.model;

import java.io.Serializable;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述
 * @作者 Su
 * @创建时间 2017/3/17 17:35
 **/
public class XRCommentNetworkImageModel implements Serializable
{
    private String imgPath;
    private String imgPathHD;
    private boolean blur;

    public XRCommentNetworkImageModel(String imgPath)
    {
        this.imgPath = imgPath;
    }

    public XRCommentNetworkImageModel(String imgPath, boolean blur)
    {
        this.imgPath = imgPath;
        this.blur = blur;
    }

    public XRCommentNetworkImageModel(String imgPath, String imgPathHD, boolean blur)
    {
        this.imgPath = imgPath;
        this.imgPathHD = imgPathHD;
        this.blur = blur;
    }

    public XRCommentNetworkImageModel(String imgPath, String imgPathHD)
    {
        this.imgPath = imgPath;
        this.imgPathHD = imgPathHD;
    }

    public String getImgPath()
    {
        return imgPath;
    }

    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }

    public boolean isBlur()
    {
        return blur;
    }

    public void setBlur(boolean blur)
    {
        this.blur = blur;
    }

    public String getImgPathHD()
    {
        if (imgPathHD == null || imgPathHD.isEmpty())
        {
            return imgPath;
        }
        return imgPathHD;
    }

    public void setImgPathHD(String imgPathHD)
    {
        this.imgPathHD = imgPathHD;
    }

}
