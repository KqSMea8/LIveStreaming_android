package com.fanwe.live;

import android.text.TextUtils;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_get_videoActModel;

public class LiveInformation implements ILiveInfo
{
    private static LiveInformation sInstance;

    /**
     * 直播间id
     */
    private int mRoomId = 0;
    /**
     * 直播间聊天组id
     */
    private String mGroupId;
    /**
     * 直播间主播id
     */
    private String mCreaterId;
    /**
     * 是否私密直播
     */
    private boolean mIsPrivate = false;
    /**
     * 是否是回放
     */
    private boolean mIsPlayback = false;
    /**
     * 0-腾讯云sdk；1-金山sdk
     */
    private int mSdkType;
    /**
     * 当前正在私聊的用户id
     */
    private String mCurrentChatPeer;
    /**
     * 是否正在竞拍
     */
    private boolean mIsAuctioning;

    private boolean mIsPushing;

    private App_get_videoActModel mRoomInfo;

    private LiveInformation()
    {
    }

    public static LiveInformation getInstance()
    {
        if (sInstance == null)
        {
            synchronized (LiveInformation.class)
            {
                if (sInstance == null)
                {
                    sInstance = new LiveInformation();
                }
            }
        }
        return sInstance;
    }

    //----------ILiveInfo implements start----------

    @Override
    public int getRoomId()
    {
        return mRoomId;
    }

    @Override
    public String getGroupId()
    {
        if (mGroupId == null)
        {
            mGroupId = "";
        }
        return mGroupId;
    }

    @Override
    public String getCreaterId()
    {
        if (mCreaterId == null)
        {
            mCreaterId = "";
        }
        return mCreaterId;
    }

    @Override
    public App_get_videoActModel getRoomInfo()
    {
        return mRoomInfo;
    }

    @Override
    public boolean isPrivate()
    {
        return mIsPrivate;
    }

    @Override
    public boolean isPlayback()
    {
        return mIsPlayback;
    }

    @Override
    public boolean isCreater()
    {
        if (TextUtils.isEmpty(getCreaterId()))
        {
            return false;
        } else
        {
            return getCreaterId().equals(UserModelDao.getUserId());
        }
    }

    @Override
    public int getSdkType()
    {
        return mSdkType;
    }

    @Override
    public boolean isAuctioning()
    {
        return mIsAuctioning;
    }

    @Override
    public boolean isPushing()
    {
        return mIsPushing;
    }

    public void setPushing(boolean pushing)
    {
        mIsPushing = pushing;
    }
//----------ILiveInfo implements end----------

    public void setRoomId(int roomId)
    {
        mRoomId = roomId;
        LogUtil.i("setRoomId:" + roomId);
    }

    public void setGroupId(String groupId)
    {
        if (TextUtils.isEmpty(mGroupId) && groupId != null)
        {
            mGroupId = groupId;
            LogUtil.i("setGroupId:" + groupId);
        }
    }

    public void setCreaterId(String createrId)
    {
        if (TextUtils.isEmpty(mCreaterId) && createrId != null)
        {
            mCreaterId = createrId;
            LogUtil.i("setCreaterId:" + createrId);
        }
    }

    public void setRoomInfo(App_get_videoActModel roomInfo)
    {
        mRoomInfo = roomInfo;
        LogUtil.i("setRoomInfo:" + roomInfo);
    }

    public void setPrivate(boolean isPrivate)
    {
        this.mIsPrivate = isPrivate;
        LogUtil.i("setPrivate:" + isPrivate);
    }

    public void setPlayback(boolean playback)
    {
        mIsPlayback = playback;
        LogUtil.i("setPrivate:" + playback);
    }

    public void setSdkType(int sdkType)
    {
        this.mSdkType = sdkType;
        LogUtil.i("setSdkType:" + sdkType);
    }

    public void setAuctioning(boolean auctioning)
    {
        mIsAuctioning = auctioning;
        LogUtil.i("setAuctioning:" + auctioning);
    }

    //----------extend start----------

    public String getCurrentChatPeer()
    {
        if (mCurrentChatPeer == null)
        {
            mCurrentChatPeer = "";
        }
        return mCurrentChatPeer;
    }

    public void setCurrentChatPeer(String currentChatPeer)
    {
        this.mCurrentChatPeer = currentChatPeer;
        LogUtil.i("setCurrentChatPeer:" + currentChatPeer);
    }

    //----------extend end----------

    public void exitRoom()
    {
        mRoomId = 0;
        mGroupId = null;
        mCreaterId = null;
        mRoomInfo = null;
        mIsPrivate = false;
        mIsPlayback = false;
        mSdkType = 0;
        mIsAuctioning = false;
        mIsPushing = false;
    }
}
