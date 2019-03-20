package com.fanwe.live.model;

/**
 * Created by Administrator on 2018/7/7 0007.
 */

public class UserShouhuModel {
    private String name;
    private String head_url;
    private int shouhu_type;
    private int star_level;
    private boolean status;
    private int page;
    private int has_next;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public UserShouhuModel(String name, String head_url, int shouhu_type, int star_level, boolean status) {
        this.name = name;
        this.head_url = head_url;
        this.shouhu_type = shouhu_type;
        this.star_level = star_level;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public int getShouhu_type() {
        return shouhu_type;
    }

    public void setShouhu_type(int shouhu_type) {
        this.shouhu_type = shouhu_type;
    }

    public int getStar_level() {
        return star_level;
    }

    public void setStar_level(int star_level) {
        this.star_level = star_level;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
