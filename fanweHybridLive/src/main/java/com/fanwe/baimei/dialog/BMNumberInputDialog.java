package com.fanwe.baimei.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.live.R;

/**
 * 包名 com.fanwe.baimei.dialog
 * 描述 数字输入弹窗
 * 作者 Su
 * 创建时间 2017/5/16 16:41
 **/
public class BMNumberInputDialog extends BMBaseCommonDialog
{
    private static final int MAX_LENGTH = 6;
    private StringBuilder mOutput = new StringBuilder();
    private TextView mDisplayNumberTextView_1, mDisplayNumberTextView_2, mDisplayNumberTextView_3,
            mDisplayNumberTextView_4, mDisplayNumberTextView_5, mDisplayNumberTextView_6;
    private TextView mNumberTextView_1, mNumberTextView_2, mNumberTextView_3,
            mNumberTextView_4, mNumberTextView_5, mNumberTextView_6,
            mNumberTextView_7, mNumberTextView_8, mNumberTextView_9,
            mNumberTextView_0;
    private TextView[] mDisplayNumberTextViews;
    private TextView mResetTextView, mDeleteTextView;
    private LinearLayout mDisplayNumbersLayout;

    private BMNumberInputDialogCallback mCallback;

    public BMNumberInputDialog(Activity activity)
    {
        super(activity);

        initListener();
    }

    private void initListener()
    {
        getNumberTextView_0().setOnClickListener(this);
        getNumberTextView_1().setOnClickListener(this);
        getNumberTextView_2().setOnClickListener(this);
        getNumberTextView_3().setOnClickListener(this);
        getNumberTextView_4().setOnClickListener(this);
        getNumberTextView_5().setOnClickListener(this);
        getNumberTextView_6().setOnClickListener(this);
        getNumberTextView_7().setOnClickListener(this);
        getNumberTextView_8().setOnClickListener(this);
        getNumberTextView_9().setOnClickListener(this);
        getResetTextView().setOnClickListener(this);
        getDeleteTextView().setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (getNumberTextView_0() == v)
        {
            append(0);
        } else if (getNumberTextView_1() == v)
        {
            append(1);
        } else if (getNumberTextView_2() == v)
        {
            append(2);
        } else if (getNumberTextView_3() == v)
        {
            append(3);
        } else if (getNumberTextView_4() == v)
        {
            append(4);
        } else if (getNumberTextView_5() == v)
        {
            append(5);
        } else if (getNumberTextView_6() == v)
        {
            append(6);
        } else if (getNumberTextView_7() == v)
        {
            append(7);
        } else if (getNumberTextView_8() == v)
        {
            append(8);
        } else if (getNumberTextView_9() == v)
        {
            append(9);
        } else if (getDeleteTextView() == v)
        {
            delete();
        } else if (getResetTextView() == v)
        {
            animReset();
        }

    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.bm_dialog_number_input;
    }

    private LinearLayout getDisplayNumbersLayout()
    {
        if (mDisplayNumbersLayout == null)
        {
            mDisplayNumbersLayout = (LinearLayout) findViewById(R.id.ll_display_numbers);
        }
        return mDisplayNumbersLayout;
    }

    private TextView getDisplayNumberTextView_1()
    {
        if (mDisplayNumberTextView_1 == null)
        {
            mDisplayNumberTextView_1 = (TextView) findViewById(R.id.tv_display_num_1);
        }
        return mDisplayNumberTextView_1;
    }

    private TextView getDisplayNumberTextView_2()
    {
        if (mDisplayNumberTextView_2 == null)
        {
            mDisplayNumberTextView_2 = (TextView) findViewById(R.id.tv_display_num_2);
        }
        return mDisplayNumberTextView_2;
    }

    private TextView getDisplayNumberTextView_3()
    {
        if (mDisplayNumberTextView_3 == null)
        {
            mDisplayNumberTextView_3 = (TextView) findViewById(R.id.tv_display_num_3);
        }
        return mDisplayNumberTextView_3;
    }

    private TextView getDisplayNumberTextView_4()
    {
        if (mDisplayNumberTextView_4 == null)
        {
            mDisplayNumberTextView_4 = (TextView) findViewById(R.id.tv_display_num_4);
        }
        return mDisplayNumberTextView_4;
    }

    private TextView getDisplayNumberTextView_5()
    {
        if (mDisplayNumberTextView_5 == null)
        {
            mDisplayNumberTextView_5 = (TextView) findViewById(R.id.tv_display_num_5);
        }
        return mDisplayNumberTextView_5;
    }

    private TextView getDisplayNumberTextView_6()
    {
        if (mDisplayNumberTextView_6 == null)
        {
            mDisplayNumberTextView_6 = (TextView) findViewById(R.id.tv_display_num_6);
        }
        return mDisplayNumberTextView_6;
    }

    private TextView getNumberTextView_1()
    {
        if (mNumberTextView_1 == null)
        {
            mNumberTextView_1 = (TextView) findViewById(R.id.tv_button_num_1);
        }
        return mNumberTextView_1;
    }

    private TextView getNumberTextView_2()
    {
        if (mNumberTextView_2 == null)
        {
            mNumberTextView_2 = (TextView) findViewById(R.id.tv_button_num_2);
        }
        return mNumberTextView_2;
    }

