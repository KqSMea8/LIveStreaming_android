package com.fanwe.baimei.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.baimei.model.BMGameCenterGameModel;
import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * 包名 com.fanwe.baimei.appview
 * 描述 游戏主页游戏展示界面item
 * 作者 Su
 * 创建时间 2017/5/16 8:49
 **/
public class BMGameCenterGameGalleryPageView extends SDAppView
{
    private BMGameCenterGameModel mGameModel;
    private ImageView mImageView;
    private BMGameCenterGameGalleryPageViewCallback mCallback;


    public BMGameCenterGameGalleryPageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        initBMGameCenterGameGalleryPageView(context);
    }

    public BMGameCenterGameGalleryPageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        initBMGameCenterGameGalleryPageView(context);
    }

    public BMGameCenterGameGalleryPageView(Context context)
    {
        super(context);

        initBMGameCenterGameGalleryPageView(context);
    }

    private void initBMGameCenterGameGalleryPageView(Context context)
    {
        setContentView(R.layout.bm_view_game_gallery_page_game_center);

        getImageView().setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCallback().onPageClick(v, getGameModel());
            }
        });
    }

    public BMGameCenterGameModel getGameModel()
    {
        return mGameModel;
    }

    public void setGameModel(BMGameCenterGameModel mGameModel)
    {
        this.mGameModel = mGameModel;

        GlideUtil.load(mGameModel.getIndex_image()).into(getImageView());
    }

    private ImageView getImageView()
    {
        if (mImageView == null)
        {
            mImageView = (ImageView) findViewById(R.id.iv_game_gallery_page);
        }
        return mImageView;
    }

    private BMGameCenterGameGalleryPageViewCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMGameCenterGameGalleryPageViewCallback()
            {
                @Override
                public void onPageClick(View view, BMGameCenterGameModel model)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMGameCenterGameGalleryPageViewCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    public interface BMGameCenterGameGalleryPageViewCallback
    {
        void onPageClick(View view, BMGameCenterGameModel model);
    }
}
