package com.fanwe.live.model.custommsg;

/**
 * Created by Administrator on 2018/8/15 0015.
 */

public class SenderBean {
    /**
     * user_id : 200009
     * nick_name : 测2
     * head_image : http://thirdwx.qlogo.cn/mmopen/vi_32/Ie0jE1E0bO7xuxJibF4zfZeCLjeVIXp8aX9OhOomVJ6ZROZ4Pycr0P5JiaG8oHrgCMx8glU8QqhjmLxQzSQMlNnA/132
     * user_level : 56
     * v_icon : http://liveimage.fanwe.net/public/attachment/201707/10/16/59633b5a41afb.png
     */

    private int user_id;
    private String nick_name;
    private String head_image;
    private String user_level;
    private String v_icon;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getV_icon() {
        return v_icon;
    }

    public void setV_icon(String v_icon) {
        this.v_icon = v_icon;
    }
}
