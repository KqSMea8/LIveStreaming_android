package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;

public class ItemLiveTabNewSingle extends BaseAppView
{

    private ImageView iv_room_image;
    private ImageView iv_level;
    private TextView tv_city;
    private TextView tv_live_state;
    private TextView tv_is_new;
    private LiveRoomModel model;

    public ItemLiveTabNewSingle(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public ItemLiveTabNewSingle(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ItemLiveTabNewSingle(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.item_live_tab_new_single);

        iv_room_image = find(R.id.iv_room_image);
        iv_level = find(R.id.iv_level);
        tv_city = find(R.id.tv_city);
        tv_live_state = find(R.id.tv_live_state);
        tv_is_new = find(R.id.tv_is_new);
    }

    public LiveRoomModel getModel()
    {
        return model;
    }

    public void setModel(LiveRoomModel model)
    {
        this.model = model;
        if (model != null)
        {
            SDViewUtil.setVisible(this);
            GlideUtil.load(model.getLive_image()).into(iv_room_image);
            iv_level.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
            SDViewBinder.setTextView(tv_city, model.getDistanceFormat());
            if (model.getIs_live_pay() == 1)
            {
                SDViewUtil.setVisible(tv_live_state);
                SDViewBinder.setTextView(tv_live_state, "付费");
            } else
            {
                SDViewUtil.setGone(tv_live_state);
            }

            if (model.getToday_create() == 1)
            {
                SDViewUtil.setVisible(tv_is_new);
            } else
            {
                SDViewUtil.setGone(tv_is_new);
            }
        } else
        {
            SDViewUtil.setGone(this);
        }
    }

}
