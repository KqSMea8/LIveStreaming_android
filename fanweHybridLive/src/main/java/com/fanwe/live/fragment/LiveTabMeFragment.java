package com.fanwe.live.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.auction.adapter.AuctionTabMeItemAdapter;
import com.fanwe.auction.model.AuctionTabMeItemModel;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveChatC2CActivity;
import com.fanwe.live.activity.LiveFamilyDetailsActivity;
import com.fanwe.live.activity.LiveFollowActivity;
import com.fanwe.live.activity.LiveMyFocusActivity;
import com.fanwe.live.activity.LiveMySelfContActivity;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveSearchUserActivity;
import com.fanwe.live.activity.LiveUserEditActivity;
import com.fanwe.live.activity.LiveUserHomeReplayActivity;
import com.fanwe.live.activity.LiveUserPhotoActivity;
import com.fanwe.live.activity.LiveUserProfitActivity;
import com.fanwe.live.activity.LiveUserSettingActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.activity.UserCenterAuthentActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveAddNewFamilyDialog;
import com.fanwe.live.event.ERefreshMsgUnReaded;
import com.fanwe.live.model.App_InitH5Model;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-7-2 上午11:01:04 类说明
 */
public class LiveTabMeFragment extends BaseFragment
{

    public static final String TAG = "LiveTabMeFragment";

    @ViewInject(R.id.ll_search)
    private LinearLayout ll_search;// 搜索

    @ViewInject(R.id.tv_use_diamonds)
    private TextView tv_use_diamonds; // 送出

    @ViewInject(R.id.ll_chat)
    private RelativeLayout ll_chat;

    @ViewInject(R.id.tv_total_unreadnum)
    private TextView tv_total_unreadnum;

    @ViewInject(R.id.fl_head)
    private FrameLayout fl_head;

    @ViewInject(R.id.tv_user_id)
    private TextView tv_user_id;

    @ViewInject(R.id.ll_user_id)
    private LinearLayout ll_user_id;

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;// 头像

    @ViewInject(R.id.iv_level)
    private ImageView iv_level;// 等级

    @ViewInject(R.id.tv_nick_name)
    private TextView tv_nick_name; // 昵称

    @ViewInject(R.id.iv_global_male)
    private ImageView iv_global_male;// 性别

    @ViewInject(R.id.iv_rank)
    private ImageView iv_rank;// 等级

    @ViewInject(R.id.iv_remark)
    private ImageView iv_remark;// 编辑

    @ViewInject(R.id.ll_my_focus)
    private LinearLayout ll_my_focus;

    @ViewInject(R.id.tv_focus_count)
    private TextView tv_focus_count;// 关注

    @ViewInject(R.id.ll_my_fans)
    private LinearLayout ll_my_fans;

    @ViewInject(R.id.tv_fans_count)
    private TextView tv_fans_count; // 粉丝

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;// 签名

    @ViewInject(R.id.tv_v_explain)
    private TextView tv_v_explain;

    @ViewInject(R.id.ll_v_explain)
    private LinearLayout ll_v_explain; // 认证

    @ViewInject(R.id.tv_anchor)
    private TextView tv_anchor;

    @ViewInject(R.id.rl_video)
    private RelativeLayout rl_video;

    @ViewInject(R.id.tv_video_num)
    private TextView tv_video_num;// 直播

    @ViewInject(R.id.rl_level)
    private RelativeLayout rl_level;

    @ViewInject(R.id.tv_level)
    private TextView tv_level; // 等级

    @ViewInject(R.id.rl_income)
    private RelativeLayout rl_income;

    @ViewInject(R.id.tv_income)
    private TextView tv_income; // 收益

    @ViewInject(R.id.rl_accout)
    private RelativeLayout rl_accout;// 账户

    @ViewInject(R.id.auction_gll_info)
    private SDGridLinearLayout auction_gll_info;//竞拍Item

    @ViewInject(R.id.tv_accout)
    private TextView tv_accout;

    @ViewInject(R.id.include_cont_linear)
    private View include_cont_linear;

    @ViewInject(R.id.ll_upgrade)
    private RelativeLayout ll_upgrade;

    @ViewInject(R.id.tv_v_type)
    private TextView tv_v_type;

    @ViewInject(R.id.ll_family)
    private RelativeLayout ll_family;

    @ViewInject(R.id.ll_setting)
    private RelativeLayout ll_setting;

    private App_userinfoActModel app_userinfoActModel;

