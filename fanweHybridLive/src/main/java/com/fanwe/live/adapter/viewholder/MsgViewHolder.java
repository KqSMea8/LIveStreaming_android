package com.fanwe.live.adapter.viewholder;

import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.adapter.SDRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.dialog.LiveUserInfoDialog;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.span.LiveLevelSpan;

/**
 * Created by Administrator on 2016/8/27.
 */
public abstract class MsgViewHolder extends SDRecyclerViewHolder<MsgModel>
{
    public TextView tv_content;
    protected SDSpannableStringBuilder sb = new SDSpannableStringBuilder();

    protected MsgModel liveMsgModel;
    protected CustomMsg customMsg;

    public MsgViewHolder(View itemView)
    {
        super(itemView);
        tv_content = find(R.id.tv_content);
    }

    @Override
    public final void onBindData(int position, MsgModel model)
    {
        liveMsgModel = model;
        customMsg = liveMsgModel.getCustomMsg();
        sb.clear();
        bindCustomMsg(position, customMsg);
        appendFinish();
    }

    protected abstract void bindCustomMsg(int position, CustomMsg customMsg);


    protected void appendUserInfo(UserModel model)
    {
        appendUserLevel(model);
        appendUserNickname(model);
    }

    /**
     * 添加用户等级
     */
    protected void appendUserLevel(UserModel user)
    {
        if (user == null)
        {
            return;
        }
        int levelImageResId = user.getLevelImageResId();
        if (levelImageResId != 0)
        {
            LiveLevelSpan levelSpan = new LiveLevelSpan(getAdapter().getActivity(), levelImageResId);
            sb.appendSpan(levelSpan, LiveConstant.LEVEL_SPAN_KEY);
        }
    }

    /**
     * 添加用户名
     */
    protected void appendUserNickname(UserModel user)
    {
        if (user == null)
        {
            return;
        }
        String nickName = user.getNick_nameFormat();
        if(user.getV_identity()==2){
            appendContent("场控 ", SDResourcesUtil.getColor(R.color.edit_green));
        }
        int nickColor = 0;
        if (user.getUser_id().equals(LiveInformation.getInstance().getCreaterId()))
        {
            // 主播
            nickColor = SDResourcesUtil.getColor(R.color.live_username_creater);
        } else
        {
            nickColor = SDResourcesUtil.getColor(R.color.live_username_viewer);
        }
        appendContent(nickName, nickColor);
    }

    protected void setUserInfoClickListener(View view)
    {
        setUserInfoClickListener(view, customMsg.getSender());
    }

    protected void setUserInfoClickListener(View view, final UserModel user)
    {
        if (view == null || user == null)
        {
            return;
        }
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LiveUserInfoDialog dialog = new LiveUserInfoDialog(getAdapter().getActivity(), user.getUser_id());
                dialog.show();
            }
        });
    }

    /**
     * 添加内容
     *
     * @param content
     * @param textColor
     */
    protected void appendContent(String content, int textColor)
    {
        if (!TextUtils.isEmpty(content))
        {
            if (textColor == 0)
            {
                if (customMsg.getSender().getUser_id().equals(LiveInformation.getInstance().getCreaterId()))
                {
                    // 主播
                    textColor = SDResourcesUtil.getColor(R.color.live_msg_text_creater);
                }
                else if(customMsg.getSender().getUser_id().equals(AppRuntimeWorker.getLoginUserID())){
                    //自己
                    textColor = SDResourcesUtil.getColor(R.color.main_color);
                }
                else{
                    textColor = SDResourcesUtil.getColor(R.color.main_color);
                }
            }
            ForegroundColorSpan textSpan = new ForegroundColorSpan(textColor);
            sb.appendSpan(textSpan, content);
        }
    }

    protected void appendFinish()
    {
        tv_content.setText(sb);
    }

}
