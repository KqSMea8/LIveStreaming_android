package com.fanwe.auction.pop;

import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.popupwindow.SDPWindowBase;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;


public class InputSuggestionPop extends SDPWindowBase
{

    private SDGridLinearLayout mLl_content;
    private EditText mEt;

    private BaseAdapter mAdapter;

    private InputSuggestionView_TextChangedListener mListenerTextChanged;
    private InputSuggestionView_OnItemClickListener mListenerOnItemClick;

    public InputSuggestionPop()
    {
        super();
        init();
    }

    public void setmListenerTextChanged(InputSuggestionView_TextChangedListener listenerTextChanged)
    {
        this.mListenerTextChanged = listenerTextChanged;
    }

    public void setmListenerOnItemClick(InputSuggestionView_OnItemClickListener listenerOnItemClick)
    {
        this.mListenerOnItemClick = listenerOnItemClick;
    }

    private void init()
    {
        setContentView(R.layout.pop_search_suggestion);
        mLl_content = (SDGridLinearLayout) getContentView().findViewById(R.id.ll_content);

        mLl_content.setItemClickListener(new SDGridLinearLayout.ItemClickListener()
        {
            @Override
            public void onItemClick(int position, View view, ViewGroup parent)
            {
                if (mListenerOnItemClick != null)
                {
                    mListenerOnItemClick.onItemClick(position);
                }
                SDKeyboardUtil.hideKeyboard(mEt);
                hide();
            }
        });

        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setFocusable(false);
        setHeight(SDViewUtil.getScreenHeight() / 2);
        hide();
    }

    public BaseAdapter getmAdapter()
    {
        return mAdapter;
    }

    public void setEditText(EditText et)
    {
        this.mEt = et;
        mEt.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (TextUtils.isEmpty(s.toString()))
                {
                    hide();
                }
                if (mListenerTextChanged != null)
                {
                    mListenerTextChanged.afterTextChanged(s);
                }
            }
        });
    }

    public void hide()
    {
        dismiss();
    }

    public void show()
    {
        showAsDropDown(mEt);
    }

    public void setAdapter(BaseAdapter adapter)
    {
        this.mAdapter = adapter;
        mAdapter.registerDataSetObserver(new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                if (mAdapter.getCount() > 0)
                {
                    show();
                } else
                {
                    hide();
                }
                super.onChanged();
            }
        });
        mLl_content.setAdapter(mAdapter);

    }

    public interface InputSuggestionView_TextChangedListener
    {
        public void afterTextChanged(Editable s);
    }

    public interface InputSuggestionView_OnItemClickListener
    {
        public void onItemClick(int position);
    }

}
