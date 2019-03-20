package com.fanwe.auction.appview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.adapter.AuctionGoodsDetailRecordAadpter;
import com.fanwe.auction.model.App_pai_user_goods_detailActModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataPaiListItemModel;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.appview.BaseAppView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/8.
 */
public class AuctionGoodsDetailRecordView extends BaseAppView
{
    private App_pai_user_goods_detailActModel actModel;
    private AuctionGoodsDetailRecordAadpter adapter;
    private LinearLayout ll_goods_record;
    private TextView tv_record_num;
    private LinearLayout ll_record;

    public AuctionGoodsDetailRecordView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionGoodsDetailRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionGoodsDetailRecordView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_goods_detail_record);
        register();
    }

    private void register()
    {
        tv_record_num = find(R.id.tv_record_num);
        ll_goods_record = find(R.id.ll_goods_record);
        ll_record = find(R.id.ll_record);

        ll_goods_record.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (actModel.getData() != null && actModel.getData().getInfo() != null)
                {
                    PaiUserGoodsDetailDataInfoModel info = actModel.getData().getInfo();
                    String pai_logs_url = info.getPai_logs_url();
                    if (!TextUtils.isEmpty(pai_logs_url))
                    {
                        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                        intent.putExtra(LiveWebViewActivity.EXTRA_URL, pai_logs_url);
                        getActivity().startActivity(intent);
                    } else
                    {
                        SDToast.showToast("pai_logs_url空");
                    }
                }
            }
        });
    }

    public void bindData(App_pai_user_goods_detailActModel actModel)
    {
        this.actModel = actModel;
        if (actModel.getData() != null && actModel.getData().getPai_list() != null && actModel.getData().getPai_list().size() > 0)
        {
            int rs_count = actModel.getData().getRs_count();
            SDViewBinder.setTextView(tv_record_num, Integer.toString(rs_count));

            List<PaiUserGoodsDetailDataPaiListItemModel> list = actModel.getData().getPai_list();
            adapter = new AuctionGoodsDetailRecordAadpter(list, getActivity());
            int length;
            if (list.size() >= 3)
            {
                length = 3;
            } else
            {
                length = list.size();
            }

            ll_record.removeAllViews();
            for (int i = 0; i < length; ++i)
            {
                View view = adapter.getView(i, null, ll_record);
                if (view != null)
                {
                    ll_record.addView(view);
                }
            }
        }
    }
}
