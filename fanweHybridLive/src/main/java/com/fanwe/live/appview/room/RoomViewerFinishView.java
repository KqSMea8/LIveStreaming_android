package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_followActModel;

public class RoomViewerFinishView extends RoomView
{

    public RoomViewerFinishView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomViewerFinishView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomViewerFinishView(Context context)
    {
        super(context);
    }

    /**
     * 观看人数 (int)
     */
    public static final String EXTRA_VIEWER_NUMBER = "extra_viewer_number";

    /**
     * 是否已关注1-已关注，0-未关注 (int)
     */
    public static final String EXTRA_HAS_FOLLOW = "extra_has_follow";

    private TextView tv_viewer_number;
    private TextView tv_follow;
    private TextView tv_back_home;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_viewer_finish;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        tv_viewer_number = find(R.id.tv_viewer_number);
        tv_follow = find(R.id.tv_follow);
        tv_back_home = find(R.id.tv_back_home);

        tv_follow.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickFollow();
            }
        });
        tv_back_home.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickBackHome();
            }
        });
    }

    public void setViewerNumber(int number)
    {
        if (number < 0)
        {
            number = 0;
        }
        tv_viewer_number.setText(String.valueOf(number));
    }

    public void setHasFollow(int hasFollow)
    {
        String strFollow = null;
        if (hasFollow == 1)
        {
            strFollow = "已关注";
        } else
        {
            strFollow = "关注";
        }
        SDViewBinder.setTextView(tv_follow, strFollow);
    }

    protected void clickFollow()
    {
        CommonInterface.requestFollow(getLiveActivity().getCreaterId(), 0, new AppRequestCallback<App_followActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    setHasFollow(actModel.getHas_focus());
                }
            }
        });
    }

    protected void clickBackHome()
    {
        getActivity().finish();
    }


    @Override
    public boolean onBackPressed()
    {
        getActivity().finish();
        return true;
    }
}
