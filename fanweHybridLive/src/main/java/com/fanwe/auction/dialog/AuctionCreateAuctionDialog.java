package com.fanwe.auction.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.activity.AuctionCreateEmptyActivity;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

/**
 * Created by shibx on 2016/8/16.
 */
public class AuctionCreateAuctionDialog extends SDDialogBase{

    private String createId;
    private AuctionRealGoodsDialog dialog;

    public AuctionCreateAuctionDialog(Activity activity) {
        super(activity);
    }

    public AuctionCreateAuctionDialog(Activity activity,int showVirtual,int showReal,String id) {
        super(activity);
        createId = id;
        initView(showVirtual,showReal);
    }

    private void initView(int showVirtual,int showReal) {
        setContentView(R.layout.dialog_create_auction);
        setCanceledOnTouchOutside(true);
        paddingLeft(0);
        paddingRight(0);
        paddingBottom(0);
        LinearLayout ll_real = (LinearLayout) findViewById(R.id.ll_real);
        LinearLayout ll_virtual = (LinearLayout) findViewById(R.id.ll_virtual);
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        ll_real.setOnClickListener(this);
        ll_virtual.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        if(showVirtual != 1) {
            ll_virtual.setVisibility(View.GONE);
        }
        if(showReal != 1) {
            ll_real.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()) {
            case R.id.ll_real :
//                openCreateAuctionAct(1);
                dialog = new AuctionRealGoodsDialog(getOwnerActivity(), createId);
                dialog.showBottom();
                dismiss();
                //显示实物商品列表
                break;
            case R.id.ll_virtual :
                openCreateAuctionAct(0);
                break;
            case R.id.tv_cancel :
                this.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 打开创建竞拍界面
     * @param viewFlag 0--虚拟竞拍 1--实物竞拍 2--新增购物商品
     */
    private void openCreateAuctionAct(int viewFlag) {
        Intent intent = new Intent(getOwnerActivity(),AuctionCreateEmptyActivity.class);
        intent.putExtra(AuctionCreateEmptyActivity.EXTRA_VIEW_FLAG,viewFlag);
        getOwnerActivity().startActivity(intent);
        dismiss();
    }
}
