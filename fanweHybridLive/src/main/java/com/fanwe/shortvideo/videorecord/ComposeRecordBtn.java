package com.fanwe.shortvideo.videorecord;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.live.R;


/**
 * Created by vinsonswang on 2017/9/8.
 */

public class ComposeRecordBtn extends RelativeLayout {
    private Context mContext;
    private ImageView mIvRecordStart;
    private ImageView mIvRecordPause;

    public ComposeRecordBtn(Context context) {
        super(context);
        init(context);
    }

    public ComposeRecordBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ComposeRecordBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.compose_record_btn, this);
        mIvRecordStart = (ImageView) findViewById(R.id.iv_record);
        mIvRecordPause = (ImageView) findViewById(R.id.iv_record_pause);
    }

    public void startRecord(){

        ObjectAnimator recordStartZoomOutXAn = ObjectAnimator.ofFloat(mIvRecordStart, "scaleX", 0.8f);
        ObjectAnimator recordStartZoomOutYAn = ObjectAnimator.ofFloat(mIvRecordStart, "scaleY", 0.8f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(80);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(recordStartZoomOutXAn).with(recordStartZoomOutYAn);
        animatorSet.start();

        mIvRecordPause.setVisibility(View.VISIBLE);
    }

    public void pauseRecord(){

        ObjectAnimator recordStartZoomInXAn = ObjectAnimator.ofFloat(mIvRecordStart, "scaleX", 1f);
        ObjectAnimator recordStartZoomInYAn = ObjectAnimator.ofFloat(mIvRecordStart, "scaleY", 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(80);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(recordStartZoomInXAn).with(recordStartZoomInYAn);
        animatorSet.start();

        mIvRecordPause.setVisibility(View.GONE);
    }
}
