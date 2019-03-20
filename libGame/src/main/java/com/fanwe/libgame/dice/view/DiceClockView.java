package com.fanwe.libgame.dice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;

/**
 * Created by yhz on 2017/6/23.
 */

public class DiceClockView  extends BaseGameView
{
    public DiceClockView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public DiceClockView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public DiceClockView(Context context)
    {
        super(context);
        init();
    }

    private TextView tv_time;

    private void init()
    {
        setContentView(R.layout.view_dice_clock);
        tv_time = (TextView) findViewById(R.id.tv_time);
    }

    public void setTextLeftTime(String text)
    {
        tv_time.setText(text);
    }
}
