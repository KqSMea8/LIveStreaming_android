package com.fanwe.pay.dialog;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;

/**
 * Created by Administrator on 2016/12/16.
 */

public class LiveImportPriceDialog extends SDDialogConfirm
{
    private TextView tv_text_min_max;
    private EditText et_diamond;
    private int live_pay_max;
    private int live_pay_min;

    public int getLive_pay_min()
    {
        return live_pay_min;
    }

    public LiveImportPriceDialog(Activity activity)
    {
        super(activity);
    }

    @Override
    protected void init()
    {
        super.init();
        this.setTextTitle("");
        this.setCancelable(false);
        setDismissAfterClick(false);
        setCustomView(R.layout.dialog_pay_pay_live_content);
        findView();
        initInputLimit();
        register();
    }

    private void findView()
    {
        et_diamond = (EditText) findViewById(R.id.et_diamond);
        tv_text_min_max = (TextView) findViewById(R.id.tv_text_max_min);
    }

    private void initInputLimit()
    {
        live_pay_max = AppRuntimeWorker.getLivePayMax();
        live_pay_min = AppRuntimeWorker.getLivePayMin();
        et_diamond.setText(Integer.toString(live_pay_min));
        et_diamond.setSelection(Integer.toString(live_pay_min).length());

        String leftText = "请输入直播价格(";
        String rightText = ")";
        String text_center = live_pay_min + "<=价格";
        if (live_pay_max > 0)
        {
            text_center = text_center + "<=" + live_pay_max;
        }
        tv_text_min_max.setText(leftText + text_center + rightText);
    }

    private void register()
    {
        et_diamond.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String text = s.toString();
                if (!TextUtils.isEmpty(text))
                {
                    int int_text = Integer.valueOf(text);
                    if (int_text == 0)
                    {
                        et_diamond.setText("1");
                        et_diamond.setSelection(1);
                        return;
                    }

                    if (live_pay_max > 0)
                    {
                        if (int_text > live_pay_max)
                        {
                            String s_text = String.valueOf(live_pay_max);
                            et_diamond.setText(s_text);
                            et_diamond.setSelection(s_text.length());
                            return;
                        }
                    }
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    //获取输入框价格
    public int getImportPrice()
    {
        int price = 1;
        if (et_diamond != null)
        {
            String diamond = et_diamond.getText().toString();
            if (!TextUtils.isEmpty(diamond))
            {
                int int_diamond = Integer.valueOf(diamond);
                if (int_diamond > 0)
                {
                    return int_diamond;
                }
            }
        }
        return price;
    }

    /**
     * 重置最低价格
     */
    public void resetMinPrice()
    {
        et_diamond.setText(Integer.toString(live_pay_min));
        et_diamond.setSelection(Integer.toString(live_pay_min).length());
    }
}
