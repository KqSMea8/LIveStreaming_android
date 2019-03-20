package com.fanwe.live.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 下午5:09:37 类说明
 */
public class PayConfig
{
	private String subject;
	private String body;;
	private String total_fee;
	private String total_fee_format;
	private String out_trade_no;
	private String notify_url;
	private String payment_type;
	private String service;
	private String _input_charset;
	private String partner;
	private String seller_id;

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getTotal_fee()
	{
		return total_fee;
	}

	public void setTotal_fee(String total_fee)
	{
		this.total_fee = total_fee;
	}

	public String getTotal_fee_format()
	{
		return total_fee_format;
	}

	public void setTotal_fee_format(String total_fee_format)
	{
		this.total_fee_format = total_fee_format;
	}

	public String getOut_trade_no()
	{
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no)
	{
		this.out_trade_no = out_trade_no;
	}

	public String getNotify_url()
	{
		return notify_url;
	}

	public void setNotify_url(String notify_url)
	{
		this.notify_url = notify_url;
	}

	public String getPayment_type()
	{
		return payment_type;
	}

	public void setPayment_type(String payment_type)
	{
		this.payment_type = payment_type;
	}

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public String get_input_charset()
	{
		return _input_charset;
	}

	public void set_input_charset(String _input_charset)
	{
		this._input_charset = _input_charset;
	}

	public String getPartner()
	{
		return partner;
	}

	public void setPartner(String partner)
	{
		this.partner = partner;
	}

	public String getSeller_id()
	{
		return seller_id;
	}

	public void setSeller_id(String seller_id)
	{
		this.seller_id = seller_id;
	}

}
