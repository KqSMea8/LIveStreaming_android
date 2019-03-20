package com.fanwe.hybrid.model;

import com.fanwe.live.model.App_InitH5Model;
import com.fanwe.live.model.HomeTabTitleModel;
import com.fanwe.live.model.IpInfoModel;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.model.custommsg.CustomMsgLiveMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yhz
 * @create time 2014-9-16 类说明 初始化init Model
 */
@SuppressWarnings("serial")
public class InitActModel extends BaseActModel
{
    // APP Key
    private String sina_app_key;
    private String sina_app_secret;
    private String sina_bind_url;
    private String qq_app_key;
    private String qq_app_secret;
    private String wx_app_key;
    private String wx_app_secret;
    private int full_msg_jump;

    public int getFull_msg_jump() {
        return full_msg_jump;
    }

    public void setFull_msg_jump(int full_msg_jump) {
        this.full_msg_jump = full_msg_jump;
    }

    private List<HomeTabTitleModel> video_classified;//创建直播间标签
    private int is_classify;//创建直播时候是否需要强制显示分类

    public int getIs_classify() {
        return is_classify;
    }

    public void setIs_classify(int is_classify) {
        this.is_classify = is_classify;
    }

    public List<HomeTabTitleModel> getVideo_classified() {
        return video_classified;
    }

    public void setVideo_classified(List<HomeTabTitleModel> video_classified) {
        this.video_classified = video_classified;
    }

    private int has_sdk_login;
    private int has_sina_login;//是否显示新浪登录
    private int has_wx_login;//是否显示微信登录
    private int has_qq_login;//是否显示QQ登录
    private int has_mobile_login;//是否显示手机登录
    private int has_visitors_login;//是否显示游客登录
    private int open_sign_in;//是否打开每日签到功能

    public int getOpen_sign_in() {
        return open_sign_in;
    }

    public void setOpen_sign_in(int open_sign_in) {
        this.open_sign_in = open_sign_in;
    }

    private List<CustomMsgLiveMsg> listmsg;
    private long monitor_second;// 单位秒
    private int bullet_screen_diamond = 10;// 弹幕一次消费的秀豆
    private int jr_user_level; // 加入房间时,如果用户等级>=值时，有用户进入房间提醒

    private String spear_live;// 角色名，主播
    private String spear_normal;// 角色名，观众
    private String spear_interact;// 角色名，连麦观众
    private List<Api_linkModel> api_link;
    private String privacy_title;//注册协议标题
    private String privacy_link;//注册协议链接
    private String agreement_link;//主播协议链接
    private int beauty_android;
    private int beauty_close;
    private int service_push;
    private int region_versions; // 城市版本
    private String city;
    private IpInfoModel ip_info;
    private String short_name; //钱客
    private String ticket_name; //钱票
    private String account_name; //帐号
    private App_InitH5Model h5_url;
    private String share_title;

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    private int has_dirty_words; // 当为1时,启用脏子过滤;默认0时不过滤

    private int open_pai_module;//隐藏全部竞拍功能
    private int pai_real_btn;//主播真实竞拍按钮
    private int pai_virtual_btn;//主播虚拟竞拍按钮

    private int shopping_goods;//隐藏所有购物功能

    private int open_family_module;//是否隐藏我的家族按钮
    private int open_society_module;//是否隐藏我的公会按钮

    private int open_sts;//是否开启OSS图片上传,1 开启使用原图上传 0 使用裁剪上传

    private int open_login_send_score;//每日首次登陆赠送积分开关
    private int login_send_score;//登陆赠送积分值
    private int first_login;//是否是每日首次登陆

    private int open_upgrade_prompt;//是否开启每日登录升级提示
    private int new_level;//新的等级，大于0为新的等级，等于0未没有升级
    private int open_ranking_list;//排行榜开关
    private int mic_max_num; //最大连麦数量

    private int live_pay_time;//开启付费整个开关;
    private int live_pay_scene;//是否开启 按场付费模式
    private int live_pay;////开启付费整个开关
    private int live_pay_max;//按时付费输入最大值
    private int live_pay_min;//按时付费输入最小值
    private int live_pay_scene_max;//按场付费输入最大值
    private int live_pay_scene_min;//按场付费输入最小值

    private int distribution;//是否开启分销按钮
    private int distribution_module;//是否在设置里面显示推荐人按钮

    private String full_group_id;//全员广播大群群组id
    private String on_line_group_id;//在线用户大群id

