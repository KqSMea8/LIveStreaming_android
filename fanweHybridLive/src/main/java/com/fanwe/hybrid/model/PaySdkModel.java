package com.fanwe.hybrid.model;

import com.alibaba.fastjson.JSON;
import com.fanwe.library.utils.SDJsonUtil;

import java.util.Map;

@SuppressWarnings("serial")
public class PaySdkModel extends BaseActModel
{
	/**
	 *  "baofoo" --宝付认证支付
	 *  "alipay" -- 支付宝
	 *  "uppay" -- 银联
	 *  "wxpay" -- 微信支付
	 *  "wxappsdk" -- 威富通微信app支付
	 *	"yjwap" -- 网页支付
	 */
	private String pay_sdk_type;

	private Map<String, Object> config;

	public String getPay_sdk_type()
	{
		return pay_sdk_type;
	}

	public void setPay_sdk_type(String pay_sdk_type)
	{
		this.pay_sdk_type = pay_sdk_type;
	}

	public MalipayModel getMalipay()
	{
		return SDJsonUtil.map2Object(config, MalipayModel.class);
	}

	public WxappModel getWxapp()
	{
		return SDJsonUtil.map2Object(config, WxappModel.class);
	}

	public WFTWxAppPayModel getWFTWxAppPayModel() {
		return SDJsonUtil.map2Object(config, WFTWxAppPayModel.class);
	}

	public YJWAPPayModel getYJWAPPayModel() {
		return SDJsonUtil.map2Object(config, YJWAPPayModel.class);
	}

	public BfupwapModel getBfupwapModel()
	{
		String s_config = JSON.toJSONString(config);
		return JSON.parseObject(s_config, BfupwapModel.class);
	}

	public BfappModel getBfappModel()
	{
		String s_config = JSON.toJSONString(config);
		return JSON.parseObject(s_config, BfappModel.class);
	}

	public JbfPayModel getJbfPay()
	{
		return SDJsonUtil.map2Object(config, JbfPayModel.class);
	}

	public Map<String, Object> getConfig()
	{
		return config;
	}

	public void setConfig(Map<String, Object> config)
	{
		this.config = config;
	}

}
