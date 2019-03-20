package com.weibo.model;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述
 * @作者 Su
 * @创建时间 2017/4/6 15:21
 **/
public class XRDynamicImagesBean
{
    /**
     * is_model : 0
     * url : http://liveimage.fanwe.net/public/attachment/201703/167314/1489991431254.jpg?x-oss-process=image/resize,m_mfit,h_50,w_50
     */

    private int is_model;
    private String url;
    private String orginal_url;


    public int getIs_model()
    {
        return is_model;
    }

    public void setIs_model(int is_model)
    {
        this.is_model = is_model;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getOrginal_url()
    {
        return orginal_url;
    }

    public void setOrginal_url(String orginal_url)
    {
        this.orginal_url = orginal_url;
    }

    @Override
    public String toString()
    {
        return "XRDynamicImagesBean{" +
                "is_model=" + is_model +
                ", url='" + url + '\'' +
                ", orginal_url='" + orginal_url + '\'' +
                '}';
    }
}
