package com.weibo.model;

import java.util.List;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述
 * @作者 Su
 * @创建时间 2017/3/15 15:13
 **/
public class XRUserDynamicsModel
{

    @Override
    public String toString() {
        return "XRUserDynamicsModel{" +
                "user_id='" + user_id + '\'' +
                ", weibo_id='" + weibo_id + '\'' +
                ", head_image='" + head_image + '\'' +
                ", is_authentication='" + is_authentication + '\'' +
                ", content='" + content + '\'' +
                ", red_count='" + red_count + '\'' +
                ", digg_count='" + digg_count + '\'' +
                ", comment_count='" + comment_count + '\'' +
                ", video_count='" + video_count + '\'' +
                ", data='" + data + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", sort_num='" + sort_num + '\'' +
                ", photo_image='" + photo_image + '\'' +
                ", city='" + city + '\'' +
                ", is_top='" + is_top + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", create_time='" + create_time + '\'' +
                ", province='" + province + '\'' +
                ", address='" + address + '\'' +
                ", is_show_weibo_report=" + is_show_weibo_report +
                ", is_show_user_report=" + is_show_user_report +
                ", is_show_user_black=" + is_show_user_black +
                ", is_show_top=" + is_show_top +
                ", show_top_des='" + show_top_des + '\'' +
                ", is_show_deal_weibo=" + is_show_deal_weibo +
                ", has_digg=" + has_digg +
                ", left_time='" + left_time + '\'' +
                ", images_count=" + images_count +
                ", goods_url='" + goods_url + '\'' +
                ", weibo_place='" + weibo_place + '\'' +
                ", video_url='" + video_url + '\'' +
                ", images=" + images +
                ", has_black=" + has_black +
                ", v_icon='" + v_icon + '\'' +
                '}';
    }

    /**
     * user_id : 101230
     * weibo_id : 355
     * head_image : http://liveimage.fanwe.net/public/attachment/201611/29/16/583d3d3808209.jpg@550-0-792-792a_0r.jpg
     * is_authentication : 2
     * content : 哈哈哈哈
     * red_count : 0
     * digg_count : 0
     * comment_count : 0
     * video_count : 0
     * data : a:2:{i:0;a:2:{s:8:"is_model";i:1;s:3:"url";s:51:"./public/attachment/201704/101230/1492478296489.jpg";}i:1;a:2:{s:8:"is_model";i:1;s:3:"url";s:51:"./public/attachment/201704/101230/1492478306975.jpg";}}
     * nick_name : 战包紫2号
     * sort_num : 0
     * photo_image :
     * city :
     * is_top : 0
     * price : 0.02
     * type : red_photo
     * create_time : 2017-04-18 09:18:33
     * province :
     * address :
     * is_show_weibo_report : 0
     * is_show_user_report : 0
     * is_show_user_black : 0
     * is_show_top : 1
     * show_top_des : 置顶动态
     * is_show_deal_weibo : 1
     * has_digg : 0
     * left_time : 26分钟前
     * images_count : 2
     * goods_url :
     * weibo_place :
     * images : [{"is_model":1,"url":"http://liveimage.fanwe.net/public/attachment/201704/101230/1492478296489.jpg?x-oss-process=image/resize,m_fill,m_mfit,h_200,w_200/blur,r_30,s_15","orginal_url":""},{"is_model":1,"url":"http://liveimage.fanwe.net/public/attachment/201704/101230/1492478306975.jpg?x-oss-process=image/resize,m_fill,m_mfit,h_200,w_200/blur,r_30,s_15","orginal_url":""}]
     * video_url :
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
    private String data;
    private String nick_name;
    private String sort_num;
    private String photo_image;
    private String city;
    private String is_top;
    private String price;
    private String type;
    private String create_time;
    private String province;
    private String address;
    private int is_show_weibo_report;
    private int is_show_user_report;
    private int is_show_user_black;
    private int is_show_top;
    private String show_top_des;
    private int is_show_deal_weibo;
    private int has_digg;
    private String left_time;
    private int images_count;
    private String goods_url;
    private String weibo_place;
    private String video_url;
    private List<XRDynamicImagesBean> images;
    private int has_black;  //动态用户是否被当前登录用户拉黑
    private String v_icon;


    //===========
    public boolean isPhotoTextDynamic()
    {
        return "imagetext".equals(type);
    }

    public boolean isVideoDynamic()
    {
        return ("video").equals(type);
    }

    public boolean isAuthentic()
    {
        if (is_authentication != null)
        {
            return "2".equals(is_authentication);
        }
        return false;
    }

    public boolean isStickTop()
    {
        return is_top != null && is_top.equals("1");
    }

    public boolean isFavorited()
    {
        return has_digg == 1;
    }

    public void setStickTop(boolean stickTop)
    {
        setIs_top(stickTop ? "1" : "0");
    }

    public void addOrMinusLikeNumber(boolean add)
    {
        int num = add ? Integer.valueOf(this.digg_count) + 1 : Integer.valueOf(this.digg_count) - 1;
        num = num < 0 ? 0 : num;
        this.digg_count = num + "";
        this.has_digg = add ? 1 : 0;
    }

    //===========

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

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
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

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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

    public String getShow_top_des()
    {
        return show_top_des;
    }

    public void setShow_top_des(String show_top_des)
    {
        this.show_top_des = show_top_des;
    }

    public int getIs_show_deal_weibo()
    {
        return is_show_deal_weibo;
    }

    public void setIs_show_deal_weibo(int is_show_deal_weibo)
    {
        this.is_show_deal_weibo = is_show_deal_weibo;
    }

    public int getHas_digg()
    {
        return has_digg;
    }

    public void setHas_digg(int has_digg)
    {
        this.has_digg = has_digg;
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

    public String getGoods_url()
    {
        return goods_url;
    }

    public void setGoods_url(String goods_url)
    {
        this.goods_url = goods_url;
    }

    public String getWeibo_place()
    {
        if (weibo_place == null)
        {
            weibo_place = "";
        }
        return weibo_place;
    }

    public void setWeibo_place(String weibo_place)
    {
        this.weibo_place = weibo_place;
    }

    public String getVideo_url()
    {
        return video_url;
    }

    public void setVideo_url(String video_url)
    {
        this.video_url = video_url;
    }

    public List<XRDynamicImagesBean> getImages()
    {
        return images;
    }

    public void setImages(List<XRDynamicImagesBean> images)
    {
        this.images = images;
    }

    public static class ImagesBean
    {
        /**
         * is_model : 1
         * url : http://liveimage.fanwe.net/public/attachment/201704/101230/1492478296489.jpg?x-oss-process=image/resize,m_fill,m_mfit,h_200,w_200/blur,r_30,s_15
         * orginal_url :
         */

        private int is_model;
        private String url;
        private String orginal_url;

        public int getIs_model()
        {
            return is_model;
        }

        public void setIs_model(int is_model)
        {
            this.is_model = is_model;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getOrginal_url()
        {
            return orginal_url;
        }

        public void setOrginal_url(String orginal_url)
        {
            this.orginal_url = orginal_url;
        }
    }

    public int getHas_black()
    {
        return has_black;
    }

    public void setHas_black(int has_black)
    {
        this.has_black = has_black;
    }

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }
}
