package com.fanwe.live.model;


/**
 * Created by Administrator on 2016/9/26.
 */

public class App_family_indexItemModel
{
    private int family_id;//家族id
    private String family_logo;//家族logo
    private String family_name;//家族名称
    private String family_manifesto;//家族宣言
    private int create_time;//创建时间
    private String memo;//备注
    private int status;//审核状态
    private int user_count;//家族人数
    private String nick_name;//族长名

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

    public String getFamily_manifesto() {
        return family_manifesto;
    }

    public void setFamily_manifesto(String family_manifesto) {
        this.family_manifesto = family_manifesto;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_count() {
        return user_count;
    }

    public void setUser_count(int user_count) {
        this.user_count = user_count;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
}
