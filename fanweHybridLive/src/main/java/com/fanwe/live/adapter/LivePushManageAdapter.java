package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.live.R;
import com.fanwe.live.model.UserModel;

import java.util.List;

/**
 * Created by shibx on 2016/7/12.
 */
public class LivePushManageAdapter extends SDSimpleAdapter<Integer>
{

    public LivePushManageAdapter(List<Integer> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_push_list;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, Integer model)
    {

    }
}
