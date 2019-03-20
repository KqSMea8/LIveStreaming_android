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
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 白色客机
 */
public class GiftAnimatorPlane2 extends GiftAnimatorView
{

    private View fl_cloud;
    private View ll_plane;
    private ImageView iv_plane;

    public GiftAnimatorPlane2(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorPlane2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorPlane2(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_gift_animator_plane2;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_cloud = find(R.id.fl_cloud);
        ll_plane = find(R.id.ll_plane);
        iv_plane = find(R.id.iv_plane);
        tv_gift_desc = find(R.id.tv_gift_desc);

    }

    @Override
    protected void createAnimator()
    {
        //云朵
        int cloudY1 = getYBottomOut(fl_cloud);
        int cloudY2 = 0;

        //飞机
        int planeX1 = getXRightOut(ll_plane);
        int planeX2 = getXCenterCenter(ll_plane);
        int planeX3 = getXLeftOut(ll_plane);

        int planeY1 = 0;
        int planeY2 = getYCenterCenter(ll_plane);
        int planeY3 = getYBottomOut(ll_plane);

        SDAnimSet animSet = SDAnimSet.from(fl_cloud)
                //云朵从底部升起
                .moveToY(cloudY1, cloudY2).setDuration(2000)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                //飞机从右上角移动到屏幕中央
                .next(ll_plane).moveToX(planeX1, planeX2).setDuration(2000).setDecelerate()
                .withClone().moveToY(planeY1, planeY2)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        SDViewUtil.startAnimationDrawable(iv_plane.getDrawable());
                    }
                })
                .delay(1500)
                //飞机从屏幕中央移动到左下角
                .next().moveToX(planeX2, planeX3).setDuration(2000).setAccelerate()
                .withClone().moveToY(planeY2, planeY3)
                .addListener(new OnEndInvisible(ll_plane))
                .addListener(new OnEndReset(ll_plane))
                .addListener(new OnEndInvisible(fl_cloud))
                .addListener(new OnEndReset(fl_cloud))
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        notifyAnimationEnd(animation);
                        SDViewUtil.stopAnimationDrawable(iv_plane.getDrawable());
                    }
                });

        setAnimatorSet(animSet.getSet());
    }

    @Override
    protected void resetView()
    {

    }
}
