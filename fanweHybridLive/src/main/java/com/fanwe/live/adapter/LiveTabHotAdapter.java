package com.fanwe.live.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDViewHolderAdapter;
import com.fanwe.library.adapter.viewholder.SDViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveTopicRoomActivity;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

public class LiveTabHotAdapter extends SDViewHolderAdapter<LiveRoomModel>
{

    public LiveTabHotAdapter(List<LiveRoomModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public SDViewHolder<LiveRoomModel> onCreateViewHolder(int position, View convertView, ViewGroup parent)
    {
        return new ViewHolder();
    }

    @Override
    public void onBindData(int position, View convertView, ViewGroup parent, LiveRoomModel model, SDViewHolder<LiveRoomModel> holder)
    {

    }

    public static class ViewHolder extends SDViewHolder<LiveRoomModel>
    {

        ImageView iv_head;
        ImageView iv_head_small;
        TextView tv_nickname;
        TextView tv_city;
        TextView tv_watch_number,tv_desc;
        ImageView iv_room_image;
        TextView tv_topic;

        //直播各种状态
        TextView tv_live_state;
        TextView tv_live_type,tv_live_hot;
        TextView tv_live_fee;
        ImageView iv_inpk;
        ImageView iv_gameing;

        ImageView iv_meda1,iv_meda2,iv_meda3;
        View bottomSpace;
        @Override
        public int getLayoutId(int position, View convertView, ViewGroup parent)
        {
            return R.layout.item_live_tab_hot;
        }

        @Override
        public void onInit(int position, View convertView, ViewGroup parent)
        {
            iv_head = find(R.id.iv_head);
            iv_head_small = find(R.id.iv_head_small);
            tv_nickname = find(R.id.tv_nickname);
            tv_city = find(R.id.tv_city);
            tv_watch_number = find(R.id.tv_watch_number);
            tv_desc = find(R.id.tv_desc);
            iv_room_image = find(R.id.iv_room_image);
            tv_topic = find(R.id.tv_topic);
            tv_live_state = find(R.id.tv_live_state);
            tv_live_hot = find(R.id.tv_live_hot);
            tv_live_type = find(R.id.tv_live_type);
            tv_live_fee = find(R.id.tv_live_fee);
            iv_gameing = find(R.id.iv_gaming);
            iv_inpk=find(R.id.iv_pk);

            iv_meda1=find(R.id.iv_meda1);
            iv_meda2=find(R.id.iv_meda2);
            iv_meda3=find(R.id.iv_meda3);
            bottomSpace = findViewById(R.id.space_item_live_tab_hot);
        }

        @Override
        public void onBindData(int position, View convertView, ViewGroup parent, final LiveRoomModel model)
        {
            SDViewUtil.setVisibleOrGone(bottomSpace, position == getAdapter().getCount() - 1);

            GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head);
            if (!TextUtils.isEmpty(model.getV_icon()))
            {
                SDViewUtil.setVisible(iv_head_small);
                GlideUtil.load(model.getV_icon()).into(iv_head_small);
            } else
            {
                SDViewUtil.setGone(iv_head_small);
            }
            if("1".equals(model.getIn_livepk())){
                SDViewUtil.setVisible(iv_inpk);
            }else{
                SDViewUtil.setGone(iv_inpk);
            }
            SDViewBinder.setTextViewVisibleOrGone(tv_live_state, model.getLive_state());
            SDViewBinder.setTextViewVisibleOrGone(tv_live_type, model.getClass_name());
            SDViewBinder.setTextViewVisibleOrGone(tv_live_fee, model.getLivePayFee());


            if(model.getHeat_rank()<20){
                SDViewBinder.setTextView(tv_live_hot, "热 "+model.getHeat_rank());
            }else{
                SDViewBinder.setTextView(tv_live_hot, "热 20+");
            }
            SDViewBinder.setTextView(tv_nickname, model.getNick_name());
            SDViewBinder.setTextView(tv_city, model.getCity());
            SDViewBinder.setTextView(tv_watch_number, model.getWatch_number());
            GlideUtil.load(model.getLive_image()).into(iv_room_image);

            if (model.getCate_id() > 0)
            {
                tv_topic.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getActivity(), LiveTopicRoomActivity.class);
                        intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_ID, model.getCate_id());
                        intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_TITLE, model.getTitle());
                        getActivity().startActivity(intent);
                    }
                });
                SDViewBinder.setTextView(tv_topic, model.getTitle());
                SDViewUtil.setVisible(tv_topic);
            } else
            {
                SDViewUtil.setGone(tv_topic);
            }

            if (model.getIs_gaming() == 1)
            {
                SDViewUtil.setVisible(iv_gameing);
            } else
            {
                SDViewUtil.setGone(iv_gameing);
            }

            iv_head.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                    intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, model.getUser_id());
                    intent.putExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL, model.getHead_image());
                    getActivity().startActivity(intent);
                }
            });
            iv_room_image.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AppRuntimeWorker.joinRoom(model, getActivity());
                }
            });
            if(null!=model.getMedals()){
                iv_meda1.setVisibility(View.GONE);
                iv_meda2.setVisibility(View.GONE);
                iv_meda3.setVisibility(View.GONE);
                for(int i=0;i<model.getMedals().size();i++){
                    switch (i){
                        case 0:
                            iv_meda1.setVisibility(View.VISIBLE);
                            GlideUtil.load(model.getMedals().get(i)).into(iv_meda1);
                            break;
                        case 1:
                            iv_meda2.setVisibility(View.VISIBLE);
                            GlideUtil.load(model.getMedals().get(i)).into(iv_meda2);
                            break;
                        case 2:
                            iv_meda3.setVisibility(View.VISIBLE);
                            GlideUtil.load(model.getMedals().get(i)).into(iv_meda3);
                            break;
                    }
                }
            }else{
                iv_meda1.setVisibility(View.GONE);
                iv_meda2.setVisibility(View.GONE);
                iv_meda3.setVisibility(View.GONE);
            }
            SDViewUtil.setVisibleOrGone(bottomSpace, position == getAdapter().getCount() - 1);
        }
    }
}
