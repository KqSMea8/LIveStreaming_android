package com.fanwe.shop.adapter.viewholder;

import android.content.Intent;
import android.view.View;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveFloatViewWebViewActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.adapter.viewholder.MsgViewHolder;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.shop.common.ShopCommonInterface;
import com.fanwe.shop.model.App_pai_user_open_goods_urlActModel;
import com.fanwe.shop.model.ShopGoodsDetailModel;
import com.fanwe.shop.model.custommsg.CustomMsgShopPush;

/**
 * Created by Administrator on 2016/10/22.
 */

public class MsgAuctionShopPushSucViewHolder extends MsgViewHolder
{

    public MsgAuctionShopPushSucViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, final CustomMsg customMsg)
    {
        final CustomMsgShopPush msg = (CustomMsgShopPush) customMsg;

        //title
        String title = SDResourcesUtil.getString(R.string.live_msg_shopping_title);
        int titleColor = SDResourcesUtil.getColor(R.color.live_msg_title);
        appendContent(title, titleColor);

        // 内容
        String text = msg.getDesc();
        int textColor = SDResourcesUtil.getColor(R.color.main_color);
        appendContent(text, textColor);

        tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ShopGoodsDetailModel goods = ((CustomMsgShopPush) customMsg).getGoods();
                if (goods != null)
                {
                    int type = goods.getType();
                    if (type == 1)
                    {
                        startNormalActivity(goods.getUrl());
                    } else
                    {
                        if (customMsg.getSender().getUser_id().equals(LiveInformation.getInstance().getCreaterId()))
                        {
                            startFloatViewActivity(goods.getUrl());
                        } else
                        {
                            requestGoodsUrl(goods.getGoods_id(), LiveInformation.getInstance().getCreaterId());
                        }
                    }
                }

            }
        });
    }

    private void requestGoodsUrl(String goods_id, String createrId)
    {
        ShopCommonInterface.requestShopGoodsUrl(String.valueOf(goods_id), createrId, new AppRequestCallback<App_pai_user_open_goods_urlActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    startFloatViewActivity(actModel.getUrl());
                }
            }
        });
    }

    /**
     * 跳转悬浮WebView
     *
     * @param url
     */
    private void startFloatViewActivity(String url)
    {
        Intent intent = new Intent(getAdapter().getActivity(), LiveFloatViewWebViewActivity.class);
        intent.putExtra(LiveFloatViewWebViewActivity.EXTRA_URL, url);
        getAdapter().getActivity().startActivity(intent);
    }

    /**
     * 跳转正常WebView
     *
     * @param url
     */
    private void startNormalActivity(String url)
    {
        Intent intent = new Intent(getAdapter().getActivity(), LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, url);
        getAdapter().getActivity().startActivity(intent);
    }
}
