package com.fanwe.hybrid.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.Constant.DeviceType;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;

public class RequestModel
{
	private Map<String, Object> mData = new HashMap<String, Object>();
	private Map<String, File> mDataFile = new HashMap<String, File>();

	private boolean isNeedCache = true;

	public RequestModel(Map<String, Object> mData)
	{
		super();
		this.mData = mData;
	}

	public RequestModel()
	{
		this.put("screen_width", SDViewUtil.getScreenWidth());
		this.put("screen_height", SDViewUtil.getScreenHeight());
		this.put("sdk_type", DeviceType.DEVICE_ANDROID);
		this.put("sdk_version_name", SDPackageUtil.getVersionCode());
	}

	public void putAct(String act)
	{
		this.put("act", act);
	}

	public void putCtl(String ctl)
	{
		this.put("ctl", ctl);
	}

	public void putCtlAndAct(String ctl, String act)
	{
		this.put("ctl", ctl);
		this.put("act", act);
	}

	public void putActAndAct_2(String act, String act_2)
	{
		putAct(act);
		this.put("act_2", act_2);
	}

	public Map<String, File> getmDataFile()
	{
		return mDataFile;
	}

	public void setmDataFile(Map<String, File> mDataFile)
	{
		this.mDataFile = mDataFile;
	}

	public Map<String, Object> getmData()
	{
		return mData;
	}

	public void setmData(Map<String, Object> mData)
	{
		this.mData = mData;
	}

	public boolean isNeedCache()
	{
		return isNeedCache;
	}

	public void setNeedCache(boolean isNeedCache)
	{
		this.isNeedCache = isNeedCache;
	}

	public void put(String key, Object value)
	{
		mData.put(key, value);
	}

	public void putFile(String key, File file)
	{
		mDataFile.put(key, file);
	}

}