    private TextView getNumberTextView_3()
    {
        if (mNumberTextView_3 == null)
        {
            mNumberTextView_3 = (TextView) findViewById(R.id.tv_button_num_3);
        }
        return mNumberTextView_3;
    }

    private TextView getNumberTextView_4()
    {
        if (mNumberTextView_4 == null)
        {
            mNumberTextView_4 = (TextView) findViewById(R.id.tv_button_num_4);
        }
        return mNumberTextView_4;
    }

    private TextView getNumberTextView_5()
    {
        if (mNumberTextView_5 == null)
        {
            mNumberTextView_5 = (TextView) findViewById(R.id.tv_button_num_5);
        }
        return mNumberTextView_5;
    }

    private TextView getNumberTextView_6()
    {
        if (mNumberTextView_6 == null)
        {
            mNumberTextView_6 = (TextView) findViewById(R.id.tv_button_num_6);
        }
        return mNumberTextView_6;
    }

    private TextView getNumberTextView_7()
    {
        if (mNumberTextView_7 == null)
        {
            mNumberTextView_7 = (TextView) findViewById(R.id.tv_button_num_7);
        }
        return mNumberTextView_7;
    }

    private TextView getNumberTextView_8()
    {
        if (mNumberTextView_8 == null)
        {
            mNumberTextView_8 = (TextView) findViewById(R.id.tv_button_num_8);
        }
        return mNumberTextView_8;
    }

    private TextView getNumberTextView_9()
    {
        if (mNumberTextView_9 == null)
        {
            mNumberTextView_9 = (TextView) findViewById(R.id.tv_button_num_9);
        }
        return mNumberTextView_9;
    }

    private TextView getNumberTextView_0()
    {
        if (mNumberTextView_0 == null)
        {
            mNumberTextView_0 = (TextView) findViewById(R.id.tv_button_num_0);
        }
        return mNumberTextView_0;
    }

    private TextView getResetTextView()
    {
        if (mResetTextView == null)
        {
            mResetTextView = (TextView) findViewById(R.id.tv_button_reset);
        }
        return mResetTextView;
    }

    private TextView getDeleteTextView()
    {
        if (mDeleteTextView == null)
        {
            mDeleteTextView = (TextView) findViewById(R.id.tv_button_delete);
        }
        return mDeleteTextView;
    }

    private TextView[] getDisplayNumberTextViews()
    {
        if (mDisplayNumberTextViews == null)
        {
            mDisplayNumberTextViews = new TextView[]{getDisplayNumberTextView_1(),
                    getDisplayNumberTextView_2(),
                    getDisplayNumberTextView_3(),
                    getDisplayNumberTextView_4(),
                    getDisplayNumberTextView_5(),
                    getDisplayNumberTextView_6()};
        }
        return mDisplayNumberTextViews;
    }

    private void append(int number)
    {
        String str = String.valueOf(number);
        int len = mOutput.length();

        if (MAX_LENGTH > len)
        {
            mOutput.append(str);
        } else
        {
            mOutput.replace(MAX_LENGTH - 1, MAX_LENGTH, str);
        }

        if (MAX_LENGTH == mOutput.length())
        {
            getCallback().onNumberFilled(getOutput());
        } else
        {
            getCallback().onNumberInput(number, getOutput());
        }

        getDisplayNumberTextViews()[mOutput.length() - 1].setText(str);
    }

    private void delete()
    {
        int len = mOutput.length();

        if (len > 0)
        {
            String delete = String.valueOf(mOutput.charAt(len - 1));
            mOutput.deleteCharAt(len - 1);
            getDisplayNumberTextViews()[len - 1].setText("");

            getCallback().onNumberDeleted(Integer.valueOf(delete), getOutput());
        }
    }

    private void reset()
    {
        int len = mOutput.length();

        if (len > 0)
        {
            mOutput.setLength(0);
            for (TextView textView : getDisplayNumberTextViews())
            {
                textView.setText("");
            }
        }
    }

    public String getOutput()
    {
        return mOutput.toString();
    }

    private BMNumberInputDialogCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMNumberInputDialogCallback()
            {
                @Override
                public void onNumberInput(int num, String output)
                {

                }

                @Override
                public void onNumberFilled(String output)
                {

                }

                @Override
                public void onNumberDeleted(int num, String output)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMNumberInputDialogCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    public void animError()
    {
        for (TextView textView : getDisplayNumberTextViews())
        {
            blink(textView);
        }
    }

    private void animReset()
    {
        int len = mOutput.length();

        if (0 == len)
        {
            return;
        }

        getDisplayNumbersLayout().animate()
                .setDuration(300)
                .translationY(-getDisplayNumbersLayout().getBottom())
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        reset();

                        getDisplayNumbersLayout().animate()
                                .setDuration(300)
                                .translationY(0)
                                .setInterpolator(new OvershootInterpolator())
                                .setListener(null)
                                .start();
                    }

                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                    }
                })
                .start();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);

        reset();
    }

    private void blink(View view)
    {
        Animation alpha = new AlphaAnimation(1.0f, 0);
        alpha.setDuration(100);
        alpha.setRepeatCount(4);
        alpha.setRepeatMode(Animation.REVERSE);
        alpha.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(alpha);
    }

    public interface BMNumberInputDialogCallback
    {
        void onNumberInput(int num, String output);

        void onNumberFilled(String output);

        void onNumberDeleted(int num, String output);
    }

}
