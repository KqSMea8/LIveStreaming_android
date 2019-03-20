package com.fanwe.shop.model;

import com.fanwe.auction.model.PageModel;
import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 实物竞拍 我的小店
 * Created by Administrator on 2016/10/11.
 */

public class App_shop_mystoreActModel extends BaseActModel
{
    private String url;
    private List<App_shop_mystoreItemModel> list;
    private PageModel page;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<App_shop_mystoreItemModel> getList() {
        return list;
    }

    public void setList(List<App_shop_mystoreItemModel> list) {
        this.list = list;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }
}
