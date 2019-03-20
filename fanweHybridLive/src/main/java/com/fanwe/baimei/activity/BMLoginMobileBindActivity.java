package com.fanwe.baimei.activity;

import android.os.Bundle;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.activity.LiveMobileBindActivity;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_ProfitBindingActModel;
import com.fanwe.live.model.UserModel;

/**
 * Created by yhz on 2017/6/22.登录手机绑定
 */

public class BMLoginMobileBindActivity extends LiveMobileBindActivity
{
    public static final String EXTRA_LOGIN_TYPE = "extra_login_type";
    public static final String EXTRA_OPEN_ID = "extra_open_id";
    public static final String EXTRA_ACCESS_TOKEN = "extra_access_token";

    private String mLoginType;//qq_login wx_login sina_login
    private String mOpenid;
    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initIntent();
    }

    private void initIntent()
    {
        mLoginType = getIntent().getStringExtra(EXTRA_LOGIN_TYPE);
        mOpenid = getIntent().getStringExtra(EXTRA_OPEN_ID);
        mAccessToken = getIntent().getStringExtra(EXTRA_ACCESS_TOKEN);
    }

    @Override
    protected void requestMobileBind(String code)
    {
        CommonInterface.requestMobileBind(strMobile, code, mLoginType, mOpenid, mAccessToken, new AppRequestCallback<App_ProfitBindingActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    requestOnSuccess(actModel);
                }
            }
        });
    }

    @Override
    protected void requestOnSuccess(App_ProfitBindingActModel actModel)
    {
        UserModel user = actModel.getUser_info();
        if (UserModel.dealLoginSuccess(user, true))
        {
            InitBusiness.startMainActivity(BMLoginMobileBindActivity.this);
        }
    }
}
