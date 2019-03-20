package com.fanwe.auction.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.auction.adapter.SearchSuggestionAdapter;
import com.fanwe.auction.pop.InputSuggestionPop;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject.ReverseAddressResult;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.lbssearch.object.result.SuggestionResultObject.SuggestionData;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择地图位置
 *
 * @author Administrator
 */
public class SelectMapLocationFragment extends BaseTencentMapFragment
{
    @ViewInject(R.id.et_search)
    private EditText mEt_search;

    @ViewInject(R.id.tv_address)
    private TextView mTv_address;

    @ViewInject(R.id.tv_done)
    private TextView mTv_done;

    private InputSuggestionPop mPopSuggestion;

    private List<SuggestionData> mListModel = new ArrayList<SuggestionData>();
    private SearchSuggestionAdapter mAdapter;

    private boolean mIsMoving = true;
    private double mLatDefault;
    private double mLngDefault;

    private SelectLocationListener mListenerSelectLocation;

    public void setmListenerSelectLocation(SelectLocationListener mListenerSelectLocation)
    {
        this.mListenerSelectLocation = mListenerSelectLocation;
    }

    public void setmLatDefault(double mLatDefault)
    {
        this.mLatDefault = mLatDefault;
    }

    public void setmLngDefault(double mLngDefault)
    {
        this.mLngDefault = mLngDefault;
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_select_map_location;
    }

    @Override
    protected void init()
    {
        if (mLatDefault != 0 && mLngDefault != 0)
        {
            focusMapTo(new LatLng(mLatDefault, mLngDefault));
        } else
        {
            startLocation(true);
        }

        initInputSuggestionView();
        registerClick();
    }

    private void registerClick()
    {
        mTv_done.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (!mIsMoving)
                {
                    LatLng ll = getLatLngCenter();
                    TencentLocation location = getLocationCurrent();
                    double lat = ll.getLatitude();
                    double lng = ll.getLongitude();
                    String address = mTv_address.getText().toString();

                    if (mListenerSelectLocation != null)
                    {
                        mListenerSelectLocation.onSelected(location, lat, lng, address);
                    }
                }
            }
        });


    }

    private void initInputSuggestionView()
    {
        mPopSuggestion = new InputSuggestionPop();
        mPopSuggestion.setEditText(mEt_search);
        mPopSuggestion.setmListenerTextChanged(new InputSuggestionPop.InputSuggestionView_TextChangedListener()
        {

            @Override
            public void afterTextChanged(Editable s)
            {
                String content = s.toString();
                searchSuggesstion(content);
            }

        });
        mPopSuggestion.setmListenerOnItemClick(new InputSuggestionPop.InputSuggestionView_OnItemClickListener()
        {

            @Override
            public void onItemClick(int position)
            {
                // TODO 保存当前经纬度
                SuggestionData model = mAdapter.getItem(position);
                if (model != null)
                {
                    LatLng ll = SDTencentMapManager.getInstance().locationToLatLng(model.location);
                    selectLocation(ll);
                }
            }
        });
        mAdapter = new SearchSuggestionAdapter(mListModel, getActivity());
        mPopSuggestion.setAdapter(mAdapter);
    }

    private void searchSuggesstion(String content)
    {
        if (!TextUtils.isEmpty(content))
        {
            getGeoCode().region(AppRuntimeWorker.getCity_name()).keyword(content).suggestion(new SDTencentGeoCode.SuggestionListener()
            {

                @Override
                public void onSuccess(SuggestionResultObject result)
                {
                    List<SuggestionData> listModel = result.data;
                    mAdapter.updateData(listModel);
                }

                @Override
                public void onFailure(String msg)
                {

                }
            });
        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition)
    {
        mIsMoving = false;
        selectLocation(getLatLngCenter());
        super.onCameraChangeFinish(cameraPosition);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition)
    {
        mIsMoving = true;
        super.onCameraChange(cameraPosition);
    }

    private void selectLocation(final LatLng ll)
    {
        if (ll != null)
        {
            getGeoCode().location(ll).geo2address(new SDTencentGeoCode.Geo2addressListener()
            {

                @Override
                public void onSuccess(ReverseAddressResult result)
                {
                    if (result.formatted_addresses != null)
                    {
                        focusMapTo(ll);
                        mTv_address.setText(result.formatted_addresses.recommend);
                    }
                }

                @Override
                public void onFailure(String msg)
                {
                    SDToast.showToast("经纬度解析失败");
                }
            });
        }
    }

    public interface SelectLocationListener
    {
        public void onSelected(TencentLocation location, double lat, double lng, String address);
    }
}
