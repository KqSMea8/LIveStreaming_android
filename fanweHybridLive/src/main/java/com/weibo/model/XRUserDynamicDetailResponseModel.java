package com.weibo.model;

import java.util.List;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述 动态详情请求返回数据实体
 * @作者 Su
 * @创建时间 2017/3/23 10:33
 **/
public class XRUserDynamicDetailResponseModel
{

    /**
     * info : {"user_id":"100996","weibo_id":"135","head_image":"http://q.qlogo.cn/qqapp/1105454078/25DE6096F071135A21F28830182DF570/100","is_authentication":"0","content":"bad","red_count":"0","digg_count":"1","comment_count":"8","video_count":"0","nick_name":"海棠\u2018依旧","sort_num":"0","photo_image":"","city":"厦门","is_top":"0","price":"0.00","type":"imagetext","create_time":"2017-03-20 16:32:42","left_time":"23小时前","images_count":1,"images":[{"url":"http://liveimage.fanwe.net/public/attachment/201703/100996/201703200432398.png?x-oss-process=image/resize,m_mfit,h_50,w_50","is_model":0}],"video_url":"","has_digg":0}
     * digg_list : [{"user_id":"100992","is_authentication":"0","nick_name":"美丽世界的孤儿","head_image":"http://liveimage.fanwe.net/public/attachment/201609/01/09/origin/1472663010100992.jpg"}]
     * comment_list : [{"user_id":"100992","nick_name":"美丽世界的孤儿","head_image":"http://liveimage.fanwe.net/public/attachment/201609/01/09/origin/1472663010100992.jpg","content":"1","to_comment_id":"0","to_user_id":"0","create_time":"2017-03-20 19:00:10","left_time":"21小时前","is_to_comment":0,"to_nick_name":" ","is_authentication":"0"}]
     * is_admin : 1
     * is_reply_but : 1
     * is_reply_comment_but : 1
     * is_show_weibo_report : 0
     * is_show_user_report : 0
     * is_show_user_black : 0
     * is_show_top : 1
     * is_show_deal_weibo : 1
     * user_id : 100996
     * has_next : 0
     * status : 1
     * page : 1
     * error :
     */

    private InfoBean info;
    private int is_admin;
    private int is_reply_but;
    private int is_reply_comment_but;
    private int is_show_weibo_report;
    private int is_show_user_report;
    private int is_show_user_black;
    private int is_show_top;
    private int is_show_deal_weibo;
    private int user_id;
    private int has_next;
    private int status;
    private int page;
    private String error;
    private List<DiggListBean> digg_list;
    private List<XRUserDynamicCommentModel> comment_list;
    private XRInviteModel invite_info;
    private String app_down_url;


    public InfoBean getInfo()
    {
        return info;
    }

    public void setInfo(InfoBean info)
    {
        this.info = info;
    }

    public int getIs_admin()
    {
        return is_admin;
    }

    public void setIs_admin(int is_admin)
    {
        this.is_admin = is_admin;
    }

    public int getIs_reply_but()
    {
        return is_reply_but;
    }

    public void setIs_reply_but(int is_reply_but)
    {
        this.is_reply_but = is_reply_but;
    }

    public int getIs_reply_comment_but()
    {
        return is_reply_comment_but;
    }

    public void setIs_reply_comment_but(int is_reply_comment_but)
    {
        this.is_reply_comment_but = is_reply_comment_but;
    }

    public int getIs_show_weibo_report()
    {
        return is_show_weibo_report;
    }

    public void setIs_show_weibo_report(int is_show_weibo_report)
    {
        this.is_show_weibo_report = is_show_weibo_report;
    }

    public int getIs_show_user_report()
    {
        return is_show_user_report;
    }

    public void setIs_show_user_report(int is_show_user_report)
    {
        this.is_show_user_report = is_show_user_report;
    }

    public int getIs_show_user_black()
    {
        return is_show_user_black;
    }

    public void setIs_show_user_black(int is_show_user_black)
    {
        this.is_show_user_black = is_show_user_black;
    }

    public int getIs_show_top()
    {
        return is_show_top;
    }

    public void setIs_show_top(int is_show_top)
    {
        this.is_show_top = is_show_top;
    }

    public int getIs_show_deal_weibo()
    {
        return is_show_deal_weibo;
    }

