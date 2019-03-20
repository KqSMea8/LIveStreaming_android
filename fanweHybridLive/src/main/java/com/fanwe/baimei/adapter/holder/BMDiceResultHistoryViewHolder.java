package com.fanwe.baimei.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.baimei.model.BMDiceResultHistoryModel;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;


/**
 * 包名: com.fanwe.baimei.adapter.holder
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/27 15:45
 **/
public class BMDiceResultHistoryViewHolder extends SDRecyclerViewHolder<BMDiceResultHistoryModel>
{
    private TextView mLatestTextView;
    private ImageView mLeftImageView;
    private ImageView mMiddleImageView;
    private ImageView mRightImageView;


    public BMDiceResultHistoryViewHolder(ViewGroup parent)
    {
        super(parent, R.layout.bm_view_holder_dice_result_history);
    }

    @Override
    public void onBindData(int position, BMDiceResultHistoryModel model)
    {
        getLatestTextView().setVisibility(model.isLastest() ? View.VISIBLE : View.INVISIBLE);

        selectResult(model.getResult());
    }

    private void selectResult(int result)
    {
        if (1 == result)
        {
            getLeftImageView().setSelected(true);
            getMiddleImageView().setSelected(false);
            getRightImageView().setSelected(false);
        } else if (2 == result)
        {
            getLatestTextView().setSelected(false);
            getMiddleImageView().setSelected(true);
            getRightImageView().setSelected(false);
        } else if (3 == result)
        {
            getLatestTextView().setSelected(false);
            getMiddleImageView().setSelected(false);
            getRightImageView().setSelected(true);
        }
    }

    private TextView getLatestTextView()
    {
        if (mLatestTextView == null)
        {
            mLatestTextView = (TextView) findViewById(R.id.tv_latest);
        }
        return mLatestTextView;
    }

    private ImageView getLeftImageView()
    {
        if (mLeftImageView == null)
        {
            mLeftImageView = (ImageView) findViewById(R.id.iv_left);
        }
        return mLeftImageView;
    }

    private ImageView getMiddleImageView()
    {
        if (mMiddleImageView == null)
        {
            mMiddleImageView = (ImageView) findViewById(R.id.iv_middle);
        }
        return mMiddleImageView;
    }

    private ImageView getRightImageView()
    {
        if (mRightImageView == null)
        {
            mRightImageView = (ImageView) findViewById(R.id.iv_right);
        }
        return mRightImageView;
    }

}
