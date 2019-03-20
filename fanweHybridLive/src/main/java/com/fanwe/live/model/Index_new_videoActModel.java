package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActListModel;

public class Index_new_videoActModel extends BaseActListModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<LiveTopicModel> cate_top;
	private List<LiveRoomModel> list;

	public List<LiveRoomModel> getList()
	{
		return list;
	}

	public void setList(List<LiveRoomModel> list)
	{
		this.list = list;
	}

	public List<LiveTopicModel> getCate_top()
	{
		return cate_top;
	}

	public void setCate_top(List<LiveTopicModel> cate_top)
	{
		this.cate_top = cate_top;
	}

}
