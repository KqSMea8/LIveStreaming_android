package com.fanwe.libgame.dice.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 包名:  com.librarygames.utils
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/24 10:20
 **/
public class DiceConstant
{
    public static final int DICE_BOARD_BIG = 0x2;
    public static final int DICE_BOARD_MIDDLE = 0x1;
    public static final int DICE_BOARD_SMALL = 0x0;

    @IntDef({DICE_BOARD_BIG, DICE_BOARD_MIDDLE, DICE_BOARD_SMALL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DiceScoreType
    {
    }

}
