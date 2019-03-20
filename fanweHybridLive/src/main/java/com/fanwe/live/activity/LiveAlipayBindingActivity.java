package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/11/28.
 * 绑定支付宝界面
 */

public class LiveAlipayBindingActivity extends BaseTitleActivity{

    @ViewInject(R.id.et_alipay_account)
    private EditText et_alipay_account;
    @ViewInject(R.id.et_alipay_nickname)
    private EditText et_alipay_nickname;
    @ViewInject(R.id.tv_do_submit)
    private TextView tv_do_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_banding_alipay);
        initTitle();
        initView();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("支付宝绑定");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void initView() {
        tv_do_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()) {
            case R.id.tv_do_submit :
                doCommit();
        }
    }

    private void doCommit() {
        String account = et_alipay_account.getText().toString();
        if(TextUtils.isEmpty(account)) {
            SDToast.showToast("请输入支付宝账号");
            return ;
        }
        String name = et_alipay_nickname.getText().toString();
        if(TextUtils.isEmpty(name)) {
            SDToast.showToast("请输入支付宝账号名称");
            return ;
        }
        CommonInterface.requestBandingAlipay(account, name, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if(actModel.isOk()) {
                    SDToast.showToast("绑定成功！");
                    finish();
                }
            }
        });
    }
}
