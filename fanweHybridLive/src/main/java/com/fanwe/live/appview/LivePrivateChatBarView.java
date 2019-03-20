package com.fanwe.live.appview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.span.view.SDSpannableEdittext;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 私聊界面底部操作栏布局
 */
public class LivePrivateChatBarView extends BaseAppView implements TextWatcher
{
    public ImageView iv_keyboard;
    public ImageView iv_voice;

    public SDSpannableEdittext et_content;
    public TextView tv_record;

    public ImageView iv_hide_expression;
    public ImageView iv_show_expression;

    public TextView tv_send;
    public ImageView iv_more;

    private boolean voiceModeEnable;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public LivePrivateChatBarView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LivePrivateChatBarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LivePrivateChatBarView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_private_chat_bar);

        iv_keyboard = find(R.id.iv_keyboard);
        iv_voice = find(R.id.iv_voice);
        et_content = find(R.id.et_content);
        tv_record = find(R.id.tv_record);
        iv_hide_expression = find(R.id.iv_hide_expression);
        iv_show_expression = find(R.id.iv_show_expression);
        tv_send = find(R.id.tv_send);
        iv_more = find(R.id.iv_more);

        iv_keyboard.setOnClickListener(this);
        iv_voice.setOnClickListener(this);

        iv_hide_expression.setOnClickListener(this);
        iv_show_expression.setOnClickListener(this);

        iv_more.setOnClickListener(this);
        tv_send.setOnClickListener(this);

        et_content.addTextChangedListener(this);
        et_content.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    et_content.append(" ");
                    return true;
                }
                return false;
            }
        });
        et_content.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                showInputMode();
                if (clickListener != null)
                {
                    return clickListener.onTouchEditText();
                }
                return false;
            }
        });

        setVoiceModeEnable(true);
    }

    public void setVoiceModeEnable(boolean voiceModeEnable)
    {
        this.voiceModeEnable = voiceModeEnable;

        showNormalMode();
        if (!voiceModeEnable)
        {
            SDViewUtil.setGone(iv_voice);
            SDViewUtil.setGone(iv_keyboard);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (v == iv_keyboard)
        {
            showInputMode();
            if (clickListener != null)
            {
                clickListener.onClickKeyboard();
            }
        } else if (v == iv_voice)
        {
            showVoiceMode();
            if (clickListener != null)
            {
                clickListener.onClickVoice();
            }
        } else if (v == iv_show_expression)
        {
            showExpressionMode();
            if (clickListener != null)
            {
                clickListener.onClickShowExpression();
            }
        } else if (v == iv_hide_expression)
        {
            showInputMode();
            if (clickListener != null)
            {
                clickListener.onClickHideExpression();
            }
        } else if (v == iv_more)
        {
            showMoreMode();
            if (clickListener != null)
            {
                clickListener.onClickMore();
            }
        } else if (v == tv_send)
        {
            if (clickListener != null)
            {
                clickListener.onClickSend(getInputContent());
            }
        }
    }

    /**
     * 显示输入键盘
     */
    public void showKeyboard()
    {
        SDKeyboardUtil.showKeyboard(et_content);
    }

    /**
     * 隐藏输入键盘
     */
    public void hideKeyboard()
    {
        SDKeyboardUtil.hideKeyboard(et_content);
    }

    public void showNormalMode()
    {
        showFirstVoice();
        showSecondInput();
        showThirdOpenExpression();
        showFourByContent();
        hideKeyboard();
    }

    /**
     * 语音输入模式
     */
    private void showVoiceMode()
    {
        showFirstKeyboard();
        showSecondVoice();
        showThirdOpenExpression();
        showFourMore();
        hideKeyboard();
    }

    /**
     * 文字输入模式
     */
    private void showInputMode()
    {
        showFirstVoice();
        showSecondInput();
        showThirdOpenExpression();
        showFourByContent();
        showKeyboard();
    }

    /**
     * 更多模式
     */
    private void showMoreMode()
    {
        showFirstVoice();
        showSecondInput();
        showThirdOpenExpression();
        showFourMore();
        hideKeyboard();
    }

    /**
     * 显示表情模式
     */
    public void showExpressionMode()
    {
        showFirstVoice();
        showSecondInput();
        showThirdHideExpression();
        showFourByContent();
        hideKeyboard();
    }

    protected void showFirstVoice()
    {
        if (voiceModeEnable)
        {
            SDViewUtil.setVisible(iv_voice);
            SDViewUtil.setGone(iv_keyboard);
        }
    }

    protected void showFirstKeyboard()
    {
        if (voiceModeEnable)
        {
            SDViewUtil.setVisible(iv_keyboard);
            SDViewUtil.setGone(iv_voice);
        }
    }

    private void showSecondVoice()
    {
        SDViewUtil.setVisible(tv_record);
        SDViewUtil.setGone(et_content);
    }

    private void showSecondInput()
    {
        SDViewUtil.setVisible(et_content);
        SDViewUtil.setGone(tv_record);
    }

    protected void showThirdOpenExpression()
    {
        SDViewUtil.setVisible(iv_show_expression);
        SDViewUtil.setGone(iv_hide_expression);
    }

    protected void showThirdHideExpression()
    {
        SDViewUtil.setVisible(iv_hide_expression);
        SDViewUtil.setGone(iv_show_expression);
    }

    protected void showFourMore()
    {
        SDViewUtil.setVisible(iv_more);
        SDViewUtil.setInvisible(tv_send);
    }

    protected void showFourSend()
    {
        SDViewUtil.setVisible(tv_send);
        SDViewUtil.setInvisible(iv_more);
    }

    protected void showFourByContent()
    {
        if (getInputContent().length() > 0)
        {
            showFourSend();
        } else
        {
            showFourMore();
        }
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
        showFourByContent();
    }

    public String getInputContent()
    {
        return et_content.getText().toString();
    }


    public interface ClickListener
    {
        /**
         * 点击键盘
         */
        void onClickKeyboard();

        /**
         * 点击语音
         */
        void onClickVoice();

        /**
         * 点击显示表情
         */
        void onClickShowExpression();

        /**
         * 点击隐藏表情
         */
        void onClickHideExpression();

        /**
         * 点击更多（加号图标）
         */
        void onClickMore();

        /**
         * 点击发送
         *
         * @param content
         */
        void onClickSend(String content);

        /**
         * 触摸输入框
         *
         * @return
         */
        boolean onTouchEditText();
    }
}
