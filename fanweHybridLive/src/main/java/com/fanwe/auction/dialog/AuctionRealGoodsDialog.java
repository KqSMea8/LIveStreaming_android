package com.fanwe.auction.dialog;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.auction.appview.AuctionRealGoodsDialogView;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 实物竞拍对话框
 * Created by Administrator on 2016/10/11.
 */

public class AuctionRealGoodsDialog extends SDDialogBase
{
    @ViewInject(R.id.ll_auction)
    private LinearLayout ll_auction;
    private AuctionRealGoodsDialogView auctionRealGoodsDialogView;

    private String createrId;//主播Id

    public AuctionRealGoodsDialog(Activity activity, String id) {
        super(activity);
        createrId = id;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_real_goods);
        paddings(0);
        x.view().inject(this, getContentView());
        addPodCastView();
    }

    private void addPodCastView()
    {
        int screenHeight = (SDViewUtil.getScreenHeight() / 2);
        auctionRealGoodsDialogView = new AuctionRealGoodsDialogView(getOwnerActivity(),this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight);
        ll_auction.addView(auctionRealGoodsDialogView, lp);
    }
}
