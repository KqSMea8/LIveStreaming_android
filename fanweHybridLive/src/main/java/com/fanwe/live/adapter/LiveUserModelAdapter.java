package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-15 下午2:14:28 类说明
 */
public class LiveUserModelAdapter extends SDSimpleAdapter<UserModel>
{
    private UserModel user = UserModelDao.query();

    public LiveUserModelAdapter(List<UserModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_focus_follow;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final UserModel model)
    {
        ImageView civ_v_icon = ViewHolder.get(R.id.civ_v_icon, convertView);
        SDViewUtil.setGone(civ_v_icon);

        ImageView civ_head_image = ViewHolder.get(R.id.civ_head_image, convertView);
        TextView tv_nick_name = ViewHolder.get(R.id.tv_nick_name, convertView);
        TextView tv_signature = ViewHolder.get(R.id.tv_signature, convertView);
        ImageView iv_global_male = ViewHolder.get(R.id.iv_global_male, convertView);
        ImageView iv_rank = ViewHolder.get(R.id.iv_rank, convertView);

        final ImageView iv_focus = ViewHolder.get(R.id.cb_focus, convertView);

        if (user.getUser_id().equals(model.getUser_id()))
        {
            SDViewUtil.setGone(iv_focus);
        } else
        {
            SDViewUtil.setVisible(iv_focus);
        }

        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_image);
        SDViewBinder.setTextView(tv_nick_name, model.getNick_name());
        SDViewBinder.setTextView(tv_signature, model.getSignature());
        if (model.getSex() > 0)
        {
            SDViewUtil.setVisible(iv_global_male);
            iv_global_male.setImageResource(model.getSexResId());
        } else
        {
            SDViewUtil.setGone(iv_global_male);
        }
        iv_rank.setImageResource(model.getLevelImageResId());

        if (model.getFollow_id() > 0)
        {
            iv_focus.setSelected(true);
        } else
        {
            iv_focus.setSelected(false);
        }

        if (user != null)
        {
            iv_focus.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    clickIvFocus(model, iv_focus, position);
                }
            });

        }

        if (onItemClickListener != null)
        {
            convertView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onItemClickListener.OnItemClick(model, position);
                }
            });
        }
    }

    private void clickIvFocus(UserModel model, final ImageView iv_focus, final int position)
    {
        // 本地默认选中和不选中
//        if (iv_focus.isSelected())
//        {
//            iv_focus.setSelected(false);
//        } else
//        {
//            iv_focus.setSelected(true);
//        }

        CommonInterface.requestFollow(model.getUser_id(), 0, new AppRequestCallback<App_followActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    if (actModel.getHas_focus() == 1)
                    {
                        iv_focus.setSelected(true);
                    } else
                    {
                        iv_focus.setSelected(false);
                    }
                    getData().get(position).setFollow_id(actModel.getHas_focus());
                    updateData(position);
                }
            }
        });
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener
    {
        void OnItemClick(UserModel user, int position);
    }
}
