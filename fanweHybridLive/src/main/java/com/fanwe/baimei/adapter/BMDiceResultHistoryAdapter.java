package com.fanwe.baimei.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.fanwe.baimei.adapter.holder.BMDiceResultHistoryViewHolder;
import com.fanwe.baimei.model.BMDiceResultHistoryModel;
import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;

/**
 * 包名: com.librarygames.adpter
 * 描述: 猜大小结果历史列表适配器
 * 作者: Su
 * 创建时间: 2017/5/27 17:06
 **/
public class BMDiceResultHistoryAdapter extends SDRecyclerAdapter<BMDiceResultHistoryModel>
{
    public BMDiceResultHistoryAdapter(Activity activity)
    {
        super(activity);
    }

    @Override
    public SDRecyclerViewHolder<BMDiceResultHistoryModel> onCreateVHolder(ViewGroup parent, int viewType)
    {
        return new BMDiceResultHistoryViewHolder(parent);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<BMDiceResultHistoryModel> holder, int position, BMDiceResultHistoryModel model)
    {
        BMDiceResultHistoryViewHolder viewHolder= (BMDiceResultHistoryViewHolder) holder;
        viewHolder.onBindData(position, model);
    }

}
