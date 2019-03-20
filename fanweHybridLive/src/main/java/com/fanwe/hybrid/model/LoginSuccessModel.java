package com.fanwe.hybrid.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-9 下午6:57:01 类说明
 */
public class LoginSuccessModel
{
	private int id;
	private String user_name;
	private String patternpassword;
	private int is_current;
	private int userid;

	public int getUserid()
	{
		return userid;
	}

	public void setUserid(int userid)
	{
		this.userid = userid;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPatternpassword()
	{
		return patternpassword;
	}

	public void setPatternpassword(String patternpassword)
	{
		this.patternpassword = patternpassword;
	}

	public int getIs_current()
	{
		return is_current;
	}

	public void setIs_current(int is_current)
	{
		this.is_current = is_current;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}
}
