package com.fanwe.live.appview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecordView;
import com.fanwe.live.R;

/**
 * Created by Administrator on 2016/7/18.
 */
public class LivePrivateChatRecordView extends BaseAppView
{
    private SDRecordView view_record_gesture;
    private View ll_cancel_view;
    private ImageView iv_record;
    private TextView tv_record_time;
    private TextView tv_tip;

    private SDRecordView.RecordViewListener recordViewListener;

    public void setRecordViewListener(SDRecordView.RecordViewListener recordViewListener)
    {
        this.recordViewListener = recordViewListener;
    }

    public LivePrivateChatRecordView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LivePrivateChatRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LivePrivateChatRecordView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_private_chat_record);

        view_record_gesture = find(R.id.view_record_gesture);
        ll_cancel_view = find(R.id.ll_cancel_view);
        iv_record = find(R.id.iv_record);
        tv_record_time = find(R.id.tv_record_time);
        tv_tip = find(R.id.tv_tip);

        view_record_gesture.setListener(defaultRecordViewListener);
        view_record_gesture.setCancelView(ll_cancel_view);
    }

    public void setRecordView(View view)
    {
        view_record_gesture.setRecordView(view);
    }

    public void cancelGesture()
    {
        view_record_gesture.cancel();
    }

    public void setTextRecordTime(String text)
    {
        tv_record_time.setText(text);
    }

    /**
     * 触摸监听
     */
    private SDRecordView.RecordViewListener defaultRecordViewListener = new SDRecordView.RecordViewListener()
    {
        @Override
        public void onUpCancelView()
        {
            tv_tip.setBackgroundColor(Color.TRANSPARENT);
            SDViewUtil.setGone(ll_cancel_view);
            if (recordViewListener != null)
            {
                recordViewListener.onUpCancelView();
            }
        }

        @Override
        public void onUp()
        {
            tv_tip.setBackgroundColor(Color.TRANSPARENT);
            SDViewUtil.setGone(ll_cancel_view);
            if (recordViewListener != null)
            {
                recordViewListener.onUp();
            }
        }

        @Override
        public void onLeaveCancelView()
        {
            tv_tip.setText("手指上滑，取消发送");
            tv_tip.setBackgroundColor(Color.TRANSPARENT);
            if (recordViewListener != null)
            {
                recordViewListener.onLeaveCancelView();
            }
        }

        @Override
        public void onEnterCancelView()
        {
            tv_tip.setText("放开手指，取消发送");
            tv_tip.setBackgroundColor(Color.parseColor("#44FF0000"));
            if (recordViewListener != null)
            {
                recordViewListener.onEnterCancelView();
            }
        }

        @Override
        public boolean onDownRecordView()
        {
            SDViewUtil.setVisible(ll_cancel_view);
            tv_tip.setText("手指上滑，取消发送");
            if (recordViewListener != null)
            {
                return recordViewListener.onDownRecordView();
            }
            return true;
        }

        @Override
        public void onCancel()
        {
            SDViewUtil.setGone(ll_cancel_view);
            if (recordViewListener != null)
            {
                recordViewListener.onCancel();
            }
        }
    };


    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }
}
