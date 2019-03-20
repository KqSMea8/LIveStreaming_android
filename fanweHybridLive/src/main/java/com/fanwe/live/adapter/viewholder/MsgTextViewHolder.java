package com.fanwe.live.adapter.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgText;

/**
 * 文字消息
 */
public class MsgTextViewHolder extends MsgViewHolder {
    public MsgTextViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg) {
        appendUserInfo(customMsg.getSender());
        // 内容
        int textColor = 0;
//        if (customMsg.getSender().getUser_id().equals(LiveInformation.getInstance().getCreaterId())) {
//            // 主播
//            textColor = SDResourcesUtil.getColor(R.color.main_color);
//        } else if (customMsg.getSender().getUser_id().equals(AppRuntimeWorker.getLoginUserID())) {
//            //自己
            textColor = Color.parseColor(getTextColor());
//        } else {
//            textColor = SDResourcesUtil.getColor(R.color.main_color);
//        }
        appendContent(getText(), textColor);
        setUserInfoClickListener(tv_content);
    }

    protected String getText() {
        CustomMsgText msg = (CustomMsgText) customMsg;
        return msg.getText();
    }

    protected String getTextColor() {
        if (customMsg.getType() == LiveConstant.CustomMsgType.MSG_TEXT) {
            CustomMsgText msg = (CustomMsgText) customMsg;
            if(null!=msg.getV_identity_colour()){
                return msg.getV_identity_colour();
            }else
            return "#FFFFFF";
        } else
            return "#FFFFFF";
    }
}
