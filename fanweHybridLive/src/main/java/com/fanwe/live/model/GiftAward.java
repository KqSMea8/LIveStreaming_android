package com.fanwe.live.model;

import com.fanwe.live.gif.GifConfigModel;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class GiftAward {

    private int status;
    private String error;
    private int award_log_id;
    private int winning_type;
    private int winning_num;
    private String prop_id;
    private int user_id;
    private int is_animated;
    private int gif_gift_show_style;
    private String desc;
    private String desc2;
    private List<AwardListBean> award_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getAward_log_id() {
        return award_log_id;
    }

    public void setAward_log_id(int award_log_id) {
        this.award_log_id = award_log_id;
    }

    public int getWinning_type() {
        return winning_type;
    }

    public void setWinning_type(int winning_type) {
        this.winning_type = winning_type;
    }

    public int getWinning_num() {
        return winning_num;
    }

    public void setWinning_num(int winning_num) {
        this.winning_num = winning_num;
    }

    public String getProp_id() {
        return prop_id;
    }

    public void setProp_id(String prop_id) {
        this.prop_id = prop_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getIs_animated() {
        return is_animated;
    }

    public void setIs_animated(int is_animated) {
        this.is_animated = is_animated;
    }

    public int getGif_gift_show_style() {
        return gif_gift_show_style;
    }

    public void setGif_gift_show_style(int gif_gift_show_style) {
        this.gif_gift_show_style = gif_gift_show_style;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public List<AwardListBean> getAward_list() {
        return award_list;
    }

    public void setAward_list(List<AwardListBean> award_list) {
        this.award_list = award_list;
    }


    public static class AwardListBean {
        /**
         * is_animated : 1
         * anim_cfg : [{"url":"http://site.88817235.cn/public/attachment/201808/14/14/5b72793bd2b4e.gif","play_count":"3","delay_time":"100","duration":"200","show_user":"1","type":"2","gif_gift_show_style":null}]
         */

        private int is_animated;
        private List<GifConfigModel> anim_cfg;

        public int getIs_animated() {
            return is_animated;
        }

        public void setIs_animated(int is_animated) {
            this.is_animated = is_animated;
        }

        public List<GifConfigModel> getAnim_cfg() {
            return anim_cfg;
        }

        public void setAnim_cfg(List<GifConfigModel> anim_cfg) {
            this.anim_cfg = anim_cfg;
        }
    }
}
