package com.fanwe.hybrid.model;

/**
 * @title 升级model
 * @author yhz
 * @create time 2014-7-8
 */
@SuppressWarnings("serial")
public class InitUpgradeModel extends BaseActModel
{
	private String serverVersion = null;
	private String filename = null;
	private String android_upgrade = null;
	private String hasfile = null;
	private String has_upgrade = null;
	private String forced_upgrade = null;

	public String getForced_upgrade()
	{
		return forced_upgrade;
	}

	public void setForced_upgrade(String forced_upgrade)
	{
		this.forced_upgrade = forced_upgrade;
	}

	public String getServerVersion()
	{
		return serverVersion;
	}

	public void setServerVersion(String serverVersion)
	{
		this.serverVersion = serverVersion;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getAndroid_upgrade()
	{
		return android_upgrade;
	}

	public void setAndroid_upgrade(String android_upgrade)
	{
		this.android_upgrade = android_upgrade;
	}

	public String getHasfile()
	{
		return hasfile;
	}

	public void setHasfile(String hasfile)
	{
		this.hasfile = hasfile;
	}

	public String getHas_upgrade()
	{
		return has_upgrade;
	}

	public void setHas_upgrade(String has_upgrade)
	{
		this.has_upgrade = has_upgrade;
	}

}
