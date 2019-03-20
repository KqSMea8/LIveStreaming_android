package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LiveTabHotBannerPagerAdapter extends SDPagerAdapter<LiveBannerModel>
{

    public LiveTabHotBannerPagerAdapter(List<LiveBannerModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public View getView(final ViewGroup container, final int position)
    {
        View view = inflate(R.layout.item_live_tab_hot_banner_pager, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_image);

        final LiveBannerModel model = getData(position);
        GlideUtil.load(model.getImage()).placeholder(0).into(iv);

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, v);
            }
        });

        return view;
    }
}
