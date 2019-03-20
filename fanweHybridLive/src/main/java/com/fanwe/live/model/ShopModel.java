package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/21 0021.
 */

public class ShopModel {
    /**
     * status : 1
     * diamonds : 9633681
     * coin : 0
     * mount_rule_list : [{"id":"1","name":"小姐姐","icon":"http://site.88817235.cn/public/attachment/test/noavatar_1.JPG","sort":"1","is_animated":"1","is_effect":"1","anim_type":"lamborghini","pc_icon":"http://site.88817235.cn/public/attachment/201808/16/13/5b75102697826.png","pc_gif":"http://site.88817235.cn/public/attachment/distribution_qrcode/qrcode_1.png","gif_gift_show_style":"0","desc":"%s开着坦克进来了","rules":[{"id":"7","mount_id":"1","day_length":"101","diamonds":"9999","score":"1","sort":"0"}]}]
     * act : mount_rule_list
     * ctl : mount
     */

    private int status;
    private String diamonds;
    private String coin;
    private String act;
    private String ctl;
    private List<MountRuleListBean> mount_rule_list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(String diamonds) {
        this.diamonds = diamonds;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
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

    public List<MountRuleListBean> getMount_rule_list() {
        return mount_rule_list;
    }

    public void setMount_rule_list(List<MountRuleListBean> mount_rule_list) {
        this.mount_rule_list = mount_rule_list;
    }

    public static class MountRuleListBean {
        /**
         * id : 1
         * name : 小姐姐
         * icon : http://site.88817235.cn/public/attachment/test/noavatar_1.JPG
         * sort : 1
         * is_animated : 1
         * is_effect : 1
         * anim_type : lamborghini
         * pc_icon : http://site.88817235.cn/public/attachment/201808/16/13/5b75102697826.png
         * pc_gif : http://site.88817235.cn/public/attachment/distribution_qrcode/qrcode_1.png
         * gif_gift_show_style : 0
         * desc : %s开着坦克进来了
         * rules : [{"id":"7","mount_id":"1","day_length":"101","diamonds":"9999","score":"1","sort":"0"}]
         */

        private String id;
        private String name;
        private String icon;
        private String sort;
        private String is_animated;
        private String is_effect;
        private String anim_type;
        private String pc_icon;
        private String pc_gif;
        private String gif_gift_show_style;
        private String desc;
        private List<RulesBean> rules;

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

        public String getIs_effect() {
            return is_effect;
        }

        public void setIs_effect(String is_effect) {
            this.is_effect = is_effect;
        }

        public String getAnim_type() {
            return anim_type;
        }

        public void setAnim_type(String anim_type) {
            this.anim_type = anim_type;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public static class RulesBean {
            /**
             * id : 7
             * mount_id : 1
             * day_length : 101
             * diamonds : 9999
             * score : 1
             * sort : 0
             */
            private boolean ispk;

            public boolean ispk() {
                return ispk;
            }

            public void setIspk(boolean ispk) {
                this.ispk = ispk;
            }

            private String id;
            private String mount_id;
            private String day_length;
            private String diamonds;
            private String score;
            private String sort;
            private String end_time_desc;

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

            public String getMount_id() {
                return mount_id;
            }

            public void setMount_id(String mount_id) {
                this.mount_id = mount_id;
            }

            public String getDay_length() {
                return day_length;
            }

            public void setDay_length(String day_length) {
                this.day_length = day_length;
            }

            public String getDiamonds() {
                return diamonds;
            }

            public void setDiamonds(String diamonds) {
                this.diamonds = diamonds;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
    }
}
