package com.fanwe.auction.model;

/**
 * Created by shibx on 2016/8/8.
 */
public class ImageModel {
    private String uri;//图片地址

    public ImageModel(String uri) {
        setUri(uri);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
