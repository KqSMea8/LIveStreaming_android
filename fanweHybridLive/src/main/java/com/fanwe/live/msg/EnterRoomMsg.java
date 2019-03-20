package com.fanwe.live.msg;

public class EnterRoomMsg implements RoomMsg
{
	private int roomId;
	private boolean isCreater;

	public int getRoomId()
	{
		return roomId;
	}

	public void setRoomId(int roomId)
	{
		this.roomId = roomId;
	}

	public boolean isCreater()
	{
		return isCreater;
	}

	public void setCreater(boolean isCreater)
	{
		this.isCreater = isCreater;
	}

}
