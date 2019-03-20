package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * Created by yhz on 2016/7/22.
 */
public class App_AuthentActModel extends BaseActModel
{
    private String identify_hold_example;//手持身份证图片
    private int is_show_identify_number;//是否展示身份证信息栏 0:1
    private String title;
    private String investor_send_info;

    private AuthentModel user;

    private List<App_AuthentItemModel> authent_list;

    //add 认证页面增加 邀请人信息 选项
    private List<InviteTypeItemModel> invite_type_list;
    private String invite_id;

    public List<App_AuthentItemModel> getAuthent_list()
    {
        return authent_list;
    }

    public void setAuthent_list(List<App_AuthentItemModel> authent_list)
    {
        this.authent_list = authent_list;
    }

    public AuthentModel getUser()
    {
        return user;
    }

    public void setUser(AuthentModel user)
    {
        this.user = user;
    }

    public String getInvestor_send_info()
    {
        return investor_send_info;
    }

    public void setInvestor_send_info(String investor_send_info)
    {
        this.investor_send_info = investor_send_info;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getIdentify_hold_example()
    {
        return identify_hold_example;
    }

    public void setIdentify_hold_example(String identify_hold_example)
    {
        this.identify_hold_example = identify_hold_example;
    }

    public int getIs_show_identify_number()
    {
        return is_show_identify_number;
    }

    public void setIs_show_identify_number(int is_show_identify_number)
    {
        this.is_show_identify_number = is_show_identify_number;
    }

    public List<InviteTypeItemModel> getInvite_type_list()
    {
        return invite_type_list;
    }

    public void setInvite_type_list(List<InviteTypeItemModel> invite_type_list)
    {
        this.invite_type_list = invite_type_list;
    }

    public String getInvite_id()
    {
        return invite_id;
    }

    public void setInvite_id(String invite_id)
    {
        this.invite_id = invite_id;
    }

    public static class AuthentModel
    {
        //认证相关
        private String authentication_type;
        private String authentication_name;
        private String contact;
        private String from_platform;
        private String wiki;
        private String identify_number;
        private String identify_positive_image;
        private String identify_nagative_image;
        private String identify_hold_image;
        private int is_authentication;// "0",//是否认证 0指未认证  1指待审核 2指认证 3指审核不通

        public int getIs_authentication()
        {
            return is_authentication;
        }

        public void setIs_authentication(int is_authentication)
        {
            this.is_authentication = is_authentication;
        }

        public String getAuthentication_type()
        {
            return authentication_type;
        }

        public void setAuthentication_type(String authentication_type)
        {
            this.authentication_type = authentication_type;
        }

        public String getAuthentication_name()
        {
            return authentication_name;
        }

        public void setAuthentication_name(String authentication_name)
        {
            this.authentication_name = authentication_name;
        }

        public String getContact()
        {
            return contact;
        }

        public void setContact(String contact)
        {
            this.contact = contact;
        }

        public String getFrom_platform()
        {
            return from_platform;
        }

        public void setFrom_platform(String from_platform)
        {
            this.from_platform = from_platform;
        }

        public String getWiki()
        {
            return wiki;
        }

        public void setWiki(String wiki)
        {
            this.wiki = wiki;
        }

        public String getIdentify_number()
        {
            return identify_number;
        }

        public void setIdentify_number(String identify_number)
        {
            this.identify_number = identify_number;
        }

        public String getIdentify_positive_image()
        {
            return identify_positive_image;
        }

        public void setIdentify_positive_image(String identify_positive_image)
        {
            this.identify_positive_image = identify_positive_image;
        }

        public String getIdentify_nagative_image()
        {
            return identify_nagative_image;
        }

        public void setIdentify_nagative_image(String identify_nagative_image)
        {
            this.identify_nagative_image = identify_nagative_image;
        }

        public String getIdentify_hold_image()
        {
            return identify_hold_image;
        }

        public void setIdentify_hold_image(String identify_hold_image)
        {
            this.identify_hold_image = identify_hold_image;
        }
    }
}
