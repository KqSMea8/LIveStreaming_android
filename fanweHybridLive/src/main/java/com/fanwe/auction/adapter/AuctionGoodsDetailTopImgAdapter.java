package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
public class AuctionGoodsDetailTopImgAdapter extends SDPagerAdapter<String>
{
    public AuctionGoodsDetailTopImgAdapter(List<String> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public View getView(final ViewGroup container, final int position)
    {
        View view = inflate(R.layout.item_live_tab_hot_banner_pager, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_image);

        final String url = getData(position);
        GlideUtil.load(url).into(iv);

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, url, v);
            }
        });

        return view;
    }
}
