package com.weibo.model;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class URIBean {
    String url;
    String is_modle;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_modle() {
        return is_modle;
    }

    public void setIs_modle(String is_modle) {
        this.is_modle = is_modle;
    }

    public URIBean(String url, String is_modle) {
        this.url = url;
        this.is_modle = is_modle;
    }

    @Override
    public String toString() {
        return "URIBean{" +
                "url='" + url + '\'' +
                ", is_modle='" + is_modle + '\'' +
                '}';
    }
}
