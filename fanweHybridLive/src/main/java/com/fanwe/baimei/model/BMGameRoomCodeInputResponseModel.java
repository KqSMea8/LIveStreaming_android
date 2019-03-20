package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 包名: com.fanwe.baimei.model
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/27 11:03
 **/
public class BMGameRoomCodeInputResponseModel extends BaseActModel
{

    /**
     * room : {"room_id":"638913","sort_num":"200000000","group_id":"638913","user_id":"167269","city":"衡水","title":"","cate_id":"0","live_in":"1","video_type":"1","create_type":"0","room_type":"1","watch_number":"0","head_image":"http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg","thumb_head_image":"http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg?x-oss-process=image/resize,m_mfit,h_150,w_150","xpoint":"115.583179","ypoint":"37.544878","v_type":"0","v_icon":null,"nick_name":"167269","user_level":"25","live_image":"http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg","is_live_pay":"0","live_pay_type":"0","live_fee":"0","user_create_time":"1487294114","today_create":0,"game_id":"1","sdk_type":0}
     */

    private RoomBean room;

    public RoomBean getRoom()
    {
        return room;
    }

    public void setRoom(RoomBean room)
    {
        this.room = room;
    }

    public static class RoomBean
    {
        /**
         * room_id : 638913
         * sort_num : 200000000
         * group_id : 638913
         * user_id : 167269
         * city : 衡水
         * title :
         * cate_id : 0
         * live_in : 1
         * video_type : 1
         * create_type : 0
         * room_type : 1
         * watch_number : 0
         * head_image : http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg
         * thumb_head_image : http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg?x-oss-process=image/resize,m_mfit,h_150,w_150
         * xpoint : 115.583179
         * ypoint : 37.544878
         * v_type : 0
         * v_icon : null
         * nick_name : 167269
         * user_level : 25
         * live_image : http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg
         * is_live_pay : 0
         * live_pay_type : 0
         * live_fee : 0
         * user_create_time : 1487294114
         * today_create : 0
         * game_id : 1
         * sdk_type : 0
         */

        private String room_id;
        private String sort_num;
        private String group_id;
        private String user_id;
        private String city;
        private String title;
        private String cate_id;
        private String live_in;
        private String video_type;
        private String create_type;
        private String room_type;
        private String watch_number;
        private String head_image;
        private String thumb_head_image;
        private String xpoint;
        private String ypoint;
        private String v_type;
        private Object v_icon;
        private String nick_name;
        private String user_level;
        private String live_image;
        private String is_live_pay;
        private String live_pay_type;
        private String live_fee;
        private String user_create_time;
        private int today_create;
        private String game_id;
        private int sdk_type;

        public String getRoom_id()
        {
            return room_id;
        }

        public void setRoom_id(String room_id)
        {
            this.room_id = room_id;
        }

        public String getSort_num()
        {
            return sort_num;
        }

        public void setSort_num(String sort_num)
        {
            this.sort_num = sort_num;
        }

        public String getGroup_id()
        {
            return group_id;
        }

        public void setGroup_id(String group_id)
        {
            this.group_id = group_id;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getCate_id()
        {
            return cate_id;
        }

        public void setCate_id(String cate_id)
        {
            this.cate_id = cate_id;
        }

        public String getLive_in()
        {
            return live_in;
        }

        public void setLive_in(String live_in)
        {
            this.live_in = live_in;
        }

        public String getVideo_type()
        {
            return video_type;
        }

        public void setVideo_type(String video_type)
        {
            this.video_type = video_type;
        }

        public String getCreate_type()
        {
            return create_type;
        }

        public void setCreate_type(String create_type)
        {
            this.create_type = create_type;
        }

        public String getRoom_type()
        {
            return room_type;
        }

        public void setRoom_type(String room_type)
        {
            this.room_type = room_type;
        }

        public String getWatch_number()
        {
            return watch_number;
        }

        public void setWatch_number(String watch_number)
        {
            this.watch_number = watch_number;
        }

        public String getHead_image()
        {
            return head_image;
        }

        public void setHead_image(String head_image)
        {
            this.head_image = head_image;
        }

        public String getThumb_head_image()
        {
            return thumb_head_image;
        }

        public void setThumb_head_image(String thumb_head_image)
        {
            this.thumb_head_image = thumb_head_image;
        }

        public String getXpoint()
        {
            return xpoint;
        }

        public void setXpoint(String xpoint)
        {
            this.xpoint = xpoint;
        }

        public String getYpoint()
        {
            return ypoint;
        }

        public void setYpoint(String ypoint)
        {
            this.ypoint = ypoint;
        }

        public String getV_type()
        {
            return v_type;
        }

        public void setV_type(String v_type)
        {
            this.v_type = v_type;
        }

        public Object getV_icon()
        {
            return v_icon;
        }

        public void setV_icon(Object v_icon)
        {
            this.v_icon = v_icon;
        }

        public String getNick_name()
        {
            return nick_name;
        }

        public void setNick_name(String nick_name)
        {
            this.nick_name = nick_name;
        }

        public String getUser_level()
        {
            return user_level;
        }

        public void setUser_level(String user_level)
        {
            this.user_level = user_level;
        }

        public String getLive_image()
        {
            return live_image;
        }

        public void setLive_image(String live_image)
        {
            this.live_image = live_image;
        }

        public String getIs_live_pay()
        {
            return is_live_pay;
        }

        public void setIs_live_pay(String is_live_pay)
        {
            this.is_live_pay = is_live_pay;
        }

        public String getLive_pay_type()
        {
            return live_pay_type;
        }

        public void setLive_pay_type(String live_pay_type)
        {
            this.live_pay_type = live_pay_type;
        }

        public String getLive_fee()
        {
            return live_fee;
        }

        public void setLive_fee(String live_fee)
        {
            this.live_fee = live_fee;
        }

        public String getUser_create_time()
        {
            return user_create_time;
        }

        public void setUser_create_time(String user_create_time)
        {
            this.user_create_time = user_create_time;
        }

        public int getToday_create()
        {
            return today_create;
        }

        public void setToday_create(int today_create)
        {
            this.today_create = today_create;
        }

        public String getGame_id()
        {
            return game_id;
        }

        public void setGame_id(String game_id)
        {
            this.game_id = game_id;
        }

        public int getSdk_type()
        {
            return sdk_type;
        }

        public void setSdk_type(int sdk_type)
        {
            this.sdk_type = sdk_type;
        }
    }
}
