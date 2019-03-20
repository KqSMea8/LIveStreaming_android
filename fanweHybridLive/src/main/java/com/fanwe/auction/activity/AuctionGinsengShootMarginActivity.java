package com.fanwe.auction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.EGinsengShootMarginSuccess;
import com.fanwe.auction.model.Pai_userDojoinActModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by HSH on 2016/8/8.
 */
public class AuctionGinsengShootMarginActivity extends BaseTitleActivity
{
    /***
     * 竞拍商品id
     */
    public final static String EXTRA_PAI_ID = "extra_pai_id";

    /***
     * 商品保证金
     */
    public final static String EXTRA_PAI_BOND = "extra_pai_bond";

    @ViewInject(R.id.tv_bond)
    private TextView tv_bond;

    @ViewInject(R.id.tv_balance)
    private TextView tv_balance;

    @ViewInject(R.id.btn_recharge)
    private Button btn_recharge;

    @ViewInject(R.id.cet_contacts)
    private ClearEditText cet_contacts;

    @ViewInject(R.id.cet_phone)
    private ClearEditText cet_phone;

    @ViewInject(R.id.btn_determine)
    private Button btn_determine;

    // 虚拟商品布局
    @ViewInject(R.id.ll_virtual_goods)
    private LinearLayout ll_virtual_goods;

    @ViewInject(R.id.ll_agreement)
    private LinearLayout ll_agreement;

    /***
     * 竞拍商品id
     */
    private int pai_id;

    private String contacts;
    private String phone;
    private long bond;
    private long balance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ginseng_shoot_margin);
        init();
    }

    private void init()
    {
        initTitle();
        getIntentData();
//        requestData();
        register();
    }

    private void getIntentData()
    {
        pai_id = getIntent().getIntExtra(EXTRA_PAI_ID, 0);
        bond = getIntent().getIntExtra(EXTRA_PAI_BOND, 0);

        if (pai_id == 0)
        {
            SDToast.showToast("pai_id==0");
        }

        if (bond >= 0L)
        {
            tv_bond.setText("" + bond + "秀豆");
        }
    }

    private void requestData()
    {
        CommonInterface.requestUserInfo(null, null, new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    UserModel user = actModel.getUser();

                    balance = user.getDiamonds();

                    tv_balance.setText("" + balance + "秀豆");
                }
            }
        });
    }


    private void editVerification()
    {
        contacts = cet_contacts.getText().toString();
        phone = cet_phone.getText().toString();

        if (!TextUtils.isEmpty(contacts) && !TextUtils.isEmpty(phone) && balance >= bond)
        {
            requestDetermine();
        } else
        {
            if (TextUtils.isEmpty(contacts))
            {
                SDToast.showToast("请填写联系人");
            } else if (TextUtils.isEmpty(phone))
            {
                SDToast.showToast("请填写联系电话");
            } else if (balance < bond)
            {
                SDToast.showToast("余额不足，请先充值");
            }
        }
    }

    private void requestDetermine()
    {
        AuctionCommonInterface.requestPai_userDojoin(pai_id, contacts, phone,"","",new AppRequestCallback<Pai_userDojoinActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    //缴纳保证金后刷新余额
                    CommonInterface.requestMyUserInfo(null);
                    SDEventManager.post(new EGinsengShootMarginSuccess());
                    finish();
                }

            }
        });
    }

    private void register()
    {
        btn_recharge.setOnClickListener(this);
        btn_determine.setOnClickListener(this);
        ll_agreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == btn_recharge)
        {
            clickRecharge();
        } else if (v == btn_determine)
        {
            clickDetermine();
        } else if (v == ll_agreement)
        {
            clickAgreement();
        }
    }

    private void clickAgreement()
    {
        Intent intent = new Intent(this, LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_auction_agreement());
        startActivity(intent);
    }


    private void clickRecharge()
    {
        Intent intent = new Intent(this, LiveRechargeDiamondsActivity.class);
        startActivity(intent);
    }


    private void clickDetermine()
    {
        editVerification();
    }

    @Override
    protected void onResume()
    {
        requestData();
        super.onResume();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("参拍交保证金");
    }
}
