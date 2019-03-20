package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndInvisible;
import com.fanwe.library.animator.listener.OnEndReset;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.live.R;

/**
 * 红色兰博基尼
 */
public class GiftAnimatorCar2 extends GiftAnimatorView
{
    public GiftAnimatorCar2(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorCar2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorCar2(Context context)
    {
        super(context);
    }

    private View fl_car;
    private ImageView iv_car_front_tyre;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_gift_animator_car2;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_car = find(R.id.fl_car);
        iv_car_front_tyre = find(R.id.iv_car_front_tyre);
        tv_gift_desc = find(R.id.tv_gift_desc);
    }

    @Override
    protected void createAnimator()
    {
        int x1 = getXLeftOut(fl_car);
        int x2 = getXCenterCenter(fl_car);
        int x3 = getXRightOut(fl_car);

        int y1 = getYTopOut(fl_car);
        int y2 = getYCenterCenter(fl_car);
        int y3 = getYBottomOut(fl_car);

        SDAnimSet animSet = SDAnimSet.from(iv_car_front_tyre)
                //轮胎旋转
                .rotation(360).setRepeatCount(-1).setDuration(1000)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                //左上角移动到屏幕中央
                .with(fl_car).moveToX(x1, x2).setDuration(2000).setDecelerate()
                .withClone().moveToY(y1, y2)
                .delay(1500)
                //屏幕中央移动到右下角
                .next().moveToX(x2, x3).setDuration(2000).setAccelerate()
                .withClone().moveToY(y2, y3)
                .addListener(new OnEndInvisible(fl_car))
                .addListener(new OnEndReset(fl_car))
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
