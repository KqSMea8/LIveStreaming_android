package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndInvisible;
import com.fanwe.library.animator.listener.OnEndReset;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 火箭发射
 */
public class GiftAnimatorRocket1 extends GiftAnimatorView
{
    private View fl_rocket_root;

    private TextView tv_number;
    private ImageView iv_rocket;
    private ImageView iv_rocket_smoke;

    private int number = 3;

    public GiftAnimatorRocket1(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorRocket1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorRocket1(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_gift_animator_rocket1;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_rocket_root = find(R.id.fl_rocket_root);

        tv_number = find(R.id.tv_number);
        iv_rocket = find(R.id.iv_rocket);
        iv_rocket_smoke = find(R.id.iv_rocket_smoke);
        tv_gift_desc = find(R.id.tv_gift_desc);

    }

    private void updateNumber()
    {
        tv_number.setText(String.valueOf(number));
    }

    private void resetNumber()
    {
        number = 3;
        updateNumber();
    }

    @Override
    protected void createAnimator()
    {
        int rocketY1 = SDViewUtil.getScreenHeight() - SDViewUtil.getHeight(fl_rocket_root);
        int rocketY2 = getYTopOut(fl_rocket_root);

        SDAnimSet animSet = SDAnimSet.from(fl_rocket_root)
                //火箭淡入
                .alpha(0, 1f).setDuration(1000)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .delay(500)
                //数字倒数
                .with(tv_number).scaleX(1f, 0f).setRepeatCount(2).setDuration(1000)
                .withClone().scaleY(1f, 0f)
                .addListener(new OnEndInvisible())
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        updateNumber();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        resetNumber();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation)
                    {
                        super.onAnimationRepeat(animation);
                        number--;
                        updateNumber();
                    }
                })
                //火箭起飞
                .next(fl_rocket_root).moveToY(rocketY1, rocketY2).setDuration(3000).setAccelerate()
                .addListener(new OnEndInvisible(fl_rocket_root))
                .addListener(new OnEndReset(fl_rocket_root))
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        SDViewUtil.startAnimationDrawable(iv_rocket.getDrawable());
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        SDViewUtil.stopAnimationDrawable(iv_rocket.getDrawable());
                    }
                })
                //烟雾淡入
                .with(iv_rocket_smoke).alpha(0, 1f).setDuration(3000).setStartDelay(500)
                //烟雾淡出
                .next().alpha(1f, 0).setDuration(500)
                .addListener(new OnEndInvisible(iv_rocket_smoke))
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        notifyAnimationEnd(animation);
                    }
                });
        setAnimatorSet(animSet.getSet());
    }

    @Override
    protected void resetView()
    {

    }
}
