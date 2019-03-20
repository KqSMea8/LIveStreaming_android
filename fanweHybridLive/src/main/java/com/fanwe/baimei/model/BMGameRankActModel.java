package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by yhz on 2017/5/25.
 */

public class BMGameRankActModel extends BaseActModel
{
    private int page;
    private int has_next;
    private String desc;
    private List<BMGameRankBean> list;

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    public List<BMGameRankBean> getList()
    {
        return list;
    }

    public void setList(List<BMGameRankBean> list)
    {
        this.list = list;
    }

    public static class BMGameRankBean
    {
        /**
         * user_id : 101536
         * nick_name : JoJoWei
         * v_type : 0
         * v_icon : http://liveimage.fanwe.net/public/attachment/201611/04/15/581c36c72e655.png
         * head_image : http://liveimage.fanwe.net/public/attachment/201611/02/09/5819469111693.jpg@58-0-157-157a_0r.jpg
         * sex : 1
         * user_level : 102
         * ticket : 3516
         * is_authentication : 2
         * is_focus : 0
         */

        private String user_id;
        private String nick_name;
        private String v_type;
        private String v_icon;
        private String head_image;
        private int sex;
        private int user_level;
        private int ticket;
        private int coins;
        private String is_authentication;
        private int is_focus;

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getNick_name()
        {
            return nick_name;
        }

        public void setNick_name(String nick_name)
        {
            this.nick_name = nick_name;
        }

        public String getV_type()
        {
            return v_type;
        }

        public void setV_type(String v_type)
        {
            this.v_type = v_type;
        }

        public String getV_icon()
        {
            return v_icon;
        }

        public void setV_icon(String v_icon)
        {
            this.v_icon = v_icon;
        }

        public String getHead_image()
        {
            return head_image;
        }

        public void setHead_image(String head_image)
        {
            this.head_image = head_image;
        }

        public int getSex()
        {
            return sex;
        }

        public void setSex(int sex)
        {
            this.sex = sex;
        }

        public int getUser_level()
        {
            return user_level;
        }

        public void setUser_level(int user_level)
        {
            this.user_level = user_level;
        }

        public int getTicket()
        {
            return ticket;
        }

        public void setTicket(int ticket)
        {
            this.ticket = ticket;
        }

        public String getIs_authentication()
        {
            return is_authentication;
        }

        public void setIs_authentication(String is_authentication)
        {
            this.is_authentication = is_authentication;
        }

        public int getIs_focus()
        {
            return is_focus;
        }

        public void setIs_focus(int is_focus)
        {
            this.is_focus = is_focus;
        }

        public int getCoins()
        {
            return coins;
        }

        public void setCoins(int coins)
        {
            this.coins = coins;
        }
    }
}