    public void setIs_show_deal_weibo(int is_show_deal_weibo)
    {
        this.is_show_deal_weibo = is_show_deal_weibo;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public int getHas_next()
    {
        return has_next;
    }

    public void setHas_next(int has_next)
    {
        this.has_next = has_next;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public List<DiggListBean> getDigg_list()
    {
        return digg_list;
    }

    public void setDigg_list(List<DiggListBean> digg_list)
    {
        this.digg_list = digg_list;
    }

    public List<XRUserDynamicCommentModel> getComment_list()
    {
        return comment_list;
    }

    public void setComment_list(List<XRUserDynamicCommentModel> comment_list)
    {
        this.comment_list = comment_list;
    }

    public static class InfoBean
    {
        /**
         * user_id : 100996
         * weibo_id : 135
         * head_image : http://q.qlogo.cn/qqapp/1105454078/25DE6096F071135A21F28830182DF570/100
         * is_authentication : 0
         * content : bad
         * red_count : 0
         * digg_count : 1
         * comment_count : 8
         * video_count : 0
         * nick_name : 海棠‘依旧
         * sort_num : 0
         * photo_image :
         * city : 厦门
         * is_top : 0
         * price : 0.00
         * type : imagetext
         * create_time : 2017-03-20 16:32:42
         * left_time : 23小时前
         * images_count : 1
         * images : [{"url":"http://liveimage.fanwe.net/public/attachment/201703/100996/201703200432398.png?x-oss-process=image/resize,m_mfit,h_50,w_50","is_model":0}]
         * video_url :
         * has_digg : 0
         */

        private String user_id;
        private String weibo_id;
        private String head_image;
        private String is_authentication;
        private String content;
        private String red_count;
        private String digg_count;
        private String comment_count;
        private String video_count;
        private String nick_name;
        private String sort_num;
        private String photo_image;
        private String city;
        private String is_top;
        private String price;
        private String type;
        private String create_time;
        private String left_time;
        private int images_count;
        private String video_url;
        private int has_digg;
        private List<XRDynamicImagesBean> images;
        private String v_icon;

        public String getV_icon()
        {
            return v_icon;
        }

        public void setV_icon(String v_icon)
        {
            this.v_icon = v_icon;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getWeibo_id()
        {
            return weibo_id;
        }

        public void setWeibo_id(String weibo_id)
        {
            this.weibo_id = weibo_id;
        }

        public String getHead_image()
        {
            return head_image;
        }

        public void setHead_image(String head_image)
        {
            this.head_image = head_image;
        }

        public String getIs_authentication()
        {
            return is_authentication;
        }

        public void setIs_authentication(String is_authentication)
        {
            this.is_authentication = is_authentication;
        }

        public String getContent()
        {
            return content;
        }

        public void setContent(String content)
        {
            this.content = content;
        }

        public String getRed_count()
        {
            return red_count;
        }

        public void setRed_count(String red_count)
        {
            this.red_count = red_count;
        }

        public String getDigg_count()
        {
            return digg_count;
        }

        public void setDigg_count(String digg_count)
        {
            this.digg_count = digg_count;
        }

        public String getComment_count()
        {
            return comment_count;
        }

        public void setComment_count(String comment_count)
        {
            this.comment_count = comment_count;
        }

        public String getVideo_count()
        {
            return video_count;
        }

        public void setVideo_count(String video_count)
        {
            this.video_count = video_count;
        }

        public String getNick_name()
        {
            return nick_name;
        }

        public void setNick_name(String nick_name)
        {
            this.nick_name = nick_name;
        }

        public String getSort_num()
        {
            return sort_num;
        }

        public void setSort_num(String sort_num)
        {
            this.sort_num = sort_num;
        }

        public String getPhoto_image()
        {
            return photo_image;
        }

        public void setPhoto_image(String photo_image)
        {
            this.photo_image = photo_image;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public String getIs_top()
        {
            return is_top;
        }

        public void setIs_top(String is_top)
        {
            this.is_top = is_top;
        }

        public String getPrice()
        {
            return price;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getCreate_time()
        {
            return create_time;
        }

        public void setCreate_time(String create_time)
        {
            this.create_time = create_time;
        }

        public String getLeft_time()
        {
            return left_time;
        }

        public void setLeft_time(String left_time)
        {
            this.left_time = left_time;
        }

        public int getImages_count()
        {
            return images_count;
        }

        public void setImages_count(int images_count)
        {
            this.images_count = images_count;
        }

        public String getVideo_url()
        {
            return video_url;
        }

        public void setVideo_url(String video_url)
        {
            this.video_url = video_url;
        }

        public int getHas_digg()
        {
            return has_digg;
        }

        public void setHas_digg(int has_digg)
        {
            this.has_digg = has_digg;
        }

        public List<XRDynamicImagesBean> getImages()
        {
            return images;
        }

        public void setImages(List<XRDynamicImagesBean> images)
        {
            this.images = images;
        }

        //===
        public boolean isAuthenticated()
        {
            return is_authentication.equals("2");
        }

    }

    public static class DiggListBean
    {
        /**
         * user_id : 100992
         * is_authentication : 0
         * nick_name : 美丽世界的孤儿
         * head_image : http://liveimage.fanwe.net/public/attachment/201609/01/09/origin/1472663010100992.jpg
         */

        private String user_id;
        private String is_authentication;
        private String nick_name;
        private String head_image;
        private String v_icon;

        public String getV_icon()
        {
            return v_icon;
        }

        public void setV_icon(String v_icon)
        {
            this.v_icon = v_icon;
        }

        public String getUser_id()
        {
            return user_id;
        }

        public void setUser_id(String user_id)
        {
            this.user_id = user_id;
        }

        public String getIs_authentication()
        {
            return is_authentication;
        }

        public void setIs_authentication(String is_authentication)
        {
            this.is_authentication = is_authentication;
        }

        public String getNick_name()
        {
            return nick_name;
        }

        public void setNick_name(String nick_name)
        {
            this.nick_name = nick_name;
        }

        public String getHead_image()
        {
            return head_image;
        }

        public void setHead_image(String head_image)
        {
            this.head_image = head_image;
        }
    }


    //==
    public boolean hasNext()
    {
        return getHas_next() == 1;
    }

    public XRInviteModel getInvite_info()
    {
        return invite_info;
    }

    public void setInvite_info(XRInviteModel invite_info)
    {
        this.invite_info = invite_info;
    }

    public String getApp_down_url()
    {
        return app_down_url;
    }

    public void setApp_down_url(String app_down_url)
    {
        this.app_down_url = app_down_url;
    }
}
