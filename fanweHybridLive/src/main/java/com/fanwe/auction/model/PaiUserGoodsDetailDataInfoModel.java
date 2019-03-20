package com.fanwe.auction.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PaiUserGoodsDetailDataInfoModel
{
    private ArrayList<String> imgs;
    private int id;
    private String podcast_id;
    private String podcast_name;
    private String tags;
    private String name;
    private String date_time;
    private String place;
    private String contact;
    private String mobile;
    private int is_true;
    private String goods_id;
    private int bz_diamonds;
    private String qp_diamonds;
    private String jj_diamonds;
    private String pai_time;
    private String pai_yanshi;
    private String max_yanshi;
    private String create_time;
    private String create_date;
    private String create_time_ymd;
    private String create_time_y;
    private String create_time_m;
    private String create_time_d;
    private String pai_nums;
    private String user_id;
    private String user_name;
    private int status;//0竞拍中 1竞拍成功 2流拍 3失败【主播关闭】
    private String order_id;
    private String order_status;
    private String last_user_id;
    private String last_user_name;
    private int last_pai_diamonds;
    private long pai_left_time;
    private String pai_logs_url;
    private String description;//拍卖详情
    private String url;
    private PaiUserGoodsDetailDataCommodityDetailModel commodity_detail;

    public PaiUserGoodsDetailDataCommodityDetailModel getCommodity_detail() {
        return commodity_detail;
    }

    public void setCommodity_detail(PaiUserGoodsDetailDataCommodityDetailModel commodity_detail) {
        this.commodity_detail = commodity_detail;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPai_logs_url()
    {
        return pai_logs_url;
    }

    public void setPai_logs_url(String pai_logs_url)
    {
        this.pai_logs_url = pai_logs_url;
    }

    public ArrayList<String> getImgs()
    {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs)
    {
        this.imgs = imgs;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPodcast_id()
    {
        return podcast_id;
    }

    public void setPodcast_id(String podcast_id)
    {
        this.podcast_id = podcast_id;
    }

    public String getPodcast_name()
    {
        return podcast_name;
    }

    public void setPodcast_name(String podcast_name)
    {
        this.podcast_name = podcast_name;
    }

    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate_time()
    {
        return date_time;
    }

    public void setDate_time(String date_time)
    {
        this.date_time = date_time;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public int getIs_true()
    {
        return is_true;
    }

    public void setIs_true(int is_true)
    {
        this.is_true = is_true;
    }

    public String getGoods_id()
    {
        return goods_id;
    }

    public void setGoods_id(String goods_id)
    {
        this.goods_id = goods_id;
    }

    public int getBz_diamonds()
    {
        return bz_diamonds;
    }

    public void setBz_diamonds(int bz_diamonds)
    {
        this.bz_diamonds = bz_diamonds;
    }

    public String getQp_diamonds()
    {
        return qp_diamonds;
    }

    public void setQp_diamonds(String qp_diamonds)
    {
        this.qp_diamonds = qp_diamonds;
    }

    public String getJj_diamonds()
    {
        return jj_diamonds;
    }

    public void setJj_diamonds(String jj_diamonds)
    {
        this.jj_diamonds = jj_diamonds;
    }

    public String getPai_time()
    {
        return pai_time;
    }

    public void setPai_time(String pai_time)
    {
        this.pai_time = pai_time;
    }

    public String getPai_yanshi()
    {
        return pai_yanshi;
    }

    public void setPai_yanshi(String pai_yanshi)
    {
        this.pai_yanshi = pai_yanshi;
    }

    public String getMax_yanshi()
    {
        return max_yanshi;
    }

    public void setMax_yanshi(String max_yanshi)
    {
        this.max_yanshi = max_yanshi;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getCreate_date()
    {
        return create_date;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getCreate_time_ymd()
    {
        return create_time_ymd;
    }

    public void setCreate_time_ymd(String create_time_ymd)
    {
        this.create_time_ymd = create_time_ymd;
    }

    public String getCreate_time_y()
    {
        return create_time_y;
    }

    public void setCreate_time_y(String create_time_y)
    {
        this.create_time_y = create_time_y;
    }

    public String getCreate_time_m()
    {
        return create_time_m;
    }

    public void setCreate_time_m(String create_time_m)
    {
        this.create_time_m = create_time_m;
    }

    public String getCreate_time_d()
    {
        return create_time_d;
    }

    public void setCreate_time_d(String create_time_d)
    {
        this.create_time_d = create_time_d;
    }

    public String getPai_nums()
    {
        return pai_nums;
    }

    public void setPai_nums(String pai_nums)
    {
        this.pai_nums = pai_nums;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getOrder_id()
    {
        return order_id;
    }

    public void setOrder_id(String order_id)
    {
        this.order_id = order_id;
    }

    public String getOrder_status()
    {
        return order_status;
    }

    public void setOrder_status(String order_status)
    {
        this.order_status = order_status;
    }

    public String getLast_user_id()
    {
        return last_user_id;
    }

    public void setLast_user_id(String last_user_id)
    {
        this.last_user_id = last_user_id;
    }

    public String getLast_user_name()
    {
        return last_user_name;
    }

    public void setLast_user_name(String last_user_name)
    {
        this.last_user_name = last_user_name;
    }

    public int getLast_pai_diamonds()
    {
        return last_pai_diamonds;
    }

    public void setLast_pai_diamonds(int last_pai_diamonds)
    {
        this.last_pai_diamonds = last_pai_diamonds;
    }

    public long getPai_left_time()
    {
        return pai_left_time;
    }

    public void setPai_left_time(long pai_left_time)
    {
        this.pai_left_time = pai_left_time;
    }
}
