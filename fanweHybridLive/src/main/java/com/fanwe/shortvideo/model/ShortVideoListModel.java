package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActListModel;

import java.util.List;

/**
 * 小视频列表
 * @author wxy
 */
public class ShortVideoListModel extends BaseActListModel
{
	private static final long serialVersionUID = 1L;

	private List<ShortVideoModel> list;

	public String share_title;
	public String share_content;

	public List<ShortVideoModel> getList()
	{
		return list;
	}

	public void setList(List<ShortVideoModel> list)
	{
		this.list = list;
	}


}