    private int has_private_chat = 1; // 是否可以发私信，1-是 0-否
    private int video_resolution_type; // 0：标清(360*640) 1：高清(540*960) 2：超清(720*1280)

    private List<LiveBannerModel> start_diagram;//广告图


    private int open_vip; //是否开启vip，1-开启 0-不开启
    private int open_game_module;//是否开启游戏币兑换
    private int open_banker_module;//是否开启游戏上庄
    private int open_send_coins_module;//是否开启赠送游戏币功能
    private int open_send_diamonds_module;//是否开启赠送秀豆功能
    private int open_diamond_game_module;//是否使用秀豆作为游戏币，1表示是
    private List<String> domain_list; //域名列表

    private String sdkappid; //IM sdkappid
    private String accountType; //IM accountType
    private int send_msg_lv; //直播间可以发言的最低等级
    private int private_letter_lv; //可以发私信的最低等级
    private String pop_msg_money;
    private String full_msg_money;

    public String getPop_msg_money() {
        return pop_msg_money;
    }

    public void setPop_msg_money(String pop_msg_money) {
        this.pop_msg_money = pop_msg_money;
    }

    public String getFull_msg_money() {
        return full_msg_money;
    }

    public void setFull_msg_money(String full_msg_money) {
        this.full_msg_money = full_msg_money;
    }

    public int getPrivate_letter_lv()
    {
        return private_letter_lv;
    }

    public void setPrivate_letter_lv(int private_letter_lv)
    {
        this.private_letter_lv = private_letter_lv;
    }

    public int getSend_msg_lv()
    {
        return send_msg_lv;
    }

    public void setSend_msg_lv(int send_msg_lv)
    {
        this.send_msg_lv = send_msg_lv;
    }

    public String getSdkappid()
    {
        return sdkappid;
    }

