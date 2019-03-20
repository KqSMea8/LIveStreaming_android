package com.fanwe.auction.dialog;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.auction.AuctionBusiness;
import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 竞拍成功付款对话框
 * Created by Administrator on 2016/8/24.
 */
public class AuctionSucPayDialog extends AuctionBaseDialog
{
    @ViewInject(R.id.iv_goods_pic)
    private ImageView iv_goods_pic;//商品图片
    @ViewInject(R.id.txv_goods_name)
    private TextView txv_goods_name;//商品名称
    @ViewInject(R.id.txv_price)
    private TextView txv_price;//商品价格
    @ViewInject(R.id.txv_surplus)
    private TextView txv_surplus;//倒计时
    @ViewInject(R.id.rel_chongzhi)
    private RelativeLayout rel_chongzhi;
    @ViewInject(R.id.txv_recharge)
    private TextView txv_recharge;//充值
    @ViewInject(R.id.txv_pay)
    private TextView txv_pay;//付款

    private PaiBuyerModel buyerModel;


    public AuctionSucPayDialog(Activity activity, AuctionBusiness auctionBusiness)
    {
        super(activity, auctionBusiness);
        this.buyerModel = getAuctionBusiness().getCurrentBuyer();
        init();
    }

    public void onAuctionPayRemaining(PaiBuyerModel buyer, long day, long hour, long min, long sec)
    {
        if (min <= 0 && sec <= 0)
        {
            txv_surplus.setText("已超时付款");
        } else
        {
            txv_surplus.setText(Long.toString(min) + "分钟" + Long.toString(sec) + "秒    内需付款");
        }
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_suc_pay);
        paddings(0);
        x.view().inject(this, getContentView());
        register();
        bindData();
    }

    private void register()
    {
        txv_pay.setOnClickListener(this);
        rel_chongzhi.setOnClickListener(this);
    }

    private void bindData()
    {
        UserModel user = UserModelDao.query();
        if (buyerModel != null && user != null)
        {
            long diamonds = user.getDiamonds();

            GlideUtil.load(buyerModel.getGoods_icon()).into(iv_goods_pic);
            SDViewBinder.setTextView(txv_goods_name, buyerModel.getGoods_name());
            SDViewBinder.setTextView(txv_price, buyerModel.getPai_diamonds());
            SDViewBinder.setTextView(txv_recharge, Long.toString(diamonds));
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch ((v.getId()))
        {
            case R.id.rel_chongzhi:
                clickTxVChongzhi();
                break;
            case R.id.txv_pay:
                clickTxvPay();
                break;
        }
    }

    private void clickTxVChongzhi()
    {
        Intent intent = new Intent(getOwnerActivity(), LiveRechargeDiamondsActivity.class);
        getOwnerActivity().startActivity(intent);
    }

    private void clickTxvPay()
    {
        requestPaiUserPayDiamonds();
    }

    private void requestPaiUserPayDiamonds()
    {
        if (buyerModel != null)
        {
            String order_sn = buyerModel.getOrder_sn();
            if (TextUtils.isEmpty(order_sn))
            {
                SDToast.showToast("order_sn为空");
                return;
            }
            AuctionCommonInterface.requestPaiUserPayDiamonds(order_sn, new AppRequestCallback<BaseActModel>()
            {
                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        //付款成功刷新余额
                        CommonInterface.requestMyUserInfo(null);
                        AuctionSucPayDialog.this.dismiss();
                    }
                }
            });
        }
    }
}
