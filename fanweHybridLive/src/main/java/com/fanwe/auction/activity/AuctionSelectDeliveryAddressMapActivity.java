package com.fanwe.auction.activity;

import android.os.Bundle;

import com.fanwe.auction.event.ESelectDeliveryAddressSuccessData;
import com.fanwe.auction.fragment.SelectMapLocationFragment;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.live.R;
import com.sunday.eventbus.SDEventManager;
import com.tencent.map.geolocation.TencentLocation;

/**
 * 选择位置(收货地址界面发起的)
 *
 * @author Administrator
 */
public class AuctionSelectDeliveryAddressMapActivity extends BaseTitleActivity
{

    /**
     * 默认纬度 (ypoint)(double)
     */
    public static final String EXTRA_LAT_DEFAULT = "extra_lat_default";
    /**
     * 默认经度 (xpoint)(double)
     */
    public static final String EXTRA_LNG_DEFAULT = "extra_lng_default";

    private SelectMapLocationFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_container);
        init();
    }

    private void init()
    {
        initTitle();
        addFrament();
    }

    private void addFrament()
    {
        mFragment = new SelectMapLocationFragment();
        mFragment.setmLatDefault(getIntent().getDoubleExtra(EXTRA_LAT_DEFAULT, 0));
        mFragment.setmLngDefault(getIntent().getDoubleExtra(EXTRA_LNG_DEFAULT, 0));

        mFragment.setmListenerSelectLocation(new SelectMapLocationFragment.SelectLocationListener()
        {

            @Override
            public void onSelected(TencentLocation location, double lat, double lng, String address)
            {
                ESelectDeliveryAddressSuccessData data = new ESelectDeliveryAddressSuccessData();
                data.lat = lat;
                data.lng = lng;
                data.address = address;
                data.location = location;
                SDEventManager.post(data);
                finish();
            }
        });

        getSDFragmentManager().replace(R.id.view_container_fl_content, mFragment);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("选择位置");
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("我的位置");
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        if (mFragment != null)
        {
            mFragment.startLocation(true);
        }
    }
}
