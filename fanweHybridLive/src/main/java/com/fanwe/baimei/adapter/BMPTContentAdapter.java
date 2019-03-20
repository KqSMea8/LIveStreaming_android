package com.fanwe.baimei.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.RankUserItemModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;
import com.fanwe.live.view.LiveStringTicketTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhz on 2017/5/25.
 */

public class BMPTContentAdapter extends SDSimpleAdapter<RankUserItemModel>
{
    private int type;
    private  PositionInterface listener;
    public interface PositionInterface{
        void getPosition(int position);
    }
    public void setPoistionInterface(PositionInterface listener)
    {
        this.listener = listener;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public BMPTContentAdapter(List<RankUserItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            case 0:
                return position;
            case 1:
                return position;
            case 2:
                return position;
            default:
                return 3;
        }

    }

    @Override
    public int getViewTypeCount()
    {
        return 4;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        int layout;
        if (position == 0)
        {
            layout = R.layout.item_cont_first;
        } else if (position == 1)
        {
            layout = R.layout.item_cont_second;
        } else if (position == 2)
        {
            layout = R.layout.item_cont_third;
        } else
        {
            layout = R.layout.item_cont;
        }
        return layout;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final RankUserItemModel model)
    {
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemClickCallback(position, model, convertView);
            }
        });
        switch (getItemViewType(position))
        {
            case 0:
                bindFirstData(position, convertView, parent, model);
                break;
            case 1:
                bindSecondData(position, convertView, parent, model);
                break;
            case 2:
                bindThirdData(position, convertView, parent, model);
                break;
            default:
                binddefaultData(position, convertView, parent, model);
                break;
        }
    }

    private void setTypeContent(View convertView)
    {
        TextView tv_type = get(R.id.tv_type, convertView);
        LiveStringTicketTextView tv_type_text = get(R.id.tv_type_text, convertView);

        if (type == 1)
        {
            tv_type.setText("贡献");
            tv_type_text.setText("秀豆");
        } else
        {
            tv_type.setText("获得");
        }
    }

    private void bindFirstData(final int position, View convertView, ViewGroup parent, final RankUserItemModel model)
    {
        setTypeContent(convertView);

        ImageView civ_head_first = get(R.id.civ_head_first, convertView);
        ImageView civ_level_first = get(R.id.civ_level_first, convertView);
        TextView tv_nickname_first = get(R.id.tv_nickname_first, convertView);
        ImageView iv_global_male_first = get(R.id.iv_global_male_first, convertView);
        ImageView iv_rank_first = get(R.id.iv_rank_first, convertView);
        TextView tv_num_first = get(R.id.tv_num_first, convertView);
        final ImageView iv_follow = get(R.id.iv_follow,convertView);


        if(model.getFocus() == 0){
            iv_follow.setImageResource(R.drawable.follow_on);
        }else{
            iv_follow.setImageResource(R.drawable.follow_off);
        }

        iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getFocus() == 0 ){
                    iv_follow.setImageResource(R.drawable.follow_off);
                }else
                {
                    iv_follow.setImageResource(R.drawable.follow_on);
                }
                listener.getPosition(position);
            }
        });

        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_first);


        if (TextUtils.isEmpty(model.getV_icon()))
        {
            SDViewUtil.setGone(civ_level_first);
        } else
        {
            SDViewUtil.show(civ_level_first);
            GlideUtil.load(model.getV_icon()).into(civ_level_first);
        }

        SDViewBinder.setTextView(tv_nickname_first, model.getNick_name(), "你未设置昵称");
        if (model.getSex() == 1)
        {
            iv_global_male_first.setImageResource(R.drawable.ic_global_male);
        } else if (model.getSex() == 2)
        {
            iv_global_male_first.setImageResource(R.drawable.ic_global_female);
        } else
        {
            SDViewUtil.setGone(iv_global_male_first);
        }
        iv_rank_first.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
        SDViewBinder.setTextView(tv_num_first, model.getTicket() + "");
    }

    private void bindSecondData(final int position, View convertView, ViewGroup parent, final RankUserItemModel model)
    {
        setTypeContent(convertView);

        ImageView civ_head_second = get(R.id.civ_head_second, convertView);
        ImageView civ_level_second = get(R.id.civ_level_second, convertView);
        TextView tv_nickname_second = get(R.id.tv_nickname_second, convertView);
        ImageView iv_global_male_second = get(R.id.iv_global_male_second, convertView);
        ImageView iv_rank_second = get(R.id.iv_rank_second, convertView);
        TextView tv_num_second = get(R.id.tv_num_second, convertView);
        final ImageView iv_follow = get(R.id.iv_follow,convertView);


        if(model.getFocus() == 0){
            iv_follow.setImageResource(R.drawable.follow_on);
        }else{
            iv_follow.setImageResource(R.drawable.follow_off);
        }

        iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getFocus() == 0 ){
                    iv_follow.setImageResource(R.drawable.follow_off);
                }else
                {
                    iv_follow.setImageResource(R.drawable.follow_on);
                }
                listener.getPosition(position);
            }
        });

        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_second);

        if (TextUtils.isEmpty(model.getV_icon()))
        {
            SDViewUtil.setGone(civ_level_second);
        } else
        {
            SDViewUtil.show(civ_level_second);
            GlideUtil.load(model.getV_icon()).into(civ_level_second);
        }

        SDViewBinder.setTextView(tv_nickname_second, model.getNick_name(), "你未设置昵称");
        if (model.getSex() == 1)
        {
            iv_global_male_second.setImageResource(R.drawable.ic_global_male);
        } else if (model.getSex() == 2)
        {
            iv_global_male_second.setImageResource(R.drawable.ic_global_female);
        } else
        {
            SDViewUtil.setGone(iv_global_male_second);
        }
        iv_rank_second.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
        SDViewBinder.setTextView(tv_num_second, model.getTicket() + "");
    }

    private void bindThirdData(final int position, View convertView, ViewGroup parent, final RankUserItemModel model)
    {
        setTypeContent(convertView);

        ImageView civ_head_third = get(R.id.civ_head_third, convertView);
        ImageView civ_level_third = get(R.id.civ_level_third, convertView);
        TextView tv_nickname_third = get(R.id.tv_nickname_third, convertView);
        ImageView iv_global_male_third = get(R.id.iv_global_male_third, convertView);
        ImageView iv_rank_third = get(R.id.iv_rank_third, convertView);
        TextView tv_num_third = get(R.id.tv_num_third, convertView);
        final ImageView iv_follow = get(R.id.iv_follow,convertView);


        if(model.getFocus() == 0){
            iv_follow.setImageResource(R.drawable.follow_on);
        }else{
            iv_follow.setImageResource(R.drawable.follow_off);
        }

        iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getFocus() == 0 ){
                    iv_follow.setImageResource(R.drawable.follow_off);
                }else
                {
                    iv_follow.setImageResource(R.drawable.follow_on);
                }
                listener.getPosition(position);
            }
        });

        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_third);

        if (TextUtils.isEmpty(model.getV_icon()))
        {
            SDViewUtil.setGone(civ_level_third);
        } else
        {
            SDViewUtil.show(civ_level_third);
            GlideUtil.load(model.getV_icon()).into(civ_level_third);
        }

        SDViewBinder.setTextView(tv_nickname_third, model.getNick_name(), "你未设置昵称");
        if (model.getSex() == 1)
        {
            iv_global_male_third.setImageResource(R.drawable.ic_global_male);
        } else if (model.getSex() == 2)
        {
            iv_global_male_third.setImageResource(R.drawable.ic_global_female);
        } else
        {
            SDViewUtil.setGone(iv_global_male_third);
        }
        iv_rank_third.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
        SDViewBinder.setTextView(tv_num_third, model.getTicket() + "");

    }

    private void binddefaultData(final int position, View convertView, ViewGroup parent, final RankUserItemModel model)
    {
        setTypeContent(convertView);

        TextView tv_position_other = get(R.id.tv_position_other, convertView);
        ImageView civ_head_other = get(R.id.civ_head_other, convertView);
        ImageView civ_level_other = get(R.id.civ_level_other, convertView);
        TextView tv_nickname_other = get(R.id.tv_nick_name, convertView);
        ImageView iv_global_male_other = get(R.id.iv_global_male, convertView);
        ImageView iv_rank_other = get(R.id.iv_rank, convertView);
        TextView tv_num_other = get(R.id.tv_num_other, convertView);

        final ImageView iv_follow = get(R.id.iv_follow,convertView);


        if(model.getFocus() == 0){
            iv_follow.setImageResource(R.drawable.follow_on);
        }else{
            iv_follow.setImageResource(R.drawable.follow_off);
        }

        iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getFocus() == 0 ){
                    iv_follow.setImageResource(R.drawable.follow_off);
                }else
                {
                    iv_follow.setImageResource(R.drawable.follow_on);
                }
                listener.getPosition(position);
            }
        });

        int number = position + 1;
        SDViewBinder.setTextView(tv_position_other, number + "");
        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_other);

        if (TextUtils.isEmpty(model.getV_icon()))
        {
            SDViewUtil.setGone(civ_level_other);
        } else
        {
            SDViewUtil.show(civ_level_other);
            GlideUtil.load(model.getV_icon()).into(civ_level_other);
        }

        SDViewBinder.setTextView(tv_nickname_other, model.getNick_name(), "你未设置昵称");
        if (model.getSex() == 1)
        {
            iv_global_male_other.setImageResource(R.drawable.ic_global_male);
        } else if (model.getSex() == 2)
        {
            iv_global_male_other.setImageResource(R.drawable.ic_global_female);
        } else
        {
            SDViewUtil.setGone(iv_global_male_other);
        }
        iv_rank_other.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
        SDViewBinder.setTextView(tv_num_other, model.getTicket() + "");
    }
}
