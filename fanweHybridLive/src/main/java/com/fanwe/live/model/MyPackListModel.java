package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23 0023.
 */

public class MyPackListModel {
    /**
     * status : 1
     * mounts_list : [{"id":"1","user_id":"200009","mount_id":"1","create_time":"0","end_time":"1539002023","name":"小姐姐","icon":"http://site.88817235.cn/public/attachment/201808/21/18/5b7bea29a5072.png","is_expired":0,"is_use":0}]
     * act : my_mounts
     * ctl : mount
     */

    private int status;
    private String act;
    private String ctl;
    private List<MountsListBean> mounts_list;

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

    public List<MountsListBean> getMounts_list() {
        return mounts_list;
    }

    public void setMounts_list(List<MountsListBean> mounts_list) {
        this.mounts_list = mounts_list;
    }

    public static class MountsListBean {
        /**
         * id : 1
         * user_id : 200009
         * mount_id : 1
         * create_time : 0
         * end_time : 1539002023
         * name : 小姐姐
         * icon : http://site.88817235.cn/public/attachment/201808/21/18/5b7bea29a5072.png
         * is_expired : 0
         * is_use : 0
         */

        private String id;
        private String user_id;
        private String mount_id;
        private String create_time;
        private String end_time;
        private String name;
        private String icon;
        private String end_time_desc;
        private int is_expired;
        private int is_use;

        public String getEnd_time_desc() {
            return end_time_desc;
        }

        public void setEnd_time_desc(String end_time_desc) {
            this.end_time_desc = end_time_desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMount_id() {
            return mount_id;
        }

        public void setMount_id(String mount_id) {
            this.mount_id = mount_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
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

        public int getIs_expired() {
            return is_expired;
        }

        public void setIs_expired(int is_expired) {
            this.is_expired = is_expired;
        }

        public int getIs_use() {
            return is_use;
        }

        public void setIs_use(int is_use) {
            this.is_use = is_use;
        }
    }
}
