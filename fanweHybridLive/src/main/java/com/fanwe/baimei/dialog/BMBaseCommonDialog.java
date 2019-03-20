package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

/**
 * 包名 com.fanwe.baimei.dialog
 * 描述 一般样式弹窗基类
 * 作者 Su
 * 创建时间 2017/5/16 16:07
 **/
public abstract class BMBaseCommonDialog extends SDDialogBase
{
    private TextView mTitleTextView;
    private ImageView mCloseImageView;
    private FrameLayout mContentContainer;

    protected abstract
    @LayoutRes
    int getContentLayoutId();


    public BMBaseCommonDialog(Activity activity)
    {
        super(activity);
        initBMBaseDialog();
    }

    private void initBMBaseDialog()
    {
        setCancelable(true);

        setCanceledOnTouchOutside(true);

        setAnimations(R.style.anim_top_top);

        setContentView(R.layout.bm_dialog_base_common);

        getContentContainer().addView(LayoutInflater.from(getContext()).inflate(getContentLayoutId(), getContentContainer(), false),
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        getCloseImageView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    private TextView getTitleTextView()
    {
        if (mTitleTextView == null)
        {
            mTitleTextView = (TextView) findViewById(R.id.tv_title_bm_dialog_base_common);
        }
        return mTitleTextView;
    }

    private ImageView getCloseImageView()
    {
        if (mCloseImageView == null)
        {
            mCloseImageView = (ImageView) findViewById(R.id.iv_close_bm_dialog_base_common);
        }
        return mCloseImageView;
    }

    private FrameLayout getContentContainer()
    {
        if (mContentContainer == null)
        {
            mContentContainer = (FrameLayout) findViewById(R.id.fl_content_bm_dialog_base_common);
        }
        return mContentContainer;
    }

    public void setTitleText(String title)
    {
        getTitleTextView().setText(title);
    }

}
