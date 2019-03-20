package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.live.R;

/**
 * 蛋糕
 */
public class GiftAnimatorCake extends GiftAnimatorView
{
   private ImageView imageviewcake;
   private ImageView imageviewpoints;
   private ImageView imageviewleftone;
   private ImageView imageviewlefttwo;
   private ImageView imageviewrightone;
   private ImageView imageviewrighttwo;
   private View fl_cake;
    public GiftAnimatorCake(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorCake(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorCake(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.cake;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_cake=find(R.id.fl_cake);
        imageviewcake = find(R.id.img_cake);
        imageviewpoints =find (R.id.img_cake_points);
        imageviewleftone = find (R.id.img_cake_left_one);
        imageviewlefttwo = find (R.id.img_cake_left_two);
        imageviewrightone = find (R.id.img_cake_right_one);
        imageviewrighttwo = find (R.id.img_cake_right_two);

    }

    @Override
    protected void createAnimator(){
    int cakeX2 = getXCenterCenter(fl_cake);

    int cakeY2 = getYCenterCenter(fl_cake);
    int cakeY3 = getYBottomOut(fl_cake);

        SDAnimSet animSet = SDAnimSet.from(imageviewcake)
                .translationY(cakeY3,cakeY2).setDuration(3000)
                .addListener(new SDAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        notifyAnimationStart(animation);
                    }
                })
                .next(imageviewcake).scaleY(1f,0.7f,1f).setDuration(1000)
                .withClone().scaleX(1f,0.7f,1f)
                .next(imageviewleftone).translationX(cakeX2,cakeX2-270f).setDuration(1000)
                .next(imageviewlefttwo).translationX(cakeX2,cakeX2-230f).setDuration(1000)
                .next(imageviewrightone).translationX(cakeX2,cakeX2+280f).setDuration(1000)
                .next(imageviewrighttwo).translationX(cakeX2,cakeX2+230f).setDuration(1000)
                .next(imageviewpoints).translationY(cakeY3,cakeY2).setDuration(2000)
                .next().alpha(1f, 0).setDuration(1500)
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
