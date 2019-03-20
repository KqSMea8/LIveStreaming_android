package com.fanwe.auction.common;

import com.fanwe.auction.model.App_Add_AddressActModel;
import com.fanwe.auction.model.App_Address_SetdefaultActModel;
import com.fanwe.auction.model.App_Edit_AddressActModel;
import com.fanwe.auction.model.App_address_listActModel;
import com.fanwe.auction.model.App_auction_createAuctionModel;
import com.fanwe.auction.model.App_auction_goodsTagsModel;
import com.fanwe.auction.model.App_auction_pai_user_go_videoActModel;
import com.fanwe.auction.model.App_auction_upload_imageActModel;
import com.fanwe.auction.model.App_message_getlistActModel;
import com.fanwe.auction.model.App_pai_podcast_addpaidetailActModel;
import com.fanwe.auction.model.App_pai_user_dopaiActModel;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.App_pai_user_goods_detailActModel;
import com.fanwe.auction.model.App_pai_user_open_goodsActModel;
import com.fanwe.auction.model.App_shop_paigoodsActModel;
import com.fanwe.auction.model.Pai_userDojoinActModel;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/9.
 */
public class AuctionCommonInterface
{
    /**
     * 观众-获得参与的竞拍列表
     *
     * @param listener
     * @param id           竞拍商品
     * @param p            当前页
     * @param get_joindata 是否获取user_id的参与记录
     * @param get_pailogs  是否获取竞拍记录
     */
    public static void requestPaiUserGoodsDetail(String id, int p, int get_joindata, int get_pailogs, AppRequestCallback<App_pai_user_goods_detailActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("goods_detail");
        params.put("id", id);
        params.put("p", p);
        params.put("get_joindata", get_joindata);
        params.put("get_pailogs", get_pailogs);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 主播-获得某竞拍竞拍详情
     *
     * @param listener
     * @param id          竞拍商品
     * @param page        当前页
     * @param get_pailogs 是否获取竞拍记录
     */
    public static void requestPaiPodcastGoodsDetail(String id, int page, int get_pailogs, AppRequestCallback<App_pai_user_goods_detailActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("goods_detail");
        params.put("id", id);
        params.put("p", page);
        params.put("get_pailogs", get_pailogs);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 虚拟竞拍的标签
     *
     * @param listener
     */
    public static void requestVirtualGoodsTag(AppRequestCallback<App_auction_goodsTagsModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("tags");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 发布竞拍 虚拟竞拍和实物竞拍
     *
     * @param mapParams 包含区分实物和虚拟的字段
     * @param listener
     */
    public static void requestAddAuction(Map<String, Object> mapParams, AppRequestCallback<App_auction_createAuctionModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("addpai");
        for (String key : mapParams.keySet())
        {
            params.put(key, mapParams.get(key));
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 参拍交保证金
     *
     * @param pai_id             竞拍商品id
     * @param consignee          联系人
     * @param consignee_mobile   联系人电话
     * @param consignee_district 区域JSON数据[虚拟非必需] 实物必传
     * @param consignee_address  地址[虚拟非必需] 实物必传
     * @param listener
     */
    public static void requestPai_userDojoin(int pai_id, String consignee, String consignee_mobile, String consignee_district, String consignee_address, AppRequestCallback<Pai_userDojoinActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("dojoin");
        params.put("id", pai_id);
        params.put("consignee", consignee);
        params.put("consignee_mobile", consignee_mobile);
        params.put("consignee_district", consignee_district);
        params.put("consignee_address", consignee_address);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 消息列表
     *
     * @param listener
     */
    public static void requestMessage_getlist(int page, AppRequestCallback<App_message_getlistActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("message");
        params.putAct("getlist");
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 检查竞拍权限
     *
     * @param listener
     */
    public static void requestCreateAuctionAuthority(AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("check");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 竞拍出价
     * http://xx.com/mapi/index.php?ctl=pai_user&act=dopai
     *
     * @param listener
     */
    public static void requestPaiUserDopai(int id, int pai_diamonds, AppRequestCallback<App_pai_user_dopaiActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("dopai");
        params.put("id", id);
        params.put("pai_diamonds", pai_diamonds);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 竞拍删除
     *
     * @param pai_id   拍品ID
     * @param listener
     */
    public static void requestDeleteAuction(int pai_id, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("del");
        params.put("pai_id", pai_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 进入直播间-获取拍卖信息
     *
     * @param pai_id   拍品ID
     * @param listener
     */
    public static void requestPaiUserGetVideo(int pai_id, AppRequestCallback<App_pai_user_get_videoActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("get_video");
        params.put("pai_id", pai_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 付款
     *
     * @param order_sn 拍品ID
     * @param listener
     */
    public static void requestPaiUserPayDiamonds(String order_sn, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("pay_diamonds");
        params.put("order_sn", order_sn);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 竞拍图片上传
     *
     * @param listener
     */
    public static void requestAuctionImageUpload(File file, AppRequestCallback<App_auction_upload_imageActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("upload");
        params.putFile("file", file);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 打开直播间-获取打开直播间参数
     *
     * @param listener
     */
    public static void requestPaiUserGoVideo(int pai_id, AppRequestCallback<App_auction_pai_user_go_videoActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("go_video");
        params.put("pai_id", pai_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 实物竞拍商品列表
     *
     * @param listener
     */
    public static void requestShopPaiGoods(int page, AppRequestCallback<App_shop_paigoodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("shop");
        params.putAct("pai_goods");
        params.put("itype", "shop");
        params.put("page", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 收货地址列表
     *
     * @param p        页码
     * @param listener
     */
    public static void requestAddressList(int p, AppRequestCallback<App_address_listActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("address");
        params.putAct("address");
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 添加收货地址
     *
     * @param consignee          收货人姓名
     * @param consignee_mobile   收货人手机号
     * @param consignee_district 收货人所在地行政地区信息,json格式
     * @param consignee_address  收货人详细地址 是否默认 0
     * @param is_default
     * @param listener
     */
    public static void requestAddAddress(String consignee, String consignee_mobile, String consignee_district, String consignee_address, int is_default, AppRequestCallback<App_Add_AddressActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("address");
        params.putAct("addaddress");
        params.put("consignee", consignee);
        params.put("consignee_mobile", consignee_mobile);
        params.put("consignee_district", consignee_district);
        params.put("consignee_address", consignee_address);
        params.put("is_default", is_default);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 编辑收货地址
     *
     * @param id
     * @param consignee
     * @param consignee_mobile
     * @param consignee_district
     * @param consignee_address
     * @param is_default
     * @param listener
     */
    public static void requestEditAddress(int id, String consignee, String consignee_mobile, String consignee_district, String consignee_address,
                                          int is_default, AppRequestCallback<App_Edit_AddressActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("address");
        params.putAct("editaddress");
        params.put("id", id);
        params.put("consignee", consignee);
        params.put("consignee_mobile", consignee_mobile);
        params.put("consignee_district", consignee_district);
        params.put("consignee_address", consignee_address);
        params.put("is_default", is_default);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 设置默认收货地址
     *
     * @param id
     * @param listener
     */
    public static void requestSetDefaultAddress(int id, AppRequestCallback<App_Address_SetdefaultActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("address");
        params.putAct("setdefault");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 主播-预载新建实物竞拍（非必须）
     *
     * @param goods_id
     * @param listener
     */
    public static void requestShopAddpaiDetail(int goods_id, AppRequestCallback<App_pai_podcast_addpaidetailActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_podcast");
        params.putAct("addpaidetail");
        params.put("itype", "shop");
        params.put("goods_id", goods_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 打开商品链接
     * @param podcast_id
     * @param listener
     */
    public static void requestPaiUserOpenGoods(String podcast_id, AppRequestCallback<App_pai_user_open_goodsActModel> listener)
    {
        AppRequestParams params = new AppRequestParams();
        params.putCtl("pai_user");
        params.putAct("open_goods");
        params.put("itype", "shop");
        params.put("podcast_id", podcast_id);
        AppHttpUtil.getInstance().post(params, listener);
    }
}
