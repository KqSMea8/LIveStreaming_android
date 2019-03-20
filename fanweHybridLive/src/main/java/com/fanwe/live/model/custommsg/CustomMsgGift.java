package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.gif.GifConfigModel;
import com.fanwe.live.model.GiftAward;
import com.fanwe.live.model.PointItem;
import com.fanwe.live.model.UserModel;

import java.util.List;

public class CustomMsgGift extends CustomMsg implements ILiveGiftMsg
{
    private int prop_id; // 礼物id
    private String animated_url; // 动画播放url
    private String icon; // 图片
    private int num; // 礼物数量
    private int is_plus; // 是否需要叠加数量，1-是
    private int is_much; // 是否可以连发
    private int is_animated = LiveConstant.GiftType.NORMAL; // 动画类型
    private String anim_type; // 动画类型，当is_animated=2时候有效
    private long total_ticket; // 总钱票数量
    private String to_user_id; // 礼物接收人(主播)
    private String fonts_color; // 字体颜色
    private String desc; // 观众收到的提示内容
    private String desc2; // 主播收到的提示内容
    private String top_title;
    private int app_plus_num; //app显示的礼物连发数量
    private List<GifConfigModel> anim_cfg;
    private GiftAward award;
    private int show_type=0;
    private List<PointItem> coordinate;
    private int drawn_time;
    private String svg_file;
    public int getDrawn_time() {
        return drawn_time;
    }

    public void setDrawn_time(int drawn_time) {
        this.drawn_time = drawn_time;
    }

    public List<PointItem> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(List<PointItem> coordinate) {
        this.coordinate = coordinate;
    }

    public String getSvg_file() {
        return svg_file;
    }

    public void setSvg_file(String svg_file) {
        this.svg_file = svg_file;
    }

    public GiftAward getAward() {
        return award;
    }

    public void setAward(GiftAward award) {
        this.award = award;
    }

    public int getShow_type() {
        return show_type;
    }

    public void setShow_type(int show_type) {
        this.show_type = show_type;
    }

    private boolean isTaked = false;

    public CustomMsgGift()
    {
        super();
        setType(CustomMsgType.MSG_GIFT);
    }

    public int getApp_plus_num()
    {
        return app_plus_num;
    }

    public void setApp_plus_num(int app_plus_num)
    {
        this.app_plus_num = app_plus_num;
    }

    public String getTop_title()
    {
        return top_title;
    }

    public void setTop_title(String top_title)
    {
        this.top_title = top_title;
    }

    public String getAnim_type()
    {
        return anim_type;
    }

    public void setAnim_type(String anim_type)
    {
        this.anim_type = anim_type;
    }

    /**
     * 是否是gif动画
     *
     * @return
     */
    public boolean isGif()
    {
        return is_animated == LiveConstant.GiftType.GIF;
    }

    /**
     * 是否是大型礼物
     *
     * @return
     */
    public boolean isAnimatior()
    {
        return is_animated == LiveConstant.GiftType.ANIMATOR;
    }

    public boolean isShouHui()
    {
        return is_animated == LiveConstant.GiftType.SHOUHUI;
    }
    public boolean isSVGA()
    {
        return is_animated == LiveConstant.GiftType.SVGA;
    }
    public int getProp_id()
    {
        return prop_id;
    }

    public void setProp_id(int prop_id)
    {
        this.prop_id = prop_id;
    }

    public String getAnimated_url()
    {
        return animated_url;
    }

    public void setAnimated_url(String animated_url)
    {
        this.animated_url = animated_url;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public long getTotal_ticket()
    {
        return total_ticket;
    }

    public void setTotal_ticket(long total_ticket)
    {
        this.total_ticket = total_ticket;
    }

    public String getTo_user_id()
    {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id)
    {
        this.to_user_id = to_user_id;
    }

    public String getFonts_color()
    {
        return fonts_color;
    }

    public void setFonts_color(String fonts_color)
    {
        this.fonts_color = fonts_color;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc2()
    {
        return desc2;
    }

    public void setDesc2(String desc2)
    {
        this.desc2 = desc2;
    }

    //----------ILiveGiftMsg implements start----------

    @Override
    public UserModel getMsgSender()
    {
        return getSender();
    }

    @Override
    public void setShowNum(int showNum)
    {
        this.app_plus_num = num;
    }

    @Override
    public int getShowNum()
    {
        return num;
    }

    @Override
    public int getAddNum() {
        return num;
    }

    @Override
    public boolean isTaked()
    {
        return isTaked;
    }

    @Override
    public void setTaked(boolean isTaked)
    {
        this.isTaked = isTaked;
    }

    @Override
    public boolean canPlay()
    {
        return !isAnimatior();
    }

    //----------ILiveGiftMsg implements end----------

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        }

        if (!(o instanceof CustomMsgGift))
        {
            return false;
        }

        CustomMsgGift msg = (CustomMsgGift) o;

        if (prop_id <= 0)
        {
            return false;
        }

        if (prop_id != msg.getProp_id())
        {
            return false;
        }

        if (getSender() == null)
        {
            return false;
        }

        if (!getSender().equals(msg.getSender()))
        {
            return false;
        }

        return true;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public int getIs_plus()
    {
        return is_plus;
    }

    public void setIs_plus(int is_plus)
    {
        this.is_plus = is_plus;
    }

    public int getIs_much()
    {
        return is_much;
    }

    public void setIs_much(int is_much)
    {
        this.is_much = is_much;
    }

    public int getIs_animated()
    {
        return is_animated;
    }

    public void setIs_animated(int is_animated)
    {
        this.is_animated = is_animated;
    }

    public List<GifConfigModel> getAnim_cfg()
    {
        return anim_cfg;
    }

    public void setAnim_cfg(List<GifConfigModel> anim_cfg)
    {
        this.anim_cfg = anim_cfg;
    }

}
