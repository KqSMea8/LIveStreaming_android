package com.fanwe.auction.model;

/**
 * 收货地址
 * Created by Administrator on 2016/10/19.
 */

public class AddressListItemActModel
{
    private int id;//地址id
    private String user_id;//所有用户id
    private String consignee;//收货人姓名
    private String consignee_mobile;//收货人手机号
    private Consignee_DistrictActModel consignee_district;// 收货人所在地行政地区信息,json格式
    private String consignee_address;//收货人详细地址
    private int is_default;//是否默认
    private String create_time;//创建时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsignee_mobile() {
        return consignee_mobile;
    }

    public void setConsignee_mobile(String consignee_mobile) {
        this.consignee_mobile = consignee_mobile;
    }

    public Consignee_DistrictActModel getConsignee_district() {
        return consignee_district;
    }

    public void setConsignee_district(Consignee_DistrictActModel consignee_district) {
        this.consignee_district = consignee_district;
    }

    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
