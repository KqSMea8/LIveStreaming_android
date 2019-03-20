package com.fanwe.live.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveUserProfitRecordAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_GainRecordActModel;
import com.fanwe.live.model.GainRecordModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shibx on 2016/7/19.
 */
public class LiveUserProfitRecordActivity extends BaseTitleActivity
{

    @ViewInject(R.id.tv_summary)
    private TextView tv_summary;
    @ViewInject(R.id.lv_record)
    private ListView lv_record;
    @ViewInject(R.id.ll_empty_tips)
    private LinearLayout ll_empty_tips;
    @ViewInject(R.id.tv_empty_text)
    private TextView tv_empty_text;

    private LiveUserProfitRecordAdapter mAdapter;
    private List<GainRecordModel> mListRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_profit_record);
        init();
    }

    private void init()
    {
        initTitle();
        initEmptyText();
        mListRecord = new ArrayList<>();
        mAdapter = new LiveUserProfitRecordAdapter(mListRecord, this);
        lv_record.setAdapter(mAdapter);
    }

    private void initEmptyText()
    {
        tv_empty_text.setText("快去直播领取劳务费");
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("领取记录");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        requestRecord();
    }

    private void requestRecord()
    {
        CommonInterface.requestGainRecord(new AppRequestCallback<App_GainRecordActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    tv_summary.setText(actModel.getTotal_money() + "元");
                    mListRecord = actModel.getList();
                    if (mListRecord != null && mListRecord.size() > 0)
                    {
                        SDViewUtil.setInvisible(ll_empty_tips);
                    } else
                    {
                        SDViewUtil.setVisible(ll_empty_tips);
                    }
                    mAdapter.updateData(mListRecord);
                }
            }
        });
    }
}
