package com.fanwe.live.appview.room;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.fragment.LiveContLocalFragment;
import com.fanwe.live.fragment.LiveContTotalFragment;

/**
 * Created by shibx on 2017/2/6.
 * 直播间 贡献榜页面(代替fragment)
 */

public class RoomContributionView extends RoomView
{

    private SDTabUnderline mTabToday;

    private SDTabUnderline mTabWeek;

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<>();

    /**
     * 用户id(String)
     */
    public static final String EXTRA_USER_ID = "extra_user_id";
    /**
     * 房间id(int)
     */
    public static final String EXTRA_ROOM_ID = "extra_room_id";

    private String mUserId;
    private int mRoomId;

    public RoomContributionView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomContributionView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomContributionView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_live_room_contribution;
    }

    protected void init()
    {
        mTabToday = find(R.id.tab_con_today);
        mTabWeek = find(R.id.tab_con_week);
        getExtraDatas();
    }

    private void getExtraDatas()
    {
        if (getLiveActivity() == null)
        {
            return;
        }
        this.mUserId = getLiveActivity().getCreaterId();
        this.mRoomId = getLiveActivity().getRoomId();
        initConTab();
    }

    public void setExtraDatas(String userId, int roomId)
    {
        this.mUserId = userId;
        this.mRoomId = roomId;
        initConTab();
    }

    private void initConTab()
    {
        mTabToday.setTextTitle("当天排行");
        mTabToday.getViewConfig(mTabToday.mTvTitle).setTextColorNormalResId(R.color.text_title).setTextColorSelectedResId(R.color.main_color);
        mTabToday.getViewConfig(mTabToday.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelectedResId(R.color.main_color);

        mTabWeek.setTextTitle("当周排行");
        mTabWeek.getViewConfig(mTabWeek.mTvTitle).setTextColorNormalResId(R.color.text_title).setTextColorSelectedResId(R.color.main_color);
        mTabWeek.getViewConfig(mTabWeek.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
                switch (index)
                {
                    case 0:
                        clickTodayConFrag();
                        break;
                    case 1:
                        clickWeekConFrag();
                        break;
                    default:
                        break;
                }
            }
        });

        mSelectManager.setItems(new SDTabUnderline[]{mTabToday, mTabWeek});
        mSelectManager.performClick(0);
    }

    private void clickTodayConFrag()
    {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ROOM_ID, mRoomId);
        ((SDBaseActivity) getActivity()).getSDFragmentManager().toggle(R.id.ll_content, null, LiveContLocalFragment.class, bundle);
    }

    private void clickWeekConFrag()
    {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_USER_ID, mUserId);
        ((SDBaseActivity) getActivity()).getSDFragmentManager().toggle(R.id.ll_content, null, LiveContTotalFragment.class, bundle);
    }
}
