package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgRedEnvelope extends CustomMsg
{
	private String desc; // 收到的提示内容
	private String fonts_color; // 字体颜色
	private String icon; // 图片链接
	private int user_prop_id; // 抢红包的id
	private long total_ticket; // 总钱票数量
	// add
	private String red_info;// 红包信息
	private boolean isOnclicked;// 是否已经抢过

	public CustomMsgRedEnvelope()
	{
		super();
		setType(CustomMsgType.MSG_RED_ENVELOPE);
	}

	public long getTotal_ticket()
	{
		return total_ticket;
	}

	public void setTotal_ticket(long total_ticket)
	{
		this.total_ticket = total_ticket;
	}

	public int getUser_prop_id()
	{
		return user_prop_id;
	}

	public void setUser_prop_id(int user_prop_id)
	{
		this.user_prop_id = user_prop_id;
	}

	public String getRed_info()
	{
		return red_info;
	}

	public void setRed_info(String red_info)
	{
		this.red_info = red_info;
	}

	public boolean isOnclicked()
	{
		return isOnclicked;
	}

	public void setOnclicked(boolean isOnclicked)
	{
		this.isOnclicked = isOnclicked;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getFonts_color()
	{
		return fonts_color;
	}

	public void setFonts_color(String fonts_color)
	{
		this.fonts_color = fonts_color;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}
}
