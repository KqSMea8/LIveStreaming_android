package com.fanwe.baimei.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.games.adapter.BMLiveGiftCoinAdapter;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDDurationBlocker;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDSlidingPageView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.appview.ILivePrivateChatMoreView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveRechargeDialog;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.App_propActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.UserModel;

import java.util.List;

/**
 * 发送礼物view
 */
public class BMLiveSendGiftCoinView extends BaseAppView implements ILivePrivateChatMoreView
{
    public BMLiveSendGiftCoinView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public BMLiveSendGiftCoinView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public BMLiveSendGiftCoinView(Context context)
    {
        super(context);
        init();
    }

    private static final long DURATION_CONTINUE = 5 * 1000;
    private static final long DURATION_COUNT = 100;

    private SDSlidingPageView spv_content;
    private View ll_charge;
    private TextView tv_diamonds;
    private TextView tv_send;
    private View view_continue_send;
    private View view_click_continue_send;
    private TextView tv_continue_number;
    private TextView tv_count_down_number;

    private BMLiveGiftCoinAdapter mAdapterGift;

    private ISDLooper mLooper = new SDSimpleLooper();
    /**
     * 倒计时数字
     */
    private long mCountDownNumber = DURATION_CONTINUE / DURATION_COUNT;
    /**
     * 连击数量
     */
    private int mClickNumber = 0;

    private SDDurationBlocker mBlocker = new SDDurationBlocker(300);

    private SendGiftViewCallback mCallback;

    /**
     * 设置回调
     *
     * @param sendGiftViewCallback
     */
    public void setCallback(SendGiftViewCallback sendGiftViewCallback)
    {
        this.mCallback = sendGiftViewCallback;
    }

    protected void init()
    {
        setContentView(R.layout.bm_view_live_send_gift_coin);

        spv_content = (SDSlidingPageView) findViewById(R.id.spv_content);
        ll_charge = findViewById(R.id.ll_charge);
        tv_diamonds = (TextView) findViewById(R.id.tv_diamonds);
        tv_send = (TextView) findViewById(R.id.tv_send);
        view_continue_send = findViewById(R.id.view_continue_send);
        view_click_continue_send = findViewById(R.id.view_click_continue_send);
        tv_continue_number = (TextView) findViewById(R.id.tv_continue_number);
        tv_count_down_number = (TextView) findViewById(R.id.tv_count_down_number);

        register();
        bindUserData();
    }

