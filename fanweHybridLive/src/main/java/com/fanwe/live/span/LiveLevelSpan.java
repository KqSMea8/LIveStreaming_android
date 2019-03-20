package com.fanwe.live.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import com.fanwe.library.utils.SDViewUtil;

public class LiveLevelSpan extends ImageSpan
{

    public LiveLevelSpan(Context context, Bitmap b, int verticalAlignment)
    {
        super(context, b, verticalAlignment);
    }

    public LiveLevelSpan(Context context, Bitmap b)
    {
        super(context, b);
    }

    public LiveLevelSpan(Context context, int resourceId, int verticalAlignment)
    {
        super(context, resourceId, verticalAlignment);
    }

    public LiveLevelSpan(Context context, int resourceId)
    {
        super(context, resourceId);
    }

    public LiveLevelSpan(Context context, Uri uri, int verticalAlignment)
    {
        super(context, uri, verticalAlignment);
    }

    public LiveLevelSpan(Context context, Uri uri)
    {
        super(context, uri);
    }

    public LiveLevelSpan(Drawable d, int verticalAlignment)
    {
        super(d, verticalAlignment);
    }

    public LiveLevelSpan(Drawable d, String source, int verticalAlignment)
    {
        super(d, source, verticalAlignment);
    }

    public LiveLevelSpan(Drawable d, String source)
    {
        super(d, source);
    }

    public LiveLevelSpan(Drawable d)
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

            int targetWidth = SDViewUtil.dp2px(35);
            int targetHeight = SDViewUtil.getScaleHeight(width, height, targetWidth);

            drawable.setBounds(0, 0, targetWidth, targetHeight);
        }
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint)
    {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        canvas.save();
        int transY = bottom - rect.bottom;
        transY -= paint.getFontMetricsInt().descent;
        canvas.translate(x, transY);
        d.draw(canvas);
        canvas.restore();
    }

}
