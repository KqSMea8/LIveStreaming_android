package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgPopMsg extends CustomMsg
{

	private String fonts_color; // 字体颜色
	private String desc; // 弹幕消息
	private long total_ticket; // 总钱票数量
	private String room_id;

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public CustomMsgPopMsg()
	{
		super();
		setType(CustomMsgType.MSG_POP_MSG);
	}

	public String getFonts_color()
	{
		return fonts_color;
	}

	public void setFonts_color(String fonts_color)
	{
		this.fonts_color = fonts_color;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public long getTotal_ticket()
	{
		return total_ticket;
	}

	public void setTotal_ticket(long total_ticket)
	{
		this.total_ticket = total_ticket;
	}

}
