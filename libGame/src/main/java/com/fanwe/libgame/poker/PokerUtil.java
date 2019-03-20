package com.fanwe.libgame.poker;

import com.fanwe.games.R;
import com.fanwe.libgame.poker.constant.PokerType;

/**
 * Created by Administrator on 2017/6/10.
 */

public class PokerUtil
{
    /**
     * 获得牌类型的图片资源id
     *
     * @param pokerType {@link PokerType}
     * @return
     */
    public static int getPokerTypeImageResId(int pokerType)
    {
        switch (pokerType)
        {
            case PokerType.SPADE:
                return R.drawable.ic_poker_type_spade;
            case PokerType.HEART:
                return R.drawable.ic_poker_type_heart;
            case PokerType.CLUB:
                return R.drawable.ic_poker_type_club;
            case PokerType.DIAMOND:
                return R.drawable.ic_poker_type_diamond;
            default:
                return 0;
        }
    }

    /**
     * 获得牌的中央的图片资源id
     *
     * @param pokerType
     * @param pokerNumber
     * @return
     */
    public static int getPokerTypeCenterImageResId(int pokerType, int pokerNumber)
    {
        if (pokerNumber < 11)
        {
            return getPokerTypeImageResId(pokerType);
        } else
        {
            switch (pokerNumber)
            {
                case 11:
                    if (pokerType == PokerType.SPADE)
                    {
                        return R.drawable.ic_poker_spade_11_center;
                    } else if (pokerType == PokerType.HEART)
                    {
                        return R.drawable.ic_poker_heart_11_center;
                    } else if (pokerType == PokerType.CLUB)
                    {
                        return R.drawable.ic_poker_club_11_center;
                    } else if (pokerType == PokerType.DIAMOND)
                    {
                        return R.drawable.ic_poker_diamond_11_center;
                    } else
                    {
                        return 0;
                    }
                case 12:
                    if (pokerType == PokerType.SPADE)
                    {
                        return R.drawable.ic_poker_spade_12_center;
                    } else if (pokerType == PokerType.HEART)
                    {
                        return R.drawable.ic_poker_heart_12_center;
                    } else if (pokerType == PokerType.CLUB)
                    {
                        return R.drawable.ic_poker_club_12_center;
                    } else if (pokerType == PokerType.DIAMOND)
                    {
                        return R.drawable.ic_poker_diamond_12_center;
                    } else
                    {
                        return 0;
                    }
                case 13:
                    if (pokerType == PokerType.SPADE)
                    {
                        return R.drawable.ic_poker_spade_13_center;
                    } else if (pokerType == PokerType.HEART)
                    {
                        return R.drawable.ic_poker_heart_13_center;
                    } else if (pokerType == PokerType.CLUB)
                    {
                        return R.drawable.ic_poker_club_13_center;
                    } else if (pokerType == PokerType.DIAMOND)
                    {
                        return R.drawable.ic_poker_diamond_13_center;
                    } else
                    {
                        return 0;
                    }
                default:
                    return 0;
            }
        }
    }

    /**
     * 获得牌点数的图片资源id
     *
     * @param pokerType   {@link PokerType}
     * @param pokerNumber
     * @return
     */
    public static int getPokerNumberImageResId(int pokerType, int pokerNumber)
    {
        if (pokerType == PokerType.SPADE || pokerType == PokerType.CLUB)
        {
            return getPokerNumberImageResId_black(pokerNumber);
        } else
        {
            return getPokerNumberImageResId_red(pokerNumber);
        }
    }

    private static int getPokerNumberImageResId_red(int pokerNumber)
    {
        switch (pokerNumber)
        {
            case 1:
                return R.drawable.ic_poker_1_red;
            case 2:
                return R.drawable.ic_poker_2_red;
            case 3:
                return R.drawable.ic_poker_3_red;
            case 4:
                return R.drawable.ic_poker_4_red;
            case 5:
                return R.drawable.ic_poker_5_red;
            case 6:
                return R.drawable.ic_poker_6_red;
            case 7:
                return R.drawable.ic_poker_7_red;
            case 8:
                return R.drawable.ic_poker_8_red;
            case 9:
                return R.drawable.ic_poker_9_red;
            case 10:
                return R.drawable.ic_poker_10_red;
            case 11:
                return R.drawable.ic_poker_11_red;
            case 12:
                return R.drawable.ic_poker_12_red;
            case 13:
                return R.drawable.ic_poker_13_red;
            default:
                return 0;
        }
    }

    private static int getPokerNumberImageResId_black(int pokerNumber)
    {
        switch (pokerNumber)
        {
            case 1:
                return R.drawable.ic_poker_1_black;
            case 2:
                return R.drawable.ic_poker_2_black;
            case 3:
                return R.drawable.ic_poker_3_black;
            case 4:
                return R.drawable.ic_poker_4_black;
            case 5:
                return R.drawable.ic_poker_5_black;
            case 6:
                return R.drawable.ic_poker_6_black;
            case 7:
                return R.drawable.ic_poker_7_black;
            case 8:
                return R.drawable.ic_poker_8_black;
            case 9:
                return R.drawable.ic_poker_9_black;
            case 10:
                return R.drawable.ic_poker_10_black;
            case 11:
                return R.drawable.ic_poker_11_black;
            case 12:
                return R.drawable.ic_poker_12_black;
            case 13:
                return R.drawable.ic_poker_13_black;
            default:
                return 0;
        }
    }


}
