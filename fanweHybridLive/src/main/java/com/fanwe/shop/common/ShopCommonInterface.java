package com.fanwe.shop.common;


import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.shop.model.App_pai_user_open_goods_urlActModel;
import com.fanwe.shop.model.App_shop_goodsActModel;
import com.fanwe.shop.model.App_shop_mystoreActModel;
import com.fanwe.shop.model.App_shop_push_goodsActModel;

/**
 * Created by Administrator on 2016/12/28.
 */

public class ShopCommonInterface
{
    /**
     * 我的星店
     *
     * @param podcast_user_id 主播Id
     * @param listener
     */
    public static void requestAuctionShopMystore(int page, int podcast_user_id, AppRequestCallback<App_shop_mystoreActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("mystore");
        params.put("page", page);
        params.put("podcast_user_id", podcast_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我的小店
     *
     * @param page
     * @param podcast_user_id 主播ID
     * @param listener
     */
    public static void requestShopPodcastMyStore(String podcast_user_id, int page, AppRequestCallback<App_shop_mystoreActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("podcast_mystore");
        params.put("page", page);
        params.put("itype", "shop");
        params.put("podcast_user_id", podcast_user_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 打开商品详情链接
     *
     * @param goods_id   商品id
     * @param podcast_id 主播id
     * @param listener
     */
    public static void requestShopGoodsUrl(String goods_id, String podcast_id, AppRequestCallback<App_pai_user_open_goods_urlActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("open_goods_detail");
        params.put("goods_id", goods_id);
        params.put("podcast_id", podcast_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 购物直播商品推送
     *
     * @param podcast_user_id
     * @param goods_id
     * @param listener
     */
    public static void requestShopGoodsPush(int podcast_user_id, String goods_id, AppRequestCallback<App_shop_push_goodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("push_goods");
        params.put("podcast_user_id", podcast_user_id);
        params.put("goods_id", goods_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除购物商品
     *
     * @param id       ID
     * @param listener
     */
    public static void requestShopDelGoods(int id, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("del_goods");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 新增购物商品
     *
     * @param user_id     主播用户ID
     * @param name        商品名称
     * @param imgs        图片（JSON数据）
     * @param price       商品价钱
     * @param url         商品详情URL地址
     * @param description 商品描述
     * @param listener
     */
    public static void requestShopAddGoods(String user_id, String name, String imgs, float price, String url, String description, AppRequestCallback<App_shop_goodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("add_goods");
        params.put("podcast_id", user_id);
        params.put("name", name);
        params.put("imgs", imgs);
        params.put("price", price);
        params.put("url", url);
        params.put("description", description);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 修改购物商品
     *
     * @param id          ID
     * @param user_id     主播用户ID
     * @param name        商品名称
     * @param imgs        图片（JSON数据）
     * @param price       商品价钱
     * @param url         商品详情URL地址
     * @param description 商品描述
     * @param listener
     */
    public static void requestShopEditGoods(String id, String user_id, String name, String imgs, float price, String url, String description, AppRequestCallback<App_shop_goodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("edit_goods");
        params.put("id", id);
        params.put("podcast_id", user_id);
        params.put("name", name);
        params.put("imgs", imgs);
        params.put("price", price);
        params.put("url", url);
        params.put("description", description);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我的小店推送
     *
     * @param goods_id 商品id
     * @param listener
     */
    public static void requestShopPushPodcastGoods(String goods_id, AppRequestCallback<App_shop_goodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("push_podcast_goods");
        params.put("goods_id", goods_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

}
