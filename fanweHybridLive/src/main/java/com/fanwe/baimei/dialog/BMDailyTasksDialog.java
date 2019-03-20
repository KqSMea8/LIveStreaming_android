package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.baimei.adapter.BMDailyTasksAdapter;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMDailyTaskResponseModel;
import com.fanwe.baimei.model.BMDailyTasksAwardAcceptResponseModel;
import com.fanwe.baimei.model.BMDailyTasksListModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.scottsu.stateslayout.StatesLayout;

/**
 * 包名: com.fanwe.baimei.dialog
 * 描述: 每日任务弹窗
 * 作者: Su
 * 创建时间: 2017/5/25 11:28
 **/
public class BMDailyTasksDialog extends SDDialogBase
{
    private StatesLayout mStatesLayout;
    private ImageView mCloseImageView;
    private RecyclerView mTasksRecyclerView;
    private BMDailyTasksAdapter mTasksAdapter;


    public BMDailyTasksDialog(Activity activity)
    {
        super(activity);
        init();
    }

    public BMDailyTasksDialog(Activity activity, int theme)
    {
        super(activity, theme);
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);

        setAnimations(R.style.anim_top_top);

        setContentView(R.layout.bm_dialog_daily_tasks);
        paddingLeft(SDViewUtil.getScreenWidthPercent(0.05f) );
        paddingRight(SDViewUtil.getScreenWidthPercent(0.05f) );

        getTasksRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getTasksRecyclerView().setAdapter(getTasksAdapter());

        getCloseImageView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        requestDailyTasks();
    }

    private StatesLayout getStatesLayout(){
        if (mStatesLayout == null) {
            mStatesLayout = (StatesLayout)findViewById(R.id.states_layout);
            mStatesLayout.setCallback(new StatesLayout.StatesLayoutCallback()
            {
                @Override
                public void onEmptyClick(View view)
                {
                    requestDailyTasks();
                }

                @Override
                public void onErrorClick(View view)
                {
                    requestDailyTasks();
                }
            });
        }
        return mStatesLayout;
    }

    private ImageView getCloseImageView()
    {
        if (mCloseImageView == null)
        {
            mCloseImageView = (ImageView) findViewById(R.id.iv_close_bm_dialog_daily_tasks);
        }
        return mCloseImageView;
    }

    private RecyclerView getTasksRecyclerView()
    {
        if (mTasksRecyclerView == null)
        {
            mTasksRecyclerView = (RecyclerView) findViewById(R.id.rv_tasks_bm_dialog_daily_tasks);
        }
        return mTasksRecyclerView;
    }

    private BMDailyTasksAdapter getTasksAdapter()
    {
        if (mTasksAdapter == null)
        {
            mTasksAdapter = new BMDailyTasksAdapter(getOwnerActivity());
            mTasksAdapter.setBMDailyTasksAdapterCallback(new BMDailyTasksAdapter.BMDailyTasksAdapterCallback()
            {
                @Override
                public void onReceiveAwardClick(View view, BMDailyTasksListModel model, int position)
                {
                    requestAcceptTaskAward(model);
                }
            });
        }
        return mTasksAdapter;
    }

    private void requestDailyTasks()
    {
        BMCommonInterface.requestDailyTasks(new AppRequestCallback<BMDailyTaskResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                getStatesLayout().showLoading();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if(actModel.isOk()){
                    getStatesLayout().showContent();
                    getTasksAdapter().setData(actModel.getList());
                    getTasksAdapter().notifyDataSetChanged();
                }else{

                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
//                getStatesLayout().showError();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    private void requestAcceptTaskAward(final BMDailyTasksListModel model)
    {
        BMCommonInterface.requestDailyTasksAwardAccept(model.getType(), new AppRequestCallback<BMDailyTasksAwardAcceptResponseModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if(actModel.isOk()){
                    BMDailyTasksAwardAcceptResponseModel.MissionBean newMission=actModel.getMission();
                    if(newMission.getLeft_times()>0){
                        model.setCurrent(newMission.getCurrent());
                        model.setMax_times(newMission.getMax_times());
                        model.setMoney(newMission.getMoney());
                        model.setProgress(newMission.getProgress());
                        model.setTarget(newMission.getTarget());
                        model.setTime(newMission.getTime());
                        model.setTitle(newMission.getTitle());
                        model.setDesc(newMission.getDesc());
//                           model.setImage(newMission.getImage());
                    }

                    model.setLeft_times(newMission.getLeft_times());
                    getTasksAdapter().notifyDataSetChanged();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                Log.e("===>task accept",resp.getDecryptedResult());
            }
        });
    }

}
