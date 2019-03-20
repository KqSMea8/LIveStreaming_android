package com.fanwe.live.model.custommsg;

import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgCreaterLeave extends CustomMsg
{

	private String text = SDResourcesUtil.getString(R.string.live_creater_leave);

	public CustomMsgCreaterLeave()
	{
		super();
		setType(CustomMsgType.MSG_CREATER_LEAVE);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}
