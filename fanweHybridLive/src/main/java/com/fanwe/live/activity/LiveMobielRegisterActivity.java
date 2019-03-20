package com.fanwe.live.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EFirstLoginNewLevel;
import com.fanwe.live.model.App_do_loginActModel;
import com.fanwe.live.model.App_is_user_verifyActModel;
import com.fanwe.live.model.App_send_mobile_verifyActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LiveMobielRegisterActivity extends BaseTitleActivity
{

    @ViewInject(R.id.ll_image_code)
    private LinearLayout ll_image_code;
    @ViewInject(R.id.et_image_code)
    private EditText et_image_code;
    @ViewInject(R.id.iv_image_code)
    private ImageView iv_image_code;

    @ViewInject(R.id.et_mobile)
    private EditText et_mobile;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton btn_send_code;

    @ViewInject(R.id.tv_login)
    private TextView tv_login;

    private App_do_loginActModel app_do_loginActModel;
    private String strMobile;
    private String strImageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_mobile_register);
        init();
    }

    private void init()
    {
        register();
        initTitle();
        reqeustIsUserVerify();
        initSDSendValidateButton();
    }

    private void register()
    {
        tv_login.setOnClickListener(this);
        iv_image_code.setOnClickListener(this);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("手机登录");
    }


    private void initSDSendValidateButton()
    {
        btn_send_code.setmListener(new SDSendValidateButton.SDSendValidateButtonListener()
        {
            @Override
            public void onTick()
            {
            }

            @Override
            public void onClickSendValidateButton()
            {
                requestSendCode();
            }
        });
    }

    private void reqeustIsUserVerify()
    {
        CommonInterface.requestIsUserVerify(new AppRequestCallback<App_is_user_verifyActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    SDViewUtil.setVisible(ll_image_code);
                    GlideUtil.load(actModel.getVerify_url())
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(iv_image_code);
                } else
                {
                    SDViewUtil.setGone(ll_image_code);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }


    private void requestSendCode()
    {
        strMobile = et_mobile.getText().toString();
        strImageCode = et_image_code.getText().toString();

        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return;
        }
        if (strMobile.trim().length()<11)
        {
            SDToast.showToast("请输入正确的手机号码");
            return;
        }
        if (ll_image_code.getVisibility() == View.VISIBLE)
        {
            if (TextUtils.isEmpty(strImageCode)){
                SDToast.showToast("请输入图形验证码");
                return;
            }
        }


        CommonInterface.requestSendMobileVerify(0, strMobile, strImageCode, new AppRequestCallback<App_send_mobile_verifyActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    btn_send_code.setmDisableTime(actModel.getTime());
                    btn_send_code.startTickWork();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                SDToast.showToast("获取验证码失败");

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.iv_image_code:
                reqeustIsUserVerify();
                break;
            case R.id.tv_login:
                clickTvLogin();
                break;
        }
    }

    private void clickTvLogin()
    {
        strMobile = et_mobile.getText().toString();
        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号");
            return;
        }
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code))
        {
            SDToast.showToast("请输入验证码");
            return;
        }

        CommonInterface.requestDoLogin(strMobile, code, new AppRequestCallback<App_do_loginActModel>()
        {
            @Override
            public void onStart()
            {
                showProgressDialog("正在登录");
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    dealSuccess(actModel);
//                    setFirstLoginAndNewLevel(actModel);
                } else {
                    if (!TextUtils.isEmpty(actModel.getError())) {
                        SDToast.showToast(actModel.getError());
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                SDToast.showToast(resp.getResult());
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    private void setFirstLoginAndNewLevel(App_do_loginActModel actModel)
    {
        InitActModel initActModel = InitActModelDao.query();
        initActModel.setFirst_login(actModel.getFirst_login());
        initActModel.setNew_level(actModel.getNew_level());
        if (!InitActModelDao.insertOrUpdate(initActModel))
        {
            SDToast.showToast("保存init信息失败");
        }
        //发送事件首次登陆升级
        EFirstLoginNewLevel event = new EFirstLoginNewLevel();
        SDEventManager.post(event);
    }

    private void dealSuccess(App_do_loginActModel actModel)
    {
        UserModel user = actModel.getUser_info();
        if (user != null)
        {
            if (actModel.getIs_lack() == 1)
            {
                Intent intent = new Intent(this, LiveDoUpdateActivity.class);
                intent.putExtra(LiveDoUpdateActivity.EXTRA_USER_MODEL, user);
                startActivity(intent);
            } else
            {
                if (UserModel.dealLoginSuccess(user, true))
                {
                    InitBusiness.startMainActivity(LiveMobielRegisterActivity.this);
                    try {
                        InitBusiness.finishLoginActivity();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e("LiveMobielRegisterActivity:" + e.getMessage());
                    }
                } else
                {
                    SDToast.showToast("保存用户信息失败");
                }
            }
        } else
        {
            SDToast.showToast("没有获取到用户信息");
        }
    }
}
