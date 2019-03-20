package com.fanwe.live.model;

import com.fanwe.live.gif.GifConfigModel;
import com.fanwe.live.model.custommsg.SenderBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10 0010.
 */

public class UserJoinGuard {

    /**
     * guard_id : 1
     * user_id : 200009
     * anchor_id : 200021
     * level : 1
     * start_time : 1533702379
     * end_time : 1535430379
     * sender : {"user_id":200009,"nick_name":"测2","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/Ie0jE1E0bO7xuxJibF4zfZeCLjeVIXp8aX9OhOomVJ6ZROZ4Pycr0P5JiaG8oHrgCMx8glU8QqhjmLxQzSQMlNnA/132","user_level":"56","v_icon":"http://liveimage.fanwe.net/public/attachment/201707/10/16/59633b5a41afb.png"}
     * level_info : {"id":"1","name":"1","level":"1","point":"1","icon":"http://livet1.iox1.com/public/attachment/temp/edu/courses.jpg"}
     * guard_animated : {"id":"1","name":"测试","icon":"http://site.88817235.cn/public/attachment/test/noavatar_1.JPG","sort":"12","is_animated":"2","anim_type":"lamborghini","content":"嗯哼开着坦克进来了","pc_icon":"http://site.88817235.cn/public/attachment/test/noavatar_5.JPG","pc_gif":"http://site.88817235.cn/public/attachment/test/noavatar_1.JPG","gif_gift_show_style":"1","anim_cfg":[]}
     */

    private String guard_id;
    private String user_id;
    private String anchor_id;
    private String level;
    private String start_time;
    private String end_time;
    private String to_user_id;
    private SenderBean sender;
    private LevelInfoBean level_info;
    private GuardAnimatedBean guard_animated;

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getGuard_id() {
        return guard_id;
    }

    public void setGuard_id(String guard_id) {
        this.guard_id = guard_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAnchor_id() {
        return anchor_id;
    }

    public void setAnchor_id(String anchor_id) {
        this.anchor_id = anchor_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public SenderBean getSender() {
        return sender;
    }

    public void setSender(SenderBean sender) {
        this.sender = sender;
    }

    public LevelInfoBean getLevel_info() {
        return level_info;
    }

    public void setLevel_info(LevelInfoBean level_info) {
        this.level_info = level_info;
    }

    public GuardAnimatedBean getGuard_animated() {
        return guard_animated;
    }

    public void setGuard_animated(GuardAnimatedBean guard_animated) {
        this.guard_animated = guard_animated;
    }

    public static class LevelInfoBean {
        @Override
        public String toString() {
            return "LevelInfoBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", level='" + level + '\'' +
                    ", point='" + point + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }

        /**
         * id : 1
         * name : 1
         * level : 1
         * point : 1
         * icon : http://livet1.iox1.com/public/attachment/temp/edu/courses.jpg
         */

        private String id;
        private String name;
        private String level;
        private String point;
        private String icon;

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    @Override
    public String toString() {
        return "App_get_video_add{" +
                "guard_id='" + guard_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", anchor_id='" + anchor_id + '\'' +
                ", level='" + level + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", to_user_id='" + to_user_id + '\'' +
                ", sender=" + sender +
                ", level_info=" + level_info +
                ", guard_animated=" + guard_animated +
                '}';
    }

    public static class GuardAnimatedBean {
        @Override
        public String toString() {
            return "GuardAnimatedBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", sort='" + sort + '\'' +
                    ", is_animated='" + is_animated + '\'' +
                    ", anim_type='" + anim_type + '\'' +
                    ", content='" + content + '\'' +
                    ", pc_icon='" + pc_icon + '\'' +
                    ", pc_gif='" + pc_gif + '\'' +
                    ", gif_gift_show_style='" + gif_gift_show_style + '\'' +
                    ", anim_cfg=" + anim_cfg +
                    '}';
        }

        /**
         * id : 1
         * name : 测试
         * icon : http://site.88817235.cn/public/attachment/test/noavatar_1.JPG
         * sort : 12
         * is_animated : 2
         * anim_type : lamborghini
         * content : 嗯哼开着坦克进来了
         * pc_icon : http://site.88817235.cn/public/attachment/test/noavatar_5.JPG
         * pc_gif : http://site.88817235.cn/public/attachment/test/noavatar_1.JPG
         * gif_gift_show_style : 1
         * anim_cfg : []
         */

        private String id;
        private String name;
        private String icon;
        private String sort;
        private String is_animated;
        private String anim_type;
        private String content;
        private String pc_icon;
        private String pc_gif;
        private String gif_gift_show_style;
        private List<GifConfigModel> anim_cfg;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getGif_gift_show_style() {
            return gif_gift_show_style;
        }

        public void setGif_gift_show_style(String gif_gift_show_style) {
            this.gif_gift_show_style = gif_gift_show_style;
        }

        public List<GifConfigModel> getAnim_cfg() {
            return anim_cfg;
        }

        public void setAnim_cfg(List<GifConfigModel> anim_cfg) {
            this.anim_cfg = anim_cfg;
        }
    }
}
