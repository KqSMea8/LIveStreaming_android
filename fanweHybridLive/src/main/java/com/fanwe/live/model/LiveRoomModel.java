package com.fanwe.live.model;

import android.text.TextUtils;

import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.utils.SDNumberUtil;

import java.math.RoundingMode;
import java.util.List;

public class LiveRoomModel
{
    public static final double MAX_NEARBY_DISTANCE = 100 * 1000;

    private String head_image;
    private String nick_name;
    private String city;
    private String watch_number;
    private String live_image;
    private int user_level;
    private int room_type; // 1-私密直播;3-直播
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    private String user_id;
    private String group_id;
    private int room_id;
    private String title;// 话题
    private int cate_id;//话题id
    private int live_in; //0-结束;1-正在直播;2-创建中;3-回看
    private int sdk_type; // 0-腾讯云sdk；1-金山sdk
    private int v_type;
    private String v_icon;
    private double xpoint; // 经度
    private double ypoint; // 纬度
    private int is_live_pay; // 1-付费模式
    private int live_pay_type; // 1-按场收费；0-按时收费
    private int live_fee; //收费价格
    private int today_create; //1-当天创建的新用户
    private String live_state;
    private String class_name;
    private int heat_rank;
    private List<String> medals;

    public List<String> getMedals() {
    return medals;
}

    public void setMedals(List<String> medals) {
        this.medals = medals;
    }

    public int getHeat_rank() {
        return heat_rank;
    }

    public void setHeat_rank(int heat_rank) {
        this.heat_rank = heat_rank;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    //add
    private double distance = Integer.MAX_VALUE; // 距离（米）
    private String distanceFormat;
    private int create_type; //直播类型 0：移动端；1:PC直播
    private int is_gaming;//该直播间是否正在游戏
    private String in_livepk;

    public String getIn_livepk() {
        return in_livepk;
    }

    public void setIn_livepk(String in_livepk) {
        this.in_livepk = in_livepk;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;//游戏房间邀请码

    public String getLive_state()
    {
        if (TextUtils.isEmpty(live_state))
        {
            live_state = getLiveState();
        }
        return live_state;
    }

    public void setLive_state(String live_state)
    {
        this.live_state = live_state;
    }

    public int getCreate_type()
    {
        return create_type;
    }

    public void setCreate_type(int create_type)
    {
        this.create_type = create_type;
    }

    //add
    public String getLiveState()
    {
        String result = "";
        if (live_in == 1)
        {
            //直播
            if (is_live_pay == 0)
            {
                //普通直播
                result = "直播";

            } else if (is_live_pay == 1)
            {
                //付费直播
                result = "付费直播";
            }
        } else if (live_in == 3)
        {
            if (is_live_pay == 1)
            {
                //付费直播
                result = "付费回看";
            } else
            {
                //回看
                result = "回看";
            }
        }
        return result;
    }

    public String getLivePayFee()
    {
        String result = "";
        //直播
        if (is_live_pay == 1)
        {
            //付费直播
            if (live_pay_type == 0)
            {
                //按时收费
                result = live_fee + "秀豆/分钟";
            } else if (live_pay_type == 1)
            {
                //按场收费
                result = live_fee + "秀豆/场";
            }
        }
        return result;
    }

    /**
     * 计算距离
     */
    private void calculateDistance()
    {
        try
        {
            if (xpoint > 0 && ypoint > 0)
            {
                distance = SDTencentMapManager.getInstance().getDistanceFromMyLocation(ypoint, xpoint);
                distanceFormat = formatDistance();
            } else
            {
                distanceFormat = city;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 距离格式化
     */
    private String formatDistance()
    {
        String result = null;
        if (distance <= 100)
        {
            result = "100m";
        } else if (distance < 1000)
        {
            result = String.valueOf((int) SDNumberUtil.scale(distance, 1, RoundingMode.HALF_UP)) + "m";
        } else if (distance < MAX_NEARBY_DISTANCE)
        {
            double value = distance / 1000d;
            result = String.valueOf(SDNumberUtil.scaleHalfUp(value, 1)) + "km";
        } else
        {
            result = city;
        }
        return result;
    }

    public int getToday_create()
    {
        return today_create;
    }

    public void setToday_create(int today_create)
    {
        this.today_create = today_create;
    }

    public int getLive_fee()
    {
        return live_fee;
    }

    public void setLive_fee(int live_fee)
    {
        this.live_fee = live_fee;
    }

    public int getLive_pay_type()
    {
        return live_pay_type;
    }

    public void setLive_pay_type(int live_pay_type)
    {
        this.live_pay_type = live_pay_type;
    }

    public int getIs_live_pay()
    {
        return is_live_pay;
    }

    public void setIs_live_pay(int is_live_pay)
    {
        this.is_live_pay = is_live_pay;
    }

    public String getDistanceFormat()
    {
        return distanceFormat;
    }

    public double getDistance()
    {
        return distance;
    }

    public String getLive_image()
    {
        if (TextUtils.isEmpty(live_image))
        {
            live_image = head_image;
        }
        return live_image;
    }

    public void setLive_image(String live_image)
    {
        this.live_image = live_image;
    }

    public double getXpoint()
    {
        return xpoint;
    }

    public void setXpoint(double xpoint)
    {
        this.xpoint = xpoint;
        calculateDistance();
    }

    public double getYpoint()
    {
        return ypoint;
    }

    public void setYpoint(double ypoint)
    {
        this.ypoint = ypoint;
        calculateDistance();
    }

    public int getLive_in()
    {
        return live_in;
    }

    public void setLive_in(int live_in)
    {
        this.live_in = live_in;
    }

    public int getSdk_type()
    {
        return sdk_type;
    }

    public void setSdk_type(int sdk_type)
    {
        this.sdk_type = sdk_type;
    }

    public int getCate_id()
    {
        return cate_id;
    }

    public void setCate_id(int cate_id)
    {
        this.cate_id = cate_id;
    }


    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getWatch_number()
    {
        return watch_number;
    }

    public void setWatch_number(String watch_number)
    {
        this.watch_number = watch_number;
    }

    public int getRoom_type()
    {
        return room_type;
    }

    public void setRoom_type(int room_type)
    {
        this.room_type = room_type;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getUser_level()
    {
        return user_level;
    }

    public void setUser_level(int user_level)
    {
        this.user_level = user_level;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getV_type()
    {
        return v_type;
    }

    public void setV_type(int v_type)
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

    public int getIs_gaming()
    {
        return is_gaming;
    }

    public void setIs_gaming(int is_gaming)
    {
        this.is_gaming = is_gaming;
    }
}
