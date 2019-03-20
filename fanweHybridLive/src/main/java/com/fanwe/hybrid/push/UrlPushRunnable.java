package com.fanwe.hybrid.push;

import android.content.Context;
import android.content.Intent;

import com.fanwe.hybrid.activity.MainActivity;
import com.fanwe.hybrid.push.msg.UrlPushMsg;
import com.umeng.message.entity.UMessage;

public class UrlPushRunnable extends PushRunnable
{

	public UrlPushRunnable(Context context, UMessage msg)
	{
		super(context, msg);
	}

	@Override
	public void run()
	{
		UrlPushMsg url = parseObject(UrlPushMsg.class);
		if (url != null)
		{
			Intent intent = new Intent(context, MainActivity.class);
			intent.putExtra(MainActivity.EXTRA_URL, url.getData());
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}

}
