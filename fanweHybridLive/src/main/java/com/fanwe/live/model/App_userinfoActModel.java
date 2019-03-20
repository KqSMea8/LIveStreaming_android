package com.fanwe.live.model;

import com.fanwe.auction.model.AuctionTabMeItemModel;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.live.R;

import java.util.ArrayList;
import java.util.List;

public class App_userinfoActModel extends BaseActModel
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UserModel user;
    private UserModel cuser; // 钱票贡献第一名
    // 举报按钮，1-显示，0-不显示
    private int show_tipoff;
    // 管理按钮，1,2-显示，0-不显示(1 管理员：举报，禁言，取消;2主播：设置为管理员/取消管理员，管理员列表，禁言，取消)
    private int show_admin;
    // 0-未关注;1-已关注
    private int has_focus;
    // 0-非管理员;1-是管理员
    private int has_admin;
    private int is_admin;

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    private int is_forbid;//0,1是否被禁言

    private App_InitH5Model h5_url;//个人中心5个按钮跳转

    public App_InitH5Model getH5_url()
    {
        return h5_url;
    }

    public void setH5_url(App_InitH5Model h5_url)
    {
        this.h5_url = h5_url;
    }

    public int getIs_forbid()
    {
        return is_forbid;
    }

    public void setIs_forbid(int is_forbid)
    {
        this.is_forbid = is_forbid;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public int getShow_tipoff()
    {
        return show_tipoff;
    }

    public void setShow_tipoff(int show_tipoff)
    {
        this.show_tipoff = show_tipoff;
    }

    public int getShow_admin()
    {
        return show_admin;
    }

    public void setShow_admin(int show_admin)
    {
        this.show_admin = show_admin;
    }

    public int getHas_focus()
    {
        return has_focus;
    }

    public void setHas_focus(int has_focus)
    {
        this.has_focus = has_focus;
    }

    public int getHas_admin()
    {
        return has_admin;
    }

    public void setHas_admin(int has_admin)
    {
        this.has_admin = has_admin;
    }

    public UserModel getCuser()
    {
        return cuser;
    }

    public void setCuser(UserModel cuser)
    {
        this.cuser = cuser;
    }

    public List<AuctionTabMeItemModel> getItem()
    {
        List<AuctionTabMeItemModel> auction_gll_info_array = new ArrayList<AuctionTabMeItemModel>();

        String url_user_order = "";
        String url_user_pai = "";
        String url_podcast_order = "";
        String url_podcast_pai = "";
        String url_podcast_goods = "";
        String url_shopping_cart = "";

        App_InitH5Model h5Model = this.getH5_url();
        if (h5Model != null)
        {
            url_user_order = h5Model.getUrl_user_order();
            url_user_pai = h5Model.getUrl_user_pai();
            url_podcast_order = h5Model.getUrl_podcast_order();
            url_podcast_pai = h5Model.getUrl_podcast_pai();
            url_podcast_goods = h5Model.getUrl_podcast_goods();
            url_shopping_cart = h5Model.getUrl_shopping_cart();
        }

        if (user.getShow_podcast_order() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_order()) + "个订单");
            model.setLeft_text("订单管理");
            model.setImage_Res(R.drawable.ic_podcast_order);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag3);
            model.setUrl(url_podcast_order);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_podcast_pai() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_pai()) + "个竞拍");
            model.setLeft_text("竞拍管理");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag4);
            model.setImage_Res(R.drawable.ic_podcast_pai);
            model.setUrl(url_podcast_pai);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_user_order() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setLeft_text("我的订单");
            model.setRight_text(Integer.toString(user.getUser_order()) + "个订单");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag1);
            model.setImage_Res(R.drawable.ic_user_order);
            model.setUrl(url_user_order);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_podcast_goods() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_goods()) + "个商品");
            model.setLeft_text("商品管理");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag5);
            model.setImage_Res(R.drawable.ic_goods_manger);
            model.setUrl(url_podcast_goods);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_user_pai() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getUser_pai()) + "个竞拍");
            model.setLeft_text("我的竞拍");
            model.setImage_Res(R.drawable.ic_user_pai);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag2);
            model.setUrl(url_user_pai);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_shopping_cart() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getShopping_cart()) + "个商品");
            model.setLeft_text("购物车");
            model.setImage_Res(R.drawable.ic_user_pai);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag6);
            model.setUrl(url_shopping_cart);
            auction_gll_info_array.add(model);
        }
        if (user.getOpen_podcast_goods() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getShop_goods()) + "个商品");
            model.setLeft_text("我的小店");
            model.setImage_Res(R.drawable.ic_podcast_goods);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag7);
            model.setUrl("");
            auction_gll_info_array.add(model);
        }

        return auction_gll_info_array;
    }
}
