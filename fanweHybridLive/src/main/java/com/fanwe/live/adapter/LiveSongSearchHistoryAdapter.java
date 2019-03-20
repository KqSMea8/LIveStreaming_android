package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import java.util.ArrayList;
import java.util.List;

public class LiveSongSearchHistoryAdapter extends SDSimpleAdapter<String>
{

    public LiveSongSearchHistoryAdapter(Activity activity)
    {
        this(new ArrayList<String>(), activity);
    }

    public LiveSongSearchHistoryAdapter(List<String> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_song_search_history;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final String model)
    {
        TextView tv = (TextView) convertView.findViewById(R.id.tv_text);
        SDViewBinder.setTextView(tv, model);
    }


}
