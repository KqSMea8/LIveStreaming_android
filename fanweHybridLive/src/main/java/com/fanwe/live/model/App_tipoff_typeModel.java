package com.fanwe.live.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午6:10:04 类说明
 */
public class App_tipoff_typeModel
{
	private long id;
	private String name;
	private boolean isSelected;

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
