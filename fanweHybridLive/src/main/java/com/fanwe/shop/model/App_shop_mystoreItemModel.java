package com.fanwe.shop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */

public class App_shop_mystoreItemModel implements Serializable
{
    private String id;
    private String name;
    private List<String> imgs;
    private String price;
    private String url;
    private String description;
    private String kd_cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKd_cost() {
        return kd_cost;
    }

    public void setKd_cost(String kd_cost) {
        this.kd_cost = kd_cost;
    }
}
