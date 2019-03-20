package com.fanwe.libgame.dice.view;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.libgame.dice.constant.DiceConstant;
import com.fanwe.libgame.dice.view.base.DiceScoreBaseBoardView;


/**
 * 包名: com.librarygames.view
 * 描述:  小
 * 作者: Su
 * 创建时间: 2017/5/23 10:07
 **/
public class DiceScoreBoardSmallView extends DiceScoreBaseBoardView
{

    public DiceScoreBoardSmallView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public DiceScoreBoardSmallView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DiceScoreBoardSmallView(Context context)
    {
        super(context);
    }

    @Override
    protected int getDiceScoreType()
    {
        return DiceConstant.DICE_BOARD_SMALL;
    }

}
