package com.fanwe.shop.dialog;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shop.model.custommsg.CustomMsgShopBuySuc;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/12/5.
 */

public class ShopGoodsPurchaseSucDialog extends SDDialogBase
{
    @ViewInject(R.id.ll_buy_creater_tips)
    private LinearLayout ll_buy_creater_tips;//送主播提示
    @ViewInject(R.id.iv_header)
    private CircleImageView iv_header;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_goods_des)
    private TextView tv_goods_des;
    @ViewInject(R.id.iv_goods)
    private ImageView iv_goods;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    @ViewInject(R.id.tv_score)
    private TextView tv_score;
    @ViewInject(R.id.tv_goods)
    private TextView tv_goods;

    public ShopGoodsPurchaseSucDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_shop_purchase_goods_suc);
        paddings(0);
        x.view().inject(this, getContentView());
    }

    public void initData(CustomMsgShopBuySuc customMsg)
    {
        int is_self = customMsg.getIs_self();
        if (is_self == 0)//0表示送主播、1表示送自己
        {
            SDViewUtil.setVisible(ll_buy_creater_tips);
        }else if (is_self == 1)
        {
            SDViewUtil.setGone(ll_buy_creater_tips);
        }

        GlideUtil.loadHeadImage(customMsg.getUser().getHead_image()).into(iv_header);
        SDViewBinder.setTextView(tv_name,customMsg.getUser().getNick_name());
        SDViewBinder.setTextView(tv_goods_des,customMsg.getDesc());

        GlideUtil.load(customMsg.getGoods().getGoods_logo()).into(iv_goods);
        SDViewBinder.setTextView(tv_num,"x" + String.valueOf(customMsg.getGoods().getQuantity()));
        SDViewBinder.setTextView(tv_score,"+" + String.valueOf(customMsg.getScore()));
        SDViewBinder.setTextView(tv_goods,customMsg.getGoods().getGoods_name());
    }
}
