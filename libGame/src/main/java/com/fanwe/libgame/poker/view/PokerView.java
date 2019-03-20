package com.fanwe.libgame.poker.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.games.R;
import com.fanwe.libgame.poker.PokerUtil;
import com.fanwe.libgame.poker.model.PokerData;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 单张扑克牌view
 */
public class PokerView extends BaseGameView implements IPokerView
{
    public PokerView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View rl_poker_front;
    private ImageView iv_poker_num;
    private ImageView iv_poker_type;
    private ImageView iv_poker_type_center;

    private ImageView iv_poker_back;

    private boolean mIsPokerFrontShowing;

    private void init()
    {
        setContentView(R.layout.view_poker);
        rl_poker_front = findViewById(R.id.rl_poker_front);
        iv_poker_num = (ImageView) findViewById(R.id.iv_poker_num);
        iv_poker_type = (ImageView) findViewById(R.id.iv_poker_type);
        iv_poker_type_center = (ImageView) findViewById(R.id.iv_poker_type_center);
        iv_poker_back = (ImageView) findViewById(R.id.iv_poker_back);

    }

    @Override
    public void showPokerFront()
    {
        SDViewUtil.setVisible(rl_poker_front);
        SDViewUtil.setInvisible(iv_poker_back);
        mIsPokerFrontShowing = true;
    }

    @Override
    public void showPokerBack()
    {
        SDViewUtil.setVisible(iv_poker_back);
        SDViewUtil.setInvisible(rl_poker_front);
        mIsPokerFrontShowing = false;
    }

    @Override
    public void setPokerData(PokerData data)
    {
        int typeRes = PokerUtil.getPokerTypeImageResId(data.getPokerType());
        iv_poker_type.setImageResource(typeRes);

        int centerRes = PokerUtil.getPokerTypeCenterImageResId(data.getPokerType(), data.getPokerNumber());
        iv_poker_type_center.setImageResource(centerRes);

        int numberRes = PokerUtil.getPokerNumberImageResId(data.getPokerType(), data.getPokerNumber());
        iv_poker_num.setImageResource(numberRes);
    }

    @Override
    public boolean isPokerFrontShowing()
    {
        return mIsPokerFrontShowing;
    }

    /**
     * 设置牌背面的图片
     *
     * @param resId
     */
    protected void setPokerBackImageResId(int resId)
    {
        iv_poker_back.setImageResource(resId);
    }
}
