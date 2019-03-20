package com.fanwe.live.model.custommsg;

import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.tencent.TIMMessage;
import com.tencent.TIMSoundElem;

public class CustomMsgPrivateVoice extends CustomMsg
{

    private String path;
    private long duration;

    //add
    private String durationFormat;
    private int viewWidth;

    public CustomMsgPrivateVoice()
    {
        super();
        setType(CustomMsgType.MSG_PRIVATE_VOICE);
    }

    @Override
    public TIMMessage parseToTIMMessage()
    {
        TIMMessage timMessage = super.parseToTIMMessage();
        if (timMessage != null)
        {
            TIMSoundElem elem = new TIMSoundElem();
            elem.setPath(path);
            elem.setDuration(duration);
            timMessage.addElement(elem);
        }
        return timMessage;
    }

    public int getViewWidth()
    {
        return viewWidth;
    }

    public String getDurationFormat()
    {
        return durationFormat;
    }

    public void setDurationFormat(String durationFormat)
    {
        this.durationFormat = durationFormat;
    }

    public long getDuration()
    {
        return duration;
    }

    public void setDuration(long duration)
    {
        this.duration = duration;
        this.durationFormat = String.valueOf(SDNumberUtil.divide(duration, 1000, 1)) + "\"";
        if (duration > 0)
        {
            this.viewWidth = (int) (SDNumberUtil.divide(duration, SDDateUtil.MILLISECONDS_MINUTES, 1) * (double) SDViewUtil.dp2px(200));
        }
        this.viewWidth += SDViewUtil.dp2px(40);
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        if (path != null)
        {
            this.path = path;
        }
    }

    @Override
    public String getConversationDesc()
    {
        return "[语音]";
    }

}
