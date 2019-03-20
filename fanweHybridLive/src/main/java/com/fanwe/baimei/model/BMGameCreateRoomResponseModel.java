package com.fanwe.baimei.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 包名: com.fanwe.baimei.model
 * 描述: 游戏中心-创建房间返回实体类
 * 作者: Su
 * 创建时间: 2017/5/22 15:58
 **/
public class BMGameCreateRoomResponseModel extends BaseActModel
{

    /**
     * room_id : 639691
     * video_type : 1
     * private_key : 625567
     * sdk_type : 0
     * share : {"share_title":"你丑你先睡，我美我直播","share_imageUrl":"./public/attachment/201704/167314/1492678147021.png","share_key":639691,"share_url":"http://ilvbt8.fanwe.net/wap/index.php?ctl=share&act=live&user_id=&video_id=639691&share_id=167314","share_content":"你丑你先睡，我美我直播正在直播,快来一起看~"}
     * ctl : games
     */

    private int has_room ;//0 创建房间  1 我的房间
    private int room_id;
    private int video_type;
    private int private_key;
    private int sdk_type;
    private ShareBean share;

    public int getHas_room()
    {
        return has_room;
    }

    public void setHas_room(int has_room)
    {
        this.has_room = has_room;
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getVideo_type()
    {
        return video_type;
    }

    public void setVideo_type(int video_type)
    {
        this.video_type = video_type;
    }

    public int getPrivate_key()
    {
        return private_key;
    }

    public void setPrivate_key(int private_key)
    {
        this.private_key = private_key;
    }

    public int getSdk_type()
    {
        return sdk_type;
    }

    public void setSdk_type(int sdk_type)
    {
        this.sdk_type = sdk_type;
    }

    public ShareBean getShare()
    {
        return share;
    }

    public void setShare(ShareBean share)
    {
        this.share = share;
    }


    public static class ShareBean
    {
        /**
         * share_title : 你丑你先睡，我美我直播
         * share_imageUrl : ./public/attachment/201704/167314/1492678147021.png
         * share_key : 639691
         * share_url : http://ilvbt8.fanwe.net/wap/index.php?ctl=share&act=live&user_id=&video_id=639691&share_id=167314
         * share_content : 你丑你先睡，我美我直播正在直播,快来一起看~
         */

        private String share_title;
        private String share_imageUrl;
        private int share_key;
        private String share_url;
        private String share_content;

        public String getShare_title()
        {
            return share_title;
        }

        public void setShare_title(String share_title)
        {
            this.share_title = share_title;
        }

        public String getShare_imageUrl()
        {
            return share_imageUrl;
        }

        public void setShare_imageUrl(String share_imageUrl)
        {
            this.share_imageUrl = share_imageUrl;
        }

        public int getShare_key()
        {
            return share_key;
        }

        public void setShare_key(int share_key)
        {
            this.share_key = share_key;
        }

        public String getShare_url()
        {
            return share_url;
        }

        public void setShare_url(String share_url)
        {
            this.share_url = share_url;
        }

        public String getShare_content()
        {
            return share_content;
        }

        public void setShare_content(String share_content)
        {
            this.share_content = share_content;
        }
    }
}
