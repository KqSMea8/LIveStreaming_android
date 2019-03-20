package com.fanwe.hybrid.http;

import org.xutils.common.Callback.Cancelable;

import com.fanwe.library.adapter.http.handler.SDRequestHandler;

public class AppRequestHandler implements SDRequestHandler
{

	private Cancelable cancelable;

	public AppRequestHandler(Cancelable cancelable)
	{
		super();
		this.cancelable = cancelable;
	}

	@Override
	public void cancel()
	{
		if (cancelable != null)
		{
			cancelable.cancel();
		}
	}

}
