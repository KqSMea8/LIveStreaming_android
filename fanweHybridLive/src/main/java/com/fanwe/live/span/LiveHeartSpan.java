package com.fanwe.live.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import com.fanwe.library.utils.SDViewUtil;

public class LiveHeartSpan extends ImageSpan
{

    public LiveHeartSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public LiveHeartSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public LiveHeartSpan(Context context, int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    public LiveHeartSpan(Context context, int resourceId)
    {
        super(context, resourceId);
    }

    public LiveHeartSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public LiveHeartSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public LiveHeartSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public LiveHeartSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public LiveHeartSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public LiveHeartSpan(Drawable d)
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

            int targetWidth = SDViewUtil.dp2px(15);
            int targetHeight = SDViewUtil.getScaleHeight(width, height, targetWidth);

            drawable.setBounds(0, 0, targetWidth, targetHeight);
        }
        return drawable;
    }
}
