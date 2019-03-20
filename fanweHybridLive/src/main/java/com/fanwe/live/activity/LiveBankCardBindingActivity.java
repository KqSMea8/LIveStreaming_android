package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.fanwe.live.utils.BankInfo;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/11/28.
 * 绑定支付宝界面
 */

public class LiveBankCardBindingActivity extends BaseTitleActivity{

    @ViewInject(R.id.et_card_account)
    private EditText et_card_account;
    @ViewInject(R.id.et_card_nickname)
    private EditText et_card_nickname;
    @ViewInject(R.id.et_bank_name)
    private EditText et_bank_name;
    @ViewInject(R.id.et_card_branchname)
    private EditText et_card_branchname;
    @ViewInject(R.id.tv_do_submit)
    private TextView tv_do_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_banding_bankcard);
        initTitle();
        initView();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("银行卡绑定");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void initView() {
        tv_do_submit.setOnClickListener(this);
        et_card_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 在输入数据时监听
                int huoqu = et_card_account.getText().toString().length();
                if (huoqu >= 6) {
                    String huoqucc = et_card_account.getText().toString();
                    char[] cardNumber = huoqucc.toCharArray();
                    String name = BankInfo.getNameOfBank(cardNumber, 0);// 获取银行卡的信息
                    et_bank_name.setText(name);
                } else {
                    et_bank_name.setText(null);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        final String account = et_card_account.getText().toString();
        if(TextUtils.isEmpty(account)) {
            SDToast.showToast("请输入银行卡号");
            return ;
        }
        String name = et_card_nickname.getText().toString();
        if(TextUtils.isEmpty(name)) {
            SDToast.showToast("请输入持卡人姓名");
            return ;
        }
        String bank_name = et_bank_name.getText().toString();
        if(TextUtils.isEmpty(bank_name)) {
            SDToast.showToast("请输入开户银行");
            return ;
        }
        String branch_name = et_card_branchname.getText().toString();
        if(TextUtils.isEmpty(branch_name)) {
            SDToast.showToast("请输入支行名称");
            return ;
        }
        CommonInterface.requestBandingBankCard(bank_name,branch_name,account, name, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if(actModel.isOk()) {
                    SDToast.showToast("绑定成功！");
                    finish();
                }else{
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }
}
