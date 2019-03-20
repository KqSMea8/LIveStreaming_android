package com.fanwe.hybrid.push;

import java.util.Map;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.fanwe.library.utils.SDToast;
import com.umeng.message.entity.UMessage;

public class PushRunnable implements Runnable
{

	protected Context context;
	protected UMessage msg;
	private Class<?> startActivity;

	public PushRunnable(Context context, UMessage msg)
	{
		super();
		this.context = context;
		this.msg = msg;
	}

	public void setStartActivity(Class<?> startActivity)
	{
		this.startActivity = startActivity;
	}

	public Class<?> getStartActivity()
	{
		return startActivity;
	}

	public <T> T parseObject(Class<T> clazz)
	{
		try
		{
			Map<String, String> mapData = msg.extra;
			if (mapData != null)
			{
				String json = JSON.toJSONString(mapData);
				return JSON.parseObject(json, clazz);
			} else
			{
				return null;
			}
		} catch (Exception e)
		{
			SDToast.showToast("数据解析失败");
			return null;
		}
	}

	@Override
	public void run()
	{

	}

}
