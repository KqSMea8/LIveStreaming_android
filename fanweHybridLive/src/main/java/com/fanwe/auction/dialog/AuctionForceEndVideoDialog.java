package com.fanwe.auction.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

import org.xutils.x;

/**
 * Created by Administrator on 2016/10/17.
 */

public class AuctionForceEndVideoDialog extends SDDialogBase
{
    public AuctionForceEndVideoDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_auction_force_end_video);
        paddings(0);
        x.view().inject(this, getContentView());
    }

    @Override
    public void show()
    {
        startDismissRunnable(5 * 1000);
        super.show();
    }

    @Override
    public void dismiss()
    {
        super.dismiss();

    }
}
