package com.weibo.manager;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fanwe.live.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @包名 com.fanwe.xianrou.manager
 * @描述
 * @作者 Su
 * @创建时间 2017/3/15 15:16
 **/
public class ImageLoader
{
    private static final int PLACE_HOLDER_NONE = -100000;
    private static final int PLACE_HOLDER_DEFAULT = R.drawable.xr_ic_placeholder_image;
    private static final int DEFAULT_BLUR_RADIUS = 8;


    public static void load(Context context, String path, ImageView target)
    {
        Glide.with(context)
                .load(path)
                .placeholder(PLACE_HOLDER_DEFAULT)
                .error(PLACE_HOLDER_DEFAULT)
                .dontAnimate()
                .into(target);
    }

    public static void load(Context context, @DrawableRes int res, ImageView target)
    {
        Glide.with(context)
                .load(res)
                .placeholder(PLACE_HOLDER_DEFAULT)
                .error(PLACE_HOLDER_DEFAULT)
                .dontAnimate()
                .into(target);
    }

    /**
     * @param context
     * @param path
     * @param target
     * @param placeholder 加载中显示图片
     * @param errorHolder 加载失败显示图片
     */
    public static void load(Context context, String path, ImageView target, @DrawableRes int placeholder, @DrawableRes int errorHolder)
    {
        Glide.with(context)
                .load(path)
                .placeholder(placeholder)
                .error(errorHolder)
                .dontAnimate()
                .into(target);
    }

    public static void loadBlur(Context context, String path, ImageView target)
    {
        Glide.with(context)
                .load(path)
                .placeholder(PLACE_HOLDER_DEFAULT)
                .error(PLACE_HOLDER_DEFAULT)
                .bitmapTransform(new BlurTransformation(context, DEFAULT_BLUR_RADIUS))
                .into(target);
    }

//    private static <E> void load(Context context, E source, final ImageView target, int placeholder, int errorHolder, @Nullable Transformation[] transformations)
//    {
//        DrawableTypeRequest request = Glide.with(context).load(source);
//
//        if (transformations != null)
//        {
//            request.bitmapTransform(transformations);
//        }
//
//        if (placeholder != PLACE_HOLDER_NONE)
//        {
//            request.placeholder(placeholder);
//        }
//
//        if (errorHolder != PLACE_HOLDER_NONE)
//        {
//            request.error(errorHolder);
//        }
//
//        request.into(target);
//    }

}
