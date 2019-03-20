package com.fanwe.live.appview;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;

import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.live.R;

import java.io.File;

/**
 * Created by Administrator on 2016/10/12.
 */

public class LiveOssImageView extends BaseAppView
{
    private static final long maxSize = 5120 * 5120;

    private String mStrUrl;

    private com.yalantis.ucrop.view.UCropView mIv_image;

    private ImageFileCompresser mCompresser;

    private ImageFileCompresser.ImageFileCompresserListener imageFileCompresserListener;

    public void setImageFileCompresserListener(ImageFileCompresser.ImageFileCompresserListener l)
    {
        this.imageFileCompresserListener = l;
        if (l != null)
        {
            mCompresser.setmListener(imageFileCompresserListener);
        }
    }

    public LiveOssImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveOssImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveOssImageView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_live_oss_image;
    }

    protected void init()
    {
        register();
        initImageFileCompresser();
    }

    private void register()
    {
        mIv_image = find(R.id.iv_image);
        mIv_image.getOverlayView().setShowCropFrame(false);
        mIv_image.getOverlayView().setShowCropGrid(false);
        mIv_image.getCropImageView().setRotateEnabled(false);
        mIv_image.getOverlayView().setDimmedColor(Color.TRANSPARENT);
    }

    private void initImageFileCompresser()
    {
        mCompresser = new ImageFileCompresser();
        mCompresser.setmMaxLength(maxSize);
    }

    public void loadUrl(String url)
    {
        this.mStrUrl = url;
        String full_url = "file://" + mStrUrl;
        Uri uri = Uri.fromFile(new File(mStrUrl));
        try
        {
            mIv_image.getCropImageView().setImageUri(uri, null);
        } catch (Exception e)
        {

        }


        //GlideUtil.load(getContext(), full_url).into(mIv_image);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        mCompresser.deleteCompressedImageFile();
        super.onDetachedFromWindow();
    }

    public void clickUpload()
    {
        File file = new File(mStrUrl);
        mCompresser.compressImageFile(file);
    }
}
