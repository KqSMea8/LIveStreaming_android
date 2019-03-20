package com.weibo.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.weibo.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名 com.fanwe.xianrou.adapter
 * @描述
 * @作者 Su
 * @创建时间 2017/3/10 14:45
 **/
public abstract class XRBaseDisplayAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements com.weibo.interfaces.XRCommonItemClickCallback<E>
{
    public static final int POSITION_NONE = -1;

    private Context context;
    private RecyclerView recyclerView;
    private List<E> dataList = new ArrayList<>();
    private int selectedPosition = -1;
    private com.weibo.interfaces.XRCommonItemClickCallback<E> XRCommonItemClickCallback;

    protected abstract VH createVH(ViewGroup parent, int viewType);

    protected abstract void bindVH(VH viewHolder, E entity, int position);

    protected abstract void onDataListChanged();


    public XRBaseDisplayAdapter(Context context)
    {
        this.context = context.getApplicationContext();
    }

    public XRBaseDisplayAdapter(Context context, List<E> dataList)
    {
        this.context = context.getApplicationContext();
        this.setDataList(dataList);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return createVH(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position)
    {
        bindVH(holder, dataList.get(position), position);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (ViewUtil.isFastClick())
                {
                    return;
                }
                onItemClick(view, getItemEntity(position), position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public Context getContext()
    {
        return context;
    }

    public List<E> getDataList()
    {
        return this.dataList;
    }

    public boolean isEmpty()
    {
        return this.dataList.isEmpty();
    }

    public void setDataList(@NonNull List<E> dataList)
    {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        onDataListChanged();
    }

    public void addData(@NonNull E data)
    {
        dataList.add(data);
        onDataListChanged();
    }

    public void addData(@NonNull E data, @IntRange(from = 0) int position)
    {
        if (position < 0 || position > getItemCount())
        {
            return;
        }

        dataList.add(position, data);
        onDataListChanged();
    }

    public void addDataList(@NonNull List<E> dataList)
    {
        this.dataList.addAll(dataList);
        onDataListChanged();
    }

    public RecyclerView getRecyclerView()
    {
        return recyclerView;
    }

    public E getItemEntity(int position)
    {
        if (null == getDataList())
        {
            return null;
        }
        return dataList.get(position);
    }

    public int getSelectedPosition()
    {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition, boolean animNotify)
    {
        if (this.selectedPosition == selectedPosition)
        {
            return;
        }

        if (this.selectedPosition != POSITION_NONE && animNotify)
        {
            notifyItemChanged(this.selectedPosition);
        }

        this.selectedPosition = selectedPosition;

        if (animNotify)
        {
            notifyItemChanged(this.selectedPosition);
        }
    }

    public void removeItem(int position)
    {
        removeItem(position, true);
    }

    public void removeItem(int position, boolean anim)
    {
        if (position < 0 || position >= dataList.size())
        {
            return;
        }

        dataList.remove(position);

        if (anim)
        {
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataList.size());
        } else
        {
            notifyDataSetChanged();
        }

        if (position == selectedPosition)
        {
            selectedPosition = -1;
        }
    }

    public void clear()
    {
        if (dataList.isEmpty())
        {
            return;
        }

        dataList.clear();
        notifyDataSetChanged();
    }

}
