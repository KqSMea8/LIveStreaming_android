package com.fanwe.live.appview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.listener.SDItemLongClickCallback;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveConversationListAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.model.App_BaseInfoActModel;
import com.fanwe.live.model.App_my_follow_ActModel;
import com.fanwe.live.model.LiveConversationListModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;


/**
 * 会话列表view
 */
public class LiveConversationListView extends BaseAppView {
    private SDProgressPullToRefreshListView listView;
    private LiveConversationListAdapter adapter;
    private List<LiveConversationListModel> listModel = new ArrayList<>();

    private List<UserModel> listFocus = new ArrayList<>();
    private List<MsgModel> listMsgFollow = new ArrayList<>();
    private List<MsgModel> listMsgUnknow = new ArrayList<>();
    private List<MsgModel> listMsgAdmin = new ArrayList<>();
    private Object lock = new Object();

    /**
     * 是否是关注的聊天列表
     */
    private boolean isFollowList;
    private boolean isAdminList;
    public LiveConversationListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LiveConversationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiveConversationListView(Context context) {
        super(context);
        init();
    }

    public void setFollowList(boolean followList) {
        isFollowList = followList;
    }
    public void setAdminList(boolean adminList) {
        isAdminList = adminList;
    }
    protected void init() {
        setContentView(R.layout.view_live_chat_c2c_list);

        listView = find(R.id.list);
        bindAdapter();
    }

