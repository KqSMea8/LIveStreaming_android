package com.fanwe.hybrid.model;

public class BfappModel
{

	private String retCode;
	private String retMsg;
	private String tradeNo;
	private String obj;
	private int is_debug;

	public int getIs_debug()
	{
		return is_debug;
	}

	public void setIs_debug(int is_debug)
	{
		this.is_debug = is_debug;
	}

	public String getRetCode()
	{
		return retCode;
	}

	public void setRetCode(String retCode)
	{
		this.retCode = retCode;
	}

	public String getRetMsg()
	{
		return retMsg;
	}

	public void setRetMsg(String retMsg)
	{
		this.retMsg = retMsg;
	}

	public String getTradeNo()
	{
		return tradeNo;
	}

	public void setTradeNo(String tradeNo)
	{
		this.tradeNo = tradeNo;
	}

	public String getObj()
	{
		return obj;
	}

	public void setObj(String obj)
	{
		this.obj = obj;
	}

}
