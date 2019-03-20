package com.fanwe.live.model.custommsg;

import android.text.TextUtils;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.tencent.TIMCustomElem;
import com.tencent.TIMMessage;

public class CustomMsg implements ICustomMsg
{
    private int type;
    private UserModel user;
    private UserModel sender;
    private String deviceType;

    /**
     * 用于群组消息中指定某个用户接收消息，如果不为0而且有值，则只有指定的用户才能处理此消息
     */
    private String user_id;

    public CustomMsg()
    {
        type = CustomMsgType.MSG_NONE;
        deviceType = "Android";
        sender = UserModelDao.query();
        if (sender == null)
        {
            LogUtil.i("sender is null--------------------------------------");
        }
    }

    /**
     * 是否是其他用户的消息
     *
     * @return
     */
    public boolean isOtherUserMsg()
    {
        return !TextUtils.isEmpty(user_id) && !"0".equals(user_id) && !user_id.equals(UserModelDao.getUserId());
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public TIMMessage parseToTIMMessage()
    {
        TIMMessage msg = null;

        try
        {
            String json = SDJsonUtil.object2Json(this);
            byte[] bytes = json.getBytes(LiveConstant.DEFAULT_CHARSET);

            TIMCustomElem elemCustom = new TIMCustomElem();
            elemCustom.setData(bytes);

            msg = new TIMMessage();
            msg.addElement(elemCustom);
            LogUtil.i("send json:" + json);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return msg;
    }

    public final MsgModel parseToMsgModel()
    {
        TIMMessage timMessage = parseToTIMMessage();
        MsgModel msgModel = new TIMMsgModel(timMessage);
        return msgModel;
    }

    public UserModel getUser()
    {
        return user;
    }

    public void setUser(UserModel user)
    {
        this.user = user;
    }

    public UserModel getSender()
    {
        return sender;
    }

    public void setSender(UserModel sender)
    {
        this.sender = sender;
    }

    /**
     * 返回用于会话列表中展示的内容
     *
     * @return
     */
    public String getConversationDesc()
    {
        return "";
    }

}
