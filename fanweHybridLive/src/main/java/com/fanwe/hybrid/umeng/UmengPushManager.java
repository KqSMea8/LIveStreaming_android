package com.fanwe.hybrid.umeng;

import android.content.Context;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.R;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


/**
 * @author 作者 E-mail:309581534@qq.com
 * @version 创建时间：2015-6-29 下午2:28:00 类说明
 */
public class UmengPushManager
{
    private static PushAgent mPushAgent;

    /**
     * 必须在Application的onCreate方法中调用
     *
     * @param context
     */
    public static void init(Context context)
    {
        mPushAgent = PushAgent.getInstance(context);
        mPushAgent.setResourcePackageName(R.class.getName().replace(".R", ""));
        mPushAgent.setNotificationClickHandler(new AppUmengNotificationClickHandler());
        mPushAgent.setDisplayNotificationNumber(5);
        mPushAgent.register(new IUmengRegisterCallback()
        {
            @Override
            public void onSuccess(String s)
            {
                LogUtil.i("register push success regId:" + s);
            }

            @Override
            public void onFailure(String s, String s1)
            {
                LogUtil.i("register push failure:" + s + "," + s1);
            }
        });
    }

    public static PushAgent getPushAgent()
    {
        return mPushAgent;
    }
}
