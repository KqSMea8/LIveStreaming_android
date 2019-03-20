package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.span.model.MatcherInfo;
import com.fanwe.library.span.view.SDSpannableTextView;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.span.LiveExpressionSpan;

import java.util.List;

public class LivePrivateChatSpanTextView extends SDSpannableTextView
{

    public LivePrivateChatSpanTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public LivePrivateChatSpanTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LivePrivateChatSpanTextView(Context context)
    {
        super(context);
    }

    @Override
    public void processSpannableStringBuilder(SDSpannableStringBuilder builder)
    {
        List<MatcherInfo> list = builder.Matche("\\[([^\\[\\]]+)\\]");
        for (final MatcherInfo info : list)
        {
            String key = info.getKey();
            key = key.substring(1, key.length() - 1);
            int resId = SDResourcesUtil.getIdentifierDrawable(key);
            if (resId != 0)
            {
                LiveExpressionSpan span = new LiveExpressionSpan(getContext(), resId);
                builder.setSpan(span, info);
            }
        }
    }

}
