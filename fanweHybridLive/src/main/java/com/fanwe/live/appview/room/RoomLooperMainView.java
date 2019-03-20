package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;

/**
 * Created by Administrator on 2016/7/15.
 */
public abstract class RoomLooperMainView<T> extends RoomLooperView<T>
{
    public RoomLooperMainView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomLooperMainView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomLooperMainView(Context context)
    {
        super(context);
    }

    @Override
    protected ISDLooper createLooper()
    {
        return new SDSimpleLooper();
    }
}
