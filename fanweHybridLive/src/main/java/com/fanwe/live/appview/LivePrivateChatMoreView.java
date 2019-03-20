package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 私聊界面，更多里面的布局
 */
public class LivePrivateChatMoreView extends BaseAppView implements ILivePrivateChatMoreView
{
    public LivePrivateChatMoreView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LivePrivateChatMoreView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LivePrivateChatMoreView(Context context)
    {
        super(context);
        init();
    }

    private LinearLayout lin_iv_gift;
    private LinearLayout lin_iv_camera;
    private LinearLayout lin_iv_photo;
    private LinearLayout lin_iv_send_coin;
    private LinearLayout lin_iv_send_diamond;

    private PrivateChatMoreViewCallback mCallback;

    protected void init()
    {
        setContentView(R.layout.view_live_private_chat_more);

        lin_iv_gift = find(R.id.lin_iv_gift);
        lin_iv_camera = find(R.id.lin_iv_camera);
        lin_iv_photo = find(R.id.lin_iv_photo);
        lin_iv_send_coin = find(R.id.lin_iv_send_coin);
        lin_iv_send_diamond = find(R.id.lin_iv_send_diamond);
        setViewImageAndText(R.id.lin_iv_gift,R.drawable.ic_private_chat_more_gift,"礼物");
        setViewImageAndText(R.id.lin_iv_camera,R.drawable.ic_private_chat_more_camera,"拍摄");
        setViewImageAndText(R.id.lin_iv_photo,R.drawable.ic_private_chat_more_photo,"照片");
        setViewImageAndText(R.id.lin_iv_send_coin,R.drawable.ic_private_chat_send_coins,"赠送游戏币");
        setViewImageAndText(R.id.lin_iv_send_diamond,R.drawable.ic_private_chat_send_diamonds,"赠送钻石");

        lin_iv_gift.setOnClickListener(this);
        lin_iv_camera.setOnClickListener(this);
        lin_iv_photo.setOnClickListener(this);
        lin_iv_send_coin.setOnClickListener(this);
        lin_iv_send_diamond.setOnClickListener(this);
    }

    private void setViewImageAndText(int viewId,int resouceId,String text){
        ((ImageView) find(viewId).findViewById(R.id.image)).setImageResource(resouceId);
        ((TextView) find(viewId).findViewById(R.id.text)).setText(text);
    }
    public void setCallback(PrivateChatMoreViewCallback callback)
    {
        this.mCallback = callback;
    }

    /**
     * 设置拍照是否可用
     *
     * @param enable
     */
    public void setTakePhotoEnable(boolean enable)
    {
        if (enable)
        {
            SDViewUtil.setVisible(lin_iv_camera);
        } else
        {
            SDViewUtil.setGone(lin_iv_camera);
        }
    }

    /**
     * 赠送游戏币是否可用
     *
     * @param enable
     */
    public void setSendCoinsEnable(boolean enable)
    {
        if (enable)
        {
            SDViewUtil.setVisible(lin_iv_send_coin);
        } else
        {
            SDViewUtil.setGone(lin_iv_send_coin);
        }
    }

    /**
     * 赠送秀豆是否可用
     *
     * @param enable
     */
    public void setSendDiamondsEnable(boolean enable)
    {
        if (enable)
        {
            SDViewUtil.setVisible(lin_iv_send_diamond);
        } else
        {
            SDViewUtil.setGone(lin_iv_send_diamond);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == lin_iv_gift)
        {
            if (mCallback != null)
            {
                mCallback.onClickGift();
            }
        } else if (v == lin_iv_camera)
        {
            if (mCallback != null)
            {
                mCallback.onClickCamera();
            }
        } else if (v == lin_iv_photo)
        {
            if (mCallback != null)
            {
                mCallback.onClickPhoto();
            }
        } else if (v == lin_iv_send_coin)
        {
            if (mCallback != null)
            {
                mCallback.onClickSendCoin();
            }
        } else if (v == lin_iv_send_diamond)
        {
            mCallback.onClickSendDialond();
        }
    }

    @Override
    public void setHeightMatchParent()
    {

    }

    @Override
    public void setHeightWrapContent()
    {

    }

    public interface PrivateChatMoreViewCallback
    {
        /**
         * 点击礼物
         */
        void onClickGift();

        /**
         * 点击相册
         */
        void onClickPhoto();

        /**
         * 点击拍照
         */
        void onClickCamera();

        /**
         * 点击赠送游戏币
         */
        void onClickSendCoin();

        /**
         * 点击赠送秀豆
         */
        void onClickSendDialond();
    }
}

