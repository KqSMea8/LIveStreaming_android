package com.fanwe.hybrid.http;

import android.text.TextUtils;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.constant.Constant.DeviceType;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.adapter.http.model.SDRequestParams;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.HostManager;

/**
 * Created by Administrator on 2016/3/30.
 */
public class AppRequestParams extends SDRequestParams
{

    private boolean isNeedShowActInfo = true;

    public static final class RequestDataType
    {
        public static final int NORMAL = 0;
        public static final int AES = 1;
    }

    public AppRequestParams()
    {
        super();

        setUrl(ApkConstant.SERVER_URL_API);

        setRequestDataType(RequestDataType.AES);

        put("screen_width", SDViewUtil.getScreenWidth());
        put("screen_height", SDViewUtil.getScreenHeight());
        put("sdk_type", DeviceType.DEVICE_ANDROID);
        put("sdk_version_name", SDPackageUtil.getVersionName());
        put("sdk_version", SDPackageUtil.getVersionCode());
        String shopping_session = SDConfig.getInstance().getString(R.string.shopping_session_id, "");
        if (!TextUtils.isEmpty(shopping_session))
        {
            put("session_id", shopping_session);
        }

        putLocation();
    }

    public void putLocation()
    {
        double xpoint = SDTencentMapManager.getInstance().getLongitude();
        double ypoint = SDTencentMapManager.getInstance().getLatitude();

        if (xpoint > 0 && ypoint > 0)
        {
            put("xpoint", xpoint);
            put("ypoint", ypoint);
        }
    }

    public boolean isNeedShowActInfo()
    {
        return isNeedShowActInfo;
    }

    public void setNeedShowActInfo(boolean isNeedShowActInfo)
    {
        this.isNeedShowActInfo = isNeedShowActInfo;
    }
}
