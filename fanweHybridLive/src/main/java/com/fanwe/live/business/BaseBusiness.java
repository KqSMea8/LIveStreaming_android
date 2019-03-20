package com.fanwe.live.business;

import android.support.annotation.CallSuper;

import com.fanwe.hybrid.http.AppHttpUtil;

/**
 * Created by Administrator on 2017/6/3.
 */

public abstract class BaseBusiness
{
    /**
     * 获得取消http请求的tag
     *
     * @return
     */
    public String getHttpCancelTag()
    {
        return getClass().getName();
    }

    /**
     * 显示加载框
     *
     * @param msg
     */
    public void showProgress(String msg)
    {
        if (getBaseBusinessCallback() != null)
        {
            getBaseBusinessCallback().onBsShowProgress(msg);
        }
    }

    /**
     * 隐藏加载框
     */
    public void hideProgress()
    {
        if (getBaseBusinessCallback() != null)
        {
            getBaseBusinessCallback().onBsHideProgress();
        }
    }

    protected abstract BaseBusinessCallback getBaseBusinessCallback();

    @CallSuper
    public void onDestroy()
    {
        AppHttpUtil.getInstance().cancelRequest(getHttpCancelTag());
    }

    public interface BaseBusinessCallback
    {
        void onBsShowProgress(String msg);

        void onBsHideProgress();
    }
}
