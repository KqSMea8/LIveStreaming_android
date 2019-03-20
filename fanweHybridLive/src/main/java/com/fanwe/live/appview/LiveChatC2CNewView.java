package com.fanwe.live.appview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fanwe.auction.appview.AuctionTradeMsgView;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.LiveConversationListModel;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.LiveUnReadNumTextView;

import org.xutils.view.annotation.ViewInject;


/**
 * Created by Administrator on 2016/12/9.
 */

public class LiveChatC2CNewView extends BaseAppView
{
    @ViewInject(R.id.rl_back)
    private View rl_back;//返回

    public void hideRl_back()
    {
        rl_back.setVisibility(View.INVISIBLE);
    }

    @ViewInject(R.id.rl_auction_trade)
    private RelativeLayout rl_auction_trade;//竞拍item
    @ViewInject(R.id.tab_trade)
    private SDTabUnderline tab_trade;
    @ViewInject(R.id.tv_admin_total)
    private LiveUnReadNumTextView tv_admin_total;

    @ViewInject(R.id.tab_left)
    private SDTabUnderline tab_left;//好友
    @ViewInject(R.id.tv_left_total)
    private LiveUnReadNumTextView tv_left_total;

    @ViewInject(R.id.tab_right)
    private SDTabUnderline tab_right;//关注
    @ViewInject(R.id.tv_right_total)
    private LiveUnReadNumTextView tv_right_total;

    @ViewInject(R.id.ll_read)
    private LinearLayout ll_read;//未读

    @ViewInject(R.id.ll_trade)
    private LinearLayout ll_trade;//交易content
    @ViewInject(R.id.ll_chat_left)
    private LinearLayout ll_chat_left;//好友content
    @ViewInject(R.id.ll_chat_right)
    private LinearLayout ll_chat_right;//未关注content

    private AuctionTradeMsgView auctionTradeMsgView;//交易View
    private LiveConversationListView liveChatC2CAdminView;//好友View
    private LiveConversationListView liveChatC2CLeftView;//好友View
    private LiveConversationListView liveChatC2CRightView;//未关注View

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<SDTabUnderline>();

    private int mSelectTabIndex = 0;

