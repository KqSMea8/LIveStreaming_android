package com.fanwe.live.model;

import com.fanwe.live.gif.GifConfigModel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23 0023.
 */

public class UserJoinMount {
    /**
     * id : 1
     * name : 小姐姐
     * icon : http://site.88817235.cn/public/attachment/201808/21/18/5b7bea29a5072.png
     * pc_icon : ./public/attachment/201808/16/13/5b75102697826.png
     * pc_gif : ./public/attachment/distribution_qrcode/qrcode_1.png
     * sort : 1
     * is_animated : 1
     * anim_type : lamborghini
     * gif_gift_show_style : 0
     * anim_cfg : [{"id":"27","url":"http://site.88817235.cn/public/attachment/201808/14/14/5b72793bd2b4e.gif","play_count":"2","delay_time":"100","duration":"10000","show_user":"0","type":"0","gif_gift_show_style":"0"}]
     * to_user_id : 200012
     * sender : {"user_id":200009,"nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","user_level":"64","v_icon":"http://liveimage.fanwe.net/public/attachment/201707/10/16/59633b5a41afb.png"}
     */

    private String id;
    private String name;
    private String icon;
    private String pc_icon;
    private String pc_gif;
    private String sort;
    private String is_animated;
    private String anim_type;
    private String gif_gift_show_style;
    private String to_user_id;
    private SenderBean sender;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private List<GifConfigModel> anim_cfg;

    public List<GifConfigModel> getAnim_cfg() {
        return anim_cfg;
    }

    public void setAnim_cfg(List<GifConfigModel> anim_cfg) {
        this.anim_cfg = anim_cfg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPc_icon() {
        return pc_icon;
    }

    public void setPc_icon(String pc_icon) {
        this.pc_icon = pc_icon;
    }

    public String getPc_gif() {
        return pc_gif;
    }

    public void setPc_gif(String pc_gif) {
        this.pc_gif = pc_gif;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIs_animated() {
        return is_animated;
    }

    public void setIs_animated(String is_animated) {
        this.is_animated = is_animated;
    }

    public String getAnim_type() {
        return anim_type;
    }

    public void setAnim_type(String anim_type) {
        this.anim_type = anim_type;
    }

    public String getGif_gift_show_style() {
        return gif_gift_show_style;
    }

    public void setGif_gift_show_style(String gif_gift_show_style) {
        this.gif_gift_show_style = gif_gift_show_style;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }


    public static class SenderBean {
        /**
         * user_id : 200009
         * nick_name : 小姐姐
         * head_image : http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg
         * user_level : 64
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

}