    private void register()
    {
        initSlidingView();
        ll_charge.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickCharge();
            }
        });
        tv_send.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickSend();
            }
        });
        view_click_continue_send.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                clickContinueSend();
            }
        });
    }

    private void initSlidingView()
    {
        spv_content.getIndicatorConfig().imageResIdNormal = R.drawable.ic_point_normal_dark;
        spv_content.getIndicatorConfig().imageResIdSelected = R.drawable.ic_point_selected_main_color;
        spv_content.getViewPager().setItemCountPerPage(8);
        spv_content.getViewPager().setColumnCountPerPage(4);
        SDViewUtil.setMarginBottom(spv_content.getViewPager(), SDViewUtil.dp2px(10));

        mAdapterGift = new BMLiveGiftCoinAdapter(null, getActivity());
        mAdapterGift.setItemClickCallback(new SDItemClickCallback<LiveGiftModel>()
        {
            @Override
            public void onItemClick(int position, LiveGiftModel item, View view)
            {
                mAdapterGift.getSelectManager().performClick(position);
                resetClick();
            }
        });
        spv_content.getViewPager().setGridAdapter(mAdapterGift);
    }

    /**
     * 更新用户数据
     */
    public void bindUserData()
    {
        updateCoins(UserModelDao.query());
    }

    /**
     * 更新游戏币数量
     *
     * @param user
     */
    private void updateCoins(UserModel user)
    {
        if (user != null)
        {
            SDViewBinder.setTextView(tv_diamonds, String.valueOf(user.getCoin()));
        }
    }

    /**
     * 发送某礼物成功调用，更新本地秀豆数量
     *
     * @param giftModel
     */
    public void sendGiftSuccess(final LiveGiftModel giftModel)
    {
        if (giftModel != null)
        {
            SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<UserModel>()
            {
                @Override
                public UserModel onBackground()
                {
                    return UserModelDao.payCoins(giftModel.getCoins());
                }

                @Override
                public void onMainThread(UserModel result)
                {
                    updateCoins(result);
                }
            });
        }
    }

    /**
     * 开始连击倒计时
     */
    private void startCountDownNumberLooper()
    {
        resetCountDownNumber();
        mLooper.start(DURATION_COUNT, mCountDownNumberRunnable);
    }

    private Runnable mCountDownNumberRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            mCountDownNumber--;
            if (mCountDownNumber <= 0)
            {
                resetClick();
            } else
            {
                if (mClickNumber > 0)
                {
                    tv_continue_number.setText("X" + mClickNumber);
                }
                tv_count_down_number.setText(String.valueOf(mCountDownNumber));
            }
        }
    };

    protected void clickCharge()
    {
        LiveRechargeDialog dialog = new LiveRechargeDialog(getActivity());
        dialog.showCenter();
    }

    private void resetClick()
    {
        mLooper.stop();
        mClickNumber = 0;
        tv_continue_number.setText("");
        hideContinueMode();
    }

    private void resetCountDownNumber()
    {
        mCountDownNumber = DURATION_CONTINUE / DURATION_COUNT;
    }

    public LiveGiftModel getSelectedGiftModel()
    {
        return mAdapterGift.getSelectManager().getSelectedItem();
    }

    /**
     * 请求礼物列表
     */
    public void requestData()
    {
        if (mAdapterGift.getItemCount() <= 0)
        {
            CommonInterface.requestGift(new AppRequestCallback<App_propActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        setDataGift(actModel.getList());
                    }
                }
            });
        } else
        {
            mAdapterGift.notifyDataSetChanged();
        }
    }

    /**
     * 设置礼物列表数据
     *
     * @param listGift
     */
    public void setDataGift(List<LiveGiftModel> listGift)
    {
        mAdapterGift.updateData(listGift);
    }

    private boolean validateSend()
    {
        if (!SDNetworkReceiver.isNetworkConnected(getActivity()))
        {
            SDToast.showToast("无网络");
            return false;
        }

        if (getSelectedGiftModel() == null)
        {
            SDToast.showToast("请选择礼物");
            return false;
        }

        if (!UserModelDao.canCoinsPay(getSelectedGiftModel().getCoins()))
        {
            SDToast.showToast("余额不足");
            return false;
        }

        return true;
    }

    /**
     * 点击发送按钮
     */
    private void clickSend()
    {
        if (validateSend())
        {
            if (getSelectedGiftModel().getIs_much() == 1)
            {
                showContinueMode();
                clickContinueSend();

            } else
            {
                //通知发送按钮被点击接口
                if (mCallback != null)
                {
                    mCallback.onClickSend(getSelectedGiftModel(),1, 0);
                }
            }
        }
    }

    /**
     * 触发连击调用方法
     */
    protected void clickContinueSend()
    {
        if (mBlocker.block())
        {
            return;
        }

        if (validateSend())
        {
            mClickNumber++;
            startCountDownNumberLooper();

            int is_plus = 0;
            if (mClickNumber > 1)
            {
                is_plus = 1;
            } else
            {
                is_plus = 0;
            }

            if (mCallback != null)
            {
                mCallback.onClickSend(getSelectedGiftModel(),1, is_plus);
            }
        }
    }

    /**
     * 显示连击模式
     */
    private void showContinueMode()
    {
        SDViewUtil.setGone(tv_send);
        SDViewUtil.setVisible(view_continue_send);
    }

    /**
     * 隐藏连击模式
     */
    private void hideContinueMode()
    {
        SDViewUtil.setGone(view_continue_send);
        SDViewUtil.setVisible(tv_send);
    }

    /**
     * 本地用户数据更新事件
     *
     * @param event
     */
    public void onEventMainThread(EUpdateUserInfo event)
    {
        bindUserData();
    }

    @Override
    public void setHeightMatchParent()
    {
        SDViewUtil.setHeightWeight(spv_content, 1);

        spv_content.setContentHeightMatchParent();
    }

    @Override
    public void setHeightWrapContent()
    {
        SDViewUtil.setHeightWrapContent(spv_content);

        spv_content.setContentHeightWrapContent();
    }

    public interface SendGiftViewCallback
    {
        /**
         * 礼物点击
         *
         * @param model   要发送的礼物
         * @param is_plus 1-需要叠加数量，0-不需要叠加数量
         */
        void onClickSend(LiveGiftModel model,int num, int is_plus);
    }

}
