package com.fanwe.games.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.games.adapter.GameBankerListAdapter;
import com.fanwe.games.model.App_banker_listActModel;
import com.fanwe.games.model.GameBankerModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shibx on 2017/02/24.
 * 游戏上庄玩家列表
 */

public class GamesBankerListDialog extends SDDialogCustom implements SDDialogCustom.SDDialogCustomCallback,
        AdapterView.OnItemClickListener
{

    private ListView lv_banker_list;

    private ProgressBar dialog_progress;

    private TextView tv_empty_text;

    private GameBankerListAdapter mAdapter;

    private List<GameBankerModel> mListBanker;

    private BankerSubmitListener mListener;

    public GamesBankerListDialog(Activity activity, BankerSubmitListener listener)
    {
        super(activity);
        this.mListener = listener;
        init();
    }

    @Override
    protected void init()
    {
        super.init();
        setCustomView(R.layout.dialog_games_banker_list);
        lv_banker_list = (ListView) findViewById(R.id.lv_banker_list);
        dialog_progress = (ProgressBar) findViewById(R.id.dialog_progress);
        tv_empty_text = (TextView) findViewById(R.id.tv_empty_text);
        lv_banker_list.setOnItemClickListener(this);
        mListBanker = new ArrayList<>();
        String unit = AppRuntimeWorker.getGameCurrencyUnit();
        mAdapter = new GameBankerListAdapter(mListBanker, getOwnerActivity(), unit);
        lv_banker_list.setAdapter(mAdapter);
        setTextTitle("选择庄家");
        setDismissAfterClick(false);
        setCallback(this);
        setTextColorTitle(SDResourcesUtil.getColor(R.color.main_color));
    }

    @Override
    public void showCenter()
    {
        super.showCenter();
        requestBankerList();
    }

    private void requestBankerList()
    {
        SDViewUtil.setVisible(dialog_progress);
        SDViewUtil.setGone(tv_empty_text);
        CommonInterface.requestBankerList(new AppRequestCallback<App_banker_listActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    List<GameBankerModel> list = actModel.getBanker_list();
                    if (!list.isEmpty())
                    {
                        mListBanker = list;
                    } else
                    {
                        SDViewUtil.setVisible(tv_empty_text);
                        mListBanker.clear();
                    }
                } else
                {
                    mListBanker.clear();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                mListBanker.clear();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                SDViewUtil.setGone(dialog_progress);
                mAdapter.updateData(mListBanker);
                if (mAdapter.getCount() > 0)
                {
                    mAdapter.getSelectManager().performClick(0);
                }
            }
        });
    }


    @Override
    public void onClickCancel(View v, SDDialogCustom dialog)
    {
        dismiss();
    }

    @Override
    public void onClickConfirm(View v, SDDialogCustom dialog)
    {
        GameBankerModel model = mAdapter.getSelectManager().getSelectedItem();
        if (model != null)
        {
            mListener.onClickChoose(dialog, model.getBanker_log_id());
        }
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        mAdapter.getSelectManager().performClick(position);
    }

    public interface BankerSubmitListener
    {
        void onClickChoose(SDDialogCustom dialog, String bankerLogId);
    }
}
