package com.fanwe.live.model.custommsg;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.common.AppRuntimeWorker;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

public class CustomMsgText extends CustomMsg
{

    private String text;
    public CustomMsgText()
    {
        super();
        setType(CustomMsgType.MSG_TEXT);
    }
    private String  v_identity_colour;

    public String getV_identity_colour() {
        return v_identity_colour;
    }

    public void setV_identity_colour(String v_identity_colour) {
        this.v_identity_colour = v_identity_colour;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }


    @Override
    public TIMMessage parseToTIMMessage()
    {
        TIMMessage timMessage = super.parseToTIMMessage();
        if (AppRuntimeWorker.getHas_dirty_words() == 1)
        {
            if (timMessage != null)
            {
                TIMTextElem textElem = new TIMTextElem();
                textElem.setText(text);
                int ret = timMessage.addElement(textElem);
                LogUtil.i("CustomMsgText add TIMTextElem:" + ret);
            }
        }
        return timMessage;
    }
}
