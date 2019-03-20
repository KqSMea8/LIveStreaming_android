package com.fanwe.hybrid.model;

import com.fanwe.library.utils.SDTypeParseUtil;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-29 下午3:02:25 类说明
 */
public class CutPhotoModel
{
	private String w;
	private String h;

	public String getW()
	{
		return w;
	}

	public void setW(String w)
	{
		this.w = w;
	}

	public String getH()
	{
		return h;
	}

	public void setH(String h)
	{
		this.h = h;
	}

	public float getIntH()
	{
		return SDTypeParseUtil.getFloat(h, 0);
	}

	public float getIntW()
	{
		return SDTypeParseUtil.getFloat(w, 0);
	}
}
