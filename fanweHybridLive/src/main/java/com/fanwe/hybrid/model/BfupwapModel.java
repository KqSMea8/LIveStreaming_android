package com.fanwe.hybrid.model;

public class BfupwapModel
{
	private String orderId;
	private String tn;
	private String respCode;
	private String respMsg;
	private int is_debug;

	public int getIs_debug()
	{
		return is_debug;
	}

	public void setIs_debug(int is_debug)
	{
		this.is_debug = is_debug;
	}

	public String getOrderId()
	{
		return orderId;
	}

	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}

	public String getTn()
	{
		return tn;
	}

	public void setTn(String tn)
	{
		this.tn = tn;
	}

	public String getRespCode()
	{
		return respCode;
	}

	public void setRespCode(String respCode)
	{
		this.respCode = respCode;
	}

	public String getRespMsg()
	{
		return respMsg;
	}

	public void setRespMsg(String respMsg)
	{
		this.respMsg = respMsg;
	}

}
