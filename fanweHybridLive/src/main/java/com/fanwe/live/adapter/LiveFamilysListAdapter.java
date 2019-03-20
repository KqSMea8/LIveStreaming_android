package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_family_createActModel;
import com.fanwe.live.model.App_family_listItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/9/24.
 */

public class LiveFamilysListAdapter extends SDSimpleAdapter<App_family_listItemModel>
{
    private SDItemClickCallback<App_family_listItemModel> clickItemListener;
    private boolean isApply = true;

    public void setClickItemListener(SDItemClickCallback<App_family_listItemModel> clickItemListener)
    {
        this.clickItemListener = clickItemListener;
    }

    public LiveFamilysListAdapter(List<App_family_listItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_familys_list;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final App_family_listItemModel model)
    {
        LinearLayout ll_all = get(R.id.ll_all, convertView);
        ImageView civ_head_image = ViewHolder.get(R.id.civ_head_image, convertView);//家族头像
        TextView txv_fam_nick = ViewHolder.get(R.id.txv_fam_nick, convertView);//家族名称
        TextView txv_fam_name = ViewHolder.get(R.id.txv_fam_name, convertView);//族长
        TextView txv_fam_num = ViewHolder.get(R.id.txv_fam_num, convertView);//人数
        final TextView txv_add_fam = ViewHolder.get(R.id.txv_add_fam, convertView);//加入家族/申请中

        GlideUtil.loadHeadImage(model.getFamily_logo()).into(civ_head_image);
        SDViewBinder.setTextView(txv_fam_nick, model.getFamily_name());
        SDViewBinder.setTextView(txv_fam_name, model.getNick_name());
        SDViewBinder.setTextView(txv_fam_num, model.getUser_count() + "");

        txv_add_fam.setEnabled(isApply);

        if (model.getIs_apply() == 1 || model.is_check())//是否已经提交申请，1：已提交、0：未提交 2：已加入家族
        {
            SDViewBinder.setTextView(txv_add_fam, "申请中");
            txv_add_fam.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
            txv_add_fam.setEnabled(false);
        } else if (model.getIs_apply() == 0)
        {
            SDViewBinder.setTextView(txv_add_fam, "加入家族");
            txv_add_fam.setBackgroundResource(R.drawable.bg_deep_orange_rectangle_radius);
//            txv_add_fam.setEnabled(true);
        } else if (model.getIs_apply() == 2)
        {
            SDViewBinder.setTextView(txv_add_fam, "已加入");
            txv_add_fam.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
            txv_add_fam.setEnabled(false);
        }

        /**
         * 家族详情
         */
        ll_all.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickItemListener != null)
                {
                    clickItemListener.onItemClick(position, model, view);
                }
            }
        });

        /**
         * 加入家族
         */
        txv_add_fam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CommonInterface.requestApplyJoinFamily(model.getFamily_id(), new AppRequestCallback<App_family_createActModel>()
                {
                    @Override
                    protected void onSuccess(SDResponse sdResponse)
                    {
                        if (actModel.getStatus() == 1)
                        {
                            SDViewBinder.setTextView(txv_add_fam, "申请中");
                            txv_add_fam.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                            txv_add_fam.setEnabled(false);
                            model.setIs_check(true);
                            SDToast.showToast(actModel.getError().toString());
                        }
                    }
                });
            }
        });
    }

    public void setEnable(boolean is_apply)
    {
        this.isApply = is_apply;
    }
}
