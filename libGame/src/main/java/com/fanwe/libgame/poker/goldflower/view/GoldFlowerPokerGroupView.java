package com.fanwe.libgame.poker.goldflower.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.fanwe.games.R;
import com.fanwe.libgame.poker.view.PokerGroupView;

/**
 * 炸金花多张扑克牌view
 */
public class GoldFlowerPokerGroupView extends PokerGroupView
{
    public GoldFlowerPokerGroupView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public GoldFlowerPokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GoldFlowerPokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private GoldFlowerPokerView view_poker_0;
    private GoldFlowerPokerView view_poker_1;
    private GoldFlowerPokerView view_poker_2;

    private void init()
    {
        setContentView(R.layout.view_goldflower_poker_group);
        view_poker_0 = (GoldFlowerPokerView) findViewById(R.id.view_poker_0);
        view_poker_1 = (GoldFlowerPokerView) findViewById(R.id.view_poker_1);
        view_poker_2 = (GoldFlowerPokerView) findViewById(R.id.view_poker_2);

        addPokerView(view_poker_0);
        addPokerView(view_poker_1);
        addPokerView(view_poker_2);
    }
}
