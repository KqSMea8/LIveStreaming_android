package com.fanwe.hybrid.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import android.text.TextUtils;

public class SDCookieFormater
{

	private static final String KEY_GROUP = "=";
	private static final String KEY_SPLIT = ";";

	private String mCookie;

	public SDCookieFormater(String cookie)
	{
		this.mCookie = cookie;
	}

	public Map<String, String> format()
	{
		Map<String, String> mapCookie = new LinkedHashMap<String, String>();
		if (!TextUtils.isEmpty(mCookie) && mCookie.contains(KEY_GROUP))
		{
			String[] arrGroup = mCookie.split(KEY_SPLIT);
			if (arrGroup != null && arrGroup.length > 0)
			{
				for (String group : arrGroup)
				{
					String[] arrItem = group.split(KEY_GROUP);
					if (arrItem != null && arrItem.length == 2)
					{
						mapCookie.put(arrItem[0].trim(), arrItem[1].trim());
					}
				}
			}
		}
		return mapCookie;
	}

}
