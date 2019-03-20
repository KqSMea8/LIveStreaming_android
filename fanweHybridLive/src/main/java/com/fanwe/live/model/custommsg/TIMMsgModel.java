package com.fanwe.live.model.custommsg;

import android.text.TextUtils;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.library.media.recorder.SDMediaRecorder;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.tencent.TIMCallBack;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFaceElem;
import com.tencent.TIMFileElem;
import com.tencent.TIMGroupSystemElem;
import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMLocationElem;
import com.tencent.TIMMessage;
import com.tencent.TIMProfileSystemElem;
import com.tencent.TIMSNSSystemElem;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;
import com.tencent.TIMVideoElem;

import java.io.File;
import java.util.List;

public class TIMMsgModel extends MsgModel
{
    private TIMMessage timMessage;
    private TIMCustomElem timCustomElem;
    private TIMFaceElem timFaceElem;
    private TIMFileElem timFileElem;
    private TIMGroupSystemElem timGroupSystemElem;
    private TIMGroupTipsElem timGroupTipsElem;
    private TIMImageElem timImageElem;
    private TIMLocationElem timLocationElem;
    private TIMProfileSystemElem timProfileSystemElem;
    private TIMSNSSystemElem timSnsSystemElem;
    private TIMSoundElem timSoundElem;
    private TIMTextElem timTextElem;
    private TIMVideoElem timVideoElem;

    private boolean printLog = false;

    public TIMMsgModel(TIMMessage timMessage)
    {
        super();
        setTimMessage(timMessage);
    }

    public TIMMsgModel(TIMMessage timMessage, boolean printLog)
    {
        super();
        this.printLog = printLog;
        setTimMessage(timMessage);
    }

    @Override
    public void remove()
    {
        if (timMessage != null)
        {
            timMessage.remove();
        }
    }

    public TIMMessage getTimMessage()
    {
        return timMessage;
    }

    @Override
    public void setTimMessage(TIMMessage timMessage)
    {
        // 解析消息
        this.timMessage = timMessage;
        readElement();
        parseCustomElem();
    }

    public TIMCustomElem getTimCustomElem()
    {
        return timCustomElem;
    }

    public void setTimCustomElem(TIMCustomElem timCustomElem)
    {
        this.timCustomElem = timCustomElem;
    }

