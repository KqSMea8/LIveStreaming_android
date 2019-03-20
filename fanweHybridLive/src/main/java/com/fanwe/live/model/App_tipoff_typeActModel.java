package com.fanwe.live.model;

import java.util.List;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午6:08:00 类说明
 */
@SuppressWarnings("serial")
public class App_tipoff_typeActModel extends BaseActModel
{
	private List<App_tipoff_typeModel> list;

	public List<App_tipoff_typeModel> getList()
	{
		return list;
	}

	public void setList(List<App_tipoff_typeModel> list)
	{
		this.list = list;
	}

}
