package com.fanwe.hybrid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.model.CutPhotoModel;
import com.fanwe.hybrid.utils.SDImageUtil;
import com.fanwe.hybrid.utils.SDIntentUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import uk.co.senab.photoview.PhotoViewCrop;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-2-1 下午4:46:38 类说明
 */
public class DialogCropPhoto extends Dialog
{
    private OnCropBitmapListner mlistner;

    private ImageView mIvClose;
    private TextView mTvChoose;
    private TextView mTvSure;
    private View crop_view;
    private PhotoViewCrop crop_iamge;

    private Activity mContext;
    private String mPath;
    private CutPhotoModel mModel;
    private int mDialogWidth;

    public DialogCropPhoto(Activity context, String path, OnCropBitmapListner listner, CutPhotoModel cut_model)
    {
        super(context, R.style.DialogCropPhoto);
        this.mContext = context;
        this.mPath = path;
        this.mlistner = listner;
        this.mModel = cut_model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_crop_photo);
        init();
    }

    private void init()
    {
        register();
        setDialogAttribute();
        setCropViewBitmap();
    }

    private void register()
    {
        mIvClose = (ImageView) findViewById(R.id.iv_close);
        mIvClose.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        mTvChoose = (TextView) findViewById(R.id.tv_choose);
        mTvChoose.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = SDIntentUtil.getSelectLocalImageIntent();
                mContext.startActivityForResult(intent, BotPhotoPopupView.REQUEST_CODE_SELECT_PHOTO);

                dismiss();
            }
        });

        mTvSure = (TextView) findViewById(R.id.tv_sure);
        mTvSure.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (mlistner != null)
                {
                    mlistner.callbackbitmap(crop_iamge.getCropViewBitmap());
                }

                dismiss();
            }
        });
        crop_view = findViewById(R.id.crop_view);
        crop_iamge = (PhotoViewCrop) findViewById(R.id.crop_iamge);
    }

    private void setDialogAttribute()
    {
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = (int) (SDViewUtil.getScreenWidth() * 0.9);
        p.height = (int) (p.width * 1.2); //
        window.setAttributes(p);
        mDialogWidth = p.width;
    }

    private void setCropViewBitmap()
    {
        float scalx = mModel.getIntH() / mModel.getIntW();
        int c_width = mDialogWidth - 250;
        int c_height = (int) (c_width * scalx);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(c_width, c_height);
        crop_view.setLayoutParams(lp);

        Bitmap bitmap = SDImageUtil.pathToBitmap(mPath);
        crop_iamge.setImageBitmap(bitmap);
        crop_iamge.setCropView(crop_view);
    }

    public interface OnCropBitmapListner
    {
        public void callbackbitmap(Bitmap bitmap);
    }
}
