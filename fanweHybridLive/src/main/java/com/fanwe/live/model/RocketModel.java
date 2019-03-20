package com.fanwe.live.model;

/**
 * Created by Administrator on 2018/8/18 0018.
 */

public class RocketModel {

    @Override
    public String toString() {
        return "RocketModel{" +
                "user_id='" + user_id + '\'' +
                ", head_image='" + head_image + '\'' +
                ", thumb_head_image='" + thumb_head_image + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", room_id=" + room_id +
                '}';
    }

    public RocketModel(String nick_name) {
        this.nick_name = nick_name;
    }

    public RocketModel() {
    }

    /**
     * user_id : 200021
     * head_image : http://thirdwx.qlogo.cn/mmopen/vi_32/R4LcRsaDJiaoGPenpzXuf1cibDHG0YrOF5ciaYvPag1mRIyDIbeAFicQQ9X8yaRyAiaclkIbWicKicWMicmEelCqB7nYGQ/132
     * thumb_head_image :
     * nick_name : 哈哈200021
     * room_id :
     */

    private String user_id;
    private String head_image;
    private String thumb_head_image;
    private String nick_name;
    private int room_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}
