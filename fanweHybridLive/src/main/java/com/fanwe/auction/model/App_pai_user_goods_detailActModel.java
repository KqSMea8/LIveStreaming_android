package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.utils.SDToast;

/**
 * Created by Administrator on 2016/8/8.
 */
public class App_pai_user_goods_detailActModel extends BaseActModel
{
    private PaiUserGoodsDetailDataModel data;

    public PaiUserGoodsDetailDataModel getData()
    {
        return data;
    }

    public void setData(PaiUserGoodsDetailDataModel data)
    {
        this.data = data;
    }

    public PaiUserGoodsDetailDataInfoModel getDataInfo()
    {
        if (data != null && data.getInfo() != null)
        {
            return data.getInfo();
        }
        if (data == null)
        {
            SDToast.showToast("data为空");
        }
        if (data.getInfo() == null)
        {
            SDToast.showToast("data里面info为空");
        }
        return null;
    }
}
