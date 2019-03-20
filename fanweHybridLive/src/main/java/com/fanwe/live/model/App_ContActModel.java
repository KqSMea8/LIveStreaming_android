package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-6 下午1:58:07 类说明
 */
@SuppressWarnings("serial")
public class App_ContActModel extends BaseActModel
{
	private int page;
	private int has_next;
	private int total_num;
	private UserModel user;
	private List<App_ContModel> list;

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getHas_next()
	{
		return has_next;
	}

	public void setHas_next(int has_next)
	{
		this.has_next = has_next;
	}

	public int getTotal_num()
	{
		return total_num;
	}

	public void setTotal_num(int total_num)
	{
		this.total_num = total_num;
	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(UserModel user)
	{
		this.user = user;
	}

	public List<App_ContModel> getList()
	{
		return list;
	}

	public void setList(List<App_ContModel> list)
	{
		this.list = list;
	}

}
