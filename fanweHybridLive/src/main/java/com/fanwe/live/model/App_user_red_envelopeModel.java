package com.fanwe.live.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-27 下午7:40:32 类说明
 */
public class App_user_red_envelopeModel
{
	private long user_id;
	private String nick_name;
	private int diamonds;
	private int sex;
	private String head_image;

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public String getHead_image()
	{
		return head_image;
	}

	public void setHead_image(String head_image)
	{
		this.head_image = head_image;
	}

	public long getUser_id()
	{
		return user_id;
	}

	public void setUser_id(long user_id)
	{
		this.user_id = user_id;
	}

	public String getNick_name()
	{
		return nick_name;
	}

	public void setNick_name(String nick_name)
	{
		this.nick_name = nick_name;
	}

	public int getDiamonds()
	{
		return diamonds;
	}

	public void setDiamonds(int diamonds)
	{
		this.diamonds = diamonds;
	}

}
