package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActListModel;
/**
 * 用户音乐列表
 * @author ldh
 *
 */
@SuppressWarnings("serial")
public class Music_searchActModel extends BaseActListModel{
	private List<LiveSongModel> list;

	public List<LiveSongModel> getList() {
		return list;
	}

	public void setList(List<LiveSongModel> list) {
		this.list = list;
	}
	
	
}
