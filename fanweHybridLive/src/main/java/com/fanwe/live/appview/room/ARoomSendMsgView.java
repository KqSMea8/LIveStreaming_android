package com.fanwe.live.appview.room;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDObjectBlocker;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_msgActModel;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.fanwe.live.view.SDSlidingButton;
import com.fanwe.live.view.SDSlidingButton.SelectedChangeListener;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * 消息输入框基类
 * {@link RoomPCSendMsgPortView} 竖屏输入框 在{@link RoomPCMessageView}内
 * {@link RoomPCSendMsgLandView} 横屏输入框 在{@link RoomPCLiveLandControlView}内
 */
public abstract class ARoomSendMsgView extends RoomView implements TextView.OnEditorActionListener
{

    private static final int MAX_INPUT_LENGTH = 30;

    /**
     * 相同消息拦截间隔
     */
    private static final int DUR_BLOCK_SAME_MSG = 5 * 1000;
    /**
     * 消息拦截间隔
     */
    private static final int DUR_BLOCK_MSG = 2 * 1000;

    protected SDSlidingButton sl_btn;
    protected ImageView iv_pop_msg_handle;
    protected EditText et_content;
    protected TextView tv_send;
    protected ImageView iv_gift;

    protected RoomSendMsgViewListener mListener;

    protected int popMsgDiamonds = 1;
    protected String strContent;
    private boolean isPopMsg = false;
    private SDObjectBlocker sendBlocker;

    public ARoomSendMsgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public ARoomSendMsgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ARoomSendMsgView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        initSendMsgView();
        sendBlocker = new SDObjectBlocker();
        sendBlocker.setMaxEqualsCount(1);
        sendBlocker.setBlockEqualsObjectDuration(DUR_BLOCK_SAME_MSG);
        sendBlocker.setBlockDuration(DUR_BLOCK_MSG);
        register();
    }

    protected abstract void initSendMsgView();

    protected abstract void onClickGift(View view);

    private void register()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            popMsgDiamonds = model.getBullet_screen_diamond();
        }

        initSDSlidingButton();
        et_content.setOnEditorActionListener(this);
//        et_content.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                return keyCode == KeyEvent.KEYCODE_ENTER;
//            }
//        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if (actionId == EditorInfo.IME_ACTION_SEND)
        {
            if (validateContent())
            {
                sendMessage();
            }
            return true;
        }
        return false;
    }

    private void initSDSlidingButton()
    {
        sl_btn.setSelectedChangeListener(new SelectedChangeListener()
        {
            @Override
            public void onSelectedChange(SDSlidingButton view, boolean selected)
            {
                if (selected)
                {
                    iv_pop_msg_handle.setImageResource(R.drawable.bg_live_pop_msg_handle_selected);
                    if (getLiveActivity().isCreater())
                    {
                        et_content.setHint(SDResourcesUtil.getString(R.string.live_send_msg_hint_normal));
                    } else
                    {
                        et_content.setHint("开启大喇叭，" + popMsgDiamonds + "秀豆/条");
                    }
                    isPopMsg = true;
                } else
                {
                    iv_pop_msg_handle.setImageResource(R.drawable.bg_live_pop_msg_handle_normal);
                    et_content.setHint(SDResourcesUtil.getString(R.string.live_send_msg_hint_normal));
                    isPopMsg = false;
                }
            }
        });
    }

    public void setContent(String content)
    {
        if (content == null)
        {
            content = "";
        }
        et_content.setText(content);
        et_content.setSelection(et_content.getText().length());
        SDKeyboardUtil.showKeyboard(et_content, 100);
    }

    private boolean validateContent()
    {
        if (!SDNetworkReceiver.isNetworkConnected(getActivity()))
        {
            SDToast.showToast("无网络");
            return false;
        }

        strContent = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(strContent))
        {
            SDToast.showToast("请输入内容");
            return false;
        }

        if (strContent.contains("\n"))
        {
            strContent = strContent.replace("\n", "");
        }

        if (strContent.length() > MAX_INPUT_LENGTH)
        {
            strContent = strContent.substring(0, MAX_INPUT_LENGTH);
        }


        return true;
    }

    protected void sendMessage()
    {
        if (isPopMsg)
        {
            AppRequestParams params = CommonInterface.requestPopMsgParams(getLiveActivity().getRoomId(), strContent);
            AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_msgActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        // 扣费
                        UserModelDao.payDiamonds(popMsgDiamonds);
                    } else
                    {
                        CommonInterface.requestMyUserInfo(null);
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    CommonInterface.requestMyUserInfo(null);
                }
            });
        } else
        {
            String groupId = getLiveActivity().getGroupId();
            if (TextUtils.isEmpty(groupId))
            {
                return;
            }

            if (!getLiveActivity().isCreater())
            {
                if (sendBlocker.block())
                {
                    SDToast.showToast("发送太频繁");
                    return;
                }
                if (sendBlocker.blockObject(strContent))
                {
                    SDToast.showToast("请勿刷屏");
                    return;
                }
            }

            final CustomMsgText msg = new CustomMsgText();
            msg.setText(strContent);
            IMHelper.sendMsgGroup(groupId, msg, new TIMValueCallBack<TIMMessage>()
            {

                @Override
                public void onSuccess(TIMMessage timMessage)
                {
                    IMHelper.postMsgLocal(msg, timMessage.getConversation().getPeer());
                }

                @Override
                public void onError(int code, String desc)
                {
                    if (code == 80001)
                    {
                        SDToast.showToast("发言含非法词汇");
                    }
                }
            });
        }
        et_content.setText("");
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev)
    {
        SDKeyboardUtil.hideKeyboard(et_content);
        et_content.clearFocus();
        return false;
    }

    @Override
    public void onVisibilityChanged(View view, int visibility)
    {
        super.onVisibilityChanged(view, visibility);
        if (view == this)
        {
            if (View.VISIBLE != visibility)
            {
                SDKeyboardUtil.hideKeyboard(et_content);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_send:
                if (validateContent())
                {
                    sendMessage();
                }
                break;
            case R.id.iv_gift:
                onClickGift(v);
                break;
            default:
                break;
        }
    }

    public void setRoomSendMsgViewListener(RoomSendMsgViewListener listener)
    {
        this.mListener = listener;
    }

    public interface RoomSendMsgViewListener
    {
        void onClickGift(View view);
    }
}
