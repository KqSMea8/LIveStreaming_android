package com.fanwe.games.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;

/**
 * Created by shibx on 2017/02/23.
 * 游戏上庄输入框
 */

public class GamesBankerDialog extends SDDialogCustom implements TextWatcher, SDDialogCustom.SDDialogCustomCallback
{

    private EditText et_apply_banker;
    private TextView tv_principal;
    private TextView tv_coins;
    private long mPrincipal;
    private long mCoins;

    private boolean canSubmit;

    private BankerSubmitListener mListener;

    public GamesBankerDialog(Activity activity, BankerSubmitListener listener)
    {
        super(activity);
        this.mListener = listener;
        init();
    }

    public GamesBankerDialog(Activity activity)
    {
        super(activity);
        init();
    }

    @Override
    protected void init()
    {
        super.init();
        setCustomView(R.layout.dialog_games_banker);
        et_apply_banker = (EditText) findViewById(R.id.et_apply_banker);
        tv_principal = (TextView) findViewById(R.id.tv_principal);
        tv_coins = (TextView) findViewById(R.id.tv_coins);
        et_apply_banker.addTextChangedListener(this);
        setTextTitle(null);
        setDismissAfterClick(false);
        setCallback(this);
        setTextColorTitle(SDResourcesUtil.getColor(R.color.main_color));
        setTextColorConfirm(Color.GRAY);//不可点击颜色
    }

    /**
     * @param principal 底金
     * @param coins     余额
     */
    public void show(long principal, long coins)
    {
        this.mPrincipal = principal;
        this.mCoins = coins;
        this.canSubmit = false;
        tv_principal.setText("底金:" + principal);
        tv_coins.setText("我的余额:" + coins);
        showCenter();
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
                    et_apply_banker.setText(String.valueOf(0));
                    et_apply_banker.setSelection(et_apply_banker.getText().toString().length());
                }
                setConfirmEnable(false);
            } else if (Long.parseLong(str) > 0 && Long.parseLong(str) < mPrincipal)
            {
                setConfirmEnable(false);
            } else if (Long.parseLong(str) >= mPrincipal && Long.parseLong(str) <= mCoins)
            {
                setConfirmEnable(true);
            } else if (Long.parseLong(str) > mCoins)
            {
                et_apply_banker.setText(String.valueOf(mCoins));
                et_apply_banker.setSelection(et_apply_banker.getText().toString().length());
                setConfirmEnable(true);
            }
        } else
        {
            setConfirmEnable(false);
        }
    }

    private void setConfirmEnable(boolean enable)
    {
        if (enable)
        {
            setTextColorConfirm(SDResourcesUtil.getColor(R.color.main_color));
            canSubmit = true;
        } else
        {
            setTextColorConfirm(Color.GRAY);
            canSubmit = false;
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
        if (canSubmit)
        {
            long coins = Long.parseLong(et_apply_banker.getText().toString());
            mListener.onClickSubmit(coins);
            dismiss();
        } else
        {
            SDToast.showToast("输入金额不能小于底金");
        }
    }

    public interface BankerSubmitListener
    {
        void onClickSubmit(long coins);
    }
}
