package com.fanwe.live.appview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.holder.CallbackHolder;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.model.ImageModel;
import com.fanwe.library.utils.LocalImageFinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDAppView;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地图库加载view
 * <p/>
 * Created by Administrator on 2016/9/6.
 */
public class SDSelectImageView extends SDAppView
{
    public SDSelectImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SDSelectImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDSelectImageView(Context context)
    {
        super(context);
        init();
    }

    public final CallbackHolder<SelectImageCallback> callback = new CallbackHolder<>(SelectImageCallback.class);

    private SDRecyclerView recyclerView;
    private SDRecyclerAdapter<ImageModel> adapter;

    private LocalImageFinder localImageFinder;
    private EnumSelectMode selectMode = EnumSelectMode.single;
    /**
     * 是否显示勾选按钮
     */
    private boolean showSelectImage = true;
    private List<ImageModel> listImage = new ArrayList<>();

    protected void init()
    {
        setContentView(R.layout.view_sd_select_image);
        recyclerView = find(R.id.recyclerView);
        recyclerView.setGridVertical(3);
        setAdapter(new DefaultAdapter(getActivity()));
    }

    /**
     * 设置是否显示勾选按钮
     *
     * @param showSelectImage
     */
    public void setShowSelectImage(boolean showSelectImage)
    {
        this.showSelectImage = showSelectImage;
        if (adapter != null)
        {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置选择模式
     *
     * @param selectMode
     */
    public void setSelectMode(EnumSelectMode selectMode)
    {
        this.selectMode = selectMode;
        updateSelectMode();
    }

    private SDSelectManager<ImageModel> getSelectManager()
    {
        return adapter.getSelectManager();
    }

    /**
     * 更新选择管理器的模式
     */
    private void updateSelectMode()
    {
        if (getSelectManager() == null)
        {
            return;
        }
        if (selectMode == EnumSelectMode.multi)
        {
            getSelectManager().setMode(SDSelectManager.Mode.MULTI);
        } else
        {
            getSelectManager().setMode(SDSelectManager.Mode.SINGLE);
        }
    }

    public void setAdapter(SDRecyclerAdapter<ImageModel> adapter)
    {
        if (adapter != null)
        {
            this.adapter = adapter;
            this.recyclerView.setAdapter(adapter);
            adapter.getSelectManager().addSelectCallback(defaultSelectCallback);
        }
    }

    private SDSelectManager.SelectCallback<ImageModel> defaultSelectCallback = new SDSelectManager.SelectCallback<ImageModel>()
    {
        @Override
        public void onNormal(int position, ImageModel item)
        {
        }

        @Override
        public void onSelected(int position, ImageModel item)
        {
            if (callback.get().onSelect(position, item))
            {
                // 如果外部消耗掉，还原选中状态
                getSelectManager().setSelected(position, false);
            }
        }
    };

    /**
     * 获得选中的图片
     *
     * @return
     */
    public List<ImageModel> getSelectedImages()
    {
        listImage.clear();
        if (getSelectManager().isSingleMode())
        {
            ImageModel imageModel = getSelectManager().getSelectedItem();
            if (imageModel != null)
            {
                listImage.add(imageModel);
            }
        } else
        {
            listImage.addAll(getSelectManager().getSelectedItems());
        }
        return listImage;
    }

    /**
     * 加载本地图片
     */
    public void loadImage()
    {
        if (localImageFinder == null)
        {
            localImageFinder = new LocalImageFinder((FragmentActivity) getActivity());
        }

        if (adapter != null)
        {
            localImageFinder.getLocalImage(loadListener);
        }
    }

    private LocalImageFinder.LocalImageFinderListener loadListener = new LocalImageFinder.LocalImageFinderListener()
    {
        @Override
        public void onResult(List<ImageModel> listImage)
        {
            adapter.updateData(listImage);
        }
    };

    private class DefaultAdapter extends SDSimpleRecyclerAdapter<ImageModel>
    {
        public DefaultAdapter(Activity activity)
        {
            super(activity);
        }

        @Override
        public int getLayoutId(ViewGroup parent, int viewType)
        {
            return R.layout.item_sd_select_image;
        }

        @Override
        public SDRecyclerViewHolder<ImageModel> onCreateVHolder(ViewGroup parent, int viewType)
        {
            SDRecyclerViewHolder<ImageModel> holder = super.onCreateVHolder(parent, viewType);
            ImageView iv_image = holder.get(R.id.iv_image);

            int itemWidth = (SDViewUtil.getScreenWidth() / 3);
            SDViewUtil.setWidth(holder.itemView, itemWidth);
            SDViewUtil.setHeight(holder.itemView, itemWidth);

            int imageWidth = itemWidth - SDViewUtil.dp2px(4);
            SDViewUtil.setWidth(iv_image, imageWidth);
            SDViewUtil.setHeight(iv_image, imageWidth);
            return holder;
        }

        @Override
        public void onBindData(SDRecyclerViewHolder<ImageModel> holder, final int position, final ImageModel model)
        {
            ImageView iv_image = holder.get(R.id.iv_image);
            ImageView iv_selected = holder.get(R.id.iv_selected);

            GlideUtil.load(model.getUri()).into(iv_image);

            if (showSelectImage)
            {
                SDViewUtil.setVisible(iv_selected);
                if (model.isSelected())
                {
                    iv_selected.setImageResource(R.drawable.ic_sd_select_image_selected);
                } else
                {
                    iv_selected.setImageResource(R.drawable.ic_sd_select_image_normal);
                }
            } else
            {
                SDViewUtil.setGone(iv_selected);
            }

            iv_selected.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getSelectManager().setSelected(position, true);
                }
            });

            iv_image.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    callback.get().onItemClick(position, model, v);
                }
            });
        }
    }

    public enum EnumSelectMode
    {
        /**
         * 单选
         */
        single,

        /**
         * 多选
         */
        multi;
    }

    public interface SelectImageCallback extends SDItemClickCallback<ImageModel>
    {
        boolean onSelect(int position, ImageModel model);
    }

}
