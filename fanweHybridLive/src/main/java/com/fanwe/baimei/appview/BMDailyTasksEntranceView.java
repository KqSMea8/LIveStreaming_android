package com.fanwe.baimei.appview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.baimei.dialog.BMDailySignDialog;
import com.fanwe.baimei.dialog.BMDailyTasksDialog;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * 包名: com.fanwe.baimei.appview
 * 描述: 每日任务列表入口
 * 作者: Su
 * 创建时间: 2017/5/31 17:04
 **/
public class BMDailyTasksEntranceView extends BaseAppView
{
    private static final long DURATION_ALARM = 1000;
    private BMDailySignDialog mTasksDialog;
    private AnimatorSet mAlarmAnimatorSet;
    private Handler mDelayHandler;
    private Runnable mAlarmRunnable;


    public BMDailyTasksEntranceView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initBMDailyTaskEntranceView(context);
    }

    public BMDailyTasksEntranceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initBMDailyTaskEntranceView(context);
    }

    public BMDailyTasksEntranceView(Context context)
    {
        super(context);
        initBMDailyTaskEntranceView(context);
    }

    private void initBMDailyTaskEntranceView(Context context)
    {
        setContentView(R.layout.bm_view_daily_tasks_entrance);

        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getTasksDialog().show();
                stopAlarm();
            }
        });
    }

    private Handler getDelayHandler()
    {
        if (mDelayHandler == null)
        {
            mDelayHandler = new Handler(Looper.getMainLooper());
        }
        return mDelayHandler;
    }

    private Runnable getAlarmRunnable()
    {
        if (mAlarmRunnable == null)
        {
            mAlarmRunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    stopAlarm();
                    getAlarmAnimatorSet().start();
                }
            };
        }
        return mAlarmRunnable;
    }

    private BMDailySignDialog getTasksDialog()
    {
        if (mTasksDialog == null)
        {
            mTasksDialog = new BMDailySignDialog(getActivity(),null);
        }
        return mTasksDialog;
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();

        stopAlarm();
    }

    /**
     * 开始提醒动画
     */
    public void startAlarm()
    {
        startAlarm(0);
    }

    /**
     * 指定毫秒后开始提醒动画
     * @param delayMills
     */
    public void startAlarm(long delayMills)
    {
        stopAlarm();
        getDelayHandler().postDelayed(getAlarmRunnable(), delayMills);
    }

    /**
     * 结束提醒动画
     */
    public void stopAlarm()
    {
        if (getAlarmAnimatorSet().isRunning())
        {
            getAlarmAnimatorSet().cancel();

            getDelayHandler().removeCallbacks(getAlarmRunnable());
        }
    }

    private AnimatorSet getAlarmAnimatorSet()
    {
        if (mAlarmAnimatorSet == null)
        {
            mAlarmAnimatorSet = new AnimatorSet();
            ObjectAnimator oa1 = ObjectAnimator.ofFloat(BMDailyTasksEntranceView.this, "scaleX", 1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1);
            ObjectAnimator oa2 = ObjectAnimator.ofFloat(BMDailyTasksEntranceView.this, "scaleY", 1, 0.9f, 0.9f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1.1f, 1);
            ObjectAnimator oa3 = ObjectAnimator.ofFloat(BMDailyTasksEntranceView.this, "rotation", 0, -5, -5, 5, -5, 5, -5, 5, -5, 0);

            oa1.setRepeatCount(Integer.MAX_VALUE);
            oa2.setRepeatCount(Integer.MAX_VALUE);
            oa3.setRepeatCount(Integer.MAX_VALUE);
            oa1.setRepeatMode(ValueAnimator.RESTART);
            oa2.setRepeatMode(ValueAnimator.RESTART);
            oa3.setRepeatMode(ValueAnimator.RESTART);

            mAlarmAnimatorSet.playTogether(oa1, oa2, oa3);
            mAlarmAnimatorSet.setDuration(DURATION_ALARM);
            mAlarmAnimatorSet.addListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationCancel(Animator animation)
                {
                    super.onAnimationCancel(animation);
                    BMDailyTasksEntranceView.this.setScaleX(1.0f);
                    BMDailyTasksEntranceView.this.setScaleY(1.0f);
                    BMDailyTasksEntranceView.this.setRotation(0);
                }
            });
        }
        return mAlarmAnimatorSet;
    }


}
