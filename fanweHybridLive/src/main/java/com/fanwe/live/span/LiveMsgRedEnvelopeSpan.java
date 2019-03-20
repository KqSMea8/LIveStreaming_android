package com.fanwe.live.span;

import com.fanwe.library.utils.SDViewUtil;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class LiveMsgRedEnvelopeSpan extends SDNetImageSpan
{

	public LiveMsgRedEnvelopeSpan(TextView view)
	{
		super(view);
	}

	@Override
	protected void beforeReturnDrawable(Drawable drawable)
	{
		if (drawable != null)
		{
			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();

			int targetWidth = SDViewUtil.dp2px(30);
			int targetHeight = SDViewUtil.getScaleHeight(width, height, targetWidth);

			drawable.setBounds(0, 0, targetWidth, targetHeight);
		}
	}

}
