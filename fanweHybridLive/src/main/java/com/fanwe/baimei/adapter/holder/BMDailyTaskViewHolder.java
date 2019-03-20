package com.fanwe.baimei.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.baimei.model.BMDailyTasksListModel;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * 包名: com.fanwe.baimei.adapter.holder
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/25 15:23
 **/
public class BMDailyTaskViewHolder extends SDRecyclerViewHolder<BMDailyTasksListModel>
{
    private static final int[] TASK_ICON_RES = {R.drawable.bm_ic_task_watch, R.drawable.bm_ic_task_game,
            R.drawable.bm_ic_task_reward, R.drawable.bm_ic_task_share, R.drawable.bm_ic_task_follow};

    private ImageView mTaskIconImageView;
    private TextView mDescriptionTextView;
    private TextView mAwardDescriptionTextView;
    private TextView mProgressTextView;
    private TextView mReceiverTextView;
    private BMDailyTaskViewHolderCallback mBMDailyTaskViewHolderCallback;


    public BMDailyTaskViewHolder(ViewGroup parent, int layoutId)
    {
        super(parent, layoutId);
    }

    @Override
    public void onBindData(final int position, final BMDailyTasksListModel model)
    {
        GlideUtil.load(model.getImage()).into(getTaskIconImageView());
        SDViewBinder.setTextView(getDescriptionTextView(), model.getTitle());
        SDViewBinder.setTextView(getAwardDescriptionTextView(), model.getDesc());

        if (0 == model.getLeft_times())
        {
            getProgressTextView().setVisibility(View.GONE);
            getReceiverTextView().setVisibility(View.VISIBLE);
            //已领取任务奖励
            getReceiverTextView().setText("已领");
            getReceiverTextView().setTextColor(Color.parseColor("#555555"));
            getReceiverTextView().setEnabled(false);
        } else
        {
//            if (model.getTarget() == model.getCurrent())
            if (model.getProgress() == 100)
            {
                //达成任务领奖条件
                getProgressTextView().setVisibility(View.GONE);
                getReceiverTextView().setVisibility(View.VISIBLE);
                getReceiverTextView().setText("领取");
                getReceiverTextView().setTextColor(Color.WHITE);
                getReceiverTextView().setEnabled(true);
            } else
            {
                getProgressTextView().setVisibility(View.VISIBLE);
                getReceiverTextView().setVisibility(View.GONE);
                getProgressTextView().setText(model.getCurrent() + "/" + model.getTarget());
            }
        }

        getReceiverTextView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getBMDailyTaskViewHolderCallback().onReceiveAwardClick(v, model, position);
            }
        });

    }

    private ImageView getTaskIconImageView()
    {
        if (mTaskIconImageView == null)
        {
            mTaskIconImageView = (ImageView) findViewById(R.id.iv_task_icon);
        }
        return mTaskIconImageView;
    }

    private TextView getDescriptionTextView()
    {
        if (mDescriptionTextView == null)
        {
            mDescriptionTextView = (TextView) findViewById(R.id.tv_task_description);
        }
        return mDescriptionTextView;
    }

    private TextView getAwardDescriptionTextView()
    {
        if (mAwardDescriptionTextView == null)
        {
            mAwardDescriptionTextView = (TextView) findViewById(R.id.tv_task_award_description);
        }
        return mAwardDescriptionTextView;
    }

    private TextView getProgressTextView()
    {
        if (mProgressTextView == null)
        {
            mProgressTextView = (TextView) findViewById(R.id.tv_task_progress);
        }
        return mProgressTextView;
    }

    private TextView getReceiverTextView()
    {
        if (mReceiverTextView == null)
        {
            mReceiverTextView = (TextView) findViewById(R.id.tv_receive_award);
        }
        return mReceiverTextView;
    }

    private BMDailyTaskViewHolderCallback getBMDailyTaskViewHolderCallback()
    {
        if (mBMDailyTaskViewHolderCallback == null)
        {
            mBMDailyTaskViewHolderCallback = new BMDailyTaskViewHolderCallback()
            {
                @Override
                public void onReceiveAwardClick(View view, BMDailyTasksListModel model, int position)
                {

                }
            };
        }
        return mBMDailyTaskViewHolderCallback;
    }

    public void setBMDailyTaskViewHolderCallback(BMDailyTaskViewHolderCallback callback)
    {
        this.mBMDailyTaskViewHolderCallback = callback;
    }

    public interface BMDailyTaskViewHolderCallback
    {
        void onReceiveAwardClick(View view, BMDailyTasksListModel model, int position);
    }


}
