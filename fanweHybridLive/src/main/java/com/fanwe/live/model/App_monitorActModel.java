package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.pay.model.App_monitorLiveModel;

public class App_monitorActModel extends BaseActModel
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

	private App_monitorLiveModel live;

	public App_monitorLiveModel getLive()
	{
		return live;
	}

	public void setLive(App_monitorLiveModel live)
	{
		this.live = live;
	}
}
