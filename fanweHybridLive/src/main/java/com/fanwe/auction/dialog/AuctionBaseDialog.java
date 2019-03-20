package com.fanwe.auction.dialog;

import android.app.Activity;

import com.fanwe.auction.AuctionBusiness;
import com.fanwe.library.dialog.SDDialogBase;

/**
 * Created by Administrator on 2016/12/1.
 */

public class AuctionBaseDialog extends SDDialogBase
{
    protected AuctionBusiness auctionBusiness;


    public AuctionBaseDialog(Activity activity, AuctionBusiness auctionBusiness)
    {
        super(activity);
        setAuctionBusiness(auctionBusiness);
    }

    public void setAuctionBusiness(AuctionBusiness auctionBusiness)
    {
        this.auctionBusiness = auctionBusiness;
    }

    public AuctionBusiness getAuctionBusiness()
    {
        return auctionBusiness;
    }
}
