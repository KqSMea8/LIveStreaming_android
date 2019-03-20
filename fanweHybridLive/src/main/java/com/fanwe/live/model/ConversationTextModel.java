package com.fanwe.live.model;

public class ConversationTextModel {

	private int type ;
//	private MsgModel msg;
	private String msg;
	/**
	 * 
	 * @param type 0-左 1-右
	 * @param msg 文字消息
	 */
	public ConversationTextModel(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}
