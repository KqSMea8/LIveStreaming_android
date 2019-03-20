package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.live.event.ERefreshMsgUnReaded;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;

/**
 * Created by Administrator on 2016/8/5.
 */
public class RoomBottomView extends RoomView
{

    public RoomBottomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomBottomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomBottomView(Context context)
    {
        super(context);
    }

    public void onEventMainThread(ERefreshMsgUnReaded event)
    {
        setUnreadMessageModel(event.model);
    }

    public void setUnreadMessageModel(TotalConversationUnreadMessageModel model)
    {
        if (model != null && model.getTotalUnreadNum() > 0)
        {
            onIMUnreadNumber(model.getStr_totalUnreadNum());
        } else
        {
            onIMUnreadNumber(null);
        }
    }

    protected void onIMUnreadNumber(String numberFormat)
    {

    }

    /**
     * 显示隐藏分享
     *
     * @param show true-显示
     */
    public void showMenuShare(boolean show)
    {

    }

    /**
     * 显示隐藏底部扩展开关
     *
     * @param show true-显示
     */
    public void showMenuBottomExtendSwitch(boolean show)
    {

    }

    /**
     * 设置开关状态为打开
     */
    public void setMenuBottomExtendSwitchStateOpen()
    {

    }

    /**
     * 设置开关状态为关闭
     */
    public void setMenuBottomExtendSwitchStateClose()
    {

    }

}
