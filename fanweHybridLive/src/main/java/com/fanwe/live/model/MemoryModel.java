package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */

public class MemoryModel {
    /**
     * status : 1
     * tag_list : [{"id":"5","name":"acacacac\t1\t1\r\nCDC调查得出的\t0\t5","is_effect":"1","sort":"0","is_selected":0},{"id":"1","name":"acacacac","is_effect":"1","sort":"1","is_selected":1},{"id":"6","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"8","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"10","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"12","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"16","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"18","name":"acacacac","is_effect":"1","sort":"1","is_selected":0},{"id":"22","name":"acacacac","is_effect":"1","sort":"1","is_selected":0}]
     * my_selected : [{"tag_id":"1","tag_name":"hahah"}]
     * act : tag_list
     * ctl : user
     */

    private int status;
    private String act;
    private String ctl;
    private List<TagListBean> tag_list;
    private List<MySelectedBean> my_selected;

    @Override
    public String toString() {
        return "MemoryModel{" +
                "tag_list=" + tag_list +
                '}';
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

    public List<TagListBean> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<TagListBean> tag_list) {
        this.tag_list = tag_list;
    }

    public List<MySelectedBean> getMy_selected() {
        return my_selected;
    }

    public void setMy_selected(List<MySelectedBean> my_selected) {
        this.my_selected = my_selected;
    }

    public static class TagListBean {
        @Override
        public String toString() {
            return "TagListBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", is_effect='" + is_effect + '\'' +
                    ", sort='" + sort + '\'' +
                    ", is_selected=" + is_selected +
                    '}';
        }

        /**
         * id : 5
         * name : acacacac	1	1
         CDC调查得出的	0	5
         * is_effect : 1
         * sort : 0
         * is_selected : 0
         */

        private String id;
        private String name;
        private String is_effect;
        private String sort;
        private int is_selected;
        private int rgb=0;

        public int getRgb() {
            return rgb;
        }

        public void setRgb(int rgb) {
            this.rgb = rgb;
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

        public String getIs_effect() {
            return is_effect;
        }

        public void setIs_effect(String is_effect) {
            this.is_effect = is_effect;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public int getIs_selected() {
            return is_selected;
        }

        public void setIs_selected(int is_selected) {
            this.is_selected = is_selected;
        }
    }

    public static class MySelectedBean {
        /**
         * tag_id : 1
         * tag_name : hahah
         */

        private String id;
        private String name;

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
    }
}
