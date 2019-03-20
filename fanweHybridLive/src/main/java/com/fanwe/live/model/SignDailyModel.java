package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class SignDailyModel {

    private int status;
    private String error;
    private int now_is_sign;
    private String act;
    private String ctl;
    private List<ListBean> list;

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

    public int getNow_is_sign() {
        return now_is_sign;
    }

    public void setNow_is_sign(int now_is_sign) {
        this.now_is_sign = now_is_sign;
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
        /**
         * id : 1
         * name : 第一天13
         * desc : 送小姐姐一个
         * prop_id : 38
         * num : 10
         * day : 1
         * is_effect : 1
         * prop_name : 全省
         * icon : https://osshttps.fanwe.net/public/attachment/201712/29/10/5a45ad3915547.jpg
         * prop_is_effect : 1
         * is_sign : 1
         */

        private String id;
        private String name;
        private String desc;
        private String prop_id;
        private int num;
        private String day;
        private String is_effect;
        private String prop_name;
        private String icon;
        private String prop_is_effect;
        private int is_sign;

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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getProp_id() {
            return prop_id;
        }

        public void setProp_id(String prop_id) {
            this.prop_id = prop_id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getIs_effect() {
            return is_effect;
        }

        public void setIs_effect(String is_effect) {
            this.is_effect = is_effect;
        }

        public String getProp_name() {
            return prop_name;
        }

        public void setProp_name(String prop_name) {
            this.prop_name = prop_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getProp_is_effect() {
            return prop_is_effect;
        }

        public void setProp_is_effect(String prop_is_effect) {
            this.prop_is_effect = prop_is_effect;
        }

        public int getIs_sign() {
            return is_sign;
        }

        public void setIs_sign(int is_sign) {
            this.is_sign = is_sign;
        }
    }
}
