package com.fanwe.shortvideo.editor.time.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.live.R;
import com.fanwe.shortvideo.common.utils.TCUtils;
import com.fanwe.shortvideo.editor.utils.Edit;
import com.fanwe.shortvideo.view.RangeRepeatSlider;
import com.tencent.liteav.basic.log.TXCLog;

import java.util.List;

public class TCVideoRepeatView extends RelativeLayout {

    private static final long DEAULT_REPEATE_TIME = 1000; //默认重复时间段
    private String TAG = TCVideoRepeatView.class.getSimpleName();

    private Context mContext;

    private TextView mTvTip, mTvTip2;
    private RecyclerView mRecyclerView;
    private RangeRepeatSlider mRangeSlider;

    private long mVideoDuration;
    private long mVideoStartPos;
    private long mVideoEndPos;
    private long mRepeatFrom;
    private long mRepeatTo;

    private TCVideoEditerAdapter mAdapter;

    private Edit.OnRepeatChangeListener mOnRepateChangeListener;

    public TCVideoRepeatView(Context context) {
        super(context);

        init(context);
    }

    public TCVideoRepeatView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public TCVideoRepeatView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private void init(Context context) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_repeate_view, this, true);

        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mTvTip2 = (TextView) findViewById(R.id.tv_tip2);

        mRangeSlider = (RangeRepeatSlider) findViewById(R.id.range_slider);
        mRangeSlider.setOnRepeatChangeListener(mRepaetChangeListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new TCVideoEditerAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setRepeatChangeListener(Edit.OnRepeatChangeListener listener) {
        mOnRepateChangeListener = listener;
    }

    public int getSegmentFrom() {
        return (int) mVideoStartPos;
    }

    public int getSegmentTo() {
        return (int) mVideoEndPos;
    }

    public int getRepeatFrom() {
        return (int) mRepeatFrom;
    }

    public int getRepeatTo() {
        return (int) mRepeatTo;
    }

    public void setDuration(long duration) {
        mVideoDuration = duration;

        mVideoStartPos = 0;
        mVideoEndPos = mVideoDuration;
    }

    public void addBitmap(int index, Bitmap bitmap) {
        mAdapter.add(index, bitmap);
    }

    public void setBitmapList(List<Bitmap> list) {
        mAdapter.setBitmapList(list);
    }

    public void clearAllBitmap() {
        mAdapter.clearAllBitmap();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private RangeRepeatSlider.OnRepeatChangeListener mRepaetChangeListener = new RangeRepeatSlider.OnRepeatChangeListener() {
        @Override
        public void onKeyDown(int type) {
            if (mOnRepateChangeListener != null) {
                mOnRepateChangeListener.onRepeatChangeKeyDown();
            }
        }

        @Override
        public void onKeyUp(int type, int leftPinIndex, int rightPinIndex) {
            TXCLog.e(TAG, "left:" + leftPinIndex + ",right:" + rightPinIndex);

            if (type == RangeRepeatSlider.TYPE_MID) {
                int leftTime = (int) (mVideoDuration * rightPinIndex / 100); //ms
                int rightTime = (int) (mVideoDuration * rightPinIndex / 100 + DEAULT_REPEATE_TIME);

                mRepeatFrom = leftTime;
                mRepeatTo = rightTime;

                if (mOnRepateChangeListener != null) {
                    mOnRepateChangeListener.onRepeatChangeKeyUp(leftTime, rightTime);
                }
                mTvTip2.setText(String.format("重复时间点 左侧 : %s, 右侧 : %s ", TCUtils.duration(leftTime), TCUtils.duration(rightTime)));
            } else {
//                int leftTime = (int) (mVideoDuration * leftPinIndex / 100); //ms
//                int rightTime = (int) (mVideoDuration * rightPinIndex / 100);
//
//                if (type == RangeSlider.TYPE_LEFT) {
//                    mVideoStartPos = leftTime;
//                } else {
//                    mVideoEndPos = rightTime;
//                }
//                if (mOnCutChangeListener != null) {
//                    mOnCutChangeListener.onCutChangeKeyUp((int) mVideoStartPos, (int) mVideoEndPos, TCVideoEditerActivity.TYPE_REPEAT);
//                }
//                mTvTip.setText(String.format("裁剪区间 左侧 : %s, 右侧 : %s ", TCUtils.duration(mVideoStartPos), TCUtils.duration(mVideoEndPos)));
            }
        }
    };

    public void setCutRange(long cutStartTime, long cutEndTime) {
        int leftPin = (int) (cutStartTime * 100 / mVideoDuration);
        int rightPin = (int) (cutEndTime * 100 / mVideoDuration);
        mTvTip.setText(String.format("裁剪区间 左侧 : %s, 右侧 : %s ", TCUtils.duration(cutStartTime), TCUtils.duration(cutEndTime)));
        mRangeSlider.setCutRange(leftPin, rightPin);
    }
}