    /**
     * 将消息的elem解析出来
     */
    private void readElement()
    {
        if (timMessage != null)
        {
            switch (timMessage.status())
            {
                case SendFail:
                    setStatus(MsgStatus.SendFail);
                    break;
                case Sending:
                    setStatus(MsgStatus.Sending);
                    break;
                case SendSucc:
                    setStatus(MsgStatus.SendSuccess);
                    break;
                case HasDeleted:
                    setStatus(MsgStatus.HasDeleted);
                    break;
                default:
                    setStatus(MsgStatus.Invalid);
                    break;
            }

            switch (timMessage.getConversation().getType())
            {
                case C2C:
                    setConversationType(ConversationType.C2C);
                    break;
                case Group:
                    setConversationType(ConversationType.Group);
                    break;
                case System:
                    setConversationType(ConversationType.System);
                    break;
                default:
                    setConversationType(ConversationType.Invalid);
                    break;
            }

            setSelf(timMessage.isSelf());
            String peer = timMessage.getConversation().getPeer();
            setConversationPeer(peer);
            setUnreadNum(timMessage.getConversation().getUnreadMessageNum());
            setTimestamp(timMessage.timestamp());

            long count = timMessage.getElementCount();
            TIMElem elem = null;
            for (int i = 0; i < count; i++)
            {
                elem = timMessage.getElement(i);
                if (elem == null)
                {
                    continue;
                }
                TIMElemType elemType = elem.getType();
                switch (elemType)
                {
                    case Custom:
                        setTimCustomElem((TIMCustomElem) elem);
                        break;
                    case Face:
                        setTimFaceElem((TIMFaceElem) elem);
                        break;
                    case File:
                        setTimFileElem((TIMFileElem) elem);
                        break;
                    case GroupSystem:
                        setTimGroupSystemElem((TIMGroupSystemElem) elem);
                        peer = getTimGroupSystemElem().getGroupId();
                        setConversationPeer(peer);
                        break;
                    case GroupTips:
                        setTimGroupTipsElem((TIMGroupTipsElem) elem);
                        break;
                    case Image:
                        setTimImageElem((TIMImageElem) elem);
                        break;
                    case Invalid:

                        break;
                    case Location:
                        setTimLocationElem((TIMLocationElem) elem);
                        break;
                    case ProfileTips:
                        setTimProfileSystemElem((TIMProfileSystemElem) elem);
                        break;
                    case SNSTips:
                        setTimSnsSystemElem((TIMSNSSystemElem) elem);
                        break;
                    case Sound:
                        setTimSoundElem((TIMSoundElem) elem);
                        break;
                    case Text:
                        setTimTextElem((TIMTextElem) elem);
                        break;
                    case Video:
                        setTimVideoElem((TIMVideoElem) elem);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    /**
     * 将TIMCustomElem解析成自定义消息
     */
    private void parseCustomElem()
    {
        if (timCustomElem != null || timGroupSystemElem != null)
        {
            CustomMsg customMsg = parseToModel(CustomMsg.class);
            if (customMsg != null)
            {
                int type = customMsg.getType();
                if (ApkConstant.DEBUG && printLog)
                {
                    LogUtil.i("--------result:" + getConversationType() + " " + getConversationPeer() + " type:" + type);
                }

                UserModel sender = customMsg.getSender();
                UserModelDao.updateLevelUp(sender);

                Class realCustomMsgClass = LiveConstant.mapCustomMsgClass.get(type);
                if (realCustomMsgClass == null)
                {
                    return;
                }
                if (ApkConstant.DEBUG && printLog)
                {
                    LogUtil.i("realCustomMsgClass:" + realCustomMsgClass.getName());
                }
                CustomMsg realCustomMsg = parseToModel(realCustomMsgClass);
                setCustomMsg(realCustomMsg);

                switch (type)
                {
                    case CustomMsgType.MSG_TEXT:
                        CustomMsgText customMsgText = getCustomMsgReal();
                        if (timTextElem != null)
                        {
                            customMsgText.setText(timTextElem.getText());
                        }
                        break;
                    case CustomMsgType.MSG_POP_MSG:
                        CustomMsgPopMsg customMsgPopMsg = getCustomMsgReal();
                        if (timTextElem != null)
                        {
                            customMsgPopMsg.setDesc(timTextElem.getText());
                        }
                        break;
                    case CustomMsgType.MSG_PRIVATE_VOICE:
                        CustomMsgPrivateVoice customMsgPrivateVoice = getCustomMsgReal();
                        if (customMsgPrivateVoice != null && timSoundElem != null)
                        {
                            String uuid = timSoundElem.getUuid();

                            if (!TextUtils.isEmpty(uuid))
                            {
                                File file = SDMediaRecorder.getInstance().getFile(uuid);
                                if (file.exists())
                                {
                                    String path = file.getAbsolutePath();
                                    customMsgPrivateVoice.setPath(path);
                                }
                            } else
                            {
                                // 主动post解析
                            }
                        }
                        break;
                    case CustomMsgType.MSG_PRIVATE_IMAGE:
                        CustomMsgPrivateImage customMsgPrivateImage = getCustomMsgReal();
                        if (customMsgPrivateImage != null && timImageElem != null)
                        {
                            List<TIMImage> listImage = timImageElem.getImageList();
                            if (!SDCollectionUtil.isEmpty(listImage))
                            {
                                TIMImage image = listImage.get(0);
                                String url = image.getUrl();
                                customMsgPrivateImage.setUrl(url);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 检测声音文件是否存在
     *
     * @param listener
     * @return true-本地不存在缓存，需要下载
     */
    public boolean checkSoundFile(final TIMValueCallBack<String> listener)
    {
        boolean needDownload = false;
        if (getCustomMsgType() == LiveConstant.CustomMsgType.MSG_PRIVATE_VOICE)
        {
            if (timSoundElem != null)
            {
                String uuid = timSoundElem.getUuid();
                if (!TextUtils.isEmpty(uuid))
                {
                    File file = SDMediaRecorder.getInstance().getFile(uuid);
                    if (file != null && !file.exists())
                    {
                        // 需要下载文件
                        needDownload = true;
                        final String path = file.getAbsolutePath();
                        timSoundElem.getSoundToFile(path, new TIMCallBack()
                        {
                            @Override
                            public void onError(int i, String s)
                            {
                                if (listener != null)
                                {
                                    listener.onError(i, s);
                                }
                            }

                            @Override
                            public void onSuccess()
                            {
                                if (listener != null)
                                {
                                    listener.onSuccess(path);
                                }
                            }
                        });
                    }
                }
            }
        }
        return needDownload;
    }

    public TIMFaceElem getTimFaceElem()
    {
        return timFaceElem;
    }

    public void setTimFaceElem(TIMFaceElem timFaceElem)
    {
        this.timFaceElem = timFaceElem;
    }

    public TIMFileElem getTimFileElem()
    {
        return timFileElem;
    }

    public void setTimFileElem(TIMFileElem timFileElem)
    {
        this.timFileElem = timFileElem;
    }

    public TIMGroupSystemElem getTimGroupSystemElem()
    {
        return timGroupSystemElem;
    }

    public void setTimGroupSystemElem(TIMGroupSystemElem timGroupSystemElem)
    {
        this.timGroupSystemElem = timGroupSystemElem;
    }

    public TIMGroupTipsElem getTimGroupTipsElem()
    {
        return timGroupTipsElem;
    }

    public void setTimGroupTipsElem(TIMGroupTipsElem timGroupTipsElem)
    {
        this.timGroupTipsElem = timGroupTipsElem;
    }

    public TIMImageElem getTimImageElem()
    {
        return timImageElem;
    }

    public void setTimImageElem(TIMImageElem timImageElem)
    {
        this.timImageElem = timImageElem;
    }

    public TIMLocationElem getTimLocationElem()
    {
        return timLocationElem;
    }

    public void setTimLocationElem(TIMLocationElem timLocationElem)
    {
        this.timLocationElem = timLocationElem;
    }

    public TIMProfileSystemElem getTimProfileSystemElem()
    {
        return timProfileSystemElem;
    }

    public void setTimProfileSystemElem(TIMProfileSystemElem timProfileSystemElem)
    {
        this.timProfileSystemElem = timProfileSystemElem;
    }

    public TIMSNSSystemElem getTimSnsSystemElem()
    {
        return timSnsSystemElem;
    }

    public void setTimSnsSystemElem(TIMSNSSystemElem timSnsSystemElem)
    {
        this.timSnsSystemElem = timSnsSystemElem;
    }

    public TIMSoundElem getTimSoundElem()
    {
        return timSoundElem;
    }

    public void setTimSoundElem(TIMSoundElem timSoundElem)
    {
        this.timSoundElem = timSoundElem;
    }

    public TIMTextElem getTimTextElem()
    {
        return timTextElem;
    }

    public void setTimTextElem(TIMTextElem timTextElem)
    {
        this.timTextElem = timTextElem;
    }

    public TIMVideoElem getTimVideoElem()
    {
        return timVideoElem;
    }

    public void setTimVideoElem(TIMVideoElem timVideoElem)
    {
        this.timVideoElem = timVideoElem;
    }

    public <T extends CustomMsg> T parseToModel(Class<T> clazz)
    {
        T model = null;
        String json = null;
        try
        {
            byte[] data = null;
            if (timGroupSystemElem != null)
            {
                data = timGroupSystemElem.getUserData();
            }
            if (data == null)
            {
                data = timCustomElem.getData();
            }

            json = new String(data, LiveConstant.DEFAULT_CHARSET);
            model = SDJsonUtil.json2Object(json, clazz);

            if (ApkConstant.DEBUG && printLog)
            {
                LogUtil.i("parseToModel " + model.getType() + ":" + json);
            }
        } catch (Exception e)
        {
            if (ApkConstant.DEBUG && printLog)
            {
                e.printStackTrace();
                LogUtil.e("(" + getConversationPeer() + ")parse msg error:" + e.toString() + ",json:" + json);
            }
        } finally
        {

        }
        return model;
    }
}
