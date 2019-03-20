package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

public class LiveGiftModel implements SDSelectManager.Selectable
{

    private int id;
    private String name; // 礼物名字
    private int score; // 积分
    private int diamonds; // 秀豆
    private String icon; // 图标
    private String animated_url; // 动画地址
    private int ticket; // 钱票
    private int is_much; // 1-可以连续发送多个，用于小金额礼物
    private int sort;
    private int is_red_envelope;// 1-红包
    private String score_fromat; //主播增加的经验
    public String is_animated; //动画类型
    public String anim_type;
    public String gif_url; //gif地址
    private String is_award;
    private String is_heat;
    private long coins; //游戏币
    private int num;
    private int drawn_min;
    private int drawn_max;

    public String getIs_animated() {
        return is_animated;
    }

    public void setIs_animated(String is_animated) {
        this.is_animated = is_animated;
    }

    public int getDrawn_min() {
        return drawn_min;
    }

    public void setDrawn_min(int drawn_min) {
        this.drawn_min = drawn_min;
    }

    public int getDrawn_max() {
        return drawn_max;
    }

    public void setDrawn_max(int drawn_max) {
        this.drawn_max = drawn_max;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getIs_heat() {
        return is_heat;
    }

    public void setIs_heat(String is_heat) {
        this.is_heat = is_heat;
    }

    public String getIs_award() {
        return is_award;
    }

    public void setIs_award(String is_award) {
        this.is_award = is_award;
    }

    // add
    private boolean selected;

    public long getCoins()
    {
        return coins;
    }

    public void setCoins(long coins)
    {
        this.coins = coins;
    }

    public String getScore_fromat()
    {
        return score_fromat;
    }

    public void setScore_fromat(String score_fromat)
    {
        this.score_fromat = score_fromat;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(int diamonds)
    {
        this.diamonds = diamonds;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public int getTicket()
    {
        return ticket;
    }

    public void setTicket(int ticket)
    {
        this.ticket = ticket;
    }

    public int getIs_much()
    {
        return is_much;
    }

    public void setIs_much(int is_much)
    {
        this.is_much = is_much;
    }

    public int getSort()
    {
        return sort;
    }

    public void setSort(int sort)
    {
        this.sort = sort;
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

    public int getIs_red_envelope()
    {
        return is_red_envelope;
    }

    public void setIs_red_envelope(int is_red_envelope)
    {
        this.is_red_envelope = is_red_envelope;
    }

    public String getAnimated_url()
    {
        return animated_url;
    }

    public void setAnimated_url(String animated_url)
    {
        this.animated_url = animated_url;
    }

}
