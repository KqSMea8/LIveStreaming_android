package com.fanwe.live.model;

import java.util.ArrayList;
import java.util.List;

public class AnimatorPath
{
	private int type;// 路径移动类型
	private List<AnimatorPart> list_part;

	// add
	public void addPart(AnimatorPart part)
	{
		if (part != null)
		{
			if (list_part == null)
			{
				list_part = new ArrayList<AnimatorPart>();
			}
			list_part.add(part);
		}
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public List<AnimatorPart> getList_part()
	{
		return list_part;
	}

	public void setList_part(List<AnimatorPart> list_part)
	{
		this.list_part = list_part;
	}

}
