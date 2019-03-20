package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weibo.util.ViewUtil;


/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/10 14:11
 **/
public abstract class XRBaseViewHolder<E>
        extends RecyclerView.ViewHolder
        implements View.OnClickListener
{
    private E entity;
    private int entityPosition;

    public abstract void bindData(Context context, E entity, int position);

//
//    public abstract @LayoutRes int getViewHolderLayout();
//
//    public XRBaseViewHolder(ViewGroup parent){
//        super(LayoutInflater.from(parent.getContext())
//                .inflate(getViewHolderLayout(),parent,false));
//    }

    public XRBaseViewHolder(ViewGroup parent, @LayoutRes int layout)
    {
        super(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    protected View findViewById(@IdRes int id){
        return itemView.findViewById(id);
    }

    @CallSuper
    @Override
    public void onClick(View view)
    {
        if (ViewUtil.isFastClick())
        {
            return;
        }
    }

    public E getHolderEntity()
    {
        return entity;
    }

    protected void setHolderEntity(E entity)
    {
        this.entity = entity;
    }

    public int getHolderEntityPosition()
    {
        return entityPosition;
    }

    protected void setHolderEntityPosition(int entityPosition)
    {
        this.entityPosition = entityPosition;
    }

    public interface XRBaseViewHolderCallback<E>
    {
        void onViewHolderItemClick(View itemView, E entity, int position);
    }

}