package com.fanwe.shortvideo.editor.motion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fanwe.live.R;
import com.fanwe.shortvideo.editor.TCVideoEditerActivity;
import com.fanwe.shortvideo.editor.TCVideoEditerWrapper;
import com.fanwe.shortvideo.editor.common.widget.videotimeline.ColorfulProgress;
import com.fanwe.shortvideo.editor.common.widget.videotimeline.VideoProgressController;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;

/**
 * Created by hans on 2017/11/7.
 * <p>
 * 动态滤镜特效的设置Fragment
 */
public class TCMotionFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "TCMotionFragment";

    private Button mBtnSpirit, mBtnSplit, mBtnLightWave, mBtnDark;
    private RelativeLayout mRlDelete;


    private boolean mIsOnTouch; // 是否已经有按下的
    private TXVideoEditer mTXVideoEditer;
    private long mVideoDuration;

    private ColorfulProgress mColorfulProgress;
    private VideoProgressController mActivityVideoProgressController;
    private boolean mStartMark;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        mTXVideoEditer = wrapper.getEditer();
        mVideoDuration = mTXVideoEditer.getTXVideoInfo().duration;
        mActivityVideoProgressController = ((TCVideoEditerActivity) getActivity()).getVideoProgressViewController();
        initViews(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mColorfulProgress != null) {
            mColorfulProgress.setVisibility(hidden ? View.GONE : View.VISIBLE);
        }
    }

    private void initViews(View view) {
        mBtnSpirit = (Button) view.findViewById(R.id.btn_soul);
        mBtnSplit = (Button) view.findViewById(R.id.btn_split);
        mBtnLightWave = (Button) view.findViewById(R.id.btn_light_wave);
        mBtnDark = (Button) view.findViewById(R.id.btn_black);
        mBtnSpirit.setOnTouchListener(this);
        mBtnSplit.setOnTouchListener(this);
        mBtnLightWave.setOnTouchListener(this);
        mBtnDark.setOnTouchListener(this);

        mRlDelete = (RelativeLayout) view.findViewById(R.id.motion_rl_delete);
        mRlDelete.setOnClickListener(this);


        mColorfulProgress = new ColorfulProgress(getContext());
        mColorfulProgress.setWidthHeight(mActivityVideoProgressController.getThumbnailPicListDisplayWidth(), getResources().getDimensionPixelOffset(R.dimen.video_progress_height));
        mActivityVideoProgressController.addColorfulProgress(mColorfulProgress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.motion_rl_delete:
                deleteLastMotion();
                break;
        }
    }



    private void deleteLastMotion() {
        ColorfulProgress.MarkInfo markInfo = mColorfulProgress.deleteLastMark();
        if (markInfo != null) {
            mActivityVideoProgressController.setCurrentTimeMs(markInfo.startTimeMs);
            TCVideoEditerActivity parentActivity = (TCVideoEditerActivity) getActivity();
            parentActivity.previewAtTime(markInfo.startTimeMs);
        }

        mTXVideoEditer.deleteLastEffect();
        if (mColorfulProgress.getMarkListSize() > 0) {
            showDeleteBtn();
        } else {
            hideDeleteBtn();
        }
    }

    public void showDeleteBtn() {
        if (mColorfulProgress.getMarkListSize() > 0) {
            mRlDelete.setVisibility(View.VISIBLE);
        }
    }

    public void hideDeleteBtn() {
        if (mColorfulProgress.getMarkListSize() == 0) {
            mRlDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mIsOnTouch && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }
        if (view.getId() == R.id.btn_soul) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                pressMotion(TXVideoEditConstants.TXEffectType_SOUL_OUT);
                mIsOnTouch = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                upMotion(TXVideoEditConstants.TXEffectType_SOUL_OUT);
                mIsOnTouch = false;
            }
            return false;
        }

        if (view.getId() == R.id.btn_split) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                pressMotion(TXVideoEditConstants.TXEffectType_SPLIT_SCREEN);
                mIsOnTouch = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                upMotion(TXVideoEditConstants.TXEffectType_SPLIT_SCREEN);
                mIsOnTouch = false;
            }
            return false;
        }

        if (view.getId() == R.id.btn_light_wave) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                pressMotion(TXVideoEditConstants.TXEffectType_ROCK_LIGHT);
                mIsOnTouch = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                upMotion(TXVideoEditConstants.TXEffectType_ROCK_LIGHT);
                mIsOnTouch = false;
            }
            return false;
        }

        if (view.getId() == R.id.btn_black) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                pressMotion(TXVideoEditConstants.TXEffectType_DARK_DRAEM);
                mIsOnTouch = true;
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                upMotion(TXVideoEditConstants.TXEffectType_DARK_DRAEM);
                mIsOnTouch = false;
            }
            return false;
        }

        return false;
    }

    private void pressMotion(int type) {
        // 未开始播放 则开始播放
        long currentTime = mActivityVideoProgressController.getCurrentTimeMs();

        if(currentTime == mVideoDuration){
            mStartMark = false;
            return;
        }
        mStartMark = true;
        ((TCVideoEditerActivity) getActivity()).startPlayAccordingState(currentTime, TCVideoEditerWrapper.getInstance().getCutterEndTime());
        mTXVideoEditer.startEffect(type, currentTime);

        switch (type) {
            case TXVideoEditConstants.TXEffectType_SOUL_OUT:
                mBtnSpirit.setBackgroundResource(R.drawable.shape_motion_spirit_press);
                // 进度条开始变颜色
                mColorfulProgress.startMark(getResources().getColor(R.color.spirit_out_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_SPLIT_SCREEN:
                mBtnSplit.setBackgroundResource(R.drawable.shape_motion_split_press);

                mColorfulProgress.startMark(getResources().getColor(R.color.screen_split_press));
                break;
            case TXVideoEditConstants.TXEffectType_ROCK_LIGHT:
                mBtnLightWave.setBackgroundResource(R.drawable.shape_motion_light_wave_press);

                mColorfulProgress.startMark(getResources().getColor(R.color.light_wave_press));
                break;
            case TXVideoEditConstants.TXEffectType_DARK_DRAEM:
                mBtnDark.setBackgroundResource(R.drawable.shape_motion_dark_press);

                mColorfulProgress.startMark(getResources().getColor(R.color.dark_illusion_press));
                break;
        }
    }

    private void upMotion(int type) {
        if( !mStartMark ){
            return;
        }
        switch (type) {
            case TXVideoEditConstants.TXEffectType_SOUL_OUT:
                mBtnSpirit.setBackgroundResource(R.drawable.shape_motion_spirit);
                break;
            case TXVideoEditConstants.TXEffectType_SPLIT_SCREEN:
                mBtnSplit.setBackgroundResource(R.drawable.shape_motion_split);
                break;
            case TXVideoEditConstants.TXEffectType_ROCK_LIGHT:
                mBtnLightWave.setBackgroundResource(R.drawable.shape_motion_light_wave);
                break;
            case TXVideoEditConstants.TXEffectType_DARK_DRAEM:
                mBtnDark.setBackgroundResource(R.drawable.shape_motion_dark);
                break;
        }

        // 暂停播放
        ((TCVideoEditerActivity) getActivity()).pausePlay();
        // 进度条结束标记
        mColorfulProgress.endMark();

        // 特效结束时间
        long currentTime = mActivityVideoProgressController.getCurrentTimeMs();
        mTXVideoEditer.stopEffect(type, currentTime);
        // 显示撤销的按钮
        showDeleteBtn();
    }
}
