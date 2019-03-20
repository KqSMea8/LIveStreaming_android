package com.fanwe.live.dialog;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.hybrid.dialog.SDProgressDialog;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.App_gameCoinsExchangeActModel;
import com.fanwe.live.model.Deal_send_propActModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by shibx on 2017/2/13.
 * 货币处理窗口，包括 游戏币赠送与兑换，秀豆赠送
 * {@link #LiveGameExchangeDialog(Activity, int, OnSuccessListener)}
 * 构造参数 int 为窗口功能选项
 * 于 2017/04/14 修改
 */

public class LiveGameExchangeDialog extends SDDialogCustom implements TextWatcher, SDDialogCustom.SDDialogCustomListener
{

    public static final int TYPE_COIN_EXCHANGE = 1; //秀豆兑换游戏币
    public static final int TYPE_COIN_SEND = 2; //赠送游戏币
    public static final int TYPE_DIAMOND_SEND = 3; //赠送秀豆

    @IntDef({TYPE_COIN_EXCHANGE, TYPE_COIN_SEND, TYPE_DIAMOND_SEND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogType
    {
    }

    private float mRate;

    private long mCurrency;

    private int mType;

    private String toUserId;

    private TextView tv_currency;
    private TextView tv_game_exchange_rate;
    private EditText et_game_exchange;
    private TextView tv_game_coins;

    private OnSuccessListener mListenerSuccess;

    public LiveGameExchangeDialog(Activity activity, @DialogType int type, OnSuccessListener listener)
    {
        super(activity);
        mType = type;
        mListenerSuccess = listener;
        init();
    }

    /**
     * 设置兑换比例
     *
     * @param rate
     */
    public void setRate(float rate)
    {
        this.mRate = rate;
        tv_game_exchange_rate.setText("兑换比例:" + rate);
    }

    /**
     * 设置当前货币余额
     *
     * @param currency
     */
    public void setCurrency(long currency)
    {
        this.mCurrency = currency;
        switch (mType)
        {
            case TYPE_COIN_EXCHANGE:
                tv_currency.setText("当前秀豆:" + currency);
                break;
            case TYPE_COIN_SEND:
                tv_currency.setText("当前" + SDResourcesUtil.getString(R.string.game_currency) + ":" + currency);
                break;
            case TYPE_DIAMOND_SEND:
                tv_currency.setText("当前秀豆:" + currency);
                break;
        }
    }

    /**
     * 货币被赠予对象
     *
     * @param userId
     */
    public void setToUserId(String userId)
    {
        this.toUserId = userId;
    }

    @Override
    protected void init()
    {
        super.init();
        initDialogTitle();
        setCustomView(R.layout.dialog_game_exchange);
        tv_currency = (TextView) findViewById(R.id.tv_currency);
        tv_game_exchange_rate = (TextView) findViewById(R.id.tv_game_exchange_rate);
        et_game_exchange = (EditText) findViewById(R.id.et_game_exchange);
        tv_game_coins = (TextView) findViewById(R.id.tv_game_coins);
        et_game_exchange.addTextChangedListener(this);
        setmListener(this);
        setDismissAfterClick(false);
    }

    private void initDialogTitle()
    {
        switch (mType)
        {
            case TYPE_COIN_EXCHANGE:
                setTextTitle("兑换" + SDResourcesUtil.getString(R.string.game_currency));
                break;
            case TYPE_COIN_SEND:
                setTextTitle("赠送" + SDResourcesUtil.getString(R.string.game_currency));
                break;
            case TYPE_DIAMOND_SEND:
                setTextTitle("赠送秀豆");
                break;
        }
    }

    @Override
    public void showCenter()
    {
        if (mType == TYPE_COIN_EXCHANGE)
        {
            SDViewUtil.setVisible(tv_game_exchange_rate);
            setTextConfirm("兑换");
        } else
        {
            SDViewUtil.setGone(tv_game_exchange_rate);
            SDViewUtil.setInvisible(tv_game_coins);
            setTextConfirm("赠送");
        }
        super.showCenter();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        String str = s.toString();

        if (!TextUtils.isEmpty(str))
        {
            if (Long.parseLong(str) == 0)
            {
                if (str.length() > 1)
                {
                    et_game_exchange.setText(String.valueOf(0));
                    et_game_exchange.setSelection(et_game_exchange.getText().toString().length());
                }
            } else if (Long.parseLong(str) > mCurrency)
            {
                et_game_exchange.setText(String.valueOf(mCurrency));
                et_game_exchange.setSelection(et_game_exchange.getText().toString().length());
            } else
            {
                double money = SDNumberUtil.multiply(Long.parseLong(str), mRate, 2);//格式化， 保留两位小数
                if (money != 0)
                {
                    SDViewUtil.setVisible(tv_game_coins);
                    tv_game_coins.setText((long) money + SDResourcesUtil.getString(R.string.game_currency));
                } else
                {
                    SDViewUtil.setInvisible(tv_game_coins);
                }
            }
        } else
        {
            SDViewUtil.setInvisible(tv_game_coins);
        }
    }

    @Override
    public void onClickCancel(View v, SDDialogCustom dialog)
    {
        dismiss();
    }

    @Override
    public void onClickConfirm(View v, SDDialogCustom dialog)
    {
        String result = et_game_exchange.getText().toString();
        if (TextUtils.isEmpty(result))
        {
            return;
        }
        switch (mType)
        {
            case TYPE_COIN_EXCHANGE:
                exchangeCoins(Long.valueOf(result));
                break;
            case TYPE_COIN_SEND:
                sendCoins(toUserId, Long.valueOf(result));
                break;
            case TYPE_DIAMOND_SEND:
                sendDiamonds(toUserId, Long.valueOf(result));
                break;
        }
    }

    @Override
    public void onDismiss(SDDialogCustom dialog)
    {

    }

    private void exchangeCoins(long money)
    {
        final SDProgressDialog dialog = new SDProgressDialog(getOwnerActivity());
        dialog.show();
        CommonInterface.requestCoinExchange(money, new AppRequestCallback<App_gameCoinsExchangeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    SDToast.showToast("兑换成功");
                    mListenerSuccess.onExchangeSuccess(actModel.getAccount_diamonds(), actModel.getCoin());
                    dismiss();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
            }
        });
    }

    /**
     * 赠送游戏币
     *
     * @param toUserId 被赠予对象
     * @param money    游戏币数量
     */
    private void sendCoins(String toUserId, final long money)
    {
        final SDProgressDialog dialog = new SDProgressDialog(getOwnerActivity());
        dialog.show();
        CommonInterface.requestSendGameCoins(toUserId, money, new AppRequestCallback<Deal_send_propActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    UserModelDao.payCoins(money);
                    mListenerSuccess.onSendCurrencySuccess(actModel);
                    dismiss();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
            }
        });
    }

    private void sendDiamonds(String toUserId, final long money)
    {
        final SDProgressDialog dialog = new SDProgressDialog(getOwnerActivity());
        dialog.show();
        CommonInterface.requestSendDiamonds(toUserId, money, new AppRequestCallback<Deal_send_propActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    UserModelDao.payDiamonds(money);
                    mListenerSuccess.onSendCurrencySuccess(actModel);
                    dismiss();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dialog.dismiss();
            }
        });
    }

    public void onEventMainThread(EUpdateUserInfo event)
    {
//        bindUserData();
        if (mType == TYPE_COIN_SEND)
        {
            setCurrency(event.user.getCoin());
//            notifyCoinsChanged();
        } else
        {
            setCurrency(event.user.getDiamonds());
        }
    }

    private void notifyCoinsChanged()
    {
        String str = et_game_exchange.getText().toString();
        if (Long.parseLong(str) > mCurrency)
        {
            et_game_exchange.setText(String.valueOf(mCurrency));
            et_game_exchange.setSelection(et_game_exchange.getText().toString().length());
        }
    }

    public interface OnSuccessListener
    {
        void onExchangeSuccess(long diamonds, long coins);

        void onSendCurrencySuccess(Deal_send_propActModel model);
    }
}

