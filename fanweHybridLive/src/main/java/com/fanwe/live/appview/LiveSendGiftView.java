package com.fanwe.live.appview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDDurationBlocker;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDSlidingPageView;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveGiftAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveRechargeDialog;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.App_propNewActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.PackgeModel;
import com.fanwe.live.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 发送礼物view
 */
public class LiveSendGiftView extends BaseAppView implements ILivePrivateChatMoreView {
    public LiveSendGiftView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LiveSendGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LiveSendGiftView(Context context) {
        super(context);
        init();
    }

    private static final long DURATION_CONTINUE = 5 * 1000;
    private static final long DURATION_COUNT = 100;
    private SDSlidingPageView spv_content;
    private SDSlidingPageView spv_packge;
    private View ll_charge;
    private TextView tv_diamonds;
    private TextView tv_send;
    private ImageView iv_hide;
    private View view_continue_send;
    private CheckBox ck_type;
    private View view_click_continue_send;
    private TextView tv_continue_number, tv_desc;
    private TextView tv_count_down_number;
    private Spinner spinner;
    private TextView iv_empty;
    private LiveGiftAdapter mAdapterGift;
    private LinearLayout ll_types,ll_type_container;
    private LiveGiftAdapter mAdapterPacket;
    private int[] gift_nums = getResources().getIntArray(R.array.gift_num);
    private String[] gift_text = getResources().getStringArray(R.array.gift_text);
    List<String> mlist = Arrays.asList(gift_text);
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
    public void setCallback(SendGiftViewCallback sendGiftViewCallback) {
        this.mCallback = sendGiftViewCallback;
    }

    private ArrayAdapter arr_adapter;