    public void setSdkappid(String sdkappid)
    {
        this.sdkappid = sdkappid;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public int getOpen_send_diamonds_module()
    {
        return open_send_diamonds_module;
    }

    public void setOpen_send_diamonds_module(int open_send_diamonds_module)
    {
        this.open_send_diamonds_module = open_send_diamonds_module;
    }

    public List<String> getDomain_list()
    {
        return domain_list;
    }

    public void setDomain_list(List<String> domain_list)
    {
        this.domain_list = domain_list;
    }

    private int open_podcast_goods;//是否开启我的小店

    public int getOpen_podcast_goods()
    {
        return open_podcast_goods;
    }

    public void setOpen_podcast_goods(int open_podcast_goods)
    {
        this.open_podcast_goods = open_podcast_goods;
    }

    public int getOpen_game_module()
    {
        return open_game_module;
    }

    public void setOpen_game_module(int open_game_module)
    {
        this.open_game_module = open_game_module;
    }

    public int getOpen_banker_module()
    {
        return open_banker_module;
    }

    public void setOpen_banker_module(int open_banker_module)
    {
        this.open_banker_module = open_banker_module;
    }

    public int getOpen_send_coins_module()
    {
        return open_send_coins_module;
    }

    public void setOpen_send_coins_module(int open_send_coins_module)
    {
        this.open_send_coins_module = open_send_coins_module;
    }

    public int getVideo_resolution_type()
    {
        return video_resolution_type;
    }

    public void setVideo_resolution_type(int video_resolution_type)
    {
        this.video_resolution_type = video_resolution_type;
    }

    public int getHas_private_chat()
    {
        return has_private_chat;
    }

    public void setHas_private_chat(int has_private_chat)
    {
        this.has_private_chat = has_private_chat;
    }

    public int getShopping_goods()
    {
        return shopping_goods;
    }

    public void setShopping_goods(int shopping_goods)
    {
        this.shopping_goods = shopping_goods;
    }

    public int getOpen_vip()
    {
        return open_vip;
    }

    public void setOpen_vip(int open_vip)
    {
        this.open_vip = open_vip;
    }

    public int getLive_pay_max()
    {
        return live_pay_max;
    }

    public void setLive_pay_max(int live_pay_max)
    {
        this.live_pay_max = live_pay_max;
    }

    public int getLive_pay_min()
    {
        return live_pay_min;
    }

    public void setLive_pay_min(int live_pay_min)
    {
        this.live_pay_min = live_pay_min;
    }

    public int getLive_pay_scene_max()
    {
        return live_pay_scene_max;
    }

    public void setLive_pay_scene_max(int live_pay_scene_max)
    {
        this.live_pay_scene_max = live_pay_scene_max;
    }

    public int getLive_pay_scene_min()
    {
        return live_pay_scene_min;
    }

    public void setLive_pay_scene_min(int live_pay_scene_min)
    {
        this.live_pay_scene_min = live_pay_scene_min;
    }

    public int getDistribution_module()
    {
        return distribution_module;
    }

    public void setDistribution_module(int distribution_module)
    {
        this.distribution_module = distribution_module;
    }

    public String getFull_group_id()
    {
        return full_group_id;
    }

    public void setFull_group_id(String full_group_id)
    {
        this.full_group_id = full_group_id;
    }

    public String getOn_line_group_id()
    {
        return on_line_group_id;
    }

    public void setOn_line_group_id(String on_line_group_id)
    {
        this.on_line_group_id = on_line_group_id;
    }

    public int getDistribution()
    {
        return distribution;
    }

    public void setDistribution(int distribution)
    {
        this.distribution = distribution;
    }

    public int getMic_max_num()
    {
        return mic_max_num;
    }

    public void setMic_max_num(int mic_max_num)
    {
        this.mic_max_num = mic_max_num;
    }

    public int getLive_pay_time()
    {
        return live_pay_time;
    }

    public void setLive_pay_time(int live_pay_time)
    {
        this.live_pay_time = live_pay_time;
    }

    public int getLive_pay_scene()
    {
        return live_pay_scene;
    }

    public void setLive_pay_scene(int live_pay_scene)
    {
        this.live_pay_scene = live_pay_scene;
    }

    public int getLive_pay()
    {
        return live_pay;
    }

    public void setLive_pay(int live_pay)
    {
        this.live_pay = live_pay;
    }

    public int getOpen_ranking_list()
    {
        return open_ranking_list;
    }

    public void setOpen_ranking_list(int open_ranking_list)
    {
        this.open_ranking_list = open_ranking_list;
    }

    public int getOpen_upgrade_prompt()
    {
        return open_upgrade_prompt;
    }

    public void setOpen_upgrade_prompt(int open_upgrade_prompt)
    {
        this.open_upgrade_prompt = open_upgrade_prompt;
    }

    public int getNew_level()
    {
        return new_level;
    }

    public void setNew_level(int new_level)
    {
        this.new_level = new_level;
    }

    public List<LiveBannerModel> getStart_diagram()
    {
        return start_diagram;
    }

    public void setStart_diagram(List<LiveBannerModel> start_diagram)
    {
        this.start_diagram = start_diagram;
    }

    public int getOpen_login_send_score()
    {
        return open_login_send_score;
    }

    public void setOpen_login_send_score(int open_login_send_score)
    {
        this.open_login_send_score = open_login_send_score;
    }

    public int getLogin_send_score()
    {
        return login_send_score;
    }

    public void setLogin_send_score(int login_send_score)
    {
        this.login_send_score = login_send_score;
    }

    public int getFirst_login()
    {
        return first_login;
    }

    public void setFirst_login(int first_login)
    {
        this.first_login = first_login;
    }

    public int getOpen_sts()
    {
        return open_sts;
    }

    public void setOpen_sts(int open_sts)
    {
        this.open_sts = open_sts;
    }

    public int getHas_dirty_words()
    {
        return has_dirty_words;
    }

    public void setHas_dirty_words(int has_dirty_words)
    {
        this.has_dirty_words = has_dirty_words;
    }

    public String getAccount_name()
    {
        return account_name;
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
    }

    public String getApp_name()
    {
        return app_name;
    }

    public void setApp_name(String app_name)
    {
        this.app_name = app_name;
    }

    private String app_name; //钱客

    public String getShort_name()
    {
        return short_name;
    }

    public void setShort_name(String short_name)
    {
        this.short_name = short_name;
    }

    public String getTicket_name()
    {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name)
    {
        this.ticket_name = ticket_name;
    }

    public IpInfoModel getIp_info()
    {
        return ip_info;
    }

    public void setIp_info(IpInfoModel ip_info)
    {
        this.ip_info = ip_info;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public int getRegion_versions()
    {
        return region_versions;
    }

    public void setRegion_versions(int region_versions)
    {
        this.region_versions = region_versions;
    }

    public String getPrivacy_title()
    {
        return privacy_title;
    }

    public void setPrivacy_title(String privacy_title)
    {
        this.privacy_title = privacy_title;
    }

    public String getPrivacy_link()
    {
        return privacy_link;
    }

    public void setPrivacy_link(String privacy_link)
    {
        this.privacy_link = privacy_link;
    }

    public String getAgreement_link()
    {
        return agreement_link;
    }

    public void setAgreement_link(String agreement_link)
    {
        this.agreement_link = agreement_link;
    }

    public int getBeauty_android()
    {
        return beauty_android;
    }

    public void setBeauty_android(int beauty_android)
    {
        this.beauty_android = beauty_android;
    }

    public int getBeauty_close()
    {
        return beauty_close;
    }

    public void setBeauty_close(int beauty_close)
    {
        this.beauty_close = beauty_close;
    }

    public int getService_push()
    {
        return service_push;
    }

    public void setService_push(int service_push)
    {
        this.service_push = service_push;
    }

    public String getSpear_live()
    {
        return spear_live;
    }

    public void setSpear_live(String spear_live)
    {
        this.spear_live = spear_live;
    }

    public String getSpear_normal()
    {
        return spear_normal;
    }

    public void setSpear_normal(String spear_normal)
    {
        this.spear_normal = spear_normal;
    }

    public String getSpear_interact()
    {
        return spear_interact;
    }

    public void setSpear_interact(String spear_interact)
    {
        this.spear_interact = spear_interact;
    }

    public List<Api_linkModel> getApi_link()
    {
        return api_link;
    }

    public void setApi_link(List<Api_linkModel> api_link)
    {
        this.api_link = api_link;
    }

    public String getSina_app_key()
    {
        return sina_app_key;
    }

    public void setSina_app_key(String sina_app_key)
    {
        this.sina_app_key = sina_app_key;
    }

    public String getSina_app_secret()
    {
        return sina_app_secret;
    }

    public void setSina_app_secret(String sina_app_secret)
    {
        this.sina_app_secret = sina_app_secret;
    }

    public String getSina_bind_url()
    {
        return sina_bind_url;
    }

    public void setSina_bind_url(String sina_bind_url)
    {
        this.sina_bind_url = sina_bind_url;
    }

    public String getQq_app_key()
    {
        return qq_app_key;
    }

    public void setQq_app_key(String qq_app_key)
    {
        this.qq_app_key = qq_app_key;
    }

    public String getQq_app_secret()
    {
        return qq_app_secret;
    }

    public void setQq_app_secret(String qq_app_secret)
    {
        this.qq_app_secret = qq_app_secret;
    }

    public String getWx_app_key()
    {
        return wx_app_key;
    }

    public void setWx_app_key(String wx_app_key)
    {
        this.wx_app_key = wx_app_key;
    }

    public String getWx_app_secret()
    {
        return wx_app_secret;
    }

    public void setWx_app_secret(String wx_app_secret)
    {
        this.wx_app_secret = wx_app_secret;
    }

    private int sina_app_api;
    private int qq_app_api;
    private int wx_app_api;
    private int statusbar_hide;
    private String statusbar_color;
    private String topnav_color;
    private String ad_img;
    private String ad_http;
    private int ad_open;
    private int reload_time;
    private String site_url;
    private InitUpgradeModel version;
    private ArrayList<String> top_url;

    public ArrayList<String> getTop_url()
    {
        return top_url;
    }

    public void setTop_url(ArrayList<String> top_url)
    {
        this.top_url = top_url;
    }

    public InitUpgradeModel getVersion()
    {
        return version;
    }

    public void setVersion(InitUpgradeModel version)
    {
        this.version = version;
    }

    public String getSite_url()
    {
        return site_url;
    }

    public void setSite_url(String site_url)
    {
        this.site_url = site_url;
    }

    public int getReload_time()
    {
        return reload_time;
    }

    public void setReload_time(int reload_time)
    {
        this.reload_time = reload_time;
    }

    public int getSina_app_api()
    {
        return sina_app_api;
    }

    public void setSina_app_api(int sina_app_api)
    {
        this.sina_app_api = sina_app_api;
    }

    public int getQq_app_api()
    {
        return qq_app_api;
    }

    public void setQq_app_api(int qq_app_api)
    {
        this.qq_app_api = qq_app_api;
    }

    public int getWx_app_api()
    {
        return wx_app_api;
    }

    public void setWx_app_api(int wx_app_api)
    {
        this.wx_app_api = wx_app_api;
    }

    public int getStatusbar_hide()
    {
        return statusbar_hide;
    }

    public void setStatusbar_hide(int statusbar_hide)
    {
        this.statusbar_hide = statusbar_hide;
    }

    public String getStatusbar_color()
    {
        return statusbar_color;
    }

    public void setStatusbar_color(String statusbar_color)
    {
        this.statusbar_color = statusbar_color;
    }

    public String getTopnav_color()
    {
        return topnav_color;
    }

    public void setTopnav_color(String topnav_color)
    {
        this.topnav_color = topnav_color;
    }

    public String getAd_img()
    {
        return ad_img;
    }

    public void setAd_img(String ad_img)
    {
        this.ad_img = ad_img;
    }

    public String getAd_http()
    {
        return ad_http;
    }

    public void setAd_http(String ad_http)
    {
        this.ad_http = ad_http;
    }

    public int getAd_open()
    {
        return ad_open;
    }

    public void setAd_open(int ad_open)
    {
        this.ad_open = ad_open;
    }

    public List<CustomMsgLiveMsg> getListmsg()
    {
        return listmsg;
    }

    public void setListmsg(List<CustomMsgLiveMsg> listmsg)
    {
        this.listmsg = listmsg;
    }

    public long getMonitor_second()
    {
        return monitor_second;
    }

    public void setMonitor_second(long monitor_second)
    {
        this.monitor_second = monitor_second;
    }

    public int getBullet_screen_diamond()
    {
        return bullet_screen_diamond;
    }

    public void setBullet_screen_diamond(int bullet_screen_diamond)
    {
        this.bullet_screen_diamond = bullet_screen_diamond;
    }

    public int getJr_user_level()
    {
        return jr_user_level;
    }

    public void setJr_user_level(int jr_user_level)
    {
        this.jr_user_level = jr_user_level;
    }

    public int getHas_sdk_login()
    {
        return has_sdk_login;
    }

    public void setHas_sdk_login(int has_sdk_login)
    {
        this.has_sdk_login = has_sdk_login;
    }

    public int getHas_sina_login()
    {
        return has_sina_login;
    }

    public void setHas_sina_login(int has_sina_login)
    {
        this.has_sina_login = has_sina_login;
    }

    public int getHas_wx_login()
    {
        return has_wx_login;
    }

    public void setHas_wx_login(int has_wx_login)
    {
        this.has_wx_login = has_wx_login;
    }

    public int getHas_qq_login()
    {
        return has_qq_login;
    }

    public void setHas_qq_login(int has_qq_login)
    {
        this.has_qq_login = has_qq_login;
    }

    public int getHas_mobile_login()
    {
        return has_mobile_login;
    }

    public void setHas_mobile_login(int has_mobile_login)
    {
        this.has_mobile_login = has_mobile_login;
    }

    public int getHas_visitors_login()
    {
        return has_visitors_login;
    }

    public void setHas_visitors_login(int has_visitors_login)
    {
        this.has_visitors_login = has_visitors_login;
    }

    public App_InitH5Model getH5_url()
    {
        return h5_url;
    }

    public void setH5_url(App_InitH5Model h5_url)
    {
        this.h5_url = h5_url;
    }

    public int getPai_real_btn()
    {
        return pai_real_btn;
    }

    public void setPai_real_btn(int pai_real_btn)
    {
        this.pai_real_btn = pai_real_btn;
    }

    public int getPai_virtual_btn()
    {
        return pai_virtual_btn;
    }

    public void setPai_virtual_btn(int pai_virtual_btn)
    {
        this.pai_virtual_btn = pai_virtual_btn;
    }

    public int getOpen_pai_module()
    {
        return open_pai_module;
    }

    public void setOpen_pai_module(int open_pai_module)
    {
        this.open_pai_module = open_pai_module;
    }

    public int getOpen_family_module()
    {
        return open_family_module;
    }

    public void setOpen_family_module(int open_family_module)
    {
        this.open_family_module = open_family_module;
    }

    public int getOpen_society_module()
    {
        return open_society_module;
    }

    public void setOpen_society_module(int open_society_module)
    {
        this.open_society_module = open_society_module;
    }

    public int getOpen_diamond_game_module()
    {
        return open_diamond_game_module;
    }

    public void setOpen_diamond_game_module(int open_diamond_game_module)
    {
        this.open_diamond_game_module = open_diamond_game_module;
    }
}
