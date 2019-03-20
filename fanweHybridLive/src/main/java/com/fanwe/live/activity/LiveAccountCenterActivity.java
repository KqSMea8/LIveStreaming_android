package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.UrlLinkBuilder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.SettingsSecurityActModel;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/7/12.
 */
public class LiveAccountCenterActivity extends BaseTitleActivity
{
    @ViewInject(R.id.tv_band_mobilephone)
    private TextView tv_band_mobilephone;

    @ViewInject(R.id.iv_account_safe)
    private ImageView iv_account_safe;

    @ViewInject(R.id.tv_safe_grade)
    private TextView tv_safe_grade;

    @ViewInject(R.id.ll_account_bind_mobilephone)
    private LinearLayout ll_account_bind_mobilephone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_account_center);
        init();
    }

    private void init()
    {
        initTitle();
    }

    @Override
    protected void onResume()
    {
        requestData();
        super.onResume();
    }

    private void requestData()
    {
        CommonInterface.requestAccountAndSafe(new AppRequestCallback<SettingsSecurityActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    bindData(actModel);
                }
            }
        });
    }

    private void bindData(final SettingsSecurityActModel model)
    {
        if (1 == model.getIs_security())
        {
            setViewAttribute(model.getMobile(), R.drawable.account_safe_guard, "安全等级：高");
            ll_account_bind_mobilephone.setOnClickListener(null);
        } else
        {
            setViewAttribute("未绑定", R.drawable.account_safe_guard_dark, "安全等级：低");
            ll_account_bind_mobilephone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(new Intent(LiveAccountCenterActivity.this, LiveMobileBindActivity.class));
                }
            });
        }
    }

    private void setViewAttribute(CharSequence content, int resId, CharSequence grade)
    {
        SDViewBinder.setTextView(tv_band_mobilephone, content);
        iv_account_safe.setImageResource(resId);
        SDViewBinder.setTextView(tv_safe_grade, grade);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("账号与安全");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }
}
