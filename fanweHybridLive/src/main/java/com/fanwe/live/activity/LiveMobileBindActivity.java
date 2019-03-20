package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDSendValidateButton;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_ProfitBindingActModel;
import com.fanwe.live.model.App_send_mobile_verifyActModel;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by HSH on 2016/7/19.
 */
public class LiveMobileBindActivity extends LiveMobileBindBaseActivity
{
    @ViewInject(R.id.et_mobile)
    private EditText et_mobile;
    @ViewInject(R.id.et_code)
    private EditText et_code;
    @ViewInject(R.id.btn_send_code)
    private SDSendValidateButton btn_send_code;

    @ViewInject(R.id.tv_mobile_bind)
    private TextView tv_mobile_bind;

    protected String strMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_mobile_bind);
        init();
        initSDSendValidateButton();
        register();
    }

    private void init()
    {
        initTitle();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("手机绑定");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    private void register()
    {
        tv_mobile_bind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickMobileBind();
            }
        });
    }

    private void clickMobileBind()
    {
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code))
        {
            SDToast.showToast("请输入验证码");
            return;
        }
        requestMobileBind(code);
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

    private void requestSendCode()
    {
        strMobile = et_mobile.getText().toString();

        if (TextUtils.isEmpty(strMobile))
        {
            SDToast.showToast("请输入手机号码");
            return;
        }

        CommonInterface.requestSendMobileVerify(1, strMobile, null, new AppRequestCallback<App_send_mobile_verifyActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    btn_send_code.setmDisableTime(actModel.getTime());
                    btn_send_code.startTickWork();
                }
            }
        });
    }

    @Override
    protected void requestMobileBind(String code)
    {
        CommonInterface.requestMobileBind(strMobile, code, new AppRequestCallback<App_ProfitBindingActModel>()
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
        finish();
    }
}