    public LiveChatC2CNewView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveChatC2CNewView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveChatC2CNewView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_chat_new_c2c);
        register();
        addTab();
        addContentView();
    }

    public void requestData()
    {
        if (liveChatC2CAdminView != null)
        {
            liveChatC2CAdminView.requestData();
        }
        if (liveChatC2CLeftView != null)
        {
            liveChatC2CLeftView.requestData();
        }
        if (liveChatC2CRightView != null)
        {
            liveChatC2CRightView.requestData();
        }

//        if (AppRuntimeWorker.getShow_hide_pai_view() == 1)
//        {
//            if (auctionTradeMsgView != null)
//            {
//                auctionTradeMsgView.refreshViewer();
//            }
//        }
    }

    private void register()
    {
        rl_back.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (clickListener != null)
                {
                    clickListener.onClickBack();
                }
            }
        });

        ll_read.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                ignoreC2CTotalUnreadNum();
            }
        });
    }

    private void ignoreC2CTotalUnreadNum()
    {
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            SDToast.showToast("user为空");
            return;
        }

        TotalConversationUnreadMessageModel model = IMHelper.getC2CTotalUnreadMessageModel();
        if (model.getTotalUnreadNum() > 0)
        {
            showDeleteUnReadMsgDialog();
        }
    }

    private void showDeleteUnReadMsgDialog()
    {
        SDDialogConfirm dialog = new SDDialogConfirm(getActivity());
        dialog.setTextContent("确定忽略所有未读消息");
        dialog.setTextCancel("否");
        dialog.setTextConfirm("是");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                setAllMsgReaded();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {

            }
        });
        dialog.show();
    }

    private void setAllMsgReaded()
    {
        IMHelper.setAllC2CReadMessage();
        if (liveChatC2CAdminView != null)
        {
            liveChatC2CAdminView.notifyTotalUnreadNumListener();
            liveChatC2CAdminView.notifyAdapter();
        }
        if (liveChatC2CLeftView != null)
        {
            liveChatC2CLeftView.notifyTotalUnreadNumListener();
            liveChatC2CLeftView.notifyAdapter();
        }
        if (liveChatC2CRightView != null)
        {
            liveChatC2CRightView.notifyTotalUnreadNumListener();
            liveChatC2CRightView.notifyAdapter();
        }
    }

    private void addTab()
    {
//        if (AppRuntimeWorker.getShow_hide_pai_view() == 1||AppRuntimeWorker.getShopping_goods()==1)
//        {
//            SDViewUtil.setVisible(ll_trade);
//            SDViewUtil.setVisible(rl_auction_trade);
//        } else
//        {
//            mSelectTabIndex = 1;
//            SDViewUtil.setGone(rl_auction_trade);
//            SDViewUtil.setGone(ll_trade);
//        }

        tab_trade.setTextTitle("系统");
        tab_trade.getViewConfig(tab_trade.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_trade.getViewConfig(tab_trade.mTvTitle).setTextColorNormalResId(R.color.text_title_bar).setTextColorSelectedResId(R.color.main_color);

        tab_left.setTextTitle("好友");
        tab_left.getViewConfig(tab_left.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_left.getViewConfig(tab_left.mTvTitle).setTextColorNormalResId(R.color.text_title_bar).setTextColorSelectedResId(R.color.main_color);

        tab_right.setTextTitle("未关注");
        tab_right.getViewConfig(tab_right.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_right.getViewConfig(tab_right.mTvTitle).setTextColorNormalResId(R.color.text_title_bar).setTextColorSelectedResId(R.color.main_color);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
                switch (index)
                {
                    case 0:
                        SDViewUtil.setVisible(ll_trade);
                        SDViewUtil.setGone(ll_chat_left);
                        SDViewUtil.setGone(ll_chat_right);
                        break;
                    case 1:
                        SDViewUtil.setVisible(ll_chat_left);
                        SDViewUtil.setGone(ll_chat_right);
                        SDViewUtil.setGone(ll_trade);
                        break;
                    case 2:
                        SDViewUtil.setVisible(ll_chat_right);
                        SDViewUtil.setGone(ll_chat_left);
                        SDViewUtil.setGone(ll_trade);
                        break;
                    default:
                        break;
                }
            }
        });

        mSelectManager.setItems(new SDTabUnderline[]
                {tab_trade, tab_left, tab_right});
        mSelectManager.performClick(mSelectTabIndex);
    }

    private void addContentView()
    {
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        liveChatC2CAdminView = new LiveConversationListView(getActivity());
        liveChatC2CAdminView.setAdminList(true);
        liveChatC2CAdminView.setOnItemClickListener(new LiveConversationListView.OnItemClickListener()
        {
            @Override
            public void onItemClickListener(LiveConversationListModel model)
            {
                if (onChatItemClickListener != null)
                {
                    onChatItemClickListener.onChatItemClickListener(model);
                }
            }
        });
        liveChatC2CAdminView.setTotalUnreadNumListener(new LiveConversationListView.TotalUnreadNumListener()
        {
            @Override
            public void onUnread(long num)
            {
                if (num > 0)
                {
                    SDViewUtil.setVisible(tv_admin_total);
                } else
                {
                    SDViewUtil.setGone(tv_admin_total);
                }
                tv_admin_total.setUnReadNumText(num);
            }
        });
        ll_trade.addView(liveChatC2CAdminView, lp);

        liveChatC2CLeftView = new LiveConversationListView(getActivity());
        liveChatC2CLeftView.setFollowList(true);
        liveChatC2CLeftView.setOnItemClickListener(new LiveConversationListView.OnItemClickListener()
        {
            @Override
            public void onItemClickListener(LiveConversationListModel model)
            {
                if (onChatItemClickListener != null)
                {
                    onChatItemClickListener.onChatItemClickListener(model);
                }
            }
        });
        liveChatC2CLeftView.setTotalUnreadNumListener(new LiveConversationListView.TotalUnreadNumListener()
        {
            @Override
            public void onUnread(long num)
            {
                if (num > 0)
                {
                    SDViewUtil.setVisible(tv_left_total);
                } else
                {
                    SDViewUtil.setGone(tv_left_total);
                }
                tv_left_total.setUnReadNumText(num);
            }
        });
        ll_chat_left.addView(liveChatC2CLeftView, lp);

        liveChatC2CRightView = new LiveConversationListView(getActivity());
        liveChatC2CRightView.setOnItemClickListener(new LiveConversationListView.OnItemClickListener()
        {
            @Override
            public void onItemClickListener(LiveConversationListModel model)
            {
                if (onChatItemClickListener != null)
                {
                    onChatItemClickListener.onChatItemClickListener(model);
                }
            }
        });
        liveChatC2CRightView.setTotalUnreadNumListener(new LiveConversationListView.TotalUnreadNumListener()
        {
            @Override
            public void onUnread(long num)
            {
                if (num > 0)
                {
                    SDViewUtil.setVisible(tv_right_total);
                } else
                {
                    SDViewUtil.setGone(tv_right_total);
                }
                tv_right_total.setUnReadNumText(num);

            }
        });
        ll_chat_right.addView(liveChatC2CRightView, lp);
    }

    private LiveChatC2CNewView.ClickListener clickListener;

    public interface ClickListener
    {
        void onClickBack();
    }

    public void setClickListener(LiveChatC2CNewView.ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    private OnChatItemClickListener onChatItemClickListener;

    public void setOnChatItemClickListener(OnChatItemClickListener onChatItemClickListener)
    {
        this.onChatItemClickListener = onChatItemClickListener;
    }

    public interface OnChatItemClickListener
    {
        void onChatItemClickListener(LiveConversationListModel itemLiveChatListModel);
    }
    public void showBackView(boolean show){
        if(show){
            rl_back.setVisibility(VISIBLE);
        }else{
            rl_back.setVisibility(INVISIBLE);
        }
    }
}
