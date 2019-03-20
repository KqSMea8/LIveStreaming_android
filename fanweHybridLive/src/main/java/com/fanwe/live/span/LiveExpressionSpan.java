package com.fanwe.live.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import com.fanwe.library.utils.SDViewUtil;

public class LiveExpressionSpan extends ImageSpan
{

    public LiveExpressionSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public LiveExpressionSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public LiveExpressionSpan(Context context, int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    public LiveExpressionSpan(Context context, int resourceId)
    {
        super(context, resourceId);
    }

    public LiveExpressionSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public LiveExpressionSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public LiveExpressionSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public LiveExpressionSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public LiveExpressionSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public LiveExpressionSpan(Drawable d)
    {
        super(d);
    }

    @Override
    public Drawable getDrawable()
    {
        Drawable drawable = super.getDrawable();
        if (drawable != null)
        {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();

            int targetHeight = SDViewUtil.dp2px(20);
            int targetWidth = SDViewUtil.getScaleWidth(width, height, targetHeight);

            drawable.setBounds(0, 0, targetWidth, targetHeight);
        }
        return drawable;
    }
}
