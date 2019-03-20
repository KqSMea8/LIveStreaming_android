package com.weibo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 项目: SAdapter
 * 包名: com.scottsu.sadapter
 * 描述:
 * 作者: Su
 * 日期: 2017/7/13 14:04
 **/
public class ReplaceableTextView extends AppCompatTextView
{
    public static final String PLACE_HOLDER = "$";


    @Override
    public void setText(CharSequence text, BufferType type)
    {
        super.setText(TextUtils.isEmpty(getTargetString()) ? text
                : text.toString().replace(PLACE_HOLDER, getTargetString()), type);
    }

    //子类重写
    protected String getTargetString()
    {
        return null;
    }

    public ReplaceableTextView(Context context)
    {
        super(context);
    }

    public ReplaceableTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ReplaceableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
}