    private void bindAdapter() {
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestMyFollow();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        adapter = new LiveConversationListAdapter(listModel, getActivity());
        adapter.setItemClickCallback(new SDItemClickCallback<LiveConversationListModel>() {
            @Override
            public void onItemClick(int position, LiveConversationListModel item, View view) {
                if (onItemClickListener != null) {
                    IMHelper.setSingleC2CReadMessage(item.getPeer(), false);
                    item.updateUnreadNumber();
                    adapter.updateData(adapter.indexOf(item));
                    notifyTotalUnreadNumListener();
                    onItemClickListener.onItemClickListener(item);
                }
            }
        });
        adapter.setItemLongClickCallback(new SDItemLongClickCallback<LiveConversationListModel>() {
            @Override
            public void onItemLongClick(int position, LiveConversationListModel item, View view) {
                LiveConversationListModel model = adapter.getItem(position);
                showBotDialog(model);
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        requestData();
    }

    public void requestData() {
        requestMyFollow();
    }

    private void requestMyFollow() {
        CommonInterface.requestMyFollow(new AppRequestCallback<App_my_follow_ActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    listFocus = actModel.getList();
                    filterMsg(actModel.getList());
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                listView.onRefreshComplete();
            }
        });
    }

    /**
     * 筛选过滤msg
     *
     * @param listFollow 关注列表
     */
    private void filterMsg(final List<UserModel> listFollow) {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>() {
            @Override
            public String onBackground() {
                synchronized (lock) {
                    List<MsgModel> listMsg = IMHelper.getC2CMsgList();
                    if (listMsg != null) {
                        listMsgFollow.clear();
                        listMsgUnknow.clear();
                        listMsgAdmin.clear();
                        for (MsgModel msg : listMsg) {
                            boolean isFollow = false;
                            boolean isAdmin = false;
                            String peer = msg.getConversationPeer();
                            if(1==msg.getCustomMsg().getSender().getIs_admin()){
                                isAdmin=true;
                            }else{
                                for (UserModel user : listFollow) {
                                    if (peer.equals(user.getUser_id())) {
                                        isFollow = true;
                                        break;
                                    }
                                }
                            }
                            if (isAdmin) {
                                listMsgAdmin.add(msg);
                            } else if (isFollow) {
                                // 好友
                                listMsgFollow.add(msg);
                            } else {
                                listMsgUnknow.add(msg);
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            public void onMainThread(String result) {
                if(isAdminList){
                    dealFilterResult(listMsgAdmin);
                }else if  (isFollowList) {
                    dealFilterResult(listMsgFollow);
                } else {
                    dealFilterResult(listMsgUnknow);
                }
            }
        });
    }

    /**
     * 处理筛选结果
     *
     * @param listMsg
     */
    private void dealFilterResult(final List<MsgModel> listMsg) {
        synchronized (lock) {
            if (SDCollectionUtil.isEmpty(listMsg)) {
                listModel.clear();
                notifyAdapter();
                return;
            }
        }

        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>() {
            @Override
            public String onBackground() {
                synchronized (lock) {
                    listModel.clear();
                    StringBuilder sb = new StringBuilder();
                    for (MsgModel msg : listMsg) {
                        LiveConversationListModel model = new LiveConversationListModel();
                        model.fillValue(msg);
                        listModel.add(model);
                        sb.append(msg.getConversationPeer()).append(",");
                    }
                    sb.deleteCharAt(sb.lastIndexOf(","));

                    return sb.toString();
                }
            }

            @Override
            public void onMainThread(String ids) {
                requestBaseInfo(ids);
            }
        });
    }

    /**
     * 请求用户的基本信息
     */
    private void requestBaseInfo(String ids) {
        if (ids == null) {
            return;
        }
        CommonInterface.requestBaseInfo(ids, new AppRequestCallback<App_BaseInfoActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>() {
                        @Override
                        public String onBackground() {
                            synchronized (lock) {
                                List<UserModel> listUser = actModel.getList();
                                if (!SDCollectionUtil.isEmpty(listUser)) {
                                    for (UserModel user : listUser) {
                                        for (LiveConversationListModel model : listModel) {
                                            if (user.getUser_id().equals(model.getUser_id())) {
                                                model.fillValue(user);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            return null;
                        }

                        @Override
                        public void onMainThread(String result) {
                            notifyAdapter();
                            notifyTotalUnreadNumListener();
                        }
                    });
                }
            }
        });
    }

    public void onEventMainThread(EImOnNewMessages event) {
        MsgModel msg = event.msg;
        if (msg.isLocalPost()) {
        } else {
            if (msg.isPrivateMsg()) {
                dealNewMsg(msg);
            }
        }
    }

    private void dealNewMsg(final MsgModel msg) {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<LiveConversationListModel>() {
            @Override
            public LiveConversationListModel onBackground() {
                synchronized (lock) {
                    int containIndex = -1;
                    for (int i = 0; i < listModel.size(); i++) {
                        LiveConversationListModel item = listModel.get(i);
                        if (msg.getConversationPeer().equals(item.getPeer())) {
                            containIndex = i; // 列表中存在
                            break;
                        }
                    }

                    LiveConversationListModel model = null;
                    if (containIndex < 0) {
                        boolean isFollowMsg = false;
                        for (UserModel user : listFocus) {
                            if (user.getUser_id().equals(msg.getConversationPeer())) {
                                isFollowMsg = true;
                                break;
                            }
                        }

                        if (isFollowList) {
                            if (isFollowMsg) {
                                model = new LiveConversationListModel();
                            }
                        } else {
                            if (!isFollowMsg) {
                                model = new LiveConversationListModel();
                            }
                        }
                    } else {
                        model = listModel.remove(containIndex);
                    }

                    if (model != null) {
                        model.fillValue(msg);
                        listModel.add(0, model);
                    }
                    return model;
                }
            }

            @Override
            public void onMainThread(LiveConversationListModel model) {
                if (model != null) {
                    notifyAdapter();
                    notifyTotalUnreadNumListener();
                }
            }
        });
    }

    /**
     * 刷新ListView
     */
    public void notifyAdapter() {
        synchronized (lock) {
            adapter.updateData(listModel);
        }
    }

    /**
     * 计算，并通知总未读数量
     */
    public void notifyTotalUnreadNumListener() {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<Integer>() {
            @Override
            public Integer onBackground() {
                synchronized (lock) {
                    int number = 0;
                    for (LiveConversationListModel item : listModel) {
                        item.updateUnreadNumber();
                        number += item.getUnreadNum();
                    }
                    return number;
                }
            }

            @Override
            public void onMainThread(Integer result) {
                if (result != null) {
                    if (totalUnreadNumListener != null) {
                        totalUnreadNumListener.onUnread(result);
                    }
                }
            }
        });
    }

    protected void showBotDialog(final LiveConversationListModel model) {
        SDDialogMenu dialog = new SDDialogMenu(getActivity());
        String[] arrOption = new String[]{"删除"};
        dialog.setItems(arrOption);
        dialog.setmListener(new SDDialogMenu.SDDialogMenuListener() {

            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog) {
                switch (index) {
                    case 0:
                        IMHelper.deleteConversationAndLocalMsgsC2C(model.getPeer());
                        synchronized (lock) {
                            adapter.removeData(model);
                        }
                        notifyTotalUnreadNumListener();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog) {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog) {
            }
        });
        dialog.showBottom();
    }

    private OnItemClickListener onItemClickListener;
    private TotalUnreadNumListener totalUnreadNumListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setTotalUnreadNumListener(TotalUnreadNumListener totalUnreadNumListener) {
        this.totalUnreadNumListener = totalUnreadNumListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(LiveConversationListModel model);
    }

    public interface TotalUnreadNumListener {
        void onUnread(long num);
    }
}
