package com.fanwe.live.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveShopDetailDaysAdapter;
import com.fanwe.live.adapter.LiveShouhuDaysAdapter;
import com.fanwe.live.adapter.LiveShouhuTypeAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.PayShouhuModel;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class LiveShopDetailDialog extends SDDialogBase implements LiveShopDetailDaysAdapter.OnDaysViewClickListener{

    private GridView lv_days;
    private ImageView iv_image;
    private TextView tv_name;
    private TextView tv_endtime;
    private TextView tv_price;
    private LiveShopDetailDaysAdapter mAdapterDays;
    private Button btn_buy;
    ShopModel.MountRuleListBean bean;
    public LiveShopDetailDialog(Activity activity, ShopModel.MountRuleListBean bean) {
        super(activity);
        this.bean=bean;
        this.activity=activity;
        init();
    }
    public LiveShopDetailDialog(Activity activity) {
        super(activity);
        this.activity=activity;
        init();
    }

    public void updateDimands(String dimands) {
        tv_price.setText(dimands);
    }

    public void updataData(ShopModel.MountRuleListBean bean){
        this.bean=bean;
        initData();
        mAdapterDays.updateData(bean.getRules());
    }
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;
    private void init() {
        setContentView(R.layout.dialog_shop_detail);
        paddings(0);
        lv_days = (GridView) findViewById(R.id.lv_days);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        tv_price = (TextView) findViewById(R.id.tv_price);
        btn_buy = (Button) findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPay();
            }
        });
        setCanceledOnTouchOutside(true);
        initGridView();
        initData();
    }

    private void initGridView() {
        mAdapterDays = new LiveShopDetailDaysAdapter(bean.getRules(),getActivity());
        mAdapterDays.setOnDaysViewClickListener(this);
        lv_days.setAdapter(mAdapterDays);
    }

    private void initData() {
        for(ShopModel.MountRuleListBean.RulesBean rulesBean:bean.getRules()){
            rulesBean.setIspk(false);
        }
        SDViewBinder.setTextView(tv_name,bean.getName());
        GlideUtil.load(bean.getIcon()).into(iv_image);
        SDViewBinder.setTextView(tv_endtime,"有效时间:"+bean.getRules().get(0).getEnd_time_desc());
        tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRechargeDialog();
            }
        });
        if(bean.getRules().size()>0){
            current=bean.getRules().get(0);
            bean.getRules().get(0).setIspk(true);
        }
        mAdapterDays.updateData(bean.getRules());
    }
    private LiveRechargeDialog mLiveRechargeDialog;
    List<PayShouhuModel.GuardRuleListBean.RulesBean> listdays;
    private void clickRechargeDialog()
    {
        if (mLiveRechargeDialog == null)
        {
            mLiveRechargeDialog = new LiveRechargeDialog(getOwnerActivity());
        }
        mLiveRechargeDialog.showCenter();
    }
    private void requestPay()
    {
        CommonInterface.requestShopPay(current.getMount_id(),current.getId() , new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    Toast.makeText(getActivity(),"开通成功！",Toast.LENGTH_SHORT).show();
                    shopBuyListener.buyShopSuccess();
                    dismiss();
                }
            }
        });
    }
    ShopModel.MountRuleListBean.RulesBean current;
    @Override
    public void onClickDaysView(ShopModel.MountRuleListBean.RulesBean model) {
        SDViewBinder.setTextView(tv_endtime,"有效时间:"+model.getEnd_time_desc());
        if(!model.getId().equals(current.getId())){
            for(ShopModel.MountRuleListBean.RulesBean rulesBean:bean.getRules()){
                rulesBean.setIspk(false);
            }
            model.setIspk(true);
            current=model;
            mAdapterDays.updateData(bean.getRules());
        }
    }
    private ShopBuyListener shopBuyListener;

    public ShopBuyListener getShopBuyListener() {
        return shopBuyListener;
    }

    public void setShopBuyListener(ShopBuyListener shopBuyListener) {
        this.shopBuyListener = shopBuyListener;
    }

    public interface ShopBuyListener{
        void buyShopSuccess();
    }
}
