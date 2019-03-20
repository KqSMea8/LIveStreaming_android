package com.weibo.model;

/**
 * Author: Su
 * Description:
 * Date: 2017/3/9 8:52
 */
public class XRSimplePhotoModel
{
    private String pathThumb;
    private String pathHD;
    private boolean isAdd;

    public XRSimplePhotoModel(String pathThumb)
    {
        this.pathThumb = pathThumb;
    }

    public XRSimplePhotoModel(String pathThumb, String pathHD)
    {
        this.pathThumb = pathThumb;
        this.pathHD = pathHD;
    }

    public XRSimplePhotoModel(String pathThumb, boolean isAdd)
    {
        this.pathThumb = pathThumb;
        this.isAdd = isAdd;
    }

    public String getPathThumb()
    {
        return pathThumb;
    }

    public boolean isAdd()
    {
        return isAdd;
    }

    public String getPathHD()
    {
        if (pathHD == null || pathHD.isEmpty())
        {
            return pathThumb;
        }
        return pathHD;
    }

    public void setPathHD(String pathHD)
    {
        this.pathHD = pathHD;
    }

    @Override
    public String toString()
    {
        return "XRSimplePhotoModel{" +
                "pathThumb='" + pathThumb + '\'' +
                ", pathHD='" + pathHD + '\'' +
                ", isAdd=" + isAdd +
                '}';
    }
}
