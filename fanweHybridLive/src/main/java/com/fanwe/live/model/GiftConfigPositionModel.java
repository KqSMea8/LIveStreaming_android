package com.fanwe.live.model;

public class GiftConfigPositionModel
{
	private int position;// 动画位置(0-顶部，1-中间，2-底部)
	private int repeat_count; // gif重复次数，0-无限播放

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	public int getRepeat_count()
	{
		return repeat_count;
	}

	public void setRepeat_count(int repeat_count)
	{
		this.repeat_count = repeat_count;
	}

}
