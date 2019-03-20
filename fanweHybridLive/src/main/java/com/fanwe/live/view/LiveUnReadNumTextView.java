package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.live.R;

/**
 * Created by Administrator on 2016/7/21.
 */
public class LiveUnReadNumTextView extends TextView
{
    private static final int DEFAULT_MAX_NUM = 99;
    private int maxNum = DEFAULT_MAX_NUM;

    public void setMaxNum(int maxNum)
    {
        this.maxNum = maxNum;
    }

    public LiveUnReadNumTextView(Context context)
    {
        super(context);
    }

    public LiveUnReadNumTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LiveUnReadNumTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private String setTextUnReadNum(long unReadNum)
    {
        setVisibility(View.VISIBLE);
        if (unReadNum > DEFAULT_MAX_NUM)
        {
            setBackgroundResource(R.drawable.layer_red_half_round);
            return maxNum + "+";
        } else if (unReadNum >= 10)
        {
            setBackgroundResource(R.drawable.layer_red_half_round);
            return Long.toString(unReadNum);
        } else if (unReadNum <= 0)
        {
            setVisibility(View.INVISIBLE);
            return "0";
        } else
        {
            setBackgroundResource(R.drawable.bg_circle_red);
            return Long.toString(unReadNum);
        }
    }

    /**
     * 设置未读数量
     * @param unReadNum
     */
    public void setUnReadNumText(long unReadNum)
    {
        this.setText(setTextUnReadNum(unReadNum));
    }
}
