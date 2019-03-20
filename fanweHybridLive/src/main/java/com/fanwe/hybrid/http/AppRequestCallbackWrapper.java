package com.fanwe.hybrid.http;

import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;

public abstract class AppRequestCallbackWrapper<D> extends AppRequestCallback<D>
{

    private AppRequestCallback<D> mOriginalCallback;

    public AppRequestCallbackWrapper(AppRequestCallback<D> originalCallback)
    {
        super();
        this.mOriginalCallback = originalCallback;
    }

    @Override
    public void setRequestHandler(SDRequestHandler requestHandler)
    {
        super.setRequestHandler(requestHandler);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.setRequestHandler(requestHandler);
        }
    }

    @Override
    public void setRequestParams(SDRequestParams requestParams)
    {
        super.setRequestParams(requestParams);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.setRequestParams(requestParams);
        }
    }

    @Override
    public String getCancelTag()
    {
        if (mOriginalCallback != null)
        {
            return mOriginalCallback.getCancelTag();
        } else
        {
            return super.getCancelTag();
        }
    }

    @Override
    public void notifyStart()
    {
        super.notifyStart();
        if (mOriginalCallback != null)
        {
            mOriginalCallback.notifyStart();
        }
    }

    @Override
    public void notifySuccess(SDResponse resp)
    {
        super.notifySuccess(resp);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.notifySuccess(resp);
        }
    }

    @Override
    public void notifyError(SDResponse resp)
    {
        super.notifyError(resp);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.notifyError(resp);
        }
    }

    @Override
    public void notifyCancel(SDResponse resp)
    {
        super.notifyCancel(resp);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.notifyCancel(resp);
        }
    }

    @Override
    public void notifyFinish(SDResponse resp)
    {
        super.notifyFinish(resp);
        if (mOriginalCallback != null)
        {
            mOriginalCallback.notifyFinish(resp);
        }
    }
}
