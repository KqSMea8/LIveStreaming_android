package com.fanwe.libgame.poker.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.fanwe.libgame.poker.model.PokerData;
import com.fanwe.libgame.poker.model.PokerGroupResultData;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 扑克牌组view
 */
public abstract class PokerGroupView extends BaseGameView implements IPokerGroupView
{
    public PokerGroupView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerGroupView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private List<PokerView> mListPokerView = new ArrayList<>();

    private void init()
    {
    }

    protected void addPokerView(PokerView view)
    {
        if (view != null)
        {
            mListPokerView.add(view);
        }
    }

    @Override
    public int getPokerCount()
    {
        return mListPokerView.size();
    }

    @Override
    public PokerView getPokerView(int position)
    {
        return SDCollectionUtil.get(mListPokerView, position);
    }

    @Override
    public void hideAllPoker()
    {
        for (PokerView item : mListPokerView)
        {
            SDViewUtil.setInvisible(item);
        }
    }

    @Override
    public void showAllPokerFront()
    {
        for (PokerView item : mListPokerView)
        {
            SDViewUtil.setVisible(item);
            item.showPokerFront();
        }
    }

    @Override
    public void showAllPokerBack()
    {
        for (PokerView item : mListPokerView)
        {
            SDViewUtil.setVisible(item);
            item.showPokerBack();
        }
    }

    @Override
    public void setResultData(PokerGroupResultData data)
    {
        if (data == null)
        {
            return;
        }
        List<PokerData> listData = data.getListPokerData();
        if (listData == null || listData.isEmpty())
        {
            return;
        }
        if (listData.size() != getPokerCount())
        {
            SDToast.showToast("牌面数据不合法");
            return;
        }
        int i = 0;
        for (PokerView item : mListPokerView)
        {
            item.setPokerData(listData.get(i));
            i++;
        }
    }

}
