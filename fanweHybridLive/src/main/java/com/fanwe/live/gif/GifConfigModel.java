package com.fanwe.live.gif;


public class GifConfigModel
{
    /**
     * gif靠屏幕顶部显示
     */
    public static final int TYPE_GRAVITY_TOP = 1;
    /**
     * gif靠屏幕中间显示
     */
    public static final int TYPE_GRAVITY_MIDDLE = 2;
    /**
     * gif靠屏幕底部显示
     */
    public static final int TYPE_GRAVITY_BOTTOM = 3;

    private String url; // gif链接
    private int type;// gif显示位置
    private long duration; // gif允许播放时长
    private long delay_time;// 延迟多少毫秒后播放gif
    private String play_count; // gif播放次数
    private int show_user; // 是否显示用户信息在gif的顶部(1-显示)


    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
    }

    public int getPlay_count()
    {
        return Integer.parseInt(play_count);
    }

    public void setPlay_count(String play_count)
    {
        this.play_count = play_count;
    }

    public int getShow_user()
    {
        return show_user;
    }

    public void setShow_user(int show_user)
    {
        this.show_user = show_user;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public long getDelay_time()
    {
        return delay_time;
    }

    public void setDelay_time(long delay_time)
    {
        if (delay_time < 0)
        {
            delay_time = 0;
        }
        this.delay_time = delay_time;
    }

}
