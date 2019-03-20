package com.fanwe.shortvideo.editor.time;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.live.R;
import com.fanwe.shortvideo.common.widget.VideoWorkProgressFragment;
import com.fanwe.shortvideo.editor.TCVideoEditerActivity;
import com.fanwe.shortvideo.editor.TCVideoEditerWrapper;
import com.fanwe.shortvideo.editor.common.widget.videotimeline.SliderViewContainer;
import com.fanwe.shortvideo.editor.common.widget.videotimeline.VideoProgressController;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 2017/11/7.
 * <p>
 * 时间特效的Fragment
 */
public class TCTimeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TCTimeFragment";
    public static final long DEAULT_DURATION_MS = 1000; //默认重复时间段1s
    private int mCurrentEffect = NONE_EFFECT;
    private static final int NONE_EFFECT = -1;
    private static final int SPEED_EFFECT = 1;
    private static final int REPEAT_EFFECT = 2;
    private static final int REVERSE_EFFECT = 3;

    private TextView mTvTips;
    private TextView mTvCancel, mTvSpeed, mTvRepeat, mTvReverse;

    private TXVideoEditer mTXVideoEditer;
    private VideoProgressController mVideoProgressController;
    private SliderViewContainer mRepeatSlider;
    private SliderViewContainer mSpeedSlider;

    private VideoWorkProgressFragment mWorkLoadingProgress;
    private boolean mIsReverseOnce;

    /**
     * ==========================================进度条==========================================
     */
    private void initWorkLoadingProgress() {
        if (mWorkLoadingProgress == null) {
            mWorkLoadingProgress = VideoWorkProgressFragment.newInstance("视频处理中...");
            mWorkLoadingProgress.setCanCancel(false);
        }
        mWorkLoadingProgress.setProgress(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        mTXVideoEditer = wrapper.getEditer();
        mVideoProgressController = ((TCVideoEditerActivity) getActivity()).getVideoProgressViewController();

        initViews(view);
        showNoneLayout();
        initWorkLoadingProgress();
    }

    private void initViews(View view) {
        mTvTips = (TextView) view.findViewById(R.id.time_tv_slogan);
        mTvCancel = (TextView) view.findViewById(R.id.time_tv_cancel);
        mTvCancel.setOnClickListener(this);
        mTvSpeed = (TextView) view.findViewById(R.id.time_tv_speed);
        mTvSpeed.setOnClickListener(this);
        mTvSpeed.setSelected(true);
        mTvRepeat = (TextView) view.findViewById(R.id.time_tv_repeat);
        mTvRepeat.setOnClickListener(this);
        mTvReverse = (TextView) view.findViewById(R.id.time_tv_reverse);
        mTvReverse.setOnClickListener(this);
    }

    private void setRepeatSliderView() {
        if (mRepeatSlider == null) {
            // 第一次展示重复界面的时候，将重复的按钮移动到当前的进度
            long currentPts = mVideoProgressController.getCurrentTimeMs();

            List<TXVideoEditConstants.TXRepeat> repeatList = new ArrayList<>();
            TXVideoEditConstants.TXRepeat repeat = new TXVideoEditConstants.TXRepeat();
            repeat.startTime = currentPts;
            repeat.endTime = currentPts + DEAULT_DURATION_MS;
            repeat.repeatTimes = 3;
            repeatList.add(repeat);
            mTXVideoEditer.setRepeatPlay(repeatList);
            ((TCVideoEditerActivity) getActivity()).previewAtTime(currentPts);

            mRepeatSlider = new SliderViewContainer(getContext());
            mRepeatSlider.setStartTimeMs(currentPts);
            mRepeatSlider.setOnStartTimeChangedListener(new SliderViewContainer.OnStartTimeChangedListener() {
                @Override
                public void onStartTimeMsChanged(long timeMs) {
                    if (mCurrentEffect != REPEAT_EFFECT) {
                        cancelSetEffect();
                    }
                    mCurrentEffect = REPEAT_EFFECT;

                    List<TXVideoEditConstants.TXRepeat> repeatList = new ArrayList<>();
                    TXVideoEditConstants.TXRepeat repeat = new TXVideoEditConstants.TXRepeat();
                    repeat.startTime = timeMs;
                    repeat.endTime = timeMs + DEAULT_DURATION_MS;
                    repeat.repeatTimes = 3;
                    repeatList.add(repeat);
                    mTXVideoEditer.setRepeatPlay(repeatList);
                    ((TCVideoEditerActivity) getActivity()).previewAtTime(timeMs);
                    // 进度条移动到当前位置
                    mVideoProgressController.setCurrentTimeMs(timeMs);
                }
            });
            mVideoProgressController.addSliderView(mRepeatSlider);
            mRepeatSlider.setVisibility(View.GONE);
        } else {
            long currentPts = mVideoProgressController.getCurrentTimeMs();

            List<TXVideoEditConstants.TXRepeat> repeatList = new ArrayList<>();
            TXVideoEditConstants.TXRepeat repeat = new TXVideoEditConstants.TXRepeat();
            repeat.startTime = currentPts;
            repeat.endTime = currentPts + DEAULT_DURATION_MS;
            repeat.repeatTimes = 3;
            repeatList.add(repeat);
            mTXVideoEditer.setRepeatPlay(repeatList);
            ((TCVideoEditerActivity) getActivity()).previewAtTime(currentPts);
            mRepeatSlider.setStartTimeMs(currentPts);
        }
    }


    private void initSpeedLayout() {
        if (mSpeedSlider == null) {
            long currentPts = mVideoProgressController.getCurrentTimeMs();
            setSpeed(currentPts);
            mCurrentEffect = SPEED_EFFECT;
            mVideoProgressController.setCurrentTimeMs(currentPts);

            mSpeedSlider = new SliderViewContainer(getContext());
            mSpeedSlider.setStartTimeMs(currentPts);
            mSpeedSlider.setOnStartTimeChangedListener(new SliderViewContainer.OnStartTimeChangedListener() {
                @Override
                public void onStartTimeMsChanged(long timeMs) {
                    if (mCurrentEffect != SPEED_EFFECT)
                        cancelSetEffect();
                    mCurrentEffect = SPEED_EFFECT;
                    setSpeed(timeMs);
                    ((TCVideoEditerActivity) getActivity()).previewAtTime(timeMs);
                    // 进度条移动到当前位置
                    mVideoProgressController.setCurrentTimeMs(timeMs);
                }
            });
            mVideoProgressController.addSliderView(mSpeedSlider);
        } else {
            long currentPts = mVideoProgressController.getCurrentTimeMs();
            setSpeed(currentPts);
            mCurrentEffect = SPEED_EFFECT;
            ((TCVideoEditerActivity) getActivity()).previewAtTime(currentPts);
            mSpeedSlider.setStartTimeMs(currentPts);
            mVideoProgressController.setCurrentTimeMs(currentPts);
        }
    }

    /**
     * SDK拥有支持多段变速的功能。 在DEMO仅展示一段慢速播放
     *
     * @param startTime
     */
    private void setSpeed(long startTime) {
        List<TXVideoEditConstants.TXSpeed> list = new ArrayList<>(1);
        TXVideoEditConstants.TXSpeed speed = new TXVideoEditConstants.TXSpeed();
        speed.startTime = startTime;                                // 开始时间
        speed.endTime = mTXVideoEditer.getTXVideoInfo().duration;   // 结束时间
        speed.speedLevel = TXVideoEditConstants.SPEED_LEVEL_SLOW;   // 慢速
        // 添加到分段变速中
        list.add(speed);

        // 设入SDK
        mTXVideoEditer.setSpeedList(list);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRepeatSlider != null && mTvRepeat != null && mTvRepeat.isSelected()) {
            mRepeatSlider.setVisibility(hidden ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_tv_cancel:
                cancelSetEffect();
                showNoneLayout();
                break;
            case R.id.time_tv_speed:
                cancelSetEffect();
                showSpeedLayout();
                break;
            case R.id.time_tv_reverse:
                if (!mIsReverseOnce)//没有倒放过，弹出一个框来loading
                    mWorkLoadingProgress.show(getFragmentManager(), "reverse_loading_fragment");
                if (mCurrentEffect == REVERSE_EFFECT) return;// 当前处于倒放状态 无视
                cancelSetEffect();
                showReverseLayout();
                ((TCVideoEditerActivity) getActivity()).stopPlay();
                mTXVideoEditer.setTXVideoReverseListener(mTxVideoReverseListener);
                mTXVideoEditer.setReverse(true);
                mCurrentEffect = REVERSE_EFFECT;
                TCVideoEditerWrapper.getInstance().setReverse(true);
                break;
            case R.id.time_tv_repeat:
                cancelSetEffect();
                showRepeatLayout();
                break;
        }
    }


    /**
     * 取消设置了的时间特效
     */
    private void cancelSetEffect() {
        switch (mCurrentEffect) {
            case SPEED_EFFECT:
                cancelSpeedEffect();
                break;
            case REPEAT_EFFECT:
                cancelRepeatEffect();
                break;
            case REVERSE_EFFECT:
                cancelReverseEffect();
                break;
        }
    }

    private void cancelSpeedEffect() {
        mCurrentEffect = NONE_EFFECT;
        mTXVideoEditer.setSpeedList(null);
    }

    private void cancelRepeatEffect() {
        mCurrentEffect = NONE_EFFECT;
        mTXVideoEditer.setRepeatPlay(null);
        ((TCVideoEditerActivity) getActivity()).restartPlay();
    }

    private void cancelReverseEffect() {
        mCurrentEffect = NONE_EFFECT;
        ((TCVideoEditerActivity) getActivity()).stopPlay();
        mTXVideoEditer.setReverse(false);
        TCVideoEditerWrapper.getInstance().setReverse(false);
        ((TCVideoEditerActivity) getActivity()).switchReverse();
        ((TCVideoEditerActivity) getActivity()).restartPlay();
    }

    private void showNoneLayout() {
        mTvTips.setText("无特效");
        mTvCancel.setSelected(true);
        mTvSpeed.setSelected(false);
        mTvRepeat.setSelected(false);
        mTvReverse.setSelected(false);
        if (mRepeatSlider != null)
            mRepeatSlider.setVisibility(View.GONE);
        if (mSpeedSlider != null) {
            mSpeedSlider.setVisibility(View.GONE);
        }
    }

    private void showSpeedLayout() {
        initSpeedLayout();
        mTvTips.setText(R.string.video_editer_set_speed);
        mTvSpeed.setSelected(true);
        mTvRepeat.setSelected(false);
        mTvCancel.setSelected(false);
        mTvReverse.setSelected(false);
        if (mRepeatSlider != null)
            mRepeatSlider.setVisibility(View.GONE);
        if (mSpeedSlider.getVisibility() == View.GONE) {
            mSpeedSlider.setVisibility(View.VISIBLE);
        }
    }

    private void showRepeatLayout() {
        setRepeatSliderView();
        mTvTips.setText(R.string.video_repeate_segment);
        mTvCancel.setSelected(false);
        mTvSpeed.setSelected(false);
        mTvRepeat.setSelected(true);
        mTvReverse.setSelected(false);
        if(mSpeedSlider != null && mSpeedSlider.getVisibility() == View.VISIBLE){
            mSpeedSlider.setVisibility(View.GONE);
        }
        if (mRepeatSlider.getVisibility() == View.GONE) {
            mRepeatSlider.setVisibility(View.VISIBLE);
        }
        mCurrentEffect = REPEAT_EFFECT;
    }

    private void showReverseLayout() {
        mTvTips.setText(R.string.video_reverse);
        mTvSpeed.setSelected(false);
        mTvCancel.setSelected(false);
        mTvRepeat.setSelected(false);
        mTvReverse.setSelected(true);
        if (mRepeatSlider != null)
            mRepeatSlider.setVisibility(View.GONE);
        if (mSpeedSlider != null) {
            mSpeedSlider.setVisibility(View.GONE);
        }
    }


    private TXVideoEditer.TXVideoReverseListener mTxVideoReverseListener = new TXVideoEditer.TXVideoReverseListener() {
        @Override
        public void onReverseComplete(TXVideoEditConstants.TXGenerateResult result) {
            // 倒放成功
            mWorkLoadingProgress.setProgress(100);
            mWorkLoadingProgress.dismiss();
            if (result.retCode == TXVideoEditConstants.GENERATE_RESULT_OK) {
                ((TCVideoEditerActivity) getActivity()).switchReverse();
                ((TCVideoEditerActivity) getActivity()).restartPlay();
                mIsReverseOnce = true;
            }
            mTXVideoEditer.setTXVideoReverseListener(null);
        }
    };


}
