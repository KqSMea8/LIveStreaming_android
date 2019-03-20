package com.weibo.model;

import android.text.TextUtils;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述 动态详情评论列表实体类
 * @作者 Su
 * @创建时间 2017/3/24 17:31
 **/
public class XRUserDynamicCommentModel
{

    /**
     * comment_id : 86
     * user_id : 100992
     * nick_name : 美丽世界的孤儿
     * head_image : http://liveimage.fanwe.net/public/attachment/201609/01/09/origin/1472663010100992.jpg
     * content : 1
     * to_comment_id : 0
     * to_user_id : 0
     * create_time : 2017-03-20 19:00:10
     * left_time : 21小时前
     * is_to_comment : 0
     * to_nick_name :
     * is_authentication : 0
     */

    private String comment_id;
    private String user_id;
    private String nick_name;
    private String head_image;
    private String content;
    private String to_comment_id;
    private String to_user_id;
    private String create_time;
    private String left_time;
    private int is_to_comment;
    private String to_nick_name;
    private String is_authentication;

    private String v_icon;

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }


    public XRUserDynamicCommentModel()
    {
    }


    public String getComment_id()
    {
        return comment_id;
    }

    public void setComment_id(String comment_id)
    {
        this.comment_id = comment_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTo_comment_id()
    {
        return to_comment_id;
    }

    public void setTo_comment_id(String to_comment_id)
    {
        this.to_comment_id = to_comment_id;
    }

    public String getTo_user_id()
    {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id)
    {
        this.to_user_id = to_user_id;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getLeft_time()
    {
        return left_time;
    }

    public void setLeft_time(String left_time)
    {
        this.left_time = left_time;
    }

    public int getIs_to_comment()
    {
        return is_to_comment;
    }

    public void setIs_to_comment(int is_to_comment)
    {
        this.is_to_comment = is_to_comment;
    }

    public String getTo_nick_name()
    {
        return to_nick_name;
    }

    public void setTo_nick_name(String to_nick_name)
    {
        this.to_nick_name = to_nick_name;
    }

    public String getIs_authentication()
    {
        return is_authentication;
    }

    public void setIs_authentication(String is_authentication)
    {
        this.is_authentication = is_authentication;
    }

    //======
    public boolean isReplyComment()
    {
//        return is_to_comment == 1;
        return !TextUtils.isEmpty(to_nick_name);
    }

    public boolean isAuthenticated()
    {
        return is_authentication.equals("2");
    }

    ///
}
