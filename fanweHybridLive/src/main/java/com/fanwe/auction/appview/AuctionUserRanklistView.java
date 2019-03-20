package com.fanwe.auction.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.auction.adapter.AuctionUserRankAdapter;
import com.fanwe.auction.model.PaiBuyerModel;
import com.fanwe.library.adapter.SDAdapter;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.dialog.LiveUserInfoDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13.
 */
public class AuctionUserRanklistView extends RoomView
{
    private LinearLayout ll_rank_parent;
    private LinearLayout ll_rank_list;
    private AuctionUserRankAdapter adapter;
    private List<PaiBuyerModel> mListBuyers;

    public AuctionUserRanklistView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionUserRanklistView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionUserRanklistView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_user_rank_list);
        register();
    }

    private void register()
    {
        ll_rank_parent = find(R.id.ll_rank_parent);
        ll_rank_list = find(R.id.ll_rank_list);
        adapter = new AuctionUserRankAdapter(mListBuyers, getActivity());
        adapter.setItemClickCallback(new SDItemClickCallback<PaiBuyerModel>()
        {
            @Override
            public void onItemClick(int position, PaiBuyerModel item, View view)
            {
                //查看用户信息
                LiveUserInfoDialog dialog = new LiveUserInfoDialog(getActivity(), item.getUser_id());
                dialog.show();
            }
        });
    }

    public void setBuyers(List<PaiBuyerModel> buyers)
    {
        this.mListBuyers = buyers;
        adapter.setData(buyers);
        addAllBuyers();
    }

    private void addAllBuyers()
    {
        SDViewUtil.setVisible(ll_rank_parent);
        ll_rank_list.removeAllViews();
        for (int i = 0; i < mListBuyers.size(); i++)
        {
            View view = adapter.getView(i, null, ll_rank_list);
            if (view != null)
            {
                ll_rank_list.addView(view);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.setMargins(0, 0, 0, SDViewUtil.px2dp(10));
                view.setLayoutParams(lp);
            }
        }
    }
}
