package com.fanwe.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDViewHolderAdapter;
import com.fanwe.library.adapter.viewholder.SDViewHolder;
import com.fanwe.library.event.EOnClick;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LivePlaybackModel;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.model.PlayBackData;
import com.fanwe.live.utils.GlideUtil;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LiveTabFollowAdapter extends SDViewHolderAdapter<Object>
{
    private static final int TOTAL_NEED_FIND_TYPE_COUNT = 2;
    private static SparseArray<Integer> arrTypeFirstPositon = new SparseArray<>();

    public LiveTabFollowAdapter(List<Object> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public SDViewHolder<Object> onCreateViewHolder(int position, View convertView, ViewGroup parent)
    {
        int type = getItemViewType(position);
        SDViewHolder viewHolder = null;
        switch (type)
        {
            case 0:
                viewHolder = new ViewHolderNoLive();
                break;
            case 1:
                viewHolder = new ViewHolderLiveRoom();
                break;
            case 2:
                viewHolder = new ViewHolderPlayback();
                break;
            default:

                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindData(int position, View convertView, ViewGroup parent, Object model, SDViewHolder<Object> holder)
    {

    }

    @Override
    public void setData(List<Object> list)
    {
        arrTypeFirstPositon.clear();
        super.setData(list);
        findFirstPosition();
    }

    private void findFirstPosition()
    {
        int findTypeCount = 0;
        int i = 0;
        for (Object item : getData())
        {
            int type = getItemViewType(i);
            boolean needFind = false;
            if (item instanceof LiveRoomModel)
            {
                needFind = true;
            } else if (item instanceof LivePlaybackModel)
            {
                needFind = true;
            }

            if (needFind)
            {
                Integer typePos = arrTypeFirstPositon.get(type);
                if (typePos == null)
                {
                    arrTypeFirstPositon.put(type, i);
                    findTypeCount++;
                }
            }

            if (TOTAL_NEED_FIND_TYPE_COUNT == findTypeCount)
            {
                break;
            }
            i++;
        }
    }


    @Override
    public int getItemViewType(int position)
    {
        Object model = getItem(position);

        if (model instanceof TypeNoLiveRoomModel)
        {
            return 0;
        } else if (model instanceof LiveRoomModel)
        {
            return 1;
        } else if (model instanceof LivePlaybackModel)
        {
            return 2;
        }

        return 0;
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }


    public static class TypeNoLiveRoomModel
    {

    }

    public static class ViewHolderNoLive extends SDViewHolder<TypeNoLiveRoomModel>
    {

        TextView tv_tab_live_follow_goto_live;
        ImageView iv_no_live;

        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_live_tab_follow_no_live;
        }

        @Override
        public void onInit(int position, View convertView, ViewGroup parent)
        {
            tv_tab_live_follow_goto_live = find(R.id.tv_tab_live_follow_goto_live);
            iv_no_live = find(R.id.iv_no_live);
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, TypeNoLiveRoomModel model)
        {
            tv_tab_live_follow_goto_live.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EOnClick event = new EOnClick(v);
                    SDEventManager.post(event);
                }
            });
        }
    }

    /**
     * 直播间
     */
    public static class ViewHolderLiveRoom extends LiveTabHotAdapter.ViewHolder
    {

        View ll_top;

        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_live_tab_follow_room;
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, LiveRoomModel model)
        {
            ll_top = find(R.id.ll_top);

            int type = getAdapter().getItemViewType(position);
            Integer typePosition = arrTypeFirstPositon.get(type);
            if (typePosition != null && typePosition == position)
            {
                SDViewUtil.setVisible(ll_top);
            } else
            {
                SDViewUtil.setGone(ll_top);
            }

            super.onBindData(position, convertView, parent, model);
        }
    }


    /**
     * 回放
     */
    public static class ViewHolderPlayback extends SDViewHolder<LivePlaybackModel>
    {

        View ll_top;

        LinearLayout ll_content;
        ImageView iv_head;
        ImageView iv_head_small;
        TextView tv_nickname;
        TextView tv_create_time;
        TextView tv_watch_number;
        TextView tv_topic;

        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_live_tab_follow_playback;
        }

        @Override
        public void onInit(int position, View convertView, ViewGroup parent)
        {
            ll_top = find(R.id.ll_top);
            ll_content = find(R.id.ll_content);
            iv_head = find(R.id.iv_head);
            iv_head_small = find(R.id.iv_head_small);
            tv_nickname = find(R.id.tv_nickname);
            tv_create_time = find(R.id.tv_create_time);
            tv_watch_number = find(R.id.tv_watch_number);
            tv_topic = find(R.id.tv_topic);
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, final LivePlaybackModel model)
        {
            int type = getAdapter().getItemViewType(position);
            Integer typePosition = arrTypeFirstPositon.get(type);
            if (typePosition != null && typePosition == position)
            {
                SDViewUtil.setVisible(ll_top);
            } else
            {
                SDViewUtil.setGone(ll_top);
            }

            GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head);
            if (!TextUtils.isEmpty(model.getV_icon()))
            {
                SDViewUtil.setVisible(iv_head_small);
                GlideUtil.load(model.getV_icon()).into(iv_head_small);
            } else
            {
                SDViewUtil.setGone(iv_head_small);
            }

            SDViewBinder.setTextView(tv_nickname, model.getNick_name());
            SDViewBinder.setTextView(tv_create_time, model.getBegin_time_format());
            SDViewBinder.setTextView(tv_watch_number, model.getWatch_number_format());
            SDViewBinder.setTextView(tv_topic, model.getTitle());

            ll_content.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    PlayBackData data = new PlayBackData();
                    data.setRoomId(model.getRoom_id());
                    AppRuntimeWorker.startPlayback(data, getActivity());
                }
            });
        }
    }


}
