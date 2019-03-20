package com.fanwe.live.model;

import android.text.TextUtils;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EUserLoginSuccess;
import com.fanwe.live.utils.LiveUtils;
import com.sunday.eventbus.SDEventManager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserModel implements SDSelectManager.Selectable, Serializable
{
    private String user_id = ""; // 用户id
    private String luck_num = ""; // 靓号
    private String nick_name; // 昵称
    private String signature; // 签名
    private int sex; // 0-未知，1-男，2-女
    private int login_type; //0：微信；1：QQ；2：手机；3：微博 ;4 : 游客登录
    private String city; // 所在城市
    private String province;//所在省份
    private String emotional_state;//情感状态
    private String birthday;//生日
    private int is_authentication;// "0",//是否认证 0指未认证  1指待审核 2指认证 3指审核不通
    private String job;//职业
    private int is_edit_sex;//是否已编辑性别(只能编辑一次)
    private long focus_count; // 关注数量
    private String head_image; // 头像
    private long fans_count; // 粉丝数量
    private long ticket; // 钱票数量
    private long useable_ticket;//可用钱票数量
    private int user_level; // 用户等级
    private long use_diamonds; // 累计消费的秀豆数量
    private long diamonds; // 秀豆数量
    private String v_type;// 认证类型 0 未认证 1 普通 2企业
    private String v_icon;// 认证图标
    private String v_explain;// 认证说明
    private String home_url;// 主页
    private Map<String, String> item;// 用户其他信息列表
    private int follow_id; // 是否关注这个粉丝;0:未关注; >0：已关注
    private long video_count;// 直播数
    private int is_agree;//是否同意直播协议 0 表示不同意 1表示同意
    private int is_remind;//是否接收推送消息 0-不接收，1-接收
    // add
    private String nick_nameFormat;
    private int sort_num = -1; // 观众列表中的排序
    private int is_vip;//该用户是否为VIP
    private String vip_expire_time;//用户vip到期时间，若非vip，则为 未开通或已过期
    private String user_ticket;
    private long coin; //游戏币

    //竞拍直播添加参数
    private int show_user_order;//是否显示【我的订单】 0否 1是
    private int user_order;//我的订单数(观众)
    private int show_user_pai;//我的订单数(观众)
    private int user_pai;//我的竞拍数（观众）
    private int show_podcast_order;//是否显示星店订单(主播) 0否 1是
    private int podcast_order;//星店订单数
    private int show_podcast_pai;//是否显示竞拍管理(主播) 0否 1是
    private int podcast_pai;//竞拍管理 数量(主播)
    private int show_podcast_goods;//是否显示 商品管理（主播） 0否 1是
    private int podcast_goods;//星店中的商品数量
    private int shopping_cart;//购物车个数
    private int show_shopping_cart;//是否显示购物车
    private int open_podcast_goods;//我的小店开关
    private int shop_goods;//我的小店数量
    private int is_admin;
    private boolean selected;
    private int v_identity;
    //家族定制添加参数
    private int family_id;//家族ID
    private int family_chieftain;//是否家族长 0：否、1：是
    private List<TagsBean> tags;
    private List<String> medals;

    public List<String> getMedals() {
        return medals;
    }

    public void setMedals(List<String> medals) {
        this.medals = medals;
    }
    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public int getV_identity() {
        return v_identity;
    }

    public void setV_identity(int v_identity) {
        this.v_identity = v_identity;
    }

    //公会定制参数
    private int society_id;//公会ID
    private int society_chieftain;//是否公会长；0：否、1：是

    public String getUser_ticket() {
        return user_ticket;
    }

    public void setUser_ticket(String user_ticket) {
        this.user_ticket = user_ticket;
    }

    //add
    private String is_payed;
    private String is_jjr;
    private String jjr_money;
    private String has_edit_num;

    public UserModel() {
    }

    public int getIs_payed() {
        if (TextUtils.isEmpty(is_payed)) {
            return 0;
        }
        try {
            return Integer.valueOf(is_payed);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setIs_payed(String is_payed) {
        this.is_payed = is_payed;
    }

    public int getIs_jjr() {
        if (TextUtils.isEmpty(is_jjr)) {
            return 0;
        }
        try {
            return Integer.valueOf(is_jjr);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setIs_jjr(String is_jjr) {
        this.is_jjr = is_jjr;
    }

    public String getJjr_money() {
        return jjr_money;
    }

    public void setJjr_money(String jjr_money) {
        this.jjr_money = jjr_money;
    }

    public int getHas_edit_num() {
        if (TextUtils.isEmpty(has_edit_num)) {
            return 0;
        }
        try {
            return Integer.valueOf(has_edit_num);
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }
    }

    public void setHas_edit_num(String has_edit_num) {
        this.has_edit_num = has_edit_num;
    }

    /**
     * 是否可以在直播间发言
     *
     * @return
     */
    public boolean canSendMsg()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            int level = initActModel.getSend_msg_lv();
            return user_level >= level;
        } else
        {
            return true;
        }
    }

    /**
     * 是否可以发私信
     *
     * @return
     */
    public boolean canSendPrivateLetter()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            int level = initActModel.getPrivate_letter_lv();
            return user_level >= level;
        } else
        {
            return true;
        }
    }

    public int getLogin_type()
    {
        return login_type;
    }

    public void setLogin_type(int login_type)
    {
        this.login_type = login_type;
    }

    public long getCoin()
    {
        return coin;
    }

    public void setCoin(long coin)
    {
        this.coin = coin;
    }

    public int getShopping_cart()
    {
        return shopping_cart;
    }

    public void setShopping_cart(int shopping_cart)
    {
        this.shopping_cart = shopping_cart;
    }

    public int getShow_shopping_cart()
    {
        return show_shopping_cart;
    }

    public void setShow_shopping_cart(int show_shopping_cart)
    {
        this.show_shopping_cart = show_shopping_cart;
    }

    public String getVip_expire_time()
    {
        return vip_expire_time;
    }

    public void setVip_expire_time(String vip_expire_time)
    {
        this.vip_expire_time = vip_expire_time;
    }

    public int getIs_vip()
    {
        return is_vip;
    }

    public void setIs_vip(int is_vip)
    {
        this.is_vip = is_vip;
    }

    public String getLuck_num()
    {
        return luck_num;
    }

    public void setLuck_num(String luck_num)
    {
        this.luck_num = luck_num;
    }

    public int getSort_num()
    {
        return sort_num;
    }

    public void setSort_num(int sort_num)
    {
        this.sort_num = sort_num;
    }

    public long getUseable_ticket()
    {
        return useable_ticket;
    }

    public void setUseable_ticket(long useable_ticket)
    {
        this.useable_ticket = useable_ticket;
    }

    public int getIs_remind()
    {
        return is_remind;
    }

    public void setIs_remind(int is_remind)
    {
        this.is_remind = is_remind;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getEmotional_state()
    {
        return emotional_state;
    }

    public void setEmotional_state(String emotional_state)
    {
        this.emotional_state = emotional_state;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public int getIs_authentication()
    {
        return is_authentication;
    }

    public void setIs_authentication(int is_authentication)
    {
        this.is_authentication = is_authentication;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public int getIs_edit_sex()
    {
        return is_edit_sex;
    }

    public void setIs_edit_sex(int is_edit_sex)
    {
        this.is_edit_sex = is_edit_sex;
    }

    /**
     * 仅登录成功后保存用户数据的时候调用，其他地方不要调用
     *
     * @param user
     * @param post
     * @return
     */
    public static boolean dealLoginSuccess(UserModel user, boolean post)
    {
        if (user == null)
        {
            return false;
        }

        boolean result = false;
        CommonInterface.requestStateChangeLogin(null);
        CommonInterface.requestUser_apns(null);
        result = UserModelDao.insertOrUpdate(user);
        if (post)
        {
            EUserLoginSuccess event = new EUserLoginSuccess();
            SDEventManager.post(event);
        }
        return result;
    }

    public int getIs_agree()
    {
        return is_agree;
    }

    public void setIs_agree(int is_agree)
    {
        this.is_agree = is_agree;
    }

    public int getSexResId()
    {
        return LiveUtils.getSexImageResId(sex);
    }

    public String getNick_nameFormat()
    {
        if (nick_nameFormat == null)
        {
            nick_nameFormat = "" + nick_name + ":";
        }
        return nick_nameFormat;
    }

    public int getLevelImageResId()
    {
        return LiveUtils.getLevelImageResId(user_level);
    }

    // add

    /**
     * 获得游戏币余额
     *
     * @return
     */
    public long getGameCurrency()
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            return getCoin();
        } else
        {
            return getDiamonds();
        }
    }

    /**
     * 扣除游戏币
     */
    public void payGameCurrency(long value)
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            payCoins(value);
        } else
        {
            payDiamonds(value);
        }
    }

    /**
     * 用户剩余的游戏币是否够支付
     *
     * @param value
     * @return
     */
    public boolean canGameCurrencyPay(long value)
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            return canCoinsPay(value);
        } else
        {
            return canDiamondsPay(value);
        }
    }

    /**
     * 更新用户游戏币
     *
     * @param value
     */
    public void updateGameCurrency(long value)
    {
        if (AppRuntimeWorker.isUseGameCurrency())
        {
            setCoin(value);
        } else
        {
            setDiamonds(value);
        }
    }

    /**
     * 扣除秀豆
     *
     * @param price
     */
    public void payDiamonds(long price)
    {
        if (price > 0)
        {
            diamonds = diamonds - price;
            if (diamonds < 0)
            {
                diamonds = 0;
            }
        }
    }

    /**
     * 扣除游戏币
     *
     * @param price
     */
    public void payCoins(long price)
    {
        if (price > 0)
        {
            coin = coin - price;
            if (coin < 0)
            {
                coin = 0;
            }
        }
    }

    /**
     * 用户所剩的余额够不够支付价格
     *
     * @param price 价格
     * @return true-够支付
     */
    public boolean canDiamondsPay(long price)
    {
        return this.diamonds >= price;
    }

    /**
     * 用户所拥有的游戏币是否足够支付
     *
     * @param price
     * @return
     */
    public boolean canCoinsPay(long price)
    {
        return this.coin >= price;
    }

    /**
     * 是否高级用户
     *
     * @return
     */
    public boolean isProUser()
    {
        boolean result = false;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            if (user_level >= model.getJr_user_level())
            {
                result = true;
            }
        }
        return result;
    }

    public String getCity()
    {
        return city;
    }

    public int getFollow_id()
    {
        return follow_id;
    }

    public void setFollow_id(int follow_id)
    {
        this.follow_id = follow_id;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public long getUse_diamonds()
    {
        return use_diamonds;
    }

    public void setUse_diamonds(long use_diamonds)
    {
        this.use_diamonds = use_diamonds;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public long getFocus_count()
    {
        return focus_count;
    }

    public void setFocus_count(long focus_count)
    {
        this.focus_count = focus_count;
    }

    public long getFans_count()
    {
        return fans_count;
    }

    public void setFans_count(long fans_count)
    {
        this.fans_count = fans_count;
    }

    public long getTicket()
    {
        return ticket;
    }

    public void setTicket(long ticket)
    {
        this.ticket = ticket;
    }

    public long getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(long diamonds)
    {
        if (diamonds < 0)
        {
            diamonds = 0;
        }
        this.diamonds = diamonds;
    }

    public int getUser_level()
    {
        return user_level;
    }

    public void setUser_level(int user_level)
    {
        this.user_level = user_level;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public String getUser_id()
    {
        return user_id;
    }

    /**
     * 获得用户展示的id
     *
     * @return
     */
    public String getShowId()
    {
        String result = luck_num;

        int num = SDTypeParseUtil.getInt(result);
        if (num <= 0)
        {
            result = user_id;
        }

        return result;
    }

    public void setUser_id(String user_id)
    {
        if (user_id != null)
        {
            this.user_id = user_id;
        }
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (!(o instanceof UserModel))
        {
            return false;
        }

        if (TextUtils.isEmpty(user_id))
        {
            return false;
        }

        UserModel model = (UserModel) o;
        if (!user_id.equals(model.getUser_id()))
        {
            return false;
        }

        return true;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getHome_url()
    {
        return home_url;
    }

    public void setHome_url(String home_url)
    {
        this.home_url = home_url;
    }

    public String getV_type()
    {
        return v_type;
    }

    public void setV_type(String v_type)
    {
        this.v_type = v_type;
    }

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }

    public String getV_explain()
    {
        return v_explain;
    }

    public void setV_explain(String v_explain)
    {
        this.v_explain = v_explain;
    }

    public Map<String, String> getItem()
    {
        return item;
    }

    public void setItem(Map<String, String> item)
    {
        this.item = item;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public long getVideo_count()
    {
        return video_count;
    }

    public void setVideo_count(long video_count)
    {
        this.video_count = video_count;
    }

    public void setNick_nameFormat(String nick_nameFormat)
    {
        this.nick_nameFormat = nick_nameFormat;
    }

    public int getShow_user_order()
    {
        return show_user_order;
    }

    public void setShow_user_order(int show_user_order)
    {
        this.show_user_order = show_user_order;
    }

    public int getUser_order()
    {
        return user_order;
    }

    public void setUser_order(int user_order)
    {
        this.user_order = user_order;
    }

    public int getShow_user_pai()
    {
        return show_user_pai;
    }

    public void setShow_user_pai(int show_user_pai)
    {
        this.show_user_pai = show_user_pai;
    }

    public int getUser_pai()
    {
        return user_pai;
    }

    public void setUser_pai(int user_pai)
    {
        this.user_pai = user_pai;
    }

    public int getShow_podcast_order()
    {
        return show_podcast_order;
    }

    public void setShow_podcast_order(int show_podcast_order)
    {
        this.show_podcast_order = show_podcast_order;
    }

    public int getPodcast_order()
    {
        return podcast_order;
    }

    public void setPodcast_order(int podcast_order)
    {
        this.podcast_order = podcast_order;
    }

    public int getShow_podcast_pai()
    {
        return show_podcast_pai;
    }

    public void setShow_podcast_pai(int show_podcast_pai)
    {
        this.show_podcast_pai = show_podcast_pai;
    }

    public int getPodcast_pai()
    {
        return podcast_pai;
    }

    public void setPodcast_pai(int podcast_pai)
    {
        this.podcast_pai = podcast_pai;
    }

    public int getShow_podcast_goods()
    {
        return show_podcast_goods;
    }

    public void setShow_podcast_goods(int show_podcast_goods)
    {
        this.show_podcast_goods = show_podcast_goods;
    }

    public int getPodcast_goods()
    {
        return podcast_goods;
    }

    public void setPodcast_goods(int podcast_goods)
    {
        this.podcast_goods = podcast_goods;
    }

    public int getFamily_id()
    {
        return family_id;
    }

    public void setFamily_id(int family_id)
    {
        this.family_id = family_id;
    }

    public int getFamily_chieftain()
    {
        return family_chieftain;
    }

    public void setFamily_chieftain(int family_chieftain)
    {
        this.family_chieftain = family_chieftain;
    }

    public int getSociety_chieftain()
    {
        return society_chieftain;
    }

    public void setSociety_chieftain(int society_chieftain)
    {
        this.society_chieftain = society_chieftain;
    }

    public int getSociety_id()
    {
        return society_id;
    }

    public void setSociety_id(int society_id)
    {
        this.society_id = society_id;
    }

    public int getOpen_podcast_goods()
    {
        return open_podcast_goods;
    }

    public void setOpen_podcast_goods(int open_podcast_goods)
    {
        this.open_podcast_goods = open_podcast_goods;
    }

    public int getShop_goods()
    {
        return shop_goods;
    }

    public void setShop_goods(int shop_goods)
    {
        this.shop_goods = shop_goods;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "user_id='" + user_id + '\'' +
                ", luck_num='" + luck_num + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", head_image='" + head_image + '\'' +
                ", user_level=" + user_level +
                ", user_ticket='" + user_ticket + '\'' +
                '}';
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean {
        /**
         * id : 5
         * user_id : 200026
         * from_user_id : 200000
         * tag_id : 1
         * tag_name : acacacac
         * tag_num : 2
         */

        private String id;
        private String user_id;
        private String from_user_id;
        private String tag_id;
        private String tag_name;
        private String tag_num;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFrom_user_id() {
            return from_user_id;
        }

        public void setFrom_user_id(String from_user_id) {
            this.from_user_id = from_user_id;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getTag_num() {
            return tag_num;
        }

        public void setTag_num(String tag_num) {
            this.tag_num = tag_num;
        }
    }
}
