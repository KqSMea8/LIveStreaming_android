package com.fanwe.shortvideo.common.utils;

import android.content.Context;

/**
 * Created by vinsonswang on 2017/11/7.
 */

public class DensityUtil {
    public static int dip2px(Context context, float dpValue) {
        return (int)(0.5F + dpValue * context.getResources().getDisplayMetrics().density);
    }

    public static int px2dip(Context context, float pxValue) {
        return (int)(0.5F + pxValue / context.getResources().getDisplayMetrics().density);
    }
}
