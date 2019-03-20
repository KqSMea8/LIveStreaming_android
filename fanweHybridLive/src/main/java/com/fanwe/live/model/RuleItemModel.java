package com.fanwe.live.model;

import com.fanwe.live.utils.SDFormatUtil;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:45:38 类说明
 */
public class RuleItemModel
{
    private int id; //商品id
    private String name;//商品描述
    private double money; //商品售价
    private long diamonds;//兑换秀豆数量
    private int gift_coins;
    private int is_payed;
    // add
    private String gift_diamonds;//赠送
    private String first_gift_diamonds;//首充赠送
    private String gift_coins_des;
    private String moneyFormat;
    private String day_num;//购买会员天数
    private boolean is_other_money;//是否是其他金额按钮

    public int getIs_payed() {
        return is_payed;
    }

    public void setIs_payed(int is_payed) {
        this.is_payed = is_payed;
    }

    public String getGift_diamonds() {
        return gift_diamonds;
    }

    public void setGift_diamonds(String gift_diamonds) {
        this.gift_diamonds = gift_diamonds;
    }

    public String getFirst_gift_diamonds() {
        return first_gift_diamonds;
    }

    public void setFirst_gift_diamonds(String first_gift_diamonds) {
        this.first_gift_diamonds = first_gift_diamonds;
    }

    public int getGift_coins()
    {
        return gift_coins;
    }

    public void setGift_coins(int gift_coins)
    {
        this.gift_coins = gift_coins;
    }

    public String getGift_coins_des() {
        return gift_coins_des;
    }

    public void setGift_coins_des(String gift_coins_des) {
        this.gift_coins_des = gift_coins_des;
    }

    public boolean is_other_money() {
        return is_other_money;
    }

    public void setIs_other_money(boolean is_other_money) {
        this.is_other_money = is_other_money;
    }

    public String getDay_num()
    {
        return day_num;
    }

    public void setDay_num(String day_num)
    {
        this.day_num = day_num;
    }

    public String getMoneyFormat()
    {
        return moneyFormat;
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

    public double getMoney()
    {
        return money;
    }

    public void setMoney(double money)
    {
        this.moneyFormat = SDFormatUtil.formatMoneyChina(money);
        this.money = money;
    }

    public long getDiamonds()
    {
        return diamonds;
    }

    public void setDiamonds(long diamonds)
    {
        this.diamonds = diamonds;
    }

}
