package com.fanwe.baimei.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * Created by yhz on 2017/6/7.
 */

public class BMLiveClosePushView extends BaseAppView
{
    private ImageView iv_close_push;

    public BMLiveClosePushView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public BMLiveClosePushView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BMLiveClosePushView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        setContentView(R.layout.bm_view_close_push);
        initView();
    }

    private void initView()
    {
        iv_close_push = (ImageView) findViewById(R.id.iv_close_push);
        iv_close_push.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_close_push)
        {
            if (mClosePushListener != null)
            {
                mClosePushListener.clickClosePush();
            }
        }
    }

    private ClosePushListener mClosePushListener;

    public void setClosePushListener(ClosePushListener closePushListener)
    {
        mClosePushListener = closePushListener;
    }

    public interface ClosePushListener
    {
        void clickClosePush();
    }
}
