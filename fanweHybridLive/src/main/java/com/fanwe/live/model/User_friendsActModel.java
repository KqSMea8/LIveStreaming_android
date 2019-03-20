package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActListModel;
import com.fanwe.live.model.UserModel;

public class User_friendsActModel extends BaseActListModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<UserModel> list;

	public List<UserModel> getList()
	{
		return list;
	}

	public void setList(List<UserModel> list)
	{
		this.list = list;
	}

}
