package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 下午5:05:03 类说明
 */
@SuppressWarnings("serial")
public class App_payActModel extends BaseActModel
{
	private PayModel pay;

	public PayModel getPay()
	{
		return pay;
	}

	public void setPay(PayModel pay)
	{
		this.pay = pay;
	}

}
