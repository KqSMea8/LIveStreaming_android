package com.weibo.model;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述
 * @作者 Su
 * @创建时间 2017/4/21 9:14
 **/
public class XRInviteModel
{
    /**
     * title : 【推荐】《方维直播》
     * content : 167314 请您加入 APP,让您毫不费力地发照片挣红包，还有美女帅哥排队等您铃！
     * imageUrl : http://liveimage.fanwe.net/public/attachment/201703/31/09/58ddb7aeb550c.jpg
     * clickUrl : http://livet1.fanwe.net/wap/xr/index.html#/activeIndex?user_id=167314
     */

    private String title;
    private String content;
    private String imageUrl;
    private String clickUrl;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getClickUrl()
    {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl)
    {
        this.clickUrl = clickUrl;
    }
}
