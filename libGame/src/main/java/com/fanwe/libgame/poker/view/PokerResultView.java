package com.fanwe.libgame.poker.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.animator.SDAnimSet;

/**
 * 牌面类型view
 */
public class PokerResultView extends BaseGameView
{
    public PokerResultView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerResultView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerResultView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_bg;
    private ImageView iv_result;

    private void init()
    {
        setContentView(R.layout.view_poker_result);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_result = (ImageView) findViewById(R.id.iv_result);

        setVisibility(View.INVISIBLE);

        SDAnimSet animSet = SDAnimSet.from(this).scaleX(0, 1.0f).setDuration(200)
                .withClone().scaleY(0, 1.0f);

        getVisibilityHandler().setVisibleAnimator(animSet.getSet());
    }

    public void show()
    {
        getVisibilityHandler().setVisible(true);
    }

    public void hide()
    {
        getVisibilityHandler().setInvisible(false);
    }

    public void setImageResult(int resId)
    {
        iv_result.setImageResource(resId);
    }
}
