package com.fanwe.live.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LivePushManageAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDSlidingButton;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shibx on 2016/7/12.
 */
public class LivePushManageActivity extends BaseTitleActivity
{

    @ViewInject(R.id.lv_push_list)
    private ListView lv_push_list;

    private SDSlidingButton sl_btn;

    private List<Integer> listModel = new ArrayList<>();
    private LivePushManageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_push_manage);
        init();
    }

    private void init()
    {
        initTitle();
        initHeader();

        adapter = new LivePushManageAdapter(listModel, this);
        lv_push_list.setAdapter(adapter);

        initSDSlidingButton();
    }


    private void initTitle()
    {
        mTitle.setMiddleTextTop("推送管理");
    }

    private void initHeader()
    {
        View headerView = LayoutInflater.from(this).inflate(R.layout.list_header_push_manage, null);
        sl_btn = (SDSlidingButton) headerView.findViewById(R.id.sl_btn);
        lv_push_list.addHeaderView(headerView);
    }

    private void initSDSlidingButton()
    {
        UserModel user = UserModelDao.query();
        if (user != null)
        {
            if (user.getIs_remind() == 1)
            {
                sl_btn.setSelected(true);
            } else
            {
                sl_btn.setSelected(false);
            }
        }
        sl_btn.setSelectedChangeListener(new SDSlidingButton.SelectedChangeListener()
        {
            @Override
            public void onSelectedChange(SDSlidingButton view, boolean selected)
            {
                if (selected)
                {
                    CommonInterface.requestSet_push(1, null);
                } else
                {
                    CommonInterface.requestSet_push(0, null);
                }
            }
        });
    }
}
