package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.live.R;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

public class LiveFriendsListAdapter extends SDSimpleAdapter<UserModel>
{

    public static final int MODE_INVITE_FRIENDS = 0;
    public static final int MODE_REMOVE_VIEWER = 1;

    private int mMode;

    public LiveFriendsListAdapter(List<UserModel> listModel, Activity activity, int mode)
    {
        super(listModel, activity);
        getSelectManager().setMode(Mode.MULTI);
        this.mMode = mode;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_private_live_invite_friends;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent,
                         UserModel model)
    {
        if (mMode == MODE_REMOVE_VIEWER)
        {
            convertView.setBackgroundResource(R.color.item_remove_viewer);
        }
        ImageView iv_check = get(R.id.iv_check, convertView);
        ImageView iv_head_img = get(R.id.civ_head_img, convertView);
        TextView tv_nick_name = get(R.id.tv_nick_name, convertView);
        ImageView iv_sexy = get(R.id.iv_global_male, convertView);
        ImageView iv_level = get(R.id.iv_rank, convertView);
        TextView tv_user_sign = get(R.id.tv_user_sign, convertView);
        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head_img);
        tv_nick_name.setText(model.getNick_name());
        iv_sexy.setImageResource(model.getSexResId());
        iv_level.setImageResource(model.getLevelImageResId());
        tv_user_sign.setText(model.getSignature());
        if (model.isSelected())
        {
            iv_check.setImageResource(R.drawable.ic_me_following);
        } else
        {
            iv_check.setImageResource(R.drawable.ic_live_pop_choose_off);
        }
    }
}
