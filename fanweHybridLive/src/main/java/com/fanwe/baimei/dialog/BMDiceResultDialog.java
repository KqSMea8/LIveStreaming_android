package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 包名: com.fanwe.baimei.dialog
 * 描述: 猜大小游戏结果弹窗
 * 作者: Su
 * 创建时间: 2017/5/26 15:38
 **/
public abstract class BMDiceResultDialog extends SDDialogBase
{
    public static final long TIME_DISMISS = 3000;

    private TextView mScoreSelfTextView;
    private TextView mScoreWinnerTextView;
    private TextView mWinnerTextView;
    private LinearLayout mLlLayoutWinner;

    protected abstract boolean isWinner();

    public BMDiceResultDialog(Activity activity)
    {
        super(activity);

        initDiceResultDialog();
    }

    public BMDiceResultDialog(Activity activity, int theme)
    {
        super(activity, theme);

        initDiceResultDialog();
    }

    private void initDiceResultDialog()
    {
        setContentView(isWinner() ? R.layout.bm_dialog_dice_result_win : R.layout.bm_dialog_dice_result_lost);

        setAnimations(R.style.anim_top_top);
        paddingLeft(1);
        paddingRight(1);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setLlLayoutWinnerShow(false);
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);

        resetInfo();
    }

    private TextView getScoreSelfTextView()
    {
        if (mScoreSelfTextView == null)
        {
            mScoreSelfTextView = (TextView) findViewById(R.id.tv_score_self);
            mScoreSelfTextView.setTextColor(isWinner() ? Color.parseColor("#FEE079")
                    : Color.parseColor("#905A29"));
        }
        return mScoreSelfTextView;
    }

    private TextView getWinnerTextView()
    {
        if (mWinnerTextView == null)
        {
            mWinnerTextView = (TextView) findViewById(R.id.tv_winner);
        }
        return mWinnerTextView;
    }

    private TextView getScoreWinnerTextView()
    {
        if (mScoreWinnerTextView == null)
        {
            mScoreWinnerTextView = (TextView) findViewById(R.id.tv_score_winner);
        }
        return mScoreWinnerTextView;
    }

    private LinearLayout getWinnerLayout()
    {
        if (mLlLayoutWinner == null)
        {
            mLlLayoutWinner = (LinearLayout) findViewById(R.id.ll_layout_winner);
        }
        return mLlLayoutWinner;
    }

    /**
     * 设置大赢家显示
     *
     * @param show
     * @return
     */
    public BMDiceResultDialog setLlLayoutWinnerShow(boolean show)
    {
        if (show)
        {
            SDViewUtil.setVisible(getWinnerLayout());
        } else
        {
            SDViewUtil.setGone(getWinnerLayout());
        }

        return this;
    }

    /**
     * @param scoreSelf 设置自己输赢数量
     * @return
     */
    public BMDiceResultDialog setSelfScore(int scoreSelf)
    {
        getScoreSelfTextView().setText(String.valueOf(scoreSelf));
        return this;
    }

    /**
     * 设置大赢家
     *
     * @param winner      大赢家名字
     * @param scoreWinner 大赢家数量
     * @return
     */
    public BMDiceResultDialog setWinner(String winner, int scoreWinner)
    {
        setLlLayoutWinnerShow(true);
        getWinnerTextView().setText(winner);
        getScoreWinnerTextView().setText(String.valueOf(scoreWinner));
        return this;
    }

    public void resetInfo()
    {
        getWinnerTextView().setText("");
        getScoreSelfTextView().setText("");
        getScoreWinnerTextView().setText("");
    }

    @Override
    public void show()
    {
        startDismissRunnable(TIME_DISMISS);
        super.show();
    }
}
