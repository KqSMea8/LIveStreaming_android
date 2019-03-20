package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_shareActModel extends BaseActModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int share_award;
	private String share_award_info;

	public int getShare_award()
	{
		return share_award;
	}

	public void setShare_award(int share_award)
	{
		this.share_award = share_award;
	}

	public String getShare_award_info()
	{
		return share_award_info;
	}

	public void setShare_award_info(String share_award_info)
	{
		this.share_award_info = share_award_info;
	}
}
