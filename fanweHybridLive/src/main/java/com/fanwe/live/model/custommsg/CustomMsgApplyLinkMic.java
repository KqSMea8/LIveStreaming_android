package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgApplyLinkMic extends CustomMsg
{
	public CustomMsgApplyLinkMic()
	{
		super();
		setType(CustomMsgType.MSG_APPLY_LINK_MIC);
	}
	private int room_id;
	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

}
