package com.fanwe.o2o.common;

import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.o2o.model.App_shop_getappActModel;
import com.fanwe.o2o.model.App_shop_getvideoActModel;
import com.fanwe.shop.model.App_shop_mystoreActModel;

/**
 * Created by Administrator on 2016/9/20.
 */

public class O2OShoppingCommonInterface
{
    /**
     * 我的小店
     *
     * @param user_id  主播用户ID
     * @param listener
     */
    public static void requestShopMystore(int page,int user_id, AppRequestCallback<App_shop_mystoreActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("mystore");
        params.put("page", page);
        params.put("podcast_user_id", user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 呼出直播小屏 (观众进入主播房间)
     *
     * @param listener
     */
    public static void requestGetVideo(String podcast_id, AppRequestCallback<App_shop_getvideoActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("getvideo");
        params.put("podcast_id", podcast_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 呼出直播应用
     *
     * @param listener
     */
    public static void requestGetApp(AppRequestCallback<App_shop_getappActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("getapp");
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 呼出发起直播
     *
     * @param listener
     */
    public static void requestOpenVideo(AppRequestCallback<App_shop_getappActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("openvideo");
        AppHttpUtil.getInstance().post(params, listener);
    }
}
