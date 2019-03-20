package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 收货地址列表
 * Created by Administrator on 2016/10/19.
 */

public class App_address_listActModel extends BaseActModel
{
    private AddressListDataModel data;

    public AddressListDataModel getData() {
        return data;
    }

    public void setData(AddressListDataModel data) {
        this.data = data;
    }
}
