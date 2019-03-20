package com.fanwe.live.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/29.
 */
public class BitmapFactoryUtils
{

    /**
     * 大图片处理机制
     * 利用Bitmap 转存 R图片
     */
    public static Bitmap getBitmapForImgResourse(Context mContext, int imgId, ImageView mImageView) throws IOException
    {
        InputStream is = mContext.getResources().openRawResource(imgId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = 1;
        Bitmap btp = BitmapFactory.decodeStream(is, null, options);
        mImageView.setImageBitmap(btp);
        is.close();
        return btp;
    }


}
