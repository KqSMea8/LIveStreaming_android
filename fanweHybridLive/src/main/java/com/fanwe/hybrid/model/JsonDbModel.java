package com.fanwe.hybrid.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "JsonDbModel")
public class JsonDbModel
{
	@Column(name = "_id", isId = true)
	private int _id;

	@Column(name = "key")
	private String key;

	@Column(name = "value")
	private String value;

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
