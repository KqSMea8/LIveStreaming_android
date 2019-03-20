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
 * 棕色轰炸机
 */
public class GiftAnimatorPlane1 extends GiftAnimatorView
{

    private View fl_animator;

    private ImageView iv_screw1;
    private ImageView iv_screw2;
    private ImageView iv_screw3;
    private ImageView iv_screw4;

    public GiftAnimatorPlane1(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorPlane1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorPlane1(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_gift_animator_plane1;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_animator = find(R.id.fl_animator);
        iv_screw1 = find(R.id.iv_screw1);
        iv_screw2 = find(R.id.iv_screw2);
        iv_screw3 = find(R.id.iv_screw3);
        iv_screw4 = find(R.id.iv_screw4);
        tv_gift_desc = find(R.id.tv_gift_desc);
    }

    @Override
    protected void createAnimator()
    {
        int planeX1 = getXRightOut(fl_animator);
        int planeX2 = getXCenterCenter(fl_animator);
        int planeX3 = getXLeftOut(fl_animator);

        int planeY1 = getYTopOut(fl_animator);
        int planeY2 = getYCenterCenter(fl_animator);
        int planeY3 = getYBottomOut(fl_animator);

        SDAnimSet animSet = SDAnimSet.from(iv_screw1)
                //螺旋桨旋转
                .rotation(360).setRepeatCount(-1).setDuration(500)
                .withClone(iv_screw2)
                .withClone(iv_screw3)
                .withClone(iv_screw4)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                //右上角移动到屏幕中央
                .with(fl_animator).moveToX(planeX1, planeX2).setDecelerate().setDuration(2000)
                .withClone().moveToY(planeY1, planeY2)
                .delay(1500)
                //屏幕中央移动到左下角
                .next().moveToX(planeX2, planeX3).setAccelerate().setDuration(2000)
                .withClone().moveToY(planeY2, planeY3)
                .addListener(new OnEndInvisible(fl_animator))
                .addListener(new OnEndReset(fl_animator))
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
