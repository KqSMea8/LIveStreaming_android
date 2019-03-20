package com.fanwe.auction.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.TencentMap.InfoWindowAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BaseTencentMapFragment extends BaseFragment implements TencentMap.OnInfoWindowClickListener, TencentMap.OnMapLoadedListener,
        TencentMap.OnMapCameraChangeListener, TencentMap.OnMapClickListener, TencentMap.OnMapLongClickListener, TencentMap.OnMarkerClickListener,
        TencentMap.OnMarkerDraggedListener, TencentLocationListener
{

    private MapView mMapView;
    private TencentMap mTencentMap;

    private SDTencentGeoCode mGeoCode;

    private Marker mMarkerLocation;
    private Marker mMarkerLastClick;

    private List<Marker> mListMarker = new ArrayList<Marker>();

    private boolean mAnimateMap;
    private boolean mLocationAllTheTime;

    public SDTencentGeoCode getGeoCode()
    {
        return mGeoCode;
    }

    public MapView getMapView()
    {
        return mMapView;
    }

    public TencentMap getTencentMap()
    {
        return mTencentMap;
    }

    public Marker getMarkerLocation()
    {
        return mMarkerLocation;
    }

    public Marker getMarkerLastClick()
    {
        return mMarkerLastClick;
    }

    public void hideLastClickMarkerInfoWindow()
    {
        if (mMarkerLastClick != null)
        {
            mMarkerLastClick.hideInfoWindow();
        }
    }

    public void setInfoWindowAdapter(InfoWindowAdapter adapter)
    {
        mTencentMap.setInfoWindowAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View parentView = super.onCreateView(inflater, container, savedInstanceState);
        View viewWrapper = wrapperView(parentView);

        return viewWrapper;
    }

    private void createMapView()
    {
        mMapView = new MapView(getActivity());
        mTencentMap = mMapView.getMap();

        mGeoCode = new SDTencentGeoCode(getActivity());

        initMap();
    }

    private void initMap()
    {
        mTencentMap.setZoom(15);

        mTencentMap.setOnMapLoadedListener(this);
        mTencentMap.setOnInfoWindowClickListener(this);
        mTencentMap.setOnMapCameraChangeListener(this);
        mTencentMap.setOnMapClickListener(this);
        mTencentMap.setOnMapLongClickListener(this);
        mTencentMap.setOnMarkerClickListener(this);
        mTencentMap.setOnMarkerDraggedListener(this);
    }

    public void startLocation(boolean animateMap)
    {
        startLocation(animateMap, mLocationAllTheTime);
    }

    public void startLocation(boolean animateMap, boolean locationAllTheTime)
    {
        mAnimateMap = animateMap;
        mLocationAllTheTime = locationAllTheTime;
        SDTencentMapManager.getInstance().startLocation(mLocationAllTheTime, this);
    }

    public void stopLocation()
    {
        SDTencentMapManager.getInstance().unRegisterLocationListener(this);
    }

    public final TencentLocation getLocationCurrent()
    {
        return SDTencentMapManager.getInstance().getLocation();
    }

    public final LatLng getLatLngCenter()
    {
        return mTencentMap.getMapCenter();
    }

    public final LatLng getLatLngCurrent()
    {
        LatLng latLng = null;
        TencentLocation location = getLocationCurrent();
        if (location != null)
        {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return latLng;
    }

    public final LatLng getLatLngTopLeft()
    {
        int x = 0;
        int y = 0;
        LatLng llTopLeft = mMapView.getProjection().fromScreenLocation(new Point(x, y));
        return llTopLeft;
    }

    public final LatLng getLatLngBottomRight()
    {
        int x = mMapView.getWidth();
        int y = mMapView.getHeight();
        LatLng llBottomRight = mMapView.getProjection().fromScreenLocation(new Point(x, y));
        return llBottomRight;
    }

    public BitmapDescriptor getBitmapDescriptorFromAsset(String path)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromAsset(path);
        return bmp;
    }

    public BitmapDescriptor getBitmapDescriptorFromBitmap(Bitmap bitmap)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromBitmap(bitmap);
        return bmp;
    }

    public BitmapDescriptor getBitmapDescriptorFromFile(String path)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromFile(path);
        return bmp;
    }

    public BitmapDescriptor getBitmapDescriptorFromPath(String path)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromPath(path);
        return bmp;
    }

    public BitmapDescriptor getBitmapDescriptorFromResource(int resId)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromResource(resId);
        return bmp;
    }

    public BitmapDescriptor getBitmapDescriptorFromView(View view)
    {
        BitmapDescriptor bmp = BitmapDescriptorFactory.fromView(view);
        return bmp;
    }

    public void removeMarker(Marker marker)
    {
        if (marker != null)
        {
            marker.remove();
            if (mListMarker.contains(marker))
            {
                mListMarker.remove(marker);
            }
        }
    }

    public void removeAllMarker()
    {
        for (Marker marker : mListMarker)
        {
            marker.remove();
        }
        mListMarker.clear();
    }

    public void removeAllMarkerExpectLocation()
    {
        removeAllMarkerExpect(mMarkerLocation);
    }

    public void removeAllMarkerExpect(Marker marker)
    {
        if (marker != null)
        {
            List<Marker> listMarker = new ArrayList<Marker>();
            listMarker.add(marker);
            removeAllMarkerExpect(listMarker);
        }
    }

    public void removeAllMarkerExpect(Marker... markers)
    {
        if (markers != null)
        {
            List<Marker> listMarker = Arrays.asList(markers);
            removeAllMarkerExpect(listMarker);
        }
    }

    public void removeAllMarkerExpect(List<Marker> listMarker)
    {
        if (listMarker != null && !listMarker.isEmpty())
        {
            Iterator<Marker> itMarker = mListMarker.iterator();
            while (itMarker.hasNext())
            {
                Marker marker = itMarker.next();
                if (!listMarker.contains(marker))
                {
                    marker.remove();
                    itMarker.remove();
                }
            }
        }
    }

    public Marker addMarkerToMap(MarkerOptions options)
    {
        return addMarkerToMap(options, EnumAnchor.BOTTOM_CENTER);
    }

    public Marker addMarkerToMap(MarkerOptions options, EnumAnchor anchor)
    {
        switch (anchor)
        {
            case TOP_LEFT:
                options.anchor(0, 0);
                break;

            case TOP_CENTER:
                options.anchor(0.5f, 0);
                break;

            case TOP_RIGHT:
                options.anchor(1, 0);
                break;
            case CENTER_LEFT:
                options.anchor(0, 0.5f);
                break;

            case CENTER:
                options.anchor(0.5f, 0.5f);
                break;

            case CENTER_RIGHT:
                options.anchor(1, 0.5f);
                break;

            case BOTTOM_LEFT:
                options.anchor(0, 1);
                break;

            case BOTTOM_CENTER:
                options.anchor(0.5f, 1);
                break;

            case BOTTOM_RIGHT:
                options.anchor(1, 1);
                break;
            default:

                break;
        }

        Marker marker = mTencentMap.addMarker(options);
        if (!mListMarker.contains(marker))
        {
            mListMarker.add(marker);
        }
        return marker;
    }

    public Marker addMarkerToMap(LatLng latlng, BitmapDescriptor bmp)
    {
        MarkerOptions options = new MarkerOptions().position(latlng).icon(bmp);
        return addMarkerToMap(options);
    }

    public Marker addMarkerToMap(LatLng latlng, BitmapDescriptor bmp, EnumAnchor anchor)
    {
        MarkerOptions options = new MarkerOptions().position(latlng).icon(bmp);
        return addMarkerToMap(options, anchor);
    }

    public Marker addMarkerToMapFromResource(LatLng latlng, int resId)
    {
        MarkerOptions options = new MarkerOptions().position(latlng).icon(getBitmapDescriptorFromResource(resId));
        return addMarkerToMap(options);
    }

    public void showInfoWindow(Marker marker)
    {
        if (marker != null && !marker.isInfoWindowShown())
        {
            marker.showInfoWindow();
            marker.set2Top();
        }
    }

    public void hideInfoWindow(Marker marker)
    {
        if (marker != null && marker.isInfoWindowShown())
        {
            marker.hideInfoWindow();
        }
    }

    public void toggleInfoWindow(Marker marker)
    {
        if (marker != null)
        {
            if (marker.isInfoWindowShown())
            {
                hideInfoWindow(marker);
            } else
            {
                showInfoWindow(marker);
            }
        }
    }

    public enum EnumAnchor
    {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER_LEFT, CENTER, CENTER_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
    }

    public void focusMapTo(LatLng latLng)
    {
        if (latLng != null)
        {
            LatLng llCenter = mTencentMap.getMapCenter();
            if (latLng.getLatitude() == llCenter.getLatitude() && latLng.getLongitude() == llCenter.getLongitude())
            {
                // do nothing
            } else
            {
                mTencentMap.animateTo(latLng);
            }
        }
    }

    public void focusMapToCurrent()
    {
        LatLng ll = getLatLngCurrent();
        if (ll == null)
        {
            startLocation(false);
        } else
        {
            focusMapTo(ll);
        }
    }

    // drawline

    public Polyline drawLine(List<LatLng> listLatLng, boolean dotted)
    {
        return drawLine(listLatLng, Color.parseColor("#2200ff"), SDViewUtil.dp2px(1), dotted);
    }

    public Polyline drawLine(List<LatLng> listLatLng, int color, boolean dotted)
    {
        return drawLine(listLatLng, color, SDViewUtil.dp2px(2), dotted);
    }

    public Polyline drawLine(List<LatLng> listLatLng, int color, float width, boolean dotted)
    {
        Polyline line = null;
        if (listLatLng != null && !listLatLng.isEmpty())
        {
            PolylineOptions options = new PolylineOptions().addAll(listLatLng).color(color).width(width).setDottedLine(dotted);
            line = mTencentMap.addPolyline(options);
        }
        return line;
    }

    public List<Polyline> drawLines(List<LatLng> listLatLng, int color, boolean dotted)
    {
        return drawLines(listLatLng, color, SDViewUtil.dp2px(1), dotted);
    }

    public List<Polyline> drawLines(List<LatLng> listLatLng, int color, float width, boolean dotted)
    {
        List<Polyline> listLine = new ArrayList<Polyline>();
        if (listLatLng != null && !listLatLng.isEmpty())
        {
            List<List<LatLng>> listGroup = SDCollectionUtil.splitListLinked(listLatLng, 2);
            if (listGroup != null)
            {
                for (List<LatLng> list : listGroup)
                {
                    Polyline line = drawLine(list, color, width, dotted);
                    listLine.add(line);
                }
            }
        }
        return listLine;
    }

    public View wrapperView(View resView)
    {
        createMapView();
        View viewFinal = null;
        FrameLayout flLayout = new FrameLayout(getActivity());
        flLayout.addView(mMapView);
        if (resView != null)
        {
            flLayout.addView(resView);
        }
        viewFinal = flLayout;
        return viewFinal;
    }

    public View wrapperView(int resViewId)
    {
        View resView = null;
        if (resViewId != 0)
        {
            resView = getActivity().getLayoutInflater().inflate(resViewId, null);
        }
        return wrapperView(resView);
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {

    }

    @Override
    public void onMapLoaded()
    {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition)
    {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition)
    {

    }

    @Override
    public void onMapClick(LatLng latLng)
    {

    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {

    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        mMarkerLastClick = marker;
        return false;
    }

    @Override
    public void onMarkerDrag(Marker marker)
    {

    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {

    }

    @Override
    public void onMarkerDragStart(Marker marker)
    {

    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason)
    {
        if (mMarkerLocation == null)
        {
            mMarkerLocation = addMarkerToMap(getLatLngCurrent(), getBitmapDescriptorFromResource(R.drawable.ic_cb_tipoff_selected), EnumAnchor.CENTER);
        } else
        {
            mMarkerLocation.setPosition(getLatLngCurrent());
        }

        if (mAnimateMap)
        {
            mAnimateMap = false;
            focusMapToCurrent();
        }

    }

    @Override
    public void onStatusUpdate(String name, int status, String desc)
    {

    }

    @Override
    public void onResume()
    {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause()
    {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop()
    {
        mMapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        mMapView.onDestroy();
        stopLocation();
        super.onDestroy();
    }
}
