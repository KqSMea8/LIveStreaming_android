package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_forbid_send_msgActModel extends BaseActModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int is_forbid;//0,1是否被禁言

	public int getIs_forbid()
	{
		return is_forbid;
	}

	public void setIs_forbid(int is_forbid)
	{
		this.is_forbid = is_forbid;
	}
}
