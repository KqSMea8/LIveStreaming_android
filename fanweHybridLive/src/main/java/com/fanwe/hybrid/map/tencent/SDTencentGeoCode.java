package com.fanwe.hybrid.map.tencent;

import android.content.Context;

import com.fanwe.library.utils.SDToast;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.Address2GeoParam;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.Address2GeoResultObject;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.mapsdk.raster.model.LatLng;


public class SDTencentGeoCode
{

    private TencentSearch mSearch;
    private Context mContext;

    private String keyword;

    private String region;
    private String address;

    private Location location;

    public SDTencentGeoCode(Context context)
    {
        this.mContext = context;
        mSearch = new TencentSearch(context);
    }

    /**
     * 搜索建议
     *
     * @param listener
     */
    public void suggestion(final SuggestionListener listener)
    {
        SuggestionParam param = getSuggestionParam();
        if (param.checkParams())
        {
            mSearch.suggestion(param, new HttpResponseListener()
            {
                @Override
                public void onSuccess(int i, BaseObject obj)
                {
                    if (obj instanceof SuggestionResultObject && obj.isStatusOk())
                    {
                        if (listener != null)
                        {
                            listener.onSuccess((SuggestionResultObject) obj);
                        }
                    } else
                    {
                        if (listener != null)
                        {
                            listener.onFailure("搜索建议失败");
                        }
                    }

                }

                @Override
                public void onFailure(int i, String msg, Throwable throwable)
                {
                    if (listener != null)
                    {
                        listener.onFailure(msg);
                    }
                }
            });
        } else
        {
            SDToast.showToast("搜索建议参数设置失败");
        }

    }

    private SuggestionParam getSuggestionParam()
    {
        SuggestionParam param = new SuggestionParam();
        param.region(region).keyword(keyword);
        return param;
    }

    public SDTencentGeoCode keyword(String keyword)
    {
        this.keyword = keyword;
        return this;
    }

    /**
     * 地址->经纬度
     *
     * @param listener
     */
    public void address2geo(final Address2GeoListener listener)
    {
        Address2GeoParam param = getAddress2GeoParam();
        if (param.checkParams())
        {
            mSearch.address2geo(param, new HttpResponseListener()
            {
                @Override
                public void onSuccess(int i, BaseObject obj)
                {
                    if (obj instanceof Address2GeoResultObject && obj.isStatusOk())
                    {
                        Address2GeoResultObject result = (Address2GeoResultObject) obj;
                        if (listener != null)
                        {
                            listener.onSuccess(result.result);
                        }
                    } else
                    {
                        if (listener != null)
                        {
                            listener.onFailure("地址解析失败");
                        }
                    }
                }

                @Override
                public void onFailure(int i, String msg, Throwable throwable)
                {
                    if (listener != null)
                    {
                        listener.onFailure(msg);
                    }
                }
            });
        } else
        {
            SDToast.showToast("地址解析参数设置失败");
        }
    }

    private Address2GeoParam getAddress2GeoParam()
    {
        Address2GeoParam param = new Address2GeoParam();
        param.region(region).address(address);
        return param;
    }

    public SDTencentGeoCode region(String region)
    {
        this.region = region;
        return this;
    }

    public SDTencentGeoCode address(String address)
    {
        this.address = address;
        return this;
    }

    /**
     * 经纬度->地址
     *
     * @param listener
     */
    public void geo2address(final Geo2addressListener listener)
    {
        Geo2AddressParam param = getGeo2AddressParam();
        if (param.checkParams())
        {
            mSearch.geo2address(param, new HttpResponseListener()
            {

                @Override
                public void onSuccess(int arg0, BaseObject obj)
                {
                    if (obj instanceof Geo2AddressResultObject && obj.isStatusOk())
                    {
                        Geo2AddressResultObject result = (Geo2AddressResultObject) obj;
                        if (listener != null)
                        {
                            listener.onSuccess(result.result);
                        }
                    } else
                    {
                        if (listener != null)
                        {
                            listener.onFailure("经纬度解析失败");
                        }
                    }
                }

                @Override
                public void onFailure(int arg0, String msg, Throwable throwable)
                {
                    if (listener != null)
                    {
                        listener.onFailure(msg);
                    }
                }
            });


        } else
        {
            SDToast.showToast("经纬度解析参数设置失败");
        }
    }

    private Geo2AddressParam getGeo2AddressParam()
    {
        Geo2AddressParam param = new Geo2AddressParam();
        param.location(location);
        return param;
    }

    public SDTencentGeoCode location(Location location)
    {
        this.location = location;
        return this;
    }

    public SDTencentGeoCode location(LatLng location)
    {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        this.location = new Location().lat((float) lat).lng((float) lng);
        return this;
    }

    public interface Geo2addressListener
    {
        public void onSuccess(Geo2AddressResultObject.ReverseAddressResult result);

        public void onFailure(String msg);
    }

    public interface Address2GeoListener
    {
        public void onSuccess(Address2GeoResultObject.Address2GeoResult result);

        public void onFailure(String msg);
    }

    public interface SuggestionListener
    {
        public void onSuccess(SuggestionResultObject result);

        public void onFailure(String msg);
    }

}
