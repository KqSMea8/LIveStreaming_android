package com.weibo.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/28 0028.
 */

public class XRCommentListModel {
    /**
     * has_next : 0
     * page : 1
     * status : 1
     * error :
     * list : [{"comment_id":"263","user_id":"200000","nick_name":"200000","head_image":"http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg","content":"xcxcx","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-27 17:28:22","is_authentication":"0","left_time":"23小时前","is_to_comment":0,"to_nick_name":""},{"comment_id":"13","user_id":"200012","nick_name":"嗯哼","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/vLu8eic2Or0nS4g2GW72kicYNMjp0FNz4NQxalhiaXZ6Pa0CpjPA7PsnQBm8DI6NyO87G4qE57vnAMYRicFlGptVrA/132","content":"哈哈哈哈","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:38:11","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"12","user_id":"200012","nick_name":"嗯哼","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/vLu8eic2Or0nS4g2GW72kicYNMjp0FNz4NQxalhiaXZ6Pa0CpjPA7PsnQBm8DI6NyO87G4qE57vnAMYRicFlGptVrA/132","content":"你今年","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:37:58","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"11","user_id":"200012","nick_name":"嗯哼","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/vLu8eic2Or0nS4g2GW72kicYNMjp0FNz4NQxalhiaXZ6Pa0CpjPA7PsnQBm8DI6NyO87G4qE57vnAMYRicFlGptVrA/132","content":"扭扭捏捏呢","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:15:18","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"10","user_id":"200012","nick_name":"嗯哼","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/vLu8eic2Or0nS4g2GW72kicYNMjp0FNz4NQxalhiaXZ6Pa0CpjPA7PsnQBm8DI6NyO87G4qE57vnAMYRicFlGptVrA/132","content":"呵呵呵呵","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:14:58","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"9","user_id":"200009","nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","content":"Igiifuyufyfcuucuucfufuucucycuccu","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:14:44","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"8","user_id":"200009","nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","content":"Hgghbgvbb","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:12:52","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"7","user_id":"200009","nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","content":"发个公告","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 11:12:45","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"6","user_id":"200012","nick_name":"嗯哼","head_image":"http://thirdwx.qlogo.cn/mmopen/vi_32/vLu8eic2Or0nS4g2GW72kicYNMjp0FNz4NQxalhiaXZ6Pa0CpjPA7PsnQBm8DI6NyO87G4qE57vnAMYRicFlGptVrA/132","content":"哈哈哈哈","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 10:58:35","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"5","user_id":"200009","nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","content":"还是说盛世回","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 10:36:43","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""},{"comment_id":"4","user_id":"200009","nick_name":"小姐姐","head_image":"http://site.88817235.cn/public/attachment/201808/13/17/origin/1534124695200009.jpg","content":"不好好哼哼唧唧","to_comment_id":"0","to_user_id":"0","create_time":"2018-08-20 10:32:17","is_authentication":"2","left_time":"8天前","is_to_comment":0,"to_nick_name":""}]
     */

    private int has_next;
    private int page;
    private int status;
    private String error;
    private List<ListBean> list;

    @Override
    public String toString() {
        return "XRCommentListModel{" +
                "has_next=" + has_next +
                ", page=" + page +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", list=" + list +
                '}';
    }

    public int getHas_next() {
        return has_next;
    }

    public void setHas_next(int has_next) {
        this.has_next = has_next;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        @Override
        public String toString() {
            return "ListBean{" +
                    "comment_id='" + comment_id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", head_image='" + head_image + '\'' +
                    ", content='" + content + '\'' +
                    ", to_comment_id='" + to_comment_id + '\'' +
                    ", to_user_id='" + to_user_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", is_authentication='" + is_authentication + '\'' +
                    ", left_time='" + left_time + '\'' +
                    ", is_to_comment=" + is_to_comment +
                    ", to_nick_name='" + to_nick_name + '\'' +
                    '}';
        }

        /**
         * comment_id : 263
         * user_id : 200000
         * nick_name : 200000
         * head_image : http://liveimage.fanwe.net/public/attachment/201612/15/10/5851fcd7d0cf2.jpg
         * content : xcxcx
         * to_comment_id : 0
         * to_user_id : 0
         * create_time : 2018-08-27 17:28:22
         * is_authentication : 0
         * left_time : 23小时前
         * is_to_comment : 0
         * to_nick_name :
         */

        private String comment_id;
        private String user_id;
        private String nick_name;
        private String head_image;
        private String content;
        private String to_comment_id;
        private String to_user_id;
        private String create_time;
        private String is_authentication;
        private String left_time;
        private int is_to_comment;
        private String to_nick_name;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTo_comment_id() {
            return to_comment_id;
        }

        public void setTo_comment_id(String to_comment_id) {
            this.to_comment_id = to_comment_id;
        }

        public String getTo_user_id() {
            return to_user_id;
        }

        public void setTo_user_id(String to_user_id) {
            this.to_user_id = to_user_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIs_authentication() {
            return is_authentication;
        }

        public void setIs_authentication(String is_authentication) {
            this.is_authentication = is_authentication;
        }

        public String getLeft_time() {
            return left_time;
        }

        public void setLeft_time(String left_time) {
            this.left_time = left_time;
        }

        public int getIs_to_comment() {
            return is_to_comment;
        }

        public void setIs_to_comment(int is_to_comment) {
            this.is_to_comment = is_to_comment;
        }

        public String getTo_nick_name() {
            return to_nick_name;
        }

        public void setTo_nick_name(String to_nick_name) {
            this.to_nick_name = to_nick_name;
        }
    }
}
