package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveSendGiftView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_propActModel;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

public class RoomSendGiftView extends RoomView
{
    public RoomSendGiftView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomSendGiftView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomSendGiftView(Context context)
    {
        super(context);
    }

    private LiveSendGiftView view_send_gift;

    private SDAnim mAnimVisible;
    private SDAnim mAnimInvisible;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_send_gift;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();

        view_send_gift = (LiveSendGiftView) findViewById(R.id.view_send_gift);
        view_send_gift.setCallback(new LiveSendGiftView.SendGiftViewCallback()
        {

            @Override
            public void onClickSend(LiveGiftModel model, int num, int is_plus, int is_packge, String json) {
                sendGift(model,num, is_plus,is_packge);
            }

            @Override
            public void hide() {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mAnimVisible == null)
        {
            mAnimVisible = SDAnim.from(this);
            setVisibleAnimator(mAnimVisible.get());
        }
        mAnimVisible.translationY(h, 0);

        if (mAnimInvisible == null)
        {
            mAnimInvisible = SDAnim.from(this);
            setInvisibleAnimator(mAnimInvisible.get());
        }
        mAnimInvisible.translationY(0, h);
    }

    private void sendGift(final LiveGiftModel giftModel,int num, int is_plus,int is_packge)
    {
        if (giftModel != null)
        {
            if (getLiveActivity().getRoomInfo() == null)
            {
                return;
            }

            if (getLiveActivity().getRoomInfo().getLive_in() == 0)
            {
                //私聊发礼物接口
                final String createrId = getLiveActivity().getCreaterId();
                if (createrId != null)
                {
                    CommonInterface.requestSendGiftPrivate(giftModel.getId(), num, createrId,0,null, new AppRequestCallback<Deal_send_propActModel>()
                    {
                        @Override
                        protected void onSuccess(SDResponse resp)
                        {
                            if (actModel.isOk())
                            {
                                view_send_gift.sendGiftSuccess(giftModel);

                                // 发送私聊消息给主播
                                final CustomMsgPrivateGift msg = new CustomMsgPrivateGift();
                                msg.fillData(actModel);
                                IMHelper.sendMsgC2C(createrId, msg, new TIMValueCallBack<TIMMessage>()
                                {
                                    @Override
                                    public void onError(int i, String s)
                                    {
                                    }

                                    @Override
                                    public void onSuccess(TIMMessage timMessage)
                                    {
//                                         如果私聊界面不是每次都加载的话要post一条来刷新界面
                                         IMHelper.postMsgLocal(msg, createrId);
                                    }
                                });
                            }
                        }
                    });
                }
            } else
            {
                AppRequestParams params = CommonInterface.requestSendGiftParams(giftModel.getId(), num, is_plus, 0, getLiveActivity().getRoomId(),is_packge,null);
                AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_propActModel>()
                {
                    @Override
                    protected void onSuccess(SDResponse resp)
                    {
                        // 扣费
                        if (actModel.isOk())
                        {
                            if(actModel.getAward().getStatus()==1){
                                CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {
                                    @Override
                                    protected void onSuccess(SDResponse resp) {
                                        if (actModel.getStatus() == 1) {
                                            UserModelDao.updateDiamonds(actModel.getUser().getDiamonds());
                                        }
                                    }
                                });
                            }
                            view_send_gift.sendGiftSuccess(giftModel);
                        }
                    }

                    @Override
                    protected void onError(SDResponse resp)
                    {
                        CommonInterface.requestMyUserInfo(null);
                    }
                });
            }
        }
    }

    public void bindData()
    {
        if (view_send_gift != null)
        {
            view_send_gift.requestData();
            view_send_gift.bindUserData();
        }
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev)
    {
        getVisibilityHandler().setInvisible(true);
        return true;
    }

    @Override
    public boolean onBackPressed()
    {
        getVisibilityHandler().setInvisible(true);
        return true;
    }
}
