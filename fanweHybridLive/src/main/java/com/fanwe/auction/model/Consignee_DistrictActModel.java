package com.fanwe.auction.model;

/**
 * 收货人所在地行政地区信息,json格式
 * Created by Administrator on 2016/10/20.
 */

public class Consignee_DistrictActModel
{
    private String province;//省
    private String city;//市
    private String area;//区
    private String zip;//邮编
    private String lng;//经度
    private String lat;//纬度

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
