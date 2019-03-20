package com.fanwe.live.appview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.media.player.SDMediaPlayer;
import com.fanwe.library.media.recorder.SDMediaRecorder;
import com.fanwe.library.media.recorder.SDSimpleMediaRecorderListener;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.title.SDTitleSimple.SDTitleSimpleListener;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDCountDownTimer;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDDialogUtil;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewSizeLocker;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecordView;
import com.fanwe.library.view.SDRecordView.RecordViewListener;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.library.view.SDReplaceableLayout;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LivePrivateChatRecyclerAdapter;
import com.fanwe.live.adapter.viewholder.privatechat.PrivateChatViewHolder;
import com.fanwe.live.business.LivePrivateChatBusiness;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveGameExchangeDialog;
import com.fanwe.live.dialog.PrivateChatLongClickMenuDialog;
import com.fanwe.live.event.EImOnNewMessages;
import com.fanwe.live.event.ESDMediaPlayerStateChanged;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.LiveExpressionModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgPrivateVoice;
import com.fanwe.live.model.custommsg.MsgModel;
import com.fanwe.live.span.LiveExpressionSpan;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.io.File;
import java.util.List;

/**
 * 私聊界面
 */
public class LivePrivateChatView extends BaseAppView implements LivePrivateChatRecyclerAdapter.Onclick{
    public LivePrivateChatView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LivePrivateChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LivePrivateChatView(Context context) {
        super(context);
        init();
    }

    /**
     * 延迟多少毫秒执行滚动逻辑
     */
    private static final long SCROLL_DELAY = 100;
    /**
     * 最大输入字符长度
     */
    private static final int MAX_INPUT_LENGTH = 255;


    private SDTitleSimple title;
    private LivePrivateChatRecordView view_record;
    private LivePrivateChatBarView view_chat_bar;
    private SDProgressPullToRefreshRecyclerView lv_content;
    private SDReplaceableLayout fl_bottom_extend;

    private LiveExpressionView view_expression;
    private LivePrivateChatMoreView view_more;
    private LiveSendGiftView view_gift;

    private LivePrivateChatRecyclerAdapter mAdapter;

    private ImageFileCompresser mImageFileCompresser;
    private PhotoHandler mPhotoHandler;

    private boolean mOnUpCancelView;
    private ClickListener mClickListener;

    private boolean mLockHeightEnable;
    private int mLockHeight;
    private SDViewSizeLocker mContentSizeLocker;

    private LivePrivateChatBusiness mChatBusiness;

