package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndReset;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.live.R;

/**
 * 城堡
 */
public class GiftAnimatorCastle extends GiftAnimatorView
{
   private ImageView imgCoach;
   private ImageView imgHeart;
   private ImageView imgHouse;
   private ImageView imgSnowField;
   private ImageView imgSnowFlake;
   private ImageView imgStar;
    public GiftAnimatorCastle(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorCastle(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorCastle(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.castle;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        imgCoach = find(R.id.img_castle_coach);
        imgHeart = find(R.id.img_castle_heart);
        imgHouse = find(R.id.img_castle_house);
        imgSnowField = find(R.id.img_castle_snowfield);
        imgSnowFlake = find(R.id.img_castle_snowflake);
        imgStar = find(R.id.img_castle_star);

    }

    @Override
    protected void createAnimator(){

    int coachX1 = getXRightOut(imgCoach);
    int snowY3 = getYBottomOut(imgSnowField);
    int snowFlakeY1 = getYTopOut(imgSnowFlake);

        SDAnimSet animSet = SDAnimSet.from(imgSnowField)
                .moveToY(snowY3,imgSnowField.getY()+60).setDuration(1500)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .next(imgHouse).moveToY(snowY3,imgHouse.getY()+120).setDuration(1500)
                .next(imgStar).alpha( 0.3f, 1f, 0.3f).setDuration(1000).setRepeatCount(ValueAnimator.INFINITE)
                .withClone(imgHeart)
                .with(imgCoach).moveToX(coachX1,imgCoach.getX()).setDuration(2000)
                .next(imgSnowFlake).moveToY(snowFlakeY1,imgSnowFlake.getY()).setDuration(2000).setRepeatCount(1) .addListener(new OnEndReset(imgSnowFlake))
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
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
