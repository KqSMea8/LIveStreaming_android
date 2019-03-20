package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_end_videoActModel extends BaseActModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int watch_number; // 观看人数
	private int vote_number; // 获得钱票
	private int has_delvideo; // 1-显示删除视频按钮，0-不显示

	public int getWatch_number()
	{
		return watch_number;
	}

	public void setWatch_number(int watch_number)
	{
		this.watch_number = watch_number;
	}

	public int getVote_number()
	{
		return vote_number;
	}

	public void setVote_number(int vote_number)
	{
		this.vote_number = vote_number;
	}

	public int getHas_delvideo()
	{
		return has_delvideo;
	}

	public void setHas_delvideo(int has_delvideo)
	{
		this.has_delvideo = has_delvideo;
	}

}
