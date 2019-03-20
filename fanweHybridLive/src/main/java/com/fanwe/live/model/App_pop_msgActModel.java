package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_pop_msgActModel extends BaseActModel
{
	private int is_backpack;
	private int status;
	public int getIs_backpack() {
		return is_backpack;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public void setIs_backpack(int is_backpack) {
		this.is_backpack = is_backpack;
	}
}
