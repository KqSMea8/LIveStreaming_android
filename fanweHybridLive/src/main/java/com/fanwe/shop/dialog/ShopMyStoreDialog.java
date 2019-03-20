package com.fanwe.shop.dialog;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.shop.appview.ShopMyStoreView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/11/17.
 */

public class ShopMyStoreDialog extends SDDialogBase
{
    @ViewInject(R.id.ll_pod_cast)
    private LinearLayout ll_pod_cast;
    private ShopMyStoreView auctionShopMystoreView;

    private boolean isCreater;
    private String createrId;//主播Id

    public ShopMyStoreDialog(Activity activity , String id,boolean isCreater) {
        super(activity);
        this.isCreater=isCreater;
        this.createrId = id;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_pod_cast);
        paddings(0);
        x.view().inject(this, getContentView());
        addPodCastView();
    }

    private void addPodCastView()
    {
        int screenHeight = (SDViewUtil.getScreenHeight() / 2);
        auctionShopMystoreView = new ShopMyStoreView(getOwnerActivity(),createrId,this,isCreater);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight);
        ll_pod_cast.addView(auctionShopMystoreView, lp);
    }
}
