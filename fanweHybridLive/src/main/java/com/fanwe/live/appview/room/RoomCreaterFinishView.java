package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_del_videoActModel;
import com.fanwe.live.model.App_end_videoActModel;

public class RoomCreaterFinishView extends RoomView
{

    public RoomCreaterFinishView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomCreaterFinishView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomCreaterFinishView(Context context)
    {
        super(context);
    }

    private TextView tv_viewer_number;
    private TextView tv_ticket;
    private TextView tv_share;
    private TextView tv_back_home;
    private TextView tv_delete_video;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        setContentView(R.layout.view_room_creater_finish);

        tv_viewer_number = find(R.id.tv_viewer_number);
        tv_ticket = find(R.id.tv_ticket);
        tv_share = find(R.id.tv_share);
        tv_back_home = find(R.id.tv_back_home);
        tv_delete_video = find(R.id.tv_delete_video);

        tv_share.setOnClickListener(this);
        tv_back_home.setOnClickListener(this);
        tv_delete_video.setOnClickListener(this);

        if (UmengSocialManager.isAllSocialDisable())
        {
            SDViewUtil.setInvisible(tv_share);
        } else
        {
            SDViewUtil.setVisible(tv_share);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_share)
        {
            if (clickListener != null)
            {
                clickListener.onClickShare(v);
            }
        } else if (v == tv_back_home)
        {
            clickBackHome();
        } else if (v == tv_delete_video)
        {
            clickDeleteVideo();
        }
    }

    protected void clickBackHome()
    {
        getActivity().finish();
    }

    protected void clickDeleteVideo()
    {
        CommonInterface.requestDeleteVideo(getLiveActivity().getRoomId(), new AppRequestCallback<App_del_videoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    getActivity().finish();
                }
            }
        });
    }

    public void onLiveCreaterRequestEndVideoSuccess(App_end_videoActModel actModel)
    {
        SDViewBinder.setTextView(tv_viewer_number, String.valueOf(actModel.getWatch_number()));
        SDViewBinder.setTextView(tv_ticket, String.valueOf(actModel.getVote_number()));
        if (actModel.getHas_delvideo() == 1)
        {
            SDViewUtil.setVisible(tv_delete_video);
        } else
        {
            SDViewUtil.setInvisible(tv_delete_video);
        }
    }

    @Override
    public boolean onBackPressed()
    {
        getActivity().finish();
        return true;
    }

    public interface ClickListener
    {
        void onClickShare(View view);
    }
}
