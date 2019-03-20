package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActListModel;

import java.util.List;

/**
 * 小视频评论列表
 *
 * @author wxy
 */
public class VideoCommentListModel extends BaseActListModel {

    private List<CommentItemModel> list;

    public List<CommentItemModel> getList() {
        return list;
    }

    public void setList(List<CommentItemModel> list) {
        this.list = list;
    }

    public static class CommentItemModel{
       public String com_content="";//哈哈哈",
       public String com_time="";//1517713920",
       public String com_userid="";//102578",
       public String sv_id="";//45",
        public String comment_id;
       public String to_userid="";//101781",
       public String user_id="";//102578",
       public String nick_name="";//啦啦",
       public String head_image="";//http://wx.qlogo.cn/mmopen/vi_32/dFibRStcAsby8frgcCKWN4MOc7NB3IWZWGLoZxHhNIpoeeH4t7bpIibGqTfdmUqN5whVw8pypbVs2w06lgyLZtYQ/96",
       public String thumb_head_image="";//",
       public String to_user_nickname="";//吻你承诺今世",
       public String to_user_head_image="";

        public String getComment_id() {
            return comment_id;
        }

        public String getTo_userid() {
            return to_userid;
        }

        public void setTo_userid(String to_userid) {
            this.to_userid = to_userid;
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

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        @Override
        public String toString() {
            return "CommentItemModel{" +
                    "com_content='" + com_content + '\'' +
                    ", com_time='" + com_time + '\'' +
                    ", com_userid='" + com_userid + '\'' +
                    ", sv_id='" + sv_id + '\'' +
                    ", to_userid='" + to_userid + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", head_image='" + head_image + '\'' +
                    ", thumb_head_image='" + thumb_head_image + '\'' +
                    ", to_user_nickname='" + to_user_nickname + '\'' +
                    ", to_user_head_image='" + to_user_head_image + '\'' +
                    '}';
        }
    }


}

