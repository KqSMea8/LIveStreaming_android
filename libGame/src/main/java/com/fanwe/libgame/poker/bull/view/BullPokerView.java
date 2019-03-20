package com.fanwe.libgame.poker.bull.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.fanwe.libgame.poker.view.PokerView;
import com.fanwe.games.R;

/**
 * 斗牛单张扑克牌view
 */
public class BullPokerView extends PokerView
{
    public BullPokerView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public BullPokerView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BullPokerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setPokerBackImageResId(R.drawable.bg_poker_back_bull);
    }
}
