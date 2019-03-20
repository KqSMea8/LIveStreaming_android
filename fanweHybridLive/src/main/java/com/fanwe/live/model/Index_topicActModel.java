package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActListModel;
import com.fanwe.hybrid.model.BaseActModel;

public class Index_topicActModel extends BaseActListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<LiveTopicModel> list;

	public List<LiveTopicModel> getList() {
		return list;
	}

	public void setList(List<LiveTopicModel> list) {
		this.list = list;
	}
}
