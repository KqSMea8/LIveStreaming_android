package com.fanwe.libgame.poker.bull.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.fanwe.games.R;
import com.fanwe.libgame.poker.view.PokerGroupView;

/**
 * 斗牛多张扑克牌view
 */
public class BullPokerGroupView extends PokerGroupView
{
    public BullPokerGroupView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public BullPokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BullPokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private BullPokerView view_poker_0;
    private BullPokerView view_poker_1;
    private BullPokerView view_poker_2;
    private BullPokerView view_poker_3;
    private BullPokerView view_poker_4;

    private void init()
    {
        setContentView(R.layout.view_bull_poker_group);
        view_poker_0 = (BullPokerView) findViewById(R.id.view_poker_0);
        view_poker_1 = (BullPokerView) findViewById(R.id.view_poker_1);
        view_poker_2 = (BullPokerView) findViewById(R.id.view_poker_2);
        view_poker_3 = (BullPokerView) findViewById(R.id.view_poker_3);
        view_poker_4 = (BullPokerView) findViewById(R.id.view_poker_4);

        addPokerView(view_poker_0);
        addPokerView(view_poker_1);
        addPokerView(view_poker_2);
        addPokerView(view_poker_3);
        addPokerView(view_poker_4);
    }
}
