package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/9/24.
 */

public class App_family_listItemModel
{
    private int family_id;//家族ID
    private String family_logo;//家族logo
    private String family_name;//家族名称
    private String user_id;//家族长ID
    private String nick_name;//家族长昵称
    private String create_time;//创建时间
    private int user_count;//家族成员数量
    private int is_apply;//1：已提交、0：未提交

    private boolean is_check;//申请中true,加入家族 false

    public int getFamily_id() {
        return family_id;
    }

    public void setFamily_id(int family_id) {
        this.family_id = family_id;
    }

    public String getFamily_logo() {
        return family_logo;
    }

    public void setFamily_logo(String family_logo) {
        this.family_logo = family_logo;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public int getIs_apply() {
        return is_apply;
    }

    public void setIs_apply(int is_apply) {
        this.is_apply = is_apply;
    }

    public boolean is_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }
}
