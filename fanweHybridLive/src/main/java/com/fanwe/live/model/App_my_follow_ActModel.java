package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-23 下午9:16:56 类说明
 */
@SuppressWarnings("serial")
public class App_my_follow_ActModel extends BaseActModel
{
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
