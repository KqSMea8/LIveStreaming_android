package com.fanwe.live.appview.room;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.animator.SDAnim;
import com.fanwe.live.R;

/**
 * 主播开播倒计时
 */
public class RoomCountDownView extends RoomView
{
    private TextView tv_number;
    private int number;
    private AnimatorSet animatorSet;

    public RoomCountDownView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomCountDownView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomCountDownView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_room_count_down);
        tv_number = find(R.id.tv_number);
    }

    public void startCountDown(int number)
    {
        this.number = number;
        if (number < 0)
        {
            return;
        }
        updateNumber();

        animatorSet = new AnimatorSet();
        SDAnim animX = SDAnim.from(tv_number).scaleX(5, 0).setDuration(1000).setRepeatCount(number - 1).addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationRepeat(Animator animation)
            {
                RoomCountDownView.this.number--;
                updateNumber();
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                removeSelf();
                super.onAnimationEnd(animation);
            }
        });
        SDAnim animY = SDAnim.from(tv_number).scaleY(5, 0).setDuration(1000).setRepeatCount(number - 1);

        animatorSet.playTogether(animX.get(), animY.get());
        animatorSet.start();
    }

    private void updateNumber()
    {
        tv_number.setText(String.valueOf(number));
    }

    @Override
    protected void onDetachedFromWindow()
    {
        if (animatorSet != null)
        {
            animatorSet.cancel();
        }
        super.onDetachedFromWindow();
    }
}
