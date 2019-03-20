package com.fanwe.live.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.baimei.activity.BMLoginMobileBindActivity;
import com.fanwe.hybrid.activity.AppWebViewActivity;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.common.CommonOpenLoginSDK;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDDurationBlocker;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EFirstLoginNewLevel;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LiveLoginActivity extends BaseActivity
{
    //微信
    private LinearLayout ll_weixin;
    private ImageView iv_weixin;

    //QQ
    private LinearLayout ll_qq;
    private ImageView iv_qq;

    //新浪
    private LinearLayout ll_sina;
    private ImageView iv_sina;

    //手机
    private LinearLayout ll_shouji;
    private ImageView iv_shouji;

    //游客
    private TextView tv_visitors;

    private TextView tv_agreement;

    private SDDurationBlocker blocker = new SDDurationBlocker(2000);

    private String mLoginType;//qq_login wx_login sina_login
    private String mOpenid;
    private String mAccessToken;

    public static final class LoginType
    {
        private static final String QQ_LOGIN = "qq_login";
        private static final String WX_LOGIN = "wx_login";
        private static final String SINA_LOGIN = "sina_login";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mIsExitApp = true;
        setFullScreen(true);
        setContentView(R.layout.act_live_login);
        init();
    }

    private void init()
    {
        if (ApkConstant.AUTO_REGISTER)
        {
            clickLoginVisitors();
            return;
        }

        register();
        bindDefaultData();
        initLoginIcon();
    }

    private void register()
    {
        ll_weixin = find(R.id.ll_weixin);
        iv_weixin = find(R.id.iv_weixin);
        ll_qq = find(R.id.ll_qq);
        iv_qq = find(R.id.iv_qq);
        ll_sina = find(R.id.ll_sina);
        iv_sina = find(R.id.iv_sina);
        ll_shouji = find(R.id.ll_shouji);
        iv_shouji = find(R.id.iv_shouji);
        tv_visitors = find(R.id.tv_visitors);
        tv_agreement = find(R.id.tv_agreement);

        iv_qq.setOnClickListener(this);
        iv_sina.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_shouji.setOnClickListener(this);
        tv_visitors.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);

        tv_visitors.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    private void bindDefaultData()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            String privacy_titile = initActModel.getPrivacy_title();
            SDViewBinder.setTextView(tv_agreement, privacy_titile);
        }
    }

    private void initLoginIcon()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            //微信
            int has_wx_login = model.getHas_wx_login();
            if (has_wx_login == 1)
            {
                SDViewUtil.setVisible(ll_weixin);
            } else
            {
                SDViewUtil.setGone(ll_weixin);
            }

            //QQ
            int has_qq_login = model.getHas_qq_login();
            if (has_qq_login == 1)
            {
                SDViewUtil.setVisible(ll_qq);
            } else
            {
                SDViewUtil.setGone(ll_qq);
            }

            //新浪
            int has_sina_login = model.getHas_sina_login();
            if (has_sina_login == 1)
            {
                SDViewUtil.setVisible(ll_sina);
            } else
            {
                SDViewUtil.setGone(ll_sina);
            }

            //手机
            int has_mobile_login = model.getHas_mobile_login();
            if (has_mobile_login == 1)
            {
                SDViewUtil.setVisible(ll_shouji);
            } else
            {
                SDViewUtil.setGone(ll_shouji);
            }

            //游客
            int has_visitors_login = model.getHas_visitors_login();
            if (has_visitors_login == 1)
            {
                SDViewUtil.setVisible(tv_visitors);
            } else
            {
                SDViewUtil.setGone(tv_visitors);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (blocker.block())
        {
            return;
        }

        if (v == iv_weixin)
        {
            clickLoginWeiXing();
        } else if (v == iv_qq)
        {
            clickLoginQQ();
        } else if (v == iv_sina)
        {
            clickLoginSina();
        } else if (v == iv_shouji)
        {
            clickLoginShouJi();
        } else if (v == tv_visitors)
        {
            clickLoginVisitors();
        } else if (v == tv_agreement)
        {
            clickAgreement();
        }
    }

    private void enableClickLogin(boolean enable)
    {
        iv_weixin.setClickable(enable);
        iv_qq.setClickable(enable);
        iv_sina.setClickable(enable);
        iv_shouji.setClickable(enable);
        tv_visitors.setClickable(enable);
    }

    private void clickAgreement()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            String privacy_link = initActModel.getPrivacy_link();
            if (!TextUtils.isEmpty(privacy_link))
            {
                Intent intent = new Intent(LiveLoginActivity.this, AppWebViewActivity.class);
                intent.putExtra(AppWebViewActivity.EXTRA_URL, privacy_link);
                intent.putExtra(AppWebViewActivity.EXTRA_IS_SCALE_TO_SHOW_ALL, false);
                startActivity(intent);
            }
        }
    }

    private void clickLoginWeiXing()
    {
        CommonOpenLoginSDK.loginWx(this, wxListener);
    }

    /**
     * 微信授权监听
     */
    private UMAuthListener wxListener = new UMAuthListener()
    {

        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map)
        {
            SDToast.showToast("授权成功");
            String openid = map.get("openid");
            String access_token = map.get("access_token");
            mLoginType = LoginType.WX_LOGIN;
            mOpenid = openid;
            mAccessToken = access_token;
            requestWeiXinLogin(openid, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable)
        {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i)
        {

        }
    };

    private void clickLoginQQ()
    {
        CommonOpenLoginSDK.umQQlogin(this, qqListener);
    }

    /**
     * qq授权监听
     */
    private UMAuthListener qqListener = new UMAuthListener()
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            SDToast.showToast("授权成功");
            String openid = data.get("openid");
            String access_token = data.get("access_token");
            mLoginType = LoginType.QQ_LOGIN;
            mOpenid = openid;
            mAccessToken = access_token;
            requestQQ(openid, access_token);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            SDToast.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            SDToast.showToast("授权取消");
        }
    };

    private void clickLoginSina()
    {
        CommonOpenLoginSDK.umSinalogin(this, sinaListener);
    }

    /**
     * 新浪授权监听
     */
    private UMAuthListener sinaListener = new UMAuthListener()
    {
        @Override
        public void onStart(SHARE_MEDIA share_media)
        {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data)
        {
            SDToast.showToast("授权成功");
            String access_token = data.get("access_token");
            String uid = data.get("uid");
            mLoginType = LoginType.SINA_LOGIN;
            mOpenid = uid;
            mAccessToken = access_token;
            requestSinaLogin(access_token, uid);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t)
        {
            SDToast.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action)
        {
            SDToast.showToast("授权取消");
        }
    };

    private void clickLoginShouJi()
    {
        Intent intent = new Intent(this, LiveMobielRegisterActivity.class);
        startActivity(intent);
    }

    private void clickLoginVisitors()
    {
        CommonInterface.requestLoginVisitorsLogin(new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                dismissProgressDialog();
            }

            @Override
            protected void onCancel(SDResponse resp)
            {
                super.onCancel(resp);
                dismissProgressDialog();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                dismissProgressDialog();
                if (actModel.isOk())
                {
                    startMainActivity(actModel);
                }
            }
        });
    }

    private void requestWeiXinLogin(String openid, String access_token)
    {
        CommonInterface.requestWxLogin(openid, access_token, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    startMainActivity(actModel);
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void requestQQ(String openid, String access_token)
    {
        CommonInterface.requestQqLogin(openid, access_token, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    startMainActivity(actModel);
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void requestSinaLogin(String access_token, String uid)
    {
        CommonInterface.requestSinaLogin(access_token, uid, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                enableClickLogin(false);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                enableClickLogin(true);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    startMainActivity(actModel);
                    setFirstLoginAndNewLevel(actModel);
                }
            }
        });
    }

    private void setFirstLoginAndNewLevel(App_do_updateActModel actModel)
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

    private void startMainActivity(App_do_updateActModel actModel)
    {
        if (actModel.getNeed_bind_mobile() == 1)
        {
            startBindMobileActivity();
            return;
        }

        UserModel user = actModel.getUser_info();
        if (UserModel.dealLoginSuccess(user, true))
        {
            InitBusiness.startMainActivity(LiveLoginActivity.this);
        }
    }

    private void startBindMobileActivity()
    {
        Intent intent = new Intent(getActivity(), BMLoginMobileBindActivity.class);
        intent.putExtra(BMLoginMobileBindActivity.EXTRA_LOGIN_TYPE,mLoginType);
        intent.putExtra(BMLoginMobileBindActivity.EXTRA_OPEN_ID,mOpenid);
        intent.putExtra(BMLoginMobileBindActivity.EXTRA_ACCESS_TOKEN,mAccessToken);
        startActivity(intent);
    }

    public void onEventMainThread(ERetryInitSuccess event)
    {
        bindDefaultData();
        initLoginIcon();
    }
}
