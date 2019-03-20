package com.fanwe.live.appview.room;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
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
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_msgActModel;
import com.fanwe.live.model.UpdataPackgeGift;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.fanwe.live.view.MultyTabView;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import de.greenrobot.event.EventBus;

public class RoomSendMsgView extends RoomView implements MultyTabView.TabChangeListener {

    public RoomSendMsgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RoomSendMsgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoomSendMsgView(Context context) {
        super(context);
        init();
    }

    /**
     * 相同消息拦截间隔
     */
    private static final int DUR_BLOCK_SAME_MSG = 5 * 1000;
    /**
     * 消息拦截间隔
     */
    private static final int DUR_BLOCK_MSG = 2 * 1000;

    private ImageView iv_pop_msg_handle;
    private EditText et_content;
    private TextView tv_send;

    private String popMsgDiamonds;
    private String fullMsgDiamonds;
    private String strContent;
    private int msg_type = 0;
    private int v_identity;
    private SDObjectBlocker sendBlocker;
    private MultyTabView multyTabView;
    private String v_identity_colour;
    private int text_size;

    public String getV_identity_colour() {
        return v_identity_colour;
    }

    public void setV_identity_colour(String v_identity_colour) {
        this.v_identity_colour = v_identity_colour;
    }

    public int getText_size() {
        return text_size;
    }

    public void setText_size(int text_size) {
        this.text_size = text_size;
    }

    public int getV_identity() {
        return v_identity;
    }

    public void initSendMeaasgeView(String v_identity_colour, int text_size) {
        if(0==text_size){
            setText_size(255);
        }else{
            setText_size(text_size);
        }
        setV_identity_colour(v_identity_colour);
    }

    public void setV_identity(int v_identity) {
        this.v_identity = v_identity;
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_send_msg;
    }

    protected void init() {
        iv_pop_msg_handle = find(R.id.iv_pop_msg_handle);
        et_content = find(R.id.et_content);
        tv_send = find(R.id.tv_send);
        multyTabView = find(R.id.multyTabView);
        multyTabView.setOnClickListener(multyTabView);
        if (getLiveActivity().isCreater()) {
            multyTabView.setType_count(2);
        } else {
            multyTabView.setType_count(3);
        }
        multyTabView.setTabChangeListener(this);
        sendBlocker = new SDObjectBlocker();
        sendBlocker.setMaxEqualsCount(1);
        sendBlocker.setBlockEqualsObjectDuration(DUR_BLOCK_SAME_MSG);
        sendBlocker.setBlockDuration(DUR_BLOCK_MSG);
        SDViewUtil.setInvisible(this);
        register();
    }

    private void register() {
        InitActModel model = InitActModelDao.query();
        if (model != null) {
            popMsgDiamonds = model.getPop_msg_money();
            fullMsgDiamonds = model.getFull_msg_money();
        }
        et_content.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateContent()) {
                    sendMessage();
                }
            }
        });
    }

    public void setContent(String content) {
        if (content == null) {
            content = "";
        }
        et_content.setText(content);
        et_content.setSelection(et_content.getText().length());
        SDKeyboardUtil.showKeyboard(et_content, 100);
    }

    private boolean validateContent() {
        if (!SDNetworkReceiver.isNetworkConnected(getActivity())) {
            SDToast.showToast("无网络");
            return false;
        }

        if (!getLiveActivity().isCreater()) {
            UserModel userModel = UserModelDao.query();
            if (userModel != null) {
                if (!userModel.canSendMsg()) {
                    SDToast.showToast("未达到发言等级，不能发言");
                    return false;
                }
            }
        }

        strContent = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(strContent)) {
            SDToast.showToast("请输入内容");
            return false;
        }

        if (strContent.contains("\n")) {
            strContent = strContent.replace("\n", "");
        }

        if (strContent.length() > getText_size()) {
            strContent = strContent.substring(0, getText_size());
        }

        return true;
    }

    protected void sendMessage() {
        if (msg_type == 1) {
            AppRequestParams params = CommonInterface.requestPopMsgParams(getLiveActivity().getRoomId(), strContent);
            AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_msgActModel>() {
                @Override
                protected void onSuccess(SDResponse resp) {
                    if (1==actModel.getStatus()) {
                        if(1==actModel.getIs_backpack()){
                            SDToast.showToast("已消耗一个背包弹幕");
                            EventBus.getDefault().post(new UpdataPackgeGift());
                        }else{
                            // 扣费
                            UserModelDao.payDiamonds(Integer.parseInt(popMsgDiamonds));
                        }
                    } else {
                        CommonInterface.requestMyUserInfo(null);
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    CommonInterface.requestMyUserInfo(null);
                }
            });
        } else if (msg_type == 2) {
            //喇叭
            AppRequestParams params = CommonInterface.requestFullMsgParams(getLiveActivity().getRoomId(), strContent);
            AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_msgActModel>() {
                @Override
                protected void onSuccess(SDResponse resp) {
                    if (1==actModel.getStatus()) {
                        if(1==actModel.getIs_backpack()){
                            SDToast.showToast("已消耗一个背包喇叭");
                            EventBus.getDefault().post(new UpdataPackgeGift());
                        }else{
                            // 扣费
                            UserModelDao.payDiamonds(Integer.parseInt(fullMsgDiamonds));
                        }
                    } else {
                        CommonInterface.requestMyUserInfo(null);
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    CommonInterface.requestMyUserInfo(null);
                }
            });
        } else {
            String groupId = getLiveActivity().getGroupId();
            if (TextUtils.isEmpty(groupId)) {
                return;
            }

            if (!getLiveActivity().isCreater()) {
                if (sendBlocker.block()) {
                    SDToast.showToast("发送太频繁");
                    return;
                }
                if (sendBlocker.blockObject(strContent)) {
                    SDToast.showToast("请勿刷屏");
                    return;
                }
            }

            final CustomMsgText msg = new CustomMsgText();
            msg.setV_identity_colour(getV_identity_colour());
            msg.getSender().setV_identity(getV_identity());
            msg.setText(strContent);
            IMHelper.sendMsgGroup(groupId, msg, new TIMValueCallBack<TIMMessage>() {

                @Override
                public void onSuccess(TIMMessage timMessage) {
                    IMHelper.postMsgLocal(msg, timMessage.getConversation().getPeer());
                }

                @Override
                public void onError(int code, String desc) {
                    if (code == 80001) {
                        SDToast.showToast("该词已被禁用");
                    }
                }
            });
        }
        et_content.setText("");
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev) {
        SDViewUtil.setInvisible(this);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        SDViewUtil.setInvisible(this);
        return true;
    }

    @Override
    public void onVisibilityChanged(View view, int visibility) {
        super.onVisibilityChanged(view, visibility);
        if (view == this) {
            if (View.VISIBLE == visibility) {
                SDKeyboardUtil.showKeyboard(et_content);
            } else {
                SDKeyboardUtil.hideKeyboard(et_content);
            }
        }
    }

    @Override
    public void tabChange(int index) {
        switch (index) {
            case 1:
                et_content.setHint("开启弹幕，" + popMsgDiamonds + "秀豆/条");
                break;
            case 0:
                et_content.setHint(SDResourcesUtil.getString(R.string.live_send_msg_hint_normal));
                break;
            case 2:
                et_content.setHint("开启大喇叭，" + fullMsgDiamonds + "秀豆/条");
                break;

        }
        msg_type = index;
    }
}