    protected void init() {
        setContentView(R.layout.view_live_send_gift);
        spinner = (Spinner) findViewById(R.id.spinner);
        //适配器
        arr_adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_gift_num, mlist);
        //设置样式
        arr_adapter.setDropDownViewResource(R.layout.item_gift_num);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spv_content = (SDSlidingPageView) findViewById(R.id.spv_content);
        spv_packge = (SDSlidingPageView) findViewById(R.id.spv_packge);
        ck_type = find(R.id.ck_type);
        ll_charge = findViewById(R.id.ll_charge);
        ll_types = find(R.id.ll_gift_type);
        ll_type_container = find(R.id.ll_type_container);
        tv_desc = find(R.id.tv_desc);
        iv_hide=find(R.id.iv_hide);
        iv_empty = find(R.id.iv_empty);
        tv_diamonds = (TextView) findViewById(R.id.tv_diamonds);
        tv_send = (TextView) findViewById(R.id.tv_send);
        view_continue_send = findViewById(R.id.view_continue_send);
        view_click_continue_send = findViewById(R.id.view_click_continue_send);
        tv_continue_number = (TextView) findViewById(R.id.tv_continue_number);
        tv_count_down_number = (TextView) findViewById(R.id.tv_count_down_number);
        register();
        bindUserData();
    }


    public void HideSendGiftView() {
        tv_send.setVisibility(GONE);
    }

    private void register() {
        initSlidingView();
        ll_charge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickCharge();
            }
        });
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是手绘礼物判断
                if ("4".equals(getSelectedGiftModel().getIs_animated())) {
                    if (draw_num < getSelectedGiftModel().getDrawn_min()) {
                        SDToast.showToast("手绘礼物数量小于" + getSelectedGiftModel().getDrawn_min() + "个不能赠送");
                        return;
                    }
                    clickSend();
                } else {
                    clickSend();
                }
            }
        });
        view_click_continue_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickContinueSend();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                gift_num = gift_nums[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ck_type.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                draw_num = 0;
                if (isChecked) {
                    mAdapterPacket.getSelectManager().setSelected(mAdapterPacket.getSelectManager().getSelectedIndex(),false);
                    if (mAdapterPacket.getDataCount() <= 0) {
                        iv_empty.setVisibility(VISIBLE);
                    } else {
                        iv_empty.setVisibility(GONE);
                    }
                    ll_type_container.setVisibility(GONE);
                    spv_packge.setVisibility(VISIBLE);
                    mAdapterPacket.notifyDataSetChanged();
                    spv_content.setVisibility(GONE);
                } else {
                    mAdapterGift.getSelectManager().setSelected(mAdapterGift.getSelectManager().getSelectedIndex(),false);
                    if (mAdapterGift.getDataCount() <= 0) {
                        iv_empty.setVisibility(VISIBLE);
                    } else {
                        iv_empty.setVisibility(GONE);
                    }
                    ll_type_container.setVisibility(VISIBLE);
                    spv_packge.setVisibility(GONE);
                    spv_content.setVisibility(VISIBLE);
                    mAdapterGift.notifyDataSetChanged();
                }
            }
        });
        iv_hide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.hide();
            }
        });
    }

    int gift_num = 1;

    private void initSlidingView() {
        spv_content.getIndicatorConfig().imageResIdNormal = R.drawable.point_nomal;
        spv_content.getIndicatorConfig().imageResIdSelected = R.drawable.point_check;
        spv_content.getViewPager().setItemCountPerPage(8);
        spv_content.getViewPager().setColumnCountPerPage(4);
        SDViewUtil.setMarginBottom(spv_content.getViewPager(), SDViewUtil.dp2px(7));
        mAdapterGift = new LiveGiftAdapter(null, getActivity());
        mAdapterGift.setItemClickCallback(new SDItemClickCallback<LiveGiftModel>() {
            @Override
            public void onItemClick(int position, LiveGiftModel item, View view) {
                if (null != getSelectedGiftModel()) {
                    if (getSelectedGiftModel().getId() == item.getId()) {
                        clickSend();
                    } else {
                        mAdapterGift.getSelectManager().performClick(position);
                        resetClick();
                    }
                } else {
                    mAdapterGift.getSelectManager().performClick(position);
                    resetClick();
                }
            }
        });
        spv_content.getViewPager().setGridAdapter(mAdapterGift);
        spv_packge.getIndicatorConfig().imageResIdNormal = R.drawable.point_nomal;
        spv_packge.getIndicatorConfig().imageResIdSelected = R.drawable.point_check;
        spv_packge.getViewPager().setItemCountPerPage(8);
        spv_packge.getViewPager().setColumnCountPerPage(4);
        SDViewUtil.setMarginBottom(spv_packge.getViewPager(), SDViewUtil.dp2px(7));
        mAdapterPacket = new LiveGiftAdapter(null, getActivity());
        mAdapterPacket.setType(1);
        spv_packge.getViewPager().setGridAdapter(mAdapterPacket);
        mAdapterPacket.setItemClickCallback(new SDItemClickCallback<LiveGiftModel>() {
            @Override
            public void onItemClick(int position, LiveGiftModel item, View view) {
                if (null != getSelectedGiftModel()) {
                    if (getSelectedGiftModel().getId() == item.getId()) {
                        clickSend();
                    } else {
                        mAdapterPacket.getSelectManager().performClick(position);
                        resetClick();
                    }
                } else {
                    mAdapterPacket.getSelectManager().performClick(position);
                    resetClick();
                }
            }
        });
    }

    /**
     * 更新用户数据
     */
    public void bindUserData() {
        updateDiamonds(UserModelDao.query());
    }

    /**
     * 更新秀豆数量
     *
     * @param user
     */
    private void updateDiamonds(UserModel user) {
        if (user != null) {
            SDViewBinder.setTextView(tv_diamonds, user.getDiamonds()+"");
//            SDViewBinder.setTextView(tv_diamonds, LiveUtils.getFormatNumber(user.getDiamonds()));
        }
    }

    /**
     * 发送某礼物成功调用，更新本地秀豆数量
     *
     * @param giftModel
     */
    public void sendGiftSuccess(final LiveGiftModel giftModel) {
        if (giftModel != null) {
            if (!ck_type.isChecked()) {
                SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<UserModel>() {
                    @Override
                    public UserModel onBackground() {
                            return UserModelDao.payDiamonds(giftModel.getDiamonds() * gift_num);
                    }

                    @Override
                    public void onMainThread(UserModel result) {
                        updateDiamonds(result);
                    }
                });
            } else {
                    giftModel.setNum(giftModel.getNum() - gift_num);
                mAdapterPacket.notifyDataSetChanged();
            }
        }
    }

    /**
     * 开始连击倒计时
     */
    private void startCountDownNumberLooper() {
        resetCountDownNumber();
        mLooper.start(DURATION_COUNT, mCountDownNumberRunnable);
    }

    private Runnable mCountDownNumberRunnable = new Runnable() {

        @Override
        public void run() {
            mCountDownNumber--;
            if (mCountDownNumber <= 0) {
                resetClick();
            } else {
                if (mClickNumber > 0) {
                    tv_continue_number.setText("X" + mClickNumber);
                }
                tv_count_down_number.setText(String.valueOf(mCountDownNumber));
            }
        }
    };

    protected void clickCharge() {
        LiveRechargeDialog dialog = new LiveRechargeDialog(getActivity());
        dialog.showCenter();
    }

    private void resetClick() {
        mLooper.stop();
        mClickNumber = 0;
        tv_continue_number.setText("");
        hideContinueMode();
    }

    private void resetCountDownNumber() {
        mCountDownNumber = DURATION_CONTINUE / DURATION_COUNT;
    }

    public LiveGiftModel getSelectedGiftModel() {
        if (ck_type.isChecked()) {
            return mAdapterPacket.getSelectManager().getSelectedItem();
        } else {
            return mAdapterGift.getSelectManager().getSelectedItem();
        }
    }

    /**
     * 请求礼物列表
     */
    public void requestData() {
        requestShopGift();
        requestPackgeGift();
    }

    public void requestShopGift() {
        if (mAdapterGift.getItemCount() <= 0) {
            CommonInterface.requestNewGift(new AppRequestCallback<App_propNewActModel>() {
                @Override
                protected void onSuccess(SDResponse resp) {
                    if (actModel.getStatus()==1) {
                        setDataGift(actModel.getList());
                    } else {
                        SDToast.showToast(actModel.getError());
                    }
                }
            });
        }
        mAdapterGift.notifyDataSetChanged();
    }

    public void requestPackgeGift() {
        CommonInterface.requestPackgeGift(new AppRequestCallback<PackgeModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    setPackgeData(actModel.getList());
                } else {
                    SDToast.showToast(actModel.getError());
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
            }
        });
        mAdapterPacket.notifyDataSetChanged();
    }

    /**
     * 设置礼物列表数据
     *
     * @param listGift
     */
    List<App_propNewActModel.ListBean> listGift;

    public void setDataGift(final List<App_propNewActModel.ListBean> listGift) {
        if (mAdapterGift.getDataCount() > 0) {
            return;
        }
        this.listGift = listGift;
        mAdapterGift.updateData(listGift.get(0).getData());
        List<SDTabUnderline> lists = new ArrayList<>();
        if (listGift.size() > 0) {
            for (App_propNewActModel.ListBean bean : listGift) {
                if("2".equals(bean.getG_id())){
                    break;
                }
                SDTabUnderline sdTabUnderline = new SDTabUnderline(getContext());
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(SDViewUtil.dp2px(50), SDViewUtil.dp2px(40));
                sdTabUnderline.setLayoutParams(linearParams);
                sdTabUnderline.setTextTitle(bean.getName());
                sdTabUnderline.getViewConfig(sdTabUnderline.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
                sdTabUnderline.getViewConfig(sdTabUnderline.mTvTitle).setTextColorNormalResId(R.color.white).setTextColorSelectedResId(R.color.main_color);
                ll_types.addView(sdTabUnderline);
                lists.add(sdTabUnderline);
            }
        }
        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>() {
            @Override
            public void onNormal(int index, SDTabUnderline item) {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item) {
                mAdapterGift.updateData(listGift.get(index).getData());

            }
        });
        if (mSelectManager.getSelectedItems().size() <= 0) {
            mSelectManager.appendItems(lists, true);
            mSelectManager.setSelected(0, true);
        }
    }

    public void setPackgeData(final List<LiveGiftModel> listGift) {
        mAdapterPacket.updateData(listGift);
        mAdapterPacket.notifyDataSetChanged();
    }

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<SDTabUnderline>();

    private int mSelectTabIndex = 0;

    private boolean validateSend() {
        if (!SDNetworkReceiver.isNetworkConnected(getActivity())) {
            SDToast.showToast("无网络");
            return false;
        }

        if (getSelectedGiftModel() == null) {
            SDToast.showToast("请选择礼物");
            return false;
        }
        if(ck_type.isChecked()){
                if(getSelectedGiftModel().getNum()<gift_num){
                    SDToast.showToast("背包数量不足");
                    return false;
                }
        }else{
            if (!UserModelDao.canDiamondsPay(getSelectedGiftModel().getDiamonds() * gift_num)) {
                SDToast.showToast("余额不足");
                return false;
            }
        }
        return true;
    }

    /**
     * 点击发送按钮
     */
    private void clickSend() {
        if (validateSend()) {
            if (getSelectedGiftModel().getIs_much() == 1) {
                showContinueMode();
                clickContinueSend();
            } else {
                //通知发送按钮被点击接口
                if (mCallback != null) {
                        mCallback.onClickSend(getSelectedGiftModel(), gift_num, 0, ck_type.isChecked() ? 1 : 0, null);
                }
            }
        }
    }

    /**
     * 触发连击调用方法
     */
    protected void clickContinueSend() {
        if (mBlocker.block()) {
            return;
        }

        if (validateSend()) {
            mClickNumber++;
            startCountDownNumberLooper();

            int is_plus = 0;
            if (mClickNumber > 1) {
                is_plus = 1;
            } else {
                is_plus = 0;
            }
            if (mCallback != null) {
                    mCallback.onClickSend(getSelectedGiftModel(), gift_num, is_plus, ck_type.isChecked() ? 1 : 0, null);
            }
        }
    }

    /**
     * 显示连击模式
     */
    private void showContinueMode() {
//        SDViewUtil.setGone(tv_send);
        SDViewUtil.setVisible(view_continue_send);
    }

    /**
     * 隐藏连击模式
     */
    private void hideContinueMode() {
        SDViewUtil.setGone(view_continue_send);
//        SDViewUtil.setVisible(tv_send);
    }

    /**
     * 本地用户数据更新事件
     *
     * @param event
     */
    public void onEventMainThread(EUpdateUserInfo event) {
        bindUserData();
    }

    @Override
    public void setHeightMatchParent() {
        SDViewUtil.setHeightWeight(spv_content, 1);
        spv_content.setContentHeightMatchParent();

        SDViewUtil.setHeightWeight(spv_packge, 1);
        spv_packge.setContentHeightMatchParent();
    }

    @Override
    public void setHeightWrapContent() {
        SDViewUtil.setHeightWrapContent(spv_content);
        spv_content.setContentHeightWrapContent();

        SDViewUtil.setHeightWrapContent(spv_packge);
        spv_packge.setContentHeightWrapContent();
    }

    int draw_num;


    public interface SendGiftViewCallback {
        /**
         * 礼物点击
         *
         * @param model   要发送的礼物
         * @param is_plus 1-需要叠加数量，0-不需要叠加数量
         */
        void onClickSend(LiveGiftModel model, int num, int is_plus, int is_packge, String json);

        void hide();
    }

}