    private void init() {
        setContentView(R.layout.view_live_private_chat);

        title = find(R.id.title);
        view_record = find(R.id.view_record);
        view_chat_bar = find(R.id.view_chat_bar);
        lv_content = find(R.id.lv_content);
        fl_bottom_extend = find(R.id.fl_bottom_extend);
        lv_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBottomExtend(false);
            }
        });
        mChatBusiness = new LivePrivateChatBusiness(mPrivateChatBusinessCallback);

        mContentSizeLocker = new SDViewSizeLocker(lv_content);
        initTitle();

        initPullView();

        view_chat_bar.setClickListener(mChatbarClickListener);
        view_chat_bar.et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MAX_INPUT_LENGTH)});
        view_record.setRecordViewListener(recordViewListener);
        view_record.setRecordView(view_chat_bar.tv_record);


        SDMediaRecorder.getInstance().registerListener(recorderListener);
        SDMediaRecorder.getInstance().registerTimerListener(recordTimerListener);
        SDMediaRecorder.getInstance().setMaxRecordTime(60 * 1000);

        initImageFileCompresser();
        initPhotoHandler();

        if (LiveInformation.getInstance().getRoomId() > 0) {
            setVoiceModeEnable(false);
            setTakePhotoEnable(false);
        } else {
            setVoiceModeEnable(true);
            setTakePhotoEnable(true);
        }

        dealHasPrivateChat();
    }

    /**
     * 设置私聊用户id
     *
     * @param userId
     */
    public void setUserId(String userId) {
        mChatBusiness.setUserId(userId);
        mChatBusiness.requestUserInfo();
        mChatBusiness.loadHistoryMessage(20);
    }

    /**
     * 设置内容高度锁定功能是否可用
     *
     * @param lockHeightEnable
     */
    public void setLockHeightEnable(boolean lockHeightEnable) {
        mLockHeightEnable = lockHeightEnable;
    }

    /**
     * 添加底部扩展监听
     *
     * @param callback
     */
    public void addBottomExtendCallback(SDReplaceableLayout.SDReplaceableLayoutCallback callback) {
        fl_bottom_extend.addCallback(callback);
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * 软键盘显示隐藏
     *
     * @param visible
     * @param height
     */
    public void onKeyboardVisibilityChange(boolean visible, int height) {
        if (visible) {
            calculateLockHeight();
        }
    }

    /**
     * 计算要锁定内容的高度
     */
    private void calculateLockHeight() {
        if (mLockHeight > 0) {
            return;
        }

        Rect rectLv = new Rect();
        lv_content.getGlobalVisibleRect(rectLv);

        mLockHeight = rectLv.height();
    }

    private void initImageFileCompresser() {
        mImageFileCompresser = new ImageFileCompresser();
        mImageFileCompresser.setmListener(new ImageFileCompresser.ImageFileCompresserListener() {
            @Override
            public void onStart() {
                showProgressDialog("正在处理图片");
            }

            @Override
            public void onSuccess(File fileCompressed) {
                mChatBusiness.sendIMImage(fileCompressed);
            }

            @Override
            public void onFailure(String msg) {
                SDToast.showToast(msg);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    private void initPullView() {
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {
                mChatBusiness.loadHistoryMessage(20);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {
            }
        });

        mAdapter = new LivePrivateChatRecyclerAdapter(getActivity());
        mAdapter.setClickListener(adapterClickListener);
        mAdapter.setOnclick(this);
        lv_content.getRefreshableView().setAdapter(mAdapter);

    }

    private void initPhotoHandler() {
        mPhotoHandler = new PhotoHandler((FragmentActivity) getActivity());
        mPhotoHandler.setListener(new PhotoHandler.PhotoHandlerListener() {
            @Override
            public void onResultFromAlbum(File file) {
                dealImage(file);
            }

            @Override
            public void onResultFromCamera(File file) {
                dealImage(file);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void dealImage(File file) {
        if (file != null) {
            mImageFileCompresser.compressImageFile(file);
        }
    }

    /**
     * 设置发送语音是否可用
     *
     * @param voiceModeEnable
     */
    public void setVoiceModeEnable(boolean voiceModeEnable) {
        if (view_chat_bar != null) {
            view_chat_bar.setVoiceModeEnable(voiceModeEnable);
        }
    }

    /**
     * 设置拍照是否可用
     *
     * @param takePhotoEnable
     */
    public void setTakePhotoEnable(boolean takePhotoEnable) {
        getMoreView();
        if (view_more != null) {
            view_more.setTakePhotoEnable(takePhotoEnable);
        }
    }

    /**
     * 隐藏输入法
     */
    public void hideKeyboard() {
        view_chat_bar.hideKeyboard();
    }

    /**
     * 获得表情布局
     *
     * @return
     */
    private View getExpressionView() {
        if (view_expression == null) {
            view_expression = new LiveExpressionView(getContext());
            view_expression.setCallback(mExpressionViewCallback);
        }
        return view_expression;
    }

    /**
     * 获得更多布局
     *
     * @return
     */
    private View getMoreView() {
        if (view_more == null) {
            view_more = new LivePrivateChatMoreView(getContext());
            view_more.setSendCoinsEnable(InitActModelDao.query().getOpen_send_coins_module() == 1);
            view_more.setSendDiamondsEnable(InitActModelDao.query().getOpen_send_diamonds_module() == 1);
            view_more.setCallback(mPrivateChatMoreViewCallback);
        }
        return view_more;
    }

    /**
     * 获得礼物布局
     *
     * @return
     */
    private View getGiftView() {
        if (view_gift == null) {
            view_gift = new LiveSendGiftView(getContext());
            view_gift.requestData();
            view_gift.setCallback(mSendGiftViewCallback);
        }
        CommonInterface.requestMyUserInfo(null);
        return view_gift;
    }

    private void initTitle() {
        title.initRightItem(1);
        title.getItemRight(0).setImageRight(R.drawable.ic_private_chat_title_bar_user);
        title.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        title.setmListener(new SDTitleSimpleListener() {

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, mChatBusiness.getUserId());
                getActivity().startActivity(intent);
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {
            }

            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
                if (mClickListener != null) {
                    mClickListener.onClickBack();
                }
            }
        });
    }

    /**
     * 发送礼物布局监听
     */
    private LiveSendGiftView.SendGiftViewCallback mSendGiftViewCallback = new LiveSendGiftView.SendGiftViewCallback() {

        @Override
        public void onClickSend(LiveGiftModel model, int num, int is_plus, int is_packge, String json) {
            mChatBusiness.requestSendGiftPrivate(model,num, is_packge, json);
        }

        @Override
        public void hide() {
            removeBottomExtend(false);
        }
    };

    /**
     * 表情布局监听
     */
    private LiveExpressionView.ExpressionViewCallback mExpressionViewCallback = new LiveExpressionView.ExpressionViewCallback() {
        @Override
        public void onClickExpression(LiveExpressionModel model) {
            String key = model.getKey();

            if (MAX_INPUT_LENGTH > 0) {
                if (key != null) {
                    int length = view_chat_bar.et_content.getText().toString().length();
                    if ((length + key.length()) > MAX_INPUT_LENGTH) {
                        return;
                    }
                }
            }

            view_chat_bar.et_content.insertSpan(new LiveExpressionSpan(getContext(), model.getResId()), key);
        }

        @Override
        public void onClickDelete() {
            view_chat_bar.et_content.delete();
        }
    };

    /**
     * 更多里面的内容点击监听
     */
    private LivePrivateChatMoreView.PrivateChatMoreViewCallback mPrivateChatMoreViewCallback = new LivePrivateChatMoreView.PrivateChatMoreViewCallback() {
        @Override
        public void onClickGift() {
            showBottomExtendGift();
        }

        @Override
        public void onClickPhoto() {
            mPhotoHandler.getPhotoFromAlbum();
        }

        @Override
        public void onClickCamera() {
            mPhotoHandler.getPhotoFromCamera();
        }

        @Override
        public void onClickSendCoin() {
            long coins = UserModelDao.query().getCoin();
            LiveGameExchangeDialog dialog = new LiveGameExchangeDialog(getActivity(), LiveGameExchangeDialog.TYPE_COIN_SEND, mListener);
            dialog.setCurrency(coins);
            dialog.setToUserId(mChatBusiness.getUserId());
            dialog.showCenter();
            CommonInterface.requestMyUserInfo(null);
        }

        @Override
        public void onClickSendDialond() {
            long diamonds = UserModelDao.query().getDiamonds();
            LiveGameExchangeDialog dialog = new LiveGameExchangeDialog(getActivity(), LiveGameExchangeDialog.TYPE_DIAMOND_SEND, mListener);
            dialog.setCurrency(diamonds);
            dialog.setToUserId(mChatBusiness.getUserId());
            dialog.showCenter();
            CommonInterface.requestMyUserInfo(null);
        }
    };

    /**
     * 触摸监听
     */
    private SDRecordView.RecordViewListener recordViewListener = new RecordViewListener() {
        @Override
        public void onUpCancelView() {
            mOnUpCancelView = true;
            view_chat_bar.tv_record.setText("按住说话");
            view_chat_bar.tv_record.setBackgroundResource(R.drawable.layer_white_stroke_corner_item_single);
            SDMediaRecorder.getInstance().stop();
        }

        @Override
        public void onUp() {
            mOnUpCancelView = false;
            view_chat_bar.tv_record.setText("按住说话");
            view_chat_bar.tv_record.setBackgroundResource(R.drawable.layer_white_stroke_corner_item_single);
            SDMediaRecorder.getInstance().stop();
        }

        @Override
        public void onLeaveCancelView() {
            view_chat_bar.tv_record.setText("松开结束");
        }

        @Override
        public void onEnterCancelView() {
            view_chat_bar.tv_record.setText("松开手指,取消发送");
        }

        @Override
        public boolean onDownRecordView() {
            view_chat_bar.tv_record.setText("松开结束");
            view_chat_bar.tv_record.setBackgroundResource(R.drawable.layer_gray_stroke_corner_item_single);
            SDMediaRecorder.getInstance().start(null);

            return true;
        }

        @Override
        public void onCancel() {
            SDMediaRecorder.getInstance().stop();
        }
    };

    /**
     * 录音计时监听
     */
    private SDCountDownTimer.SDCountDownTimerListener recordTimerListener = new SDCountDownTimer.SDCountDownTimerListener() {
        @Override
        public void onTick(long leftTime) {
            view_record.setTextRecordTime(SDDateUtil.formatDuring2mmss(leftTime));
        }

        @Override
        public void onFinish() {
            view_record.setTextRecordTime(String.valueOf(0));
            view_record.cancelGesture();
        }
    };

    /**
     * 录音监听
     */
    private SDSimpleMediaRecorderListener recorderListener = new SDSimpleMediaRecorderListener() {
        @Override
        public void onStopped(File file, long duration) {
            if (file != null) {
                if (mOnUpCancelView) {
                    SDFileUtil.deleteFileOrDir(file);
                } else {
                    if (duration < 1000) {
                        SDFileUtil.deleteFileOrDir(file);
                        SDToast.showToast("录音时间太短");
                    } else {
                        mChatBusiness.sendIMVoice(file, duration);
                    }
                }
            }
        }
    };

    /**
     * 锁定聊天内容高度
     */
    public void lockContent() {
        if (mLockHeightEnable) {
            if (mLockHeight > 0) {
                mContentSizeLocker.lockHeight(mLockHeight);
                SDViewUtil.setHeightMatchParent(fl_bottom_extend);
            }
        }
    }

    /**
     * 解锁聊天内容高度
     */
    public void unLockContent() {
        if (mLockHeightEnable) {
            if (mLockHeight > 0) {
                mContentSizeLocker.unlockHeight();
                SDViewUtil.setHeightWrapContent(fl_bottom_extend);
            }
        }
    }

    /**
     * 底部聊天栏点击监听
     */
    private LivePrivateChatBarView.ClickListener mChatbarClickListener = new LivePrivateChatBarView.ClickListener() {
        @Override
        public void onClickKeyboard() {
            removeBottomExtend(true);
            lv_content.getRefreshableView().scrollToEndDelayed(SCROLL_DELAY);
        }

        @Override
        public void onClickVoice() {
            removeBottomExtend(true);
        }

        @Override
        public void onClickShowExpression() {
//            lockContent();
            showBottomExtendExpression();
            lv_content.getRefreshableView().scrollToEndDelayed(SCROLL_DELAY);
        }

        @Override
        public void onClickHideExpression() {
            removeBottomExtend(false);
        }

        @Override
        public void onClickMore() {
//            lockContent();
            showBottomExtendMore();
            lv_content.getRefreshableView().scrollToEndDelayed(SCROLL_DELAY);
        }

        @Override
        public void onClickSend(String content) {
            if (TextUtils.isEmpty(content)) {
                SDToast.showToast("请输入内容");
                return;
            }
            mChatBusiness.sendIMText(content);
            view_chat_bar.et_content.setText("");
        }

        @Override
        public boolean onTouchEditText() {
            removeBottomExtend(false);
            lv_content.getRefreshableView().scrollToEndDelayed(SCROLL_DELAY);
            return false;
        }
    };

    /**
     * 显示底部表情布局
     */
    private void showBottomExtendExpression() {
        replaceBottomExtend(getExpressionView());
    }

    /**
     * 显示底部礼物布局
     */
    private void showBottomExtendGift() {
        replaceBottomExtend(getGiftView());
    }

    /**
     * 显示底部更多布局
     */
    private void showBottomExtendMore() {
        replaceBottomExtend(getMoreView());
    }

    private void replaceBottomExtend(View view) {
        if (mLockHeightEnable) {
            if (view instanceof ILivePrivateChatMoreView) {
                ILivePrivateChatMoreView moreView = (ILivePrivateChatMoreView) view;
//                if (mContentSizeLocker.hasLockHeight())
//                {
//                    moreView.setHeightMatchParent();
//                } else
//                {
                moreView.setHeightWrapContent();
//                }
            }
        }

        fl_bottom_extend.replaceContent(view);
    }

    /**
     * 移除底部扩展布局
     */
    private void removeBottomExtend(boolean unlockContent) {
        if (unlockContent) {
//            unLockContent();
        }
        fl_bottom_extend.removeContent();
    }

    private void dealHasPrivateChat() {
        if (AppRuntimeWorker.hasPrivateChat()) {
            if (mChatBusiness.canSendPrivateLetter()) {
                SDViewUtil.setVisible(view_chat_bar);
            } else {
                SDViewUtil.setGone(view_chat_bar);
            }
        } else {
            SDViewUtil.setGone(view_chat_bar);
        }
    }

    /**
     * 接收新消息
     *
     * @param event
     */
    public void onEventMainThread(EImOnNewMessages event) {
        mChatBusiness.onEventMainThread(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN)
//        {
//            boolean isAbove = ev.getRawY() < SDViewUtil.getGlobalVisibleRect(view_chat_bar).bottom;
//        if(null==view_gift){
//
//        }else if(view_gift.isShown()){
//            view_chat_bar.showNormalMode();
//            removeBottomExtend(true);
//        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 聊天列表点击监听
     */
    private PrivateChatViewHolder.ClickListener adapterClickListener = new PrivateChatViewHolder.ClickListener() {

        @Override
        public void onClickResend(MsgModel model) {
            mChatBusiness.sendIMMsg(model);
        }

        @Override
        public void onClickHeadImage(MsgModel model) {
            Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
            intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, model.getCustomMsg().getSender().getUser_id());
            getActivity().startActivity(intent);
        }

        @Override
        public void onClickBack() {
            if (null == view_gift) {
                removeBottomExtend(false);
            }
        }

        @Override
        public void onLongClick(final MsgModel model, View v) {
            final PrivateChatLongClickMenuDialog dialog = new PrivateChatLongClickMenuDialog(getActivity());
            switch (model.getCustomMsgType()) {
                case LiveConstant.CustomMsgType.MSG_PRIVATE_TEXT:
                    dialog.setItems("复制");
                    dialog.setItemClickCallback(new SDItemClickCallback<String>() {
                        @Override
                        public void onItemClick(int position, String item, View view) {
                            SDOtherUtil.copyText(model.getCustomMsgPrivateText().getText());
                            SDToast.showToast("已复制");
                            dialog.dismiss();
                        }
                    });
                    SDDialogUtil.setDialogTopAlignCenter(dialog, v, 10, 0);
                    dialog.show();
                    break;

                default:
                    break;
            }
        }
    };

    public void onEventMainThread(ESDMediaPlayerStateChanged event) {
        if (event.state == SDMediaPlayer.State.Completed ||
                event.state == SDMediaPlayer.State.Stopped ||
                event.state == SDMediaPlayer.State.Playing) {
            List<MsgModel> listMsg = mAdapter.getData();
            if (!SDCollectionUtil.isEmpty(listMsg)) {
                int i = 0;
                for (MsgModel msg : listMsg) {
                    if (msg.getCustomMsgType() == LiveConstant.CustomMsgType.MSG_PRIVATE_VOICE) {
                        CustomMsgPrivateVoice msgVoice = msg.getCustomMsgPrivateVoice();
                        String filePah = msgVoice.getPath();
                        if (SDMediaPlayer.getInstance().hasDataFilePath(filePah)) {
                            mAdapter.updateData(i);
                        }
                    }
                    i++;
                }
            }
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mPhotoHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDetachedFromWindow() {
        mChatBusiness.onDestroy();
        SDMediaRecorder.getInstance().stop();
        SDMediaRecorder.getInstance().unregisterListener(recorderListener);
        SDMediaRecorder.getInstance().unregisterTimerListener(recordTimerListener);
        SDMediaPlayer.getInstance().reset();
        mImageFileCompresser.deleteCompressedImageFile();

        super.onDetachedFromWindow();
    }

    @Override
    public void click() {
        removeBottomExtend(false);
    }

    public interface ClickListener {
        void onClickBack();
    }

    private LiveGameExchangeDialog.OnSuccessListener mListener = new LiveGameExchangeDialog.OnSuccessListener() {
        @Override
        public void onExchangeSuccess(long diamonds, long coins) {

        }

        @Override
        public void onSendCurrencySuccess(Deal_send_propActModel model) {
            mChatBusiness.sendIMGift(model);
        }
    };

    /**
     * 业务回调
     */
    private LivePrivateChatBusiness.LivePrivateChatBusinessCallback mPrivateChatBusinessCallback = new LivePrivateChatBusiness.LivePrivateChatBusinessCallback() {
        @Override
        public void onBsShowProgress(String msg) {

        }

        @Override
        public void onBsHideProgress() {

        }

        @Override
        public void onRequestUserInfoSuccess(UserModel userModel) {
            title.setMiddleTextTop(userModel.getNick_name());
        }

        @Override
        public void onRequestSendGiftPrivateSuccess(Deal_send_propActModel actModel, LiveGiftModel giftModel) {
            view_gift.sendGiftSuccess(giftModel);
            mChatBusiness.sendIMGift(actModel);
        }

        @Override
        public void onLoadHistoryMessageSuccess(List<MsgModel> listMsg) {
            if (listMsg != null) {
                mAdapter.insertData(0, listMsg);
            }
            lv_content.onRefreshComplete();
            lv_content.getRefreshableView().scrollToPosition(listMsg.size() - 1);
        }

        @Override
        public void onLoadHistoryMessageError() {
            lv_content.onRefreshComplete();
        }

        @Override
        public void onAdapterAppendData(MsgModel model) {
            mAdapter.appendData(model);
            lv_content.getRefreshableView().scrollToEndDelayed(SCROLL_DELAY);
        }

        @Override
        public void onAdapterUpdateData(int position, MsgModel model) {
            mAdapter.updateData(position, model);
        }

        @Override
        public void onAdapterUpdateData(int position) {
            mAdapter.updateData(position);
        }

        @Override
        public int onAdapterIndexOf(MsgModel model) {
            return mAdapter.indexOf(model);
        }
    };
}