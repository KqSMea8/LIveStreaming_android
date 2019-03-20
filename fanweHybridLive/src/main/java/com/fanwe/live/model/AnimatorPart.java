package com.fanwe.live.model;

public class AnimatorPart
{

	private int interpolator;// 加速减速类型(0-减速，1-匀速，2-加速)
	private long duration;// 动画时长(毫秒)
	private float percent;// 距离百分比(0-1)

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public int getInterpolator()
	{
		return interpolator;
	}

	public void setInterpolator(int interpolator)
	{
		this.interpolator = interpolator;
	}

	public float getPercent()
	{
		return percent;
	}

	public void setPercent(float percent)
	{
		this.percent = percent;
	}

}
