package com.fanwe.libgame.poker.bull;

import com.fanwe.libgame.poker.bull.constant.BullResultType;
import com.fanwe.games.R;

/**
 * Created by Administrator on 2017/6/10.
 */

public class BullUtil
{
    /**
     * 获得炸金花牌面类型的图片资源id
     *
     * @param resultType {@link BullResultType}
     * @return
     */
    public static int getResultTypeImageResId(int resultType)
    {
        switch (resultType)
        {
            case BullResultType.WU_XIAO_NIU:
                return R.drawable.ic_bull_result_type_wu_xiao_niu;
            case BullResultType.ZHA_DAN:
                return R.drawable.ic_bull_result_type_zha_dan;
            case BullResultType.WU_HUA_NIU:
                return R.drawable.ic_bull_result_type_wu_hua_niu;
            case BullResultType.SI_HUA_NIU:
                return R.drawable.ic_bull_result_type_si_hua_niu;
            case BullResultType.NIU_NIU:
                return R.drawable.ic_bull_result_type_niu_niu;
            case BullResultType.NIU_9:
                return R.drawable.ic_bull_result_type_niu_9;
            case BullResultType.NIU_8:
                return R.drawable.ic_bull_result_type_niu_8;
            case BullResultType.NIU_7:
                return R.drawable.ic_bull_result_type_niu_7;
            case BullResultType.NIU_6:
                return R.drawable.ic_bull_result_type_niu_6;
            case BullResultType.NIU_5:
                return R.drawable.ic_bull_result_type_niu_5;
            case BullResultType.NIU_4:
                return R.drawable.ic_bull_result_type_niu_4;
            case BullResultType.NIU_3:
                return R.drawable.ic_bull_result_type_niu_3;
            case BullResultType.NIU_2:
                return R.drawable.ic_bull_result_type_niu_2;
            case BullResultType.NIU_1:
                return R.drawable.ic_bull_result_type_niu_1;
            case BullResultType.MEI_NIU:
                return R.drawable.ic_bull_result_type_mei_niu;
            default:
                return 0;
        }
    }

}
