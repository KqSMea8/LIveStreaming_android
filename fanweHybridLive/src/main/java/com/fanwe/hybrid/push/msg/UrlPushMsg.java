package com.fanwe.hybrid.push.msg;

public class UrlPushMsg extends PushMsg
{
	private String data;
	private String title;

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
