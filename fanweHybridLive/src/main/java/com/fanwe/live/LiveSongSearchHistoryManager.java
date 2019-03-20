package com.fanwe.live;

import android.text.TextUtils;

import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDCollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LiveSongSearchHistoryManager
{
    private static int MAX_NUM = 10;
    private SearchSongHistory mHistory;

    public static LiveSongSearchHistoryManager getInstance()
    {
        return new LiveSongSearchHistoryManager();
    }

    public LiveSongSearchHistoryManager()
    {
        load();
    }

    public void load()
    {
        if (mHistory == null)
        {
            mHistory = SDCache.getObject(SearchSongHistory.class);
            if (mHistory == null || SDCollectionUtil.isEmpty(mHistory.mList))
            {
                mHistory = new SearchSongHistory();
            } else
            {

            }
        }

    }

    public List<String> getDatas()
    {
        return mHistory.getSortList();
    }

    public int getCount()
    {
        return mHistory.mList.size();
    }

    public boolean isEmpty()
    {
        return getCount() == 0;
    }

    public void add(String value)
    {
        if (TextUtils.isEmpty(value) || TextUtils.isEmpty(value.trim()))
        {
            return;
        }
        boolean isExist = false;
        String xString = value.trim();
        for (int i = 0; i < mHistory.mList.size(); i++)
        {
            if (mHistory.mList.get(i).value.contentEquals(xString))
            {
                mHistory.mList.get(i).time = (new Date()).getTime();
                isExist = true;
                break;
            }
        }
        if (!isExist)
        {
            HistoryItem xx = new HistoryItem();
            xx.time = (new Date()).getTime();
            xx.value = xString;
            mHistory.mList.add(0, xx);
            if (mHistory.mList.size() > MAX_NUM)
            {
                mHistory.mList.remove(mHistory.mList.size() - 1);
            }
        } else
        {

        }

        cache();
    }

    protected void cache()
    {
        if (SDCollectionUtil.isEmpty(mHistory.mList))
        {
            return;
        }
        SDCache.setObject(mHistory);
    }

    static class SearchSongHistory implements Serializable
    {
        private static final long serialVersionUID = 0L;

        public List<HistoryItem> mList;

        public SearchSongHistory()
        {
            mList = new ArrayList<>();
        }

        public List<String> getSortList()
        {
            List<String> list = new ArrayList<>();
            if (SDCollectionUtil.isEmpty(mList))
            {
                return list;
            }
            Collections.sort(mList);
            Collections.reverse(mList);

            for (int i = 0; i < mList.size(); i++)
            {
                list.add(mList.get(i).value);
            }

            return list;
        }

    }

    static class HistoryItem implements Comparable<HistoryItem>, Serializable
    {
        private static final long serialVersionUID = 0L;

        public long time;
        public String value;

        @Override
        public int compareTo(HistoryItem arg0)
        {

            Long xxLong = arg0.time;
            Long yLong = this.time;

            return yLong.compareTo(xxLong);
        }
    }
}
