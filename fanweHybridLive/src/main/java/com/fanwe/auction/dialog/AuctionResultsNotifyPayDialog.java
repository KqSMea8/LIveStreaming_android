package com.fanwe.auction.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.AuctionBusiness;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionNotifyPay;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 竞拍通知付款对话框
 * Created by Administrator on 2016/8/16.
 */
public class AuctionResultsNotifyPayDialog extends AuctionBaseDialog
{

    private boolean isBelongToMe = false;

    @ViewInject(R.id.img_act_result)
    private ImageView img_act_result;//竞拍成功或失败图片
    @ViewInject(R.id.ll_name)
    private LinearLayout ll_name;
    @ViewInject(R.id.ll_pay)
    private LinearLayout ll_pay;
    @ViewInject(R.id.ll_act_fail)
    private LinearLayout ll_act_fail;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;//竞拍成功观众姓名
    @ViewInject(R.id.tv_price)
    private TextView tv_price;//竞拍最后成交金额
    @ViewInject(R.id.tv_pay)
    private TextView tv_pay;//竞拍支付
    @ViewInject(R.id.tv_act_fail)
    private TextView tv_act_fail;//竞拍失败提示语

    /**
     * 竞拍通知付款，比如第一名超时未付款，通知下一名付款
     */
    private CustomMsgAuctionNotifyPay customMsgAuctionNotifyPay;

    /**
     * 竞拍通知付款，比如第一名超时未付款，通知下一名付款
     *
     * @param activity
     * @param customMsgAuctionNotifyPay
     */
    public AuctionResultsNotifyPayDialog(Activity activity, CustomMsgAuctionNotifyPay customMsgAuctionNotifyPay, AuctionBusiness auctionBusiness)
    {
        super(activity, auctionBusiness);
        this.customMsgAuctionNotifyPay = customMsgAuctionNotifyPay;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_results);
        paddings(0);
        x.view().inject(this, getContentView());

        UserModel dao = UserModelDao.query();
        for (int i = 0; i < customMsgAuctionNotifyPay.getBuyer().size(); i++)
        {
            if (dao.getUser_id().equals(customMsgAuctionNotifyPay.getBuyer().get(i).getUser_id()) &&
                    customMsgAuctionNotifyPay.getBuyer().get(i).getType() == 1)
            {//0 出局 1待付款 2排队中 3超时出局 4 付款完成
                final PaiBuyerModel buyerModel = customMsgAuctionNotifyPay.getBuyer().get(i);
                img_act_result.setImageResource(R.drawable.ic_auction_suc);
                SDViewUtil.setGone(ll_name);
                SDViewUtil.setVisible(ll_pay);
                SDViewUtil.setGone(ll_act_fail);
                tv_pay.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        getAuctionBusiness().clickAuctionPay(view);
                    }
                });
                isBelongToMe = true;
                break;
            } else if (dao.getUser_id().equals(customMsgAuctionNotifyPay.getBuyer().get(i).getUser_id()) &&
                    customMsgAuctionNotifyPay.getBuyer().get(i).getType() == 3)
            {
                img_act_result.setImageResource(R.drawable.ic_auction_fail);
                SDViewUtil.setGone(ll_name);
                SDViewUtil.setGone(ll_pay);
                SDViewUtil.setVisible(ll_act_fail);
                tv_act_fail.setText("您参与的竞拍超时未付款");
                isBelongToMe = true;
                break;
            } else if (customMsgAuctionNotifyPay.getBuyer().get(i).getType() == 1)
            {
                img_act_result.setImageResource(R.drawable.ic_auction_suc);
                SDViewUtil.setVisible(ll_name);
                SDViewUtil.setGone(ll_pay);
                SDViewUtil.setGone(ll_act_fail);
                tv_name.setText(customMsgAuctionNotifyPay.getBuyer().get(i).getNick_name());
                tv_price.setText(customMsgAuctionNotifyPay.getBuyer().get(i).getPai_diamonds());
                break;
            }
        }
    }

    @Override
    public void show()
    {
        if (!isBelongToMe)
        {
            startDismissRunnable(3 * 1000);
        }
        super.show();
    }
}
