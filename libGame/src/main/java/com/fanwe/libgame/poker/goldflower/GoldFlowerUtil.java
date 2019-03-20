package com.fanwe.libgame.poker.goldflower;

import com.fanwe.games.R;
import com.fanwe.libgame.poker.goldflower.constant.GoldFlowerResultType;

/**
 * Created by Administrator on 2017/6/10.
 */

public class GoldFlowerUtil
{
    /**
     * 获得炸金花牌面类型的图片资源id
     *
     * @param resultType {@link GoldFlowerResultType}
     * @return
     */
    public static int getResultTypeImageResId(int resultType)
    {
        switch (resultType)
        {
            case GoldFlowerResultType.BAO_ZI:
                return R.drawable.ic_goldflower_result_type_bao_zi;
            case GoldFlowerResultType.TONG_HUA_SHUN:
                return R.drawable.ic_goldflower_result_type_tong_hua_shun;
            case GoldFlowerResultType.TONG_HUA:
                return R.drawable.ic_goldflower_result_type_tong_hua;
            case GoldFlowerResultType.SHUN_ZI:
                return R.drawable.ic_goldflower_result_type_shun_zi;
            case GoldFlowerResultType.DUI_ZI:
                return R.drawable.ic_goldflower_result_type_dui_zi;
            case GoldFlowerResultType.DANP_AI:
                return R.drawable.ic_goldflower_result_type_dan_pai;
            default:
                return 0;
        }
    }

}
