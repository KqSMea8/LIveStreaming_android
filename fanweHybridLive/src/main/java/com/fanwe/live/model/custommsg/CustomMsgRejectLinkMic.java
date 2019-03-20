package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgRejectLinkMic extends CustomMsg
{
    public static final String MSG_REJECT = "主播拒绝了你的连麦请求";
    public static final String MSG_MAX = "当前主播连麦已上限，请稍候尝试";
    public static final String MSG_BUSY = "主播有未处理的连麦请求，请稍候再试";
    public static final String MSG_REJECT_PK = "主播拒绝了你的PK请求";
    public static final String MSG_PK_MAX = "主播正在PK中，请稍候再试";
    public static final String MSG_IN_MIC = "主播正在连麦中，暂时无法PK";
    public static final String MSG_NO_RESPONE = "主播没有及时处理你的请求，默认拒绝";
    private String msg = MSG_REJECT;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public CustomMsgRejectLinkMic()
    {
        super();
        setType(CustomMsgType.MSG_REJECT_LINK_MIC);
    }
    private boolean is_pk;
    public boolean is_pk() {
        return is_pk;
    }

    public void setIs_pk(boolean is_pk) {
        this.is_pk = is_pk;
    }
}
