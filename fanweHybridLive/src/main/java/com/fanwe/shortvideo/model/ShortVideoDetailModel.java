package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 小视频列表
 *
 * @author wxy
 */
public class ShortVideoDetailModel extends BaseActModel {

    public List<VideoDetail> video;//

    public static class VideoDetail {
        public String sv_id;//21,
        public String sv_userid;//101872,
        public String sv_url;//http;////1253918958.vod2.myqcloud.com/397c1fb2vodgzp1253918958/b10ea18c4564972819017555603/8uV91jan03sA.mp4,
        public String sv_img;//http;////1253918958.vod2.myqcloud.com/397c1fb2vodgzp1253918958/b10ea18c4564972819017555603/4564972819017555604.jpg,
        public String sv_content;//,
        public String click;//6,
        public String sv_time;//1516047306,
        public String user_id;//101872,
        public String nick_name;//映秀官方巡查,
        public String head_image;//http;////wx.qlogo.cn/mmopen/vi_32/KbC410CniaDHPZN5NZ8JMpXia3RdicbxPxdwCpFQYtmxvkSe6dUbEQXRXzaicnde5GAIoiaXKoctEj85o5cHANyAeYw/96,
        public String thumb_head_image;//,
        public String count_praise="0";//2,
        public String count_comment;//10
        public String count_gift;
        public String is_praise;
        public int is_attention;
        public String share_url;
    }
}

