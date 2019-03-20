package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:41:14 类说明
 */
@SuppressWarnings("serial")
public class App_rechargeActModel extends BaseActModel
{
	private long diamonds;
	private int show_other;//,===>显示其它(用户直接输入充值金额）
	private List<PayItemModel> pay_list;
	private List<RuleItemModel> rule_list;
	private int rate;// 金额兑换比例 RMB-->秀豆

	//add
	private long coin;
	private float exchange_rate;

	public int getShow_other()
	{
		return show_other;
	}

	public void setShow_other(int show_other)
	{
		this.show_other = show_other;
	}

	public int getRate()
	{
		return rate;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

	public long getDiamonds()
	{
		return diamonds;
	}

	public void setDiamonds(long diamonds)
	{
		this.diamonds = diamonds;
	}

	public List<PayItemModel> getPay_list()
	{
		return pay_list;
	}

	public void setPay_list(List<PayItemModel> pay_list)
	{
		this.pay_list = pay_list;
	}

	public List<RuleItemModel> getRule_list()
	{
		return rule_list;
	}

	public void setRule_list(List<RuleItemModel> rule_list)
	{
		this.rule_list = rule_list;
	}

	public float getExchange_rate() {
		return exchange_rate;
	}

	public void setExchange_rate(float exchange_rate) {
		this.exchange_rate = exchange_rate;
	}

	public long getCoin() {
		return coin;
	}

	public void setCoin(long coin) {
		this.coin = coin;
	}
}
