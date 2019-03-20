package com.fanwe.baimei.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.baimei.common.BMActivityLauncher;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMGamesJoinActModel;
import com.fanwe.baimei.model.BMHomeLiveCenterTabModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by yhz on 2017/5/16.
 */

public class BMHomeLiveCenterTabAdapter extends SDSimpleAdapter<BMHomeLiveCenterTabModel>
{
    private int isRequestting = 0;//是否正在请求 1 是 0 否

    public BMHomeLiveCenterTabAdapter(List<BMHomeLiveCenterTabModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.bm_view_home_live_center_tab_item;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final BMHomeLiveCenterTabModel model)
    {
        ImageView iv_tab_anchor = ViewHolder.get(R.id.iv_tab_anchor, convertView);
        TextView tv_tab_titile = ViewHolder.get(R.id.tv_tab_titile, convertView);

        GlideUtil.loadHeadImage(model.getIcon()).into(iv_tab_anchor);
        SDViewBinder.setTextView(tv_tab_titile, model.getName());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickTab(model);
            }
        });
    }

    private void clickTab(BMHomeLiveCenterTabModel model)
    {
        if (BMHomeLiveCenterTabModel.BMHomeLiveCenterTabTAG.ANCHOR_RANK.equals(model.getTag()))
        {
            BMActivityLauncher.launcherLiveRankingActivity(getActivity());
        } else if (BMHomeLiveCenterTabModel.BMHomeLiveCenterTabTAG.RANDOM_GAME.equals(model.getTag()))
        {
            requestJoinGameRoom();
        } else if (BMHomeLiveCenterTabModel.BMHomeLiveCenterTabTAG.HERO_RANK.equals(model.getTag()))
        {
            BMActivityLauncher.launcherGameHeroRankActivity(getActivity());
        }
    }

    private void requestJoinGameRoom()
    {
        if (isRequestting == 1)
        {
            return;
        }
        isRequestting = 1;
        BMCommonInterface.requestGamesJoin(new AppRequestCallback<BMGamesJoinActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    BMGamesJoinActModel.RoomBean roomBean = actModel.getRoom();
                    if (roomBean == null)
                    {
                        return;
                    }
                    JoinLiveData data = new JoinLiveData();
                    data.setCreaterId(roomBean.getUser_id());
                    data.setGroupId(roomBean.getGroup_id());
                    data.setLoadingVideoImageUrl(roomBean.getLive_image());
                    data.setRoomId(roomBean.getRoom_id());
                    data.setSdkType(roomBean.getSdk_type());
                    data.setCreate_type(roomBean.getCreate_type());
                    AppRuntimeWorker.joinLive(data, getActivity());
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                isRequestting = 0;
            }
        });
    }
}
