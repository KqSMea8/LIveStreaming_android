package com.fanwe.auction.dialog;

import android.app.Activity;
import android.widget.TextView;

import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionFail;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 竞拍失败（流拍）对话框
 * Created by Administrator on 2016/10/25.
 */

public class AuctionResultsFailDialog extends SDDialogBase
{
    @ViewInject(R.id.tv_act_fail)
    private TextView tv_act_fail;//竞拍失败提示语

    /**
     * 流拍
     */
    private CustomMsgAuctionFail customMsgAuctionFail;

    /**
     * 流拍
     *
     * @param activity
     * @param customMsgAuctionFail
     */
    public AuctionResultsFailDialog(Activity activity, CustomMsgAuctionFail customMsgAuctionFail)
    {
        super(activity);
        this.customMsgAuctionFail = customMsgAuctionFail;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_fail);
        x.view().inject(this, getContentView());

        if (customMsgAuctionFail.getOut_type() == 0)//竞拍类型 0无人出价 1无人付款
            tv_act_fail.setText("无人出价");
        else if (customMsgAuctionFail.getOut_type() == 1)
            tv_act_fail.setText("中拍者超时未付款");
    }
}
