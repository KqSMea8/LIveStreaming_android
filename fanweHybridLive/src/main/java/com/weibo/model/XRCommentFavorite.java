package com.weibo.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class XRCommentFavorite {
    /**
     * unlike : []
     * digg : []
     * comment : {"weibo_id":39,"weibo_user_id":"200012","user_id":200009,"content":"哈哈哈哈好吧","type":1,"create_time":"2018-08-30 12:31:04","is_audit":1,"storey":1,"comment_id":13,"nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/29/15/origin/1535500252200009.jpg","left_time":"刚刚","to_nick_name":""}
     * error : 评论发表成功!
     * comment_id : 0
     * status : 1
     * comment_count : 2
     */

    private String error;
    private int comment_id;
    private int status;
    private int digg_count;
    private int weibo_id;

    public int getWeibo_id() {
        return weibo_id;
    }

    public void setWeibo_id(int weibo_id) {
        this.weibo_id = weibo_id;
    }

    private String comment_count;
    private int has_digg;
    public int getHas_digg() {
        return has_digg;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public void setHas_digg(int has_digg) {
        this.has_digg = has_digg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public static class CommentBean {
        /**
         * weibo_id : 39
         * weibo_user_id : 200012
         * user_id : 200009
         * content : 哈哈哈哈好吧
         * type : 1
         * create_time : 2018-08-30 12:31:04
         * is_audit : 1
         * storey : 1
         * comment_id : 13
         * nick_name : 小姐姐
         * head_image : http://site.88817235.cn/public/attachment/201808/29/15/origin/1535500252200009.jpg
         * left_time : 刚刚
         * to_nick_name :
         */

        private int weibo_id;
        private String weibo_user_id;
        private int user_id;
        private String content;
        private int type;
        private String create_time;
        private int is_audit;
        private int storey;
        private int comment_id;
        private String nick_name;
        private String head_image;
        private String left_time;
        private String to_nick_name;

        public int getWeibo_id() {
            return weibo_id;
        }

        public void setWeibo_id(int weibo_id) {
            this.weibo_id = weibo_id;
        }

        public String getWeibo_user_id() {
            return weibo_user_id;
        }

        public void setWeibo_user_id(String weibo_user_id) {
            this.weibo_user_id = weibo_user_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_audit() {
            return is_audit;
        }

        public void setIs_audit(int is_audit) {
            this.is_audit = is_audit;
        }

        public int getStorey() {
            return storey;
        }

        public void setStorey(int storey) {
            this.storey = storey;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_image() {
            return head_image;
        }

        public void setHead_image(String head_image) {
            this.head_image = head_image;
        }

        public String getLeft_time() {
            return left_time;
        }

        public void setLeft_time(String left_time) {
            this.left_time = left_time;
        }

        public String getTo_nick_name() {
            return to_nick_name;
        }

        public void setTo_nick_name(String to_nick_name) {
            this.to_nick_name = to_nick_name;
        }
    }
}