    private AuctionTabMeItemAdapter adapter;
    private List<AuctionTabMeItemModel> auction_gll_info_array = new ArrayList<AuctionTabMeItemModel>();

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_live_tab_me;
    }

    @Override
    protected void init()
    {
        super.init();
        register();
        bindAuctionAdapter();
    }

    private void register()
    {
        ll_search.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        fl_head.setOnClickListener(this);
        iv_remark.setOnClickListener(this);
        SDViewUtil.setVisible(iv_remark);
        SDViewUtil.setVisible(ll_user_id);
        include_cont_linear.setOnClickListener(this);
        ll_my_focus.setOnClickListener(this);
        ll_my_fans.setOnClickListener(this);
        rl_video.setOnClickListener(this);
        rl_level.setOnClickListener(this);
        rl_income.setOnClickListener(this);
        rl_accout.setOnClickListener(this);
        ll_upgrade.setOnClickListener(this);
        ll_family.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
    }

    private void bindAuctionAdapter()
    {
        auction_gll_info.setColNumber(2);
        adapter = new AuctionTabMeItemAdapter(auction_gll_info_array, getActivity());
        adapter.setItemClickCallback(new SDItemClickCallback<AuctionTabMeItemModel>()
        {
            @Override
            public void onItemClick(int position, AuctionTabMeItemModel item, View view)
            {
                if (!TextUtils.isEmpty(item.getUrl()))
                {
                    Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                    intent.putExtra(LiveWebViewActivity.EXTRA_URL, item.getUrl());
                    startActivity(intent);
                } else
                {
                    SDToast.showToast("url为空");
                }
            }
        });
        auction_gll_info.setAdapter(adapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        if (!hidden)
        {
            request();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume()
    {
        initUnReadNum();
        request();
        super.onResume();
    }

    private void request()
    {
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    app_userinfoActModel = actModel;
                    bindData(actModel);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    private void bindData(App_userinfoActModel actModel)
    {
        UserModel user = actModel.getUser();
        if (user != null)
        {
            if (!TextUtils.isEmpty(user.getUser_id()))
            {
                UserModelDao.insertOrUpdate(user);//更新用户数据
            } else
            {
                SDToast.showToast("接口返回user_id为空");
            }

            if (AppRuntimeWorker.getShow_hide_pai_view() == 1)
            {
                setAuctionItemView(user);
            }

            if (AppRuntimeWorker.getOpen_family_module() == 1)
                ll_family.setVisibility(View.VISIBLE);
            else
                ll_family.setVisibility(View.GONE);

            SDViewBinder.setTextView(tv_user_id, user.getUser_id());

            String user_diamonds = user.getUse_diamonds() + "";
            SDViewBinder.setTextView(tv_use_diamonds, user_diamonds);

            GlideUtil.loadHeadImage(user.getHead_image()).into(iv_head);

            if (!TextUtils.isEmpty(user.getV_icon()))
            {
                GlideUtil.load(user.getV_icon()).into(iv_level);
                SDViewUtil.setVisible(iv_level);
            } else
            {
                SDViewUtil.setGone(iv_level);
            }

            SDViewBinder.setTextView(tv_nick_name, user.getNick_name());
            if (user.getSexResId() > 0)
            {
                SDViewUtil.setVisible(iv_global_male);
                iv_global_male.setImageResource(user.getSexResId());
            } else
            {
                SDViewUtil.setGone(iv_global_male);
            }
            iv_rank.setImageResource(user.getLevelImageResId());

            String focus_count = user.getFocus_count() + "";
            SDViewBinder.setTextView(tv_focus_count, focus_count);

//            SDViewBinder.setTextView(tv_fans_count, LiveUtils.getFormatNumber(user.getFans_count()));
            SDViewBinder.setTextView(tv_fans_count, user.getFans_count()+"");
            SDViewBinder.setTextView(tv_introduce, user.getSignature(), "TA好像忘记写签名了");

            if (!TextUtils.isEmpty(user.getV_explain()))
            {
                SDViewUtil.setVisible(ll_v_explain);
                SDViewBinder.setTextView(tv_v_explain, user.getV_explain());
            } else
            {
                SDViewUtil.setGone(ll_v_explain);
            }

            String video_count = user.getVideo_count() + "";
            SDViewBinder.setTextView(tv_video_num, video_count);

            String user_level = user.getUser_level() + "";
            SDViewBinder.setTextView(tv_level, user_level);
            SDViewBinder.setTextView(tv_income, user.getUseable_ticket()+"");

            SDViewBinder.setTextView(tv_accout, user.getDiamonds()+"");
//            SDViewBinder.setTextView(tv_income, LiveUtils.getFormatNumber(user.getUseable_ticket()));
//
//            SDViewBinder.setTextView(tv_accout, LiveUtils.getFormatNumber(user.getDiamonds()));

            int v_type = SDTypeParseUtil.getInt(user.getV_type());
            if (v_type == 0)
            {
                SDViewUtil.setVisible(ll_upgrade);
            } else if (v_type == 1)
            {
                SDViewUtil.setGone(ll_upgrade);
            } else if (v_type == 2)
            {
                SDViewUtil.setGone(ll_upgrade);
            }

            String anchor = SDResourcesUtil.getString(R.string.live_account_authentication);
            anchor = anchor + "认证";
            SDViewBinder.setTextView(tv_anchor, anchor);

            int is_authentication = user.getIs_authentication();
            if (is_authentication == 0)
            {
                tv_v_type.setText("未认证");
            } else if (is_authentication == 1)
            {
                tv_v_type.setText("认证待审核");
            } else if (is_authentication == 2)
            {
                tv_v_type.setText("已认证");
            } else if (is_authentication == 3)
            {
                tv_v_type.setText("认证审核不通过");
            }
        }

    }

    private void setAuctionItemView(UserModel user)
    {
        InitActModel initmodel = InitActModelDao.query();

        String url_user_order = "";
        String url_user_pai = "";
        String url_podcast_order = "";
        String url_podcast_pai = "";
        String url_podcast_goods = "";
        if (initmodel != null)
        {
            App_InitH5Model h5Model = initmodel.getH5_url();
            if (h5Model != null)
            {
                url_user_order = h5Model.getUrl_user_order();
                url_user_pai = h5Model.getUrl_user_pai();
                url_podcast_order = h5Model.getUrl_podcast_order();
                url_podcast_pai = h5Model.getUrl_podcast_pai();
                url_podcast_goods = h5Model.getUrl_podcast_goods();
            }
        }

        auction_gll_info_array.clear();
        if (user.getShow_user_order() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setLeft_text("我的订单");
            model.setRight_text(Integer.toString(user.getUser_order()) + "个");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag1);
            model.setImage_Res(R.drawable.ic_user_order);
            model.setUrl(url_user_order);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_user_pai() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getUser_pai()) + "个");
            model.setLeft_text("我参与的竞拍");
            model.setImage_Res(R.drawable.ic_user_pai);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag2);
            model.setUrl(url_user_pai);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_podcast_order() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_order()) + "个");
            model.setLeft_text("星店订单");
            model.setImage_Res(R.drawable.ic_podcast_order);
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag3);
            model.setUrl(url_podcast_order);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_podcast_pai() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_pai()) + "个");
            model.setLeft_text("我发起的竞拍");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag4);
            model.setImage_Res(R.drawable.ic_podcast_pai);
            model.setUrl(url_podcast_pai);
            auction_gll_info_array.add(model);
        }

        if (user.getShow_podcast_goods() == 1)
        {
            AuctionTabMeItemModel model = new AuctionTabMeItemModel();
            model.setRight_text(Integer.toString(user.getPodcast_goods()) + "个");
            model.setLeft_text("商品管理");
            model.setStr_Tag(AuctionTabMeItemModel.TabMeTag.tag5);
            model.setImage_Res(R.drawable.ic_goods_manger);
            model.setUrl(url_podcast_goods);
            auction_gll_info_array.add(model);
        }

        if (auction_gll_info_array.size() == 0)
        {
            SDViewUtil.setGone(auction_gll_info);
        } else
        {
            //如果基数添加一个白色区域
            if (auction_gll_info_array.size() % 2 != 0)
            {
                AuctionTabMeItemModel model = new AuctionTabMeItemModel();
                model.setBlankPage(true);
                auction_gll_info_array.add(model);
            }
            SDViewUtil.setVisible(auction_gll_info);
            auction_gll_info.notifyDataSetChanged();
        }
    }

    private void initH5Url()
    {

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ll_search:
                clickLLSearch();
                break;
            case R.id.ll_chat:
                clickLlChat();
                break;
            case R.id.fl_head:
                clickFlHead();
                break;
            case R.id.iv_remark:
                clickIvRemark();
                break;
            case R.id.ll_my_focus:
                clickLlMyFocus();
                break;
            case R.id.ll_my_fans:
                clickLlMyFans();
                break;
            case R.id.include_cont_linear:
                clickIncludeContLinear();
                break;
            case R.id.rl_video:
                clickRlVideo();
                break;
            case R.id.rl_level:
                clickRlLevel();
                break;
            case R.id.rl_income:
                clickRlIncome();
                break;
            case R.id.rl_accout:
                clickRlAccout();
                break;
            case R.id.ll_upgrade:
                clickLlUpgrade();
                break;
            case R.id.ll_setting:
                clickSetting();
                break;
            case R.id.ll_family:
                clickfamily();
                break;
            default:
                break;
        }
    }

    /**
     * 我的家族
     */
    private void clickfamily()
    {
        UserModel dao = UserModelDao.query();
        if (dao.getFamily_id() == 0)
            showFamDialog();
        else
        {
            //家族详情
            Intent intent = new Intent(getActivity(), LiveFamilyDetailsActivity.class);
            startActivity(intent);
        }
    }

    private void showFamDialog()
    {
        LiveAddNewFamilyDialog dialog = new LiveAddNewFamilyDialog(getActivity());
        dialog.showCenter();
    }

    /**
     * 设置
     */
    private void clickSetting()
    {
        Intent intent = new Intent(getActivity(), LiveUserSettingActivity.class);
        startActivity(intent);
    }

    // 我关注的人
    private void clickLlMyFocus()
    {
        UserModel user = UserModelDao.query();
        if (user != null)
        {
            String user_id = user.getUser_id();
            if (!TextUtils.isEmpty(user_id))
            {
                Intent intent = new Intent(getActivity(), LiveFollowActivity.class);
                intent.putExtra(LiveFollowActivity.EXTRA_USER_ID, user_id);
                startActivity(intent);
            } else
            {
                SDToast.showToast("本地user_id为空");
            }
        } else
        {

        }
    }

    // 我的粉丝
    private void clickLlMyFans()
    {
        Intent intent = new Intent(getActivity(), LiveMyFocusActivity.class);
        startActivity(intent);
    }

    //认证
    private void clickLlUpgrade()
    {
        if (app_userinfoActModel != null)
        {
            Intent intent = new Intent(getActivity(), UserCenterAuthentActivity.class);
            startActivity(intent);
        }
    }

    private void clickRlAccout()
    {
        Intent intent = new Intent(getActivity(), LiveRechargeDiamondsActivity.class);
        startActivity(intent);
    }

    //收益
    private void clickRlIncome()
    {
        Intent intent = new Intent(getActivity(), LiveUserProfitActivity.class);
        startActivity(intent);
    }

    //等级
    private void clickRlLevel()
    {
        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_my_grades());
        startActivity(intent);
    }

    // 回放列表
    private void clickRlVideo()
    {
        Intent intent = new Intent(getActivity(), LiveUserHomeReplayActivity.class);
        startActivity(intent);
    }

    private void clickIncludeContLinear()
    {
        Intent intent = new Intent(getActivity(), LiveMySelfContActivity.class);
        startActivity(intent);
    }

    //编辑
    private void clickIvRemark()
    {
        Intent intent = new Intent(getActivity(), LiveUserEditActivity.class);
        startActivity(intent);
    }

    // 我的头像
    private void clickFlHead()
    {
        if (app_userinfoActModel != null)
        {
            UserModel user = app_userinfoActModel.getUser();
            if (user != null)
            {
                Intent intent = new Intent(getActivity(), LiveUserPhotoActivity.class);
                intent.putExtra(LiveUserPhotoActivity.EXTRA_USER_IMG_URL, user.getHead_image());
                startActivity(intent);
            }
        }
    }

    //聊天
    private void clickLlChat()
    {
        Intent intent = new Intent(getActivity(), LiveChatC2CActivity.class);
        startActivity(intent);
    }

    // 搜索
    private void clickLLSearch()
    {
        Intent intent = new Intent(getActivity(), LiveSearchUserActivity.class);
        startActivity(intent);
    }

    private void initUnReadNum()
    {
        TotalConversationUnreadMessageModel model = IMHelper.getC2CTotalUnreadMessageModel();
        setUnReadNumModel(model);
    }

    public void onEventMainThread(ERefreshMsgUnReaded event)
    {
        TotalConversationUnreadMessageModel model = event.model;
        setUnReadNumModel(model);
    }

    private void setUnReadNumModel(TotalConversationUnreadMessageModel model)
    {
        SDViewUtil.setGone(tv_total_unreadnum);
        if (model != null && model.getTotalUnreadNum() > 0)
        {
            SDViewUtil.setVisible(tv_total_unreadnum);
            tv_total_unreadnum.setText(model.getStr_totalUnreadNum());
        }
    }
}
