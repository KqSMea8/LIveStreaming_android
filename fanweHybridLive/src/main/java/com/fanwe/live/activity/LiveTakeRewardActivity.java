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
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/11/28.
 */

public class LiveTakeRewardActivity extends BaseTitleActivity implements TextWatcher{

    @ViewInject(R.id.tv_useable_ticket)
    private TextView tv_useable_ticket;
    @ViewInject(R.id.tv_total_available)
    private TextView tv_total_available;
    @ViewInject(R.id.et_reward_num)
    private EditText et_reward_num;
    @ViewInject(R.id.tv_do_submit)
    private TextView tv_do_submit;
    @ViewInject(R.id.tv_money)
    private TextView tv_money;

    private String total_tickets;
    private String useable_ticket;
    private String ratio;
    private int type;
    /**
     * 今日可兑换秀豆额度
     */
    public static final String EXTRA_TOTAL_TICKET = "extra_total_ticket";
    //可用秀豆
    public static final String EXTRA_USEABLE_TICKET = "extra_available_ticket";
    public static final String EXTRA_RATIO = "extra_ratio";
    public static final String EXTRA_TYPE = "type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_take_reward);
        initTitle();
        getExtraDatas();
        initView();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop("提现");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
    }

    private void getExtraDatas() {
        useable_ticket = getIntent().getStringExtra(EXTRA_USEABLE_TICKET);
        tv_useable_ticket.setText(useable_ticket);
        total_tickets = getIntent().getStringExtra(EXTRA_TOTAL_TICKET);
        tv_total_available.setText(total_tickets);
        ratio = getIntent().getStringExtra(EXTRA_RATIO);
        type = getIntent().getIntExtra(EXTRA_TYPE,0);
    }

    private void initView() {
        tv_do_submit.setOnClickListener(this);
        et_reward_num.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_do_submit :
                doCommit();
                break;
            default:
                break;
        }
    }

    private void doCommit() {
        String ticket = et_reward_num.getText().toString();
        if(TextUtils.isEmpty(ticket)) {
            SDToast.showToast("请填写提现数量");
            return ;
        }
        if(type==1){
            CommonInterface.requestSubmitRefundAlipay(ticket, new AppRequestCallback<BaseActModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if(actModel.isOk()) {
                        SDToast.showToast("已提交审核");
                        finish();
                    }else {
                        SDToast.showToast(actModel.getError());
                    }
                }
            });
        }else if(type==3){
            CommonInterface.requestSubmitRefundBankCard(ticket, new AppRequestCallback<BaseActModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if(actModel.isOk()) {
                        SDToast.showToast("已提交审核");
                        finish();
                    }else {
                        SDToast.showToast(actModel.getError());
                    }
                }
            });
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = s.toString();

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if(!TextUtils.isEmpty(str)) {
            if(Long.parseLong(str) > Long.parseLong(total_tickets)) {
                et_reward_num.setText(total_tickets);
                et_reward_num.setSelection(et_reward_num.getText().toString().length());
            } else if(Long.parseLong(str) > Long.parseLong(useable_ticket)) {
                et_reward_num.setText(useable_ticket);
                et_reward_num.setSelection(et_reward_num.getText().toString().length());
            } else {
                double money = SDNumberUtil.multiply(Long.parseLong(str), Double.valueOf(ratio), 2);//格式化， 保留两位小数
                if(money != 0) {
                    SDViewUtil.setVisible(tv_money);
                    tv_money.setText(String.valueOf(money) + "元");
                } else {
                    SDViewUtil.setInvisible(tv_money);
                }
            }

        } else {
            SDViewUtil.setInvisible(tv_money);
        }
    }
}
