package com.fanwe.auction.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.AuctionBusiness;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 竞拍成功对话框
 * Created by Administrator on 2016/10/25.
 */

public class AuctionResultsSucDialog extends AuctionBaseDialog
{
    @ViewInject(R.id.ll_name)
    private LinearLayout ll_name;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;//竞拍成功观众姓名
    @ViewInject(R.id.tv_price)
    private TextView tv_price;//竞拍最后成交金额
    @ViewInject(R.id.ll_pay)
    private LinearLayout ll_pay;
    @ViewInject(R.id.tv_pay)
    private TextView tv_pay;//竞拍支付

    /**
     * 竞拍成功
     */
    private CustomMsgAuctionSuccess customMsgAuctionSuccess;

    private boolean isBelongToMe = false;

    /**
     * 竞拍成功
     *
     * @param activity
     * @param customMsgAuctionSuccess
     */
    public AuctionResultsSucDialog(Activity activity, CustomMsgAuctionSuccess customMsgAuctionSuccess, AuctionBusiness auctionBusiness)
    {
        super(activity, auctionBusiness);
        this.customMsgAuctionSuccess = customMsgAuctionSuccess;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_suc);
        x.view().inject(this, getContentView());

        UserModel dao = UserModelDao.query();
        List<PaiBuyerModel> buyer = customMsgAuctionSuccess.getBuyer();
        if (buyer != null && buyer.size() > 0)
        {
            //竞拍成功取第一条数据如果是自己则显示付款其他显示谁中标了
            final PaiBuyerModel item = buyer.get(0);
            if (dao.getUser_id().equals(item.getUser_id()))
            {
                SDViewUtil.setGone(ll_name);
                SDViewUtil.setVisible(ll_pay);
                tv_pay.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //付款
                        getAuctionBusiness().clickAuctionPay(view);
                    }
                });
                isBelongToMe = true;
            } else
            {
                SDViewUtil.setGone(ll_pay);
                SDViewUtil.setVisible(ll_name);
                tv_name.setText(customMsgAuctionSuccess.getBuyer().get(0).getNick_name());
                tv_price.setText(customMsgAuctionSuccess.getBuyer().get(0).getPai_diamonds());
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
