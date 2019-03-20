package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class App_shop_paigoodsActModel extends BaseActModel
{
    private String url;
    private List<App_shop_paigoodsItemModel> list;
    private PageModel page;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<App_shop_paigoodsItemModel> getList() {
        return list;
    }

    public void setList(List<App_shop_paigoodsItemModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
