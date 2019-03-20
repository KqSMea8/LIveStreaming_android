package com.fanwe.live.model;

import com.fanwe.hybrid.model.PaySdkModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 下午5:07:25 类说明
 */
public class PayModel
{
	private int is_wap;//是否wap支付
	private int is_without;//调用外部浏览器支付
	private String url;//支付地址
	private String pay_info;
	private String payment_name;
	private double pay_money;
	private String class_name;

	private PayConfig config;
	private PaySdkModel sdk_code;

	public int getIs_wap()
	{
		return is_wap;
	}

	public void setIs_wap(int is_wap)
	{
		this.is_wap = is_wap;
	}

	public int getIs_without()
	{
		return is_without;
	}

	public void setIs_without(int is_without)
	{
		this.is_without = is_without;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getPay_info()
	{
		return pay_info;
	}

	public void setPay_info(String pay_info)
	{
		this.pay_info = pay_info;
	}

	public String getPayment_name()
	{
		return payment_name;
	}

	public void setPayment_name(String payment_name)
	{
		this.payment_name = payment_name;
	}

	public double getPay_money()
	{
		return pay_money;
	}

	public void setPay_money(double pay_money)
	{
		this.pay_money = pay_money;
	}

	public String getClass_name()
	{
		return class_name;
	}

	public void setClass_name(String class_name)
	{
		this.class_name = class_name;
	}

	public PayConfig getConfig()
	{
		return config;
	}

	public void setConfig(PayConfig config)
	{
		this.config = config;
	}

	public PaySdkModel getSdk_code()
	{
		return sdk_code;
	}

	public void setSdk_code(PaySdkModel sdk_code)
	{
		this.sdk_code = sdk_code;
	}

}
