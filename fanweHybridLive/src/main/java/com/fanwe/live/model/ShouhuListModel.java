package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13 0013.
 */

public class ShouhuListModel {

    private int status;
    private String act;
    private String ctl;
    private List<ListBean> list;
    int has_next;
    int page=1;

    @Override
    public String toString() {
        return "ShouhuListModel{" +
                "list=" + list +
                '}';
    }

    public int getHas_next() {
        return has_next;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCtl() {
        return ctl;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "guard_id='" + guard_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }

        /**
         * id : 3
         * guard_id : 1
         * user_id : 200019
         * anchor_id : 200009
         * level : 1
         * start_time : 1533624634
         * end_time : 1542351034
         * level_name : 1
         * level_icon : http://livet1.iox1.com/public/attachment/temp/edu/courses.jpg
         * nick_name : 游客:200019
         * head_image : http://livet1.iox1.com/public/attachment/test/noavatar_0.JPG
         * thumb_head_image :
         * user_level : 6
         * v_icon :
         */
        private int in_room;

        public int getIn_room() {
            return in_room;
        }

        public void setIn_room(int in_room) {
            this.in_room = in_room;
        }

        private String id;
        private String guard_id;
        private String user_id;
        private String anchor_id;
        private String level;
        private String start_time;
        private String end_time;
        private String level_name;
        private String level_icon;
        private String nick_name;
        private String head_image;
        private String thumb_head_image;
        private String user_level;
        private String v_icon;
        private String icon;
        private int day;
        private int ticket;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getTicket() {
            return ticket;
        }

        public void setTicket(int ticket) {
            this.ticket = ticket;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getLevel_icon() {
            return level_icon;
        }

        public void setLevel_icon(String level_icon) {
            this.level_icon = level_icon;
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

        public String getThumb_head_image() {
            return thumb_head_image;
        }

        public void setThumb_head_image(String thumb_head_image) {
            this.thumb_head_image = thumb_head_image;
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
