package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/4 0004.
 */

public class PayShouhuModel {

    private int status;
    private String diamonds;
    private String act;
    private String ctl;
    private List<GuardRuleListBean> guard_rule_list;

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

    public List<GuardRuleListBean> getGuard_rule_list() {
        return guard_rule_list;
    }

    public void setGuard_rule_list(List<GuardRuleListBean> guard_rule_list) {
        this.guard_rule_list = guard_rule_list;
    }

    public static class GuardRuleListBean {
        /**
         * id : 1
         * name : 12
         * icon : 12
         * sort : 12
         * is_animated : 1
         * is_effect : 1
         * anim_type : 1
         * pc_icon : 12
         * pc_gif : 12
         * gif_gift_show_style : 12
         * rules : [{"id":"1","guard_id":"1","day_length":"101","diamonds":"10","ticket":"20.00","score":"10","sort":"0"}]
         */
        private boolean checked;
        private String id;
        private String name;
        private String icon;
        private List<RulesBean> rules;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
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

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public static class RulesBean {
            /**
             * id : 1
             * guard_id : 1
             * day_length : 101
             * diamonds : 10
             * ticket : 20.00
             * score : 10
             * sort : 0
             */
            private boolean checked;
            private String id;
            private String guard_id;
            private String day_length;
            private String diamonds;

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
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
        }
    }
}
