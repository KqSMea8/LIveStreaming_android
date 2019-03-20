package com.weibo.model;

/**
 * Created by Administrator on 2018/8/30 0030.
 */

public class UpdateFavorite {
    int weibo_id;
    int digg_count;
    int has_digg;
    int video_count;
    int type;
    public static final int COMMENT=1;
    public static final int FAVORITE=2;
    public static final int VIDEO_COUNT=3;
    // 2点赞 3视频 1评论
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHas_digg() {
        return has_digg;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public void setHas_digg(int has_digg) {
        this.has_digg = has_digg;
    }

    public UpdateFavorite() {
    }

    public int getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(int weibo_id) {
        this.weibo_id = weibo_id;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }
}
