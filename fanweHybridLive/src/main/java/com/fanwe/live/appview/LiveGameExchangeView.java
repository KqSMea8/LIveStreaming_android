package com.fanwe.live.appview;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/3/7.
 * 直播间充值窗口{@link com.fanwe.live.dialog.LiveRechargeDialog} 兑换视图
 */

public class LiveGameExchangeView extends BaseAppView implements TextWatcher
{

    private float mRate;

    private long mDiamonds;

    private TextView tv_currency;
    private TextView tv_game_exchange_rate;
    private EditText et_game_exchange;
    private TextView tv_game_coins;

    private TextView tv_cancel;
    private TextView tv_confirm;

    private OnClickExchangeListener mListener;

    public LiveGameExchangeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveGameExchangeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveGameExchangeView(Context context)
    {
        super(context);
        init();
    }

    public LiveGameExchangeView(Context context, long diamonds, float rate)
    {
        super(context);
        this.mDiamonds = diamonds;
        this.mRate = rate;
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_game_exchange;
    }

    protected void init()
    {
        tv_currency = find(R.id.tv_currency);
        tv_game_exchange_rate = find(R.id.tv_game_exchange_rate);
        et_game_exchange = find(R.id.et_game_exchange);
        tv_game_coins = find(R.id.tv_game_coins);
        tv_cancel = find(R.id.tv_cancel);
        tv_confirm = find(R.id.tv_confirm);
        et_game_exchange.addTextChangedListener(this);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        initData();
    }

    private void initData()
    {
        tv_game_exchange_rate.setText("兑换比例:" + mRate);
        tv_currency.setText("秀豆余额:" + mDiamonds);
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
            } else if (Long.parseLong(str) > mDiamonds)
            {
                et_game_exchange.setText(String.valueOf(mDiamonds));
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
    public void onClick(View v)
    {
        super.onClick(v);
        if (mListener == null)
            return;

        switch (v.getId())
        {
            case R.id.tv_cancel:
                mListener.onClickCancel();
                break;
            case R.id.tv_confirm:
                String content = et_game_exchange.getText().toString();
                if (TextUtils.isEmpty(content))
                {
                    SDToast.showToast("请输入需要兑换的秀豆");
                    return;
                }
                long diamonds = Long.parseLong(content);
                if (diamonds == 0)
                {
                    SDToast.showToast("请输入需要兑换的秀豆");
                    return;
                }
                mListener.onClickConfirm(diamonds);
                break;
            default:
                break;
        }
    }

    public void setOnClickExchangeListener(OnClickExchangeListener listener)
    {
        this.mListener = listener;
    }

    public interface OnClickExchangeListener
    {
        void onClickCancel();

        void onClickConfirm(long diamonds);
    }
}
