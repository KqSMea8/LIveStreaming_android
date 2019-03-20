package com.fanwe.auction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.EGinsengShootMarginSuccess;
import com.fanwe.auction.model.App_address_listActModel;
import com.fanwe.auction.model.Pai_userDojoinActModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19.
 */

public class AuctionRealGoodsGinsengShootMarginActivity extends BaseTitleActivity
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
    private TextView tv_bond;//系统将为您的账户中扣取的金额

    @ViewInject(R.id.tv_balance)
    private TextView tv_balance;//余额

    @ViewInject(R.id.btn_recharge)
    private Button btn_recharge;//充值

    @ViewInject(R.id.btn_add_address)
    private Button btn_add_address;//添加收货地址

    @ViewInject(R.id.rel_address)
    private RelativeLayout rel_address;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;//收货人

    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;//收货人电话

    @ViewInject(R.id.tv_address)
    private TextView tv_address;//收货地址

    @ViewInject(R.id.ll_agreement)
    private LinearLayout ll_agreement;//同意竞拍协议

    @ViewInject(R.id.btn_determine)
    private Button btn_determine;//同意并确认

    /***
     * 竞拍商品id
     */
    private int pai_id;
    private int p = 1;//页码

    private int addressId;//收货地址ID
    private String consignee;//收货人
    private String phone;//电话
    private String address;//地址
    private int is_default;//1为默认地址
    private String province,city,area,lng,lat;
    private long bond;
    private long balance;

    private Map<String ,Object> mapLocation = new HashMap<>();//地理位置参数集合

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_real_goods_ginseng_shoot_margin);
        init();
    }

    @Override
    protected void onResume()
    {
        requestData();
        requestAddressListData();
        super.onResume();
    }

    private void init()
    {
        initTitle();
        getIntentData();
        register();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("参拍交保证金");
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

    /**
     * 获得用户信息
     */
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

    /**
     * 获取收货地址列表
     */
    private void requestAddressListData()
    {
        AuctionCommonInterface.requestAddressList(p, new AppRequestCallback<App_address_listActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getData() != null)
                    {
                        if (actModel.getData().getList().size() != 0)
                        {
                            btn_add_address.setVisibility(View.GONE);
                            rel_address.setVisibility(View.VISIBLE);
                            addressId = actModel.getData().getList().get(0).getId();
                            is_default = actModel.getData().getList().get(0).getIs_default();
                            consignee = actModel.getData().getList().get(0).getConsignee();
                            phone = actModel.getData().getList().get(0).getConsignee_mobile();
                            address = actModel.getData().getList().get(0).getConsignee_address();
                            province = actModel.getData().getList().get(0).getConsignee_district().getProvince();
                            city = actModel.getData().getList().get(0).getConsignee_district().getCity();
                            area = actModel.getData().getList().get(0).getConsignee_district().getArea();
                            lng = actModel.getData().getList().get(0).getConsignee_district().getLng();
                            lat = actModel.getData().getList().get(0).getConsignee_district().getLat();
                            setLocationParams(province,city,area,lng,lat);
                            SDViewBinder.setTextView(tv_name,consignee);
                            SDViewBinder.setTextView(tv_phone,phone);
                            SDViewBinder.setTextView(tv_address,province + city + area + address);
                        }
                        else
                        {
                            btn_add_address.setVisibility(View.VISIBLE);
                            rel_address.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    private void register()
    {
        btn_recharge.setOnClickListener(this);
        btn_determine.setOnClickListener(this);
        ll_agreement.setOnClickListener(this);
        btn_add_address.setOnClickListener(this);
        rel_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_recharge:
                clickRecharge();
                break;
            case R.id.btn_add_address:
                startActivity(new Intent(this,AuctionAddEditAddressActivity.class));
                break;
            case R.id.rel_address:
                Intent intent = new Intent(this,AuctionAddEditAddressActivity.class);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_EDIT,AuctionAddEditAddressActivity.EXTRA_EDIT);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_ADDRESS_ID,addressId);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_CONSIGNEE,consignee);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_PHONE,phone);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_ADDRESS_DETAIL,address);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_PROVINCE,province);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_CITY,city);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_AREA,area);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_LNG,lng);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_LAT,lat);
                intent.putExtra(AuctionAddEditAddressActivity.EXTRA_IS_DEFAULT,is_default);
                startActivity(intent);
                break;
            case R.id.ll_agreement:
                clickAgreement();
                break;
            case R.id.btn_determine:
                clickDetermine();
                break;
        }
    }

    private void clickRecharge()
    {
        Intent intent = new Intent(this, LiveRechargeDiamondsActivity.class);
        startActivity(intent);
    }

    private void clickAgreement()
    {
        Intent intent = new Intent(this, LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_auction_agreement());
        startActivity(intent);
    }

    private void clickDetermine()
    {
        editVerification();
    }

    private void editVerification()
    {
        if (balance >= bond)
        {
            requestDetermine();
        } else
        {
            SDToast.showToast("余额不足，请先充值");
        }
    }

    /**
     * 提交参拍交保证金
     */
    private void requestDetermine()
    {
        AuctionCommonInterface.requestPai_userDojoin(pai_id, consignee, phone, SDJsonUtil.object2Json(mapLocation),address,new AppRequestCallback<Pai_userDojoinActModel>()
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

    private void setLocationParams(String province,String city,String district,String lng ,String lat)
    {
        mapLocation.clear();
        mapLocation.put("province",province);
        mapLocation.put("city",city);
        mapLocation.put("area",district);
        mapLocation.put("lng",lng);
        mapLocation.put("lat",lat);
    }
}
