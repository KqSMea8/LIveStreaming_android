package com.fanwe.auction.dialog;

import android.app.Activity;
import android.widget.TextView;

import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionPaySuccess;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionSuccess;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 支付成功对话框
 * Created by Administrator on 2016/10/25.
 */

public class AuctionResultsPaySucDialog extends SDDialogBase
{
    @ViewInject(R.id.tv_name)
    private TextView tv_name;//竞拍成功观众姓名
    @ViewInject(R.id.tv_price)
    private TextView tv_price;//竞拍最后成交金额

    /**
     * 支付成功
     */
    private CustomMsgAuctionPaySuccess customMsgAuctionPaySuccess;

    private boolean isBelongToMe = false;

    /**
     * 竞拍成功
     *
     * @param activity
     * @param customMsgAuctionPaySuccess
     */
    public AuctionResultsPaySucDialog(Activity activity, CustomMsgAuctionPaySuccess customMsgAuctionPaySuccess)
    {
        super(activity);
        this.customMsgAuctionPaySuccess = customMsgAuctionPaySuccess;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_pay_suc);
        x.view().inject(this, getContentView());

        UserModel dao = UserModelDao.query();
        if (customMsgAuctionPaySuccess.getBuyer() != null && customMsgAuctionPaySuccess.getBuyer().size() > 0)
        {
            for (int i = 0; i < customMsgAuctionPaySuccess.getBuyer().size(); i++)
            {
                if (customMsgAuctionPaySuccess.getBuyer().get(i).getType() == 4)//4 付款完成
                {
                    tv_name.setText(customMsgAuctionPaySuccess.getBuyer().get(i).getNick_name());
                    tv_price.setText(customMsgAuctionPaySuccess.getBuyer().get(i).getPai_diamonds());
                    if (customMsgAuctionPaySuccess.getBuyer().get(i).getUser_id().equals(dao.getUser_id()))
                    {
                        isBelongToMe = true;
                    }
                    break;
                }
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
