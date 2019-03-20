package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgGreedLinkMic extends CustomMsg
{
    public static final String MSG_Greed = "主播接受了你的连麦请求";

    private String msg = MSG_Greed;
    private long end_time;
    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public CustomMsgGreedLinkMic(long end_time)
    {
        super();
        this.end_time=end_time;
        setType(CustomMsgType.MSG_Greed_LINK_MIC);
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    private boolean is_pk;
    public boolean is_pk() {
        return is_pk;
    }

    public void setIs_pk(boolean is_pk) {
        this.is_pk = is_pk;
    }
}
