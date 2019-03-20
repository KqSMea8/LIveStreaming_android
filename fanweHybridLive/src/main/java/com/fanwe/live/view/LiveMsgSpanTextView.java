package com.fanwe.live.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.hybrid.activity.AppWebViewActivity;
import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.span.model.MatcherInfo;
import com.fanwe.library.span.view.SDSpannableTextView;
import com.fanwe.live.span.LiveUrlSpan;

public class LiveMsgSpanTextView extends SDSpannableTextView
{

	public LiveMsgSpanTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public LiveMsgSpanTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public LiveMsgSpanTextView(Context context)
	{
		super(context);
	}

	@Override
	public void processSpannableStringBuilder(SDSpannableStringBuilder builder)
	{
		List<MatcherInfo> list = builder.MatcheUrl();
		for (final MatcherInfo info : list)
		{
			LiveUrlSpan urlSpan = new LiveUrlSpan();
			builder.setSpan(urlSpan, info);
			builder.coverSpan(urlSpan, new ClickableSpan()
			{

				@Override
				public void onClick(View widget)
				{
					Context context = getContext();
					if (context instanceof Activity)
					{
						Activity activity = (Activity) context;
						String url = info.getKey();
						Intent intent = new Intent(activity, AppWebViewActivity.class);
						intent.putExtra(AppWebViewActivity.EXTRA_URL, url);
						activity.startActivity(intent);
					}
				}
			});
		}
	}

}
