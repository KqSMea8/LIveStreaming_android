package com.fanwe.live.model;

public class RoomModel
{

	private int room_id;
	private String group_id;
	private String user_id; // 主播id
	private String head_image; // 主播头像

	public int getRoom_id()
	{
		return room_id;
	}

	public void setRoom_id(int room_id)
	{
		this.room_id = room_id;
	}

	public String getGroup_id()
	{
		return group_id;
	}

	public void setGroup_id(String group_id)
	{
		this.group_id = group_id;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getHead_image()
	{
		return head_image;
	}

	public void setHead_image(String head_image)
	{
		this.head_image = head_image;
	}

}
