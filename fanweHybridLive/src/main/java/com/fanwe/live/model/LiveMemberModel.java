package com.fanwe.live.model;

public class LiveMemberModel
{
	private String userPhone = "";
	private String userName;
	private String headImagePath = "";
	public String identifier = "";
	public String name;
	public boolean isSpeaking = false;
	public boolean isVideoIn = false;
	public boolean isShareSrc = false;
	public boolean isShareMovie = false;
	public boolean hasGetInfo = false;

	public LiveMemberModel()
	{

	}

	public LiveMemberModel(String phone)
	{
		userPhone = phone;
	}

	public LiveMemberModel(String phone, String name, String path)
	{
		userPhone = phone;
		userName = name;
		headImagePath = path;
	}

	public void setUserPhone(String phone)
	{
		userPhone = phone;
	}

	public String getUserPhone()
	{
		return userPhone;
	}

	public void setUserName(String name)
	{
		userName = name;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setHeadImagePath(String path)
	{
		headImagePath = path;
	}

	public String getHeadImagePath()
	{
		return headImagePath;
	}

	@Override
	public String toString()
	{
		return "MemberInfo identifier = " + identifier + ", isSpeaking = " + isSpeaking + ", isVideoIn = " + isVideoIn + ", isShareSrc = "
				+ isShareSrc + ", isShareMovie = " + isShareMovie + ", hasGetInfo = " + hasGetInfo + ", name = " + name;
	}
}