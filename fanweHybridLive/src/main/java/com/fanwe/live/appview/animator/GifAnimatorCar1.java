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
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsgGift;

/**
 * 蓝色法拉利
 */
public class GifAnimatorCar1 extends GiftAnimatorView
{
    public GifAnimatorCar1(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GifAnimatorCar1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GifAnimatorCar1(Context context)
    {
        super(context);
    }

    private View fl_down_car;
    private ImageView iv_down_car_front_tyre;
    private ImageView iv_down_car_back_tyre;

    private View fl_up_car;
    private ImageView iv_up_car_front_tyre;
    private ImageView iv_up_car_back_tyre;

    private TextView tv_gift_desc_car_up;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_gift_animator_car1;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        fl_down_car = find(R.id.fl_car_down);
        iv_down_car_front_tyre = find(R.id.iv_car_down_front_tyre);
        iv_down_car_back_tyre = find(R.id.iv_car_down_back_tyre);

        fl_up_car = find(R.id.fl_car_up);
        iv_up_car_front_tyre = find(R.id.iv_car_up_front_tyre);
        iv_up_car_back_tyre = find(R.id.iv_car_up_back_tyre);

        tv_gift_desc_car_up = find(R.id.tv_gift_desc_car_up);
        tv_gift_desc = find(R.id.tv_gift_desc);

    }

    @Override
    public void setMsg(CustomMsgGift msg)
    {
        super.setMsg(msg);
        SDViewBinder.setTextView(tv_gift_desc_car_up, msg.getTop_title());
    }

    @Override
    protected void createAnimator()
    {
        //汽车下来
        int carDownX1 = getXRightOut(fl_down_car);
        int carDownX2 = getXCenterCenter(fl_down_car);
        int carDownX3 = getXLeftOut(fl_down_car);

        int carDownY1 = getYTopOut(fl_down_car);
        int carDownY2 = getYCenterCenter(fl_down_car);
        int carDownY3 = getYBottomOut(fl_down_car);

        //汽车上去
        int carUpX1 = getXLeftOut(fl_up_car);
        int carUpX2 = getXCenterCenter(fl_up_car);
        int carUpX3 = getXRightOut(fl_up_car);

        int carUpY1 = getYBottomOut(fl_up_car);
        int carUpY2 = getYCenterCenter(fl_up_car);
        int carUpY3 = getYTopOut(fl_up_car);

        SDAnimSet animSet = SDAnimSet.from(iv_down_car_front_tyre)
                //轮胎旋转
                .rotation(-360).setRepeatCount(-1).setDuration(1000).withClone(iv_down_car_back_tyre)
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
                .with(fl_down_car).moveToX(carDownX1, carDownX2).setDuration(2000).setDecelerate()
                .withClone().moveToY(carDownY1, carDownY2)
                .delay(1500)
                //屏幕中央移动到左下角
                .next().moveToX(carDownX2, carDownX3).setDuration(2000).setAccelerate()
                .withClone().moveToY(carDownY2, carDownY3)
                .addListener(new OnEndInvisible(fl_down_car))
                .addListener(new OnEndReset(fl_down_car))
                //汽车上去
                .next(iv_up_car_front_tyre)
                //轮胎旋转
                .rotation(360).setRepeatCount(-1).setDuration(1000).withClone(iv_up_car_back_tyre)
                //左下角移动到屏幕中央
                .with(fl_up_car).moveToX(carUpX1, carUpX2).setDuration(2000).setDecelerate()
                .withClone().moveToY(carUpY1, carUpY2)
                .delay(1500)
                //屏幕中央移动到右上角
                .next().moveToX(carUpX2, carUpX3).setDuration(2000).setAccelerate()
                .withClone().moveToY(carUpY2, carUpY3)
                .addListener(new OnEndInvisible(fl_up_car))
                .addListener(new OnEndReset(fl_up_car))
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
