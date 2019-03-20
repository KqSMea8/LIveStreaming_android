package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.InviteTypeItemModel;

import java.util.List;

/**
 * Created by yhz on 2017/04/10
 */
public class LiveInviteTypeAdapter extends SDSimpleAdapter<InviteTypeItemModel>
{
    public LiveInviteTypeAdapter(List<InviteTypeItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_user_center_authent;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final InviteTypeItemModel model)
    {
        TextView item_invite_type = get(R.id.item_authentication_type, convertView);
        SDViewBinder.setTextView(item_invite_type, model.getName());
    }
}
