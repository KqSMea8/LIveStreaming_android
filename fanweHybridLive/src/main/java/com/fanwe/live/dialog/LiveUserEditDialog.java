package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserEditActivity;

/**
 * Created by shibx on 2016/7/13.
 */
public class LiveUserEditDialog extends SDDialogBase implements TextWatcher
{

    private SDTitleSimple sdTitleSimple;
    private TextView tv_text_limit;
    private EditText et_edit_dialog;
    private TextView tv_text_nick_info;

    private OnSaveClickListener mListener;

    private int maxTextLength;

    private String mModifyText;

    private int flag;

    private String beforeTextChanged;

    public LiveUserEditDialog(Activity activity)
    {
        super(activity);
        setContentView(R.layout.dialog_user_edit);
        initView();
    }

    private void initView()
    {
        sdTitleSimple = (SDTitleSimple) findViewById(R.id.title);
        tv_text_limit = (TextView) findViewById(R.id.tv_text_limit);
        et_edit_dialog = (EditText) findViewById(R.id.et_edit_dialog);
        tv_text_nick_info = (TextView) findViewById(R.id.tv_text_nick_info);
        et_edit_dialog.addTextChangedListener(this);
        setFullScreen();

        sdTitleSimple.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        sdTitleSimple.initRightItem(1);
        sdTitleSimple.getItemRight(0).setTextBot("保存");
        sdTitleSimple.setmListener(new SDTitleSimple.SDTitleSimpleListener()
        {
            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v)
            {
                dismiss();
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v)
            {

            }

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
            {
                if (mListener != null)
                {
                    mModifyText = et_edit_dialog.getText().toString().trim();
                    if (flag == LiveUserEditActivity.FLAG_NICK_NAME && TextUtils.isEmpty(mModifyText))
                    {
                        SDToast.showToast("昵称不可为空");
                        return;
                    }
                    if (TextUtils.equals(beforeTextChanged, mModifyText))
                    {
                        mListener.onSaveClick(flag, mModifyText, false);
                    } else
                    {
                        mListener.onSaveClick(flag, mModifyText, true);
                    }
                    dismiss();
                }
            }
        });
    }

    private InputFilter[] getFilter(int maxLength)
    {
        InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
        return filters;
    }

    /**
     * 设置窗口内容
     *
     * @param content       文本内容
     * @param maxTextLength 文本输入长度限制
     * @param flag
     */
    public void setDialogContent(String content, int maxTextLength, int flag)
    {
        this.flag = flag;
        this.beforeTextChanged = content;
        this.maxTextLength = maxTextLength;
        sdTitleSimple.setMiddleTextTop(getTitle(flag));
        et_edit_dialog.setText(content);
        et_edit_dialog.setSelection(et_edit_dialog.getText().toString().length());
        et_edit_dialog.setFilters(getFilter(maxTextLength));
        int currentLength = getCurrentLength(content);
        tv_text_limit.setText(currentLength + "/" + maxTextLength);
    }

    /**
     * 获取flag对应的标题
     *
     * @param flag
     * @return
     */
    private String getTitle(int flag)
    {
        switch (flag)
        {
            case LiveUserEditActivity.FLAG_NICK_NAME:
                return LiveUserEditActivity.TITLE_EDIT_NICK_NAME;
            case LiveUserEditActivity.FLAG_MOTTO:
                return LiveUserEditActivity.TITLE_EDIT_MOTTO;
            case LiveUserEditActivity.FLAG_PROFESSION:
                return LiveUserEditActivity.TITLE_EDIT_PROFESSION;
            default:
                return "";
        }
    }

    /**
     * 获取当前文本长度
     *
     * @param content
     * @return
     */
    private int getCurrentLength(String content)
    {
        return TextUtils.isEmpty(content) ? 0 : content.length();
    }

    public void setOnSaveClickListener(OnSaveClickListener listener)
    {
        this.mListener = listener;
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
        tv_text_limit.setText(et_edit_dialog.getText().toString().length() + "/" + maxTextLength);
        mModifyText = et_edit_dialog.getText().toString();
    }

    public interface OnSaveClickListener
    {
        void onSaveClick(int flag, String modifyText, boolean isChanged);
    }

    public void setTvTextNickInfoText(String text)
    {
        if (TextUtils.isEmpty(text))
        {
            SDViewUtil.setGone(tv_text_nick_info);
            return;
        }

        SDViewUtil.setVisible(tv_text_nick_info);
        SDViewBinder.setTextView(tv_text_nick_info, text);
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
        SDViewUtil.setGone(tv_text_nick_info);
    }
}
