package com.fanwe.libgame.poker.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.utils.SDViewUtil;

/**
 * 下注区域view
 */
public class PokerBetView extends BaseGameView
{
    public PokerBetView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerBetView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerBetView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_star;
    private TextView tv_bet_multiple; //投注倍数
    private TextView tv_total_bet; //总投注
    private TextView tv_user_bet; //用户投注

    private long mTotalBet;
    private long mUserBet;

    private void init()
    {
        setContentView(R.layout.view_poker_bet);

        iv_star = (ImageView) findViewById(R.id.iv_star);
        tv_bet_multiple = (TextView) findViewById(R.id.tv_bet_multiple);
        tv_total_bet = (TextView) findViewById(R.id.tv_total_bet);
        tv_user_bet = (TextView) findViewById(R.id.tv_user_bet);
    }

    public void setImageStar(int resId)
    {
        iv_star.setImageResource(resId);
    }

    /**
     * 设置投注倍数
     *
     * @param text
     */
    public void setTextBetMultiple(String text)
    {
        if (!TextUtils.isEmpty(text))
        {
            tv_bet_multiple.setText(text + "倍");
        } else
        {
            tv_bet_multiple.setText("");
        }
    }

    /**
     * 设置总投注
     *
     * @param totalBet
     */
    public void setTextTotalBet(long totalBet)
    {
        if (totalBet < mTotalBet)
        {
            return;
        }

        mTotalBet = totalBet;
        tv_total_bet.setText(String.valueOf(totalBet));
    }

    /**
     * 设置用户投注
     *
     * @param userBet
     */
    public void setTextUserBet(long userBet)
    {
        if (userBet < mUserBet)
        {
            return;
        }

        mUserBet = userBet;
        if (userBet <= 0)
        {
            SDViewUtil.setInvisible(tv_user_bet);
        } else
        {
            SDViewUtil.setVisible(tv_user_bet);
            tv_user_bet.setText(String.valueOf(userBet));
        }
    }

    public void resetView()
    {
        mTotalBet = 0;
        mUserBet = 0;
        setTextTotalBet(0);
        setTextUserBet(0);
    }
}
