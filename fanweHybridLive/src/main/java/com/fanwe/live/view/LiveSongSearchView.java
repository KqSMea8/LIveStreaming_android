package com.fanwe.live.view;

import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LiveSongSearchView  extends LinearLayout implements View.OnClickListener {  
  
    /** 
     * 输入框 
     */  
    private EditText etInput;  
  
    /** 
     * 删除键 
     */  
    private ImageView ivDelete;  
  
    /** 
     * 返回按钮 
     */  
    public Button btnSearch;
  
    /** 
     * 上下文对象 
     */  
    private Context mContext;  
  
    /** 
     * 搜索回调接口 
     */  
    private SearchViewListener mListener;  
    
    private String mSearchKey;
  
    /** 
     * 设置搜索回调接口 
     * 
     * @param listener 监听者 
     */  
    public void setSearchViewListener(SearchViewListener listener) {  
        mListener = listener;  
    }  
  
    public LiveSongSearchView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        mContext = context;  
        LayoutInflater.from(context).inflate(R.layout.view_live_song_search_bar, this);  
        initViews();  
    }  
  
    private void initViews() {  
        etInput = (EditText) findViewById(R.id.search_et_input);  
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);  
        btnSearch = (Button) findViewById(R.id.btn_search);  

  
        ivDelete.setOnClickListener(this);  
        btnSearch.setOnClickListener(this);  
  
        etInput.addTextChangedListener(new EditChangedListener());  
        etInput.setOnClickListener(this);  
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
            @Override  
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {  
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {  
                    notifyStartSearching(etInput.getText().toString());  
                }  
                return true;  
            }

        });  
        
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
//      imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); 
        imm.showSoftInput(etInput, 0);
    }  
  
    public EditText getEtInput()
	{
		return etInput;
	}

	/** 
     * 通知监听者 进行搜索操作 
     * @param text 
     */  
    private void notifyStartSearching(String text){  
    	if (!TextUtils.isEmpty(text)) {
			mSearchKey = text;
		}else {
			SDToast.showToast("请输入关键字");
		}
        if (mListener != null) {  
            mListener.onSearch(etInput.getText().toString());  
        }  
        //隐藏软键盘  
       hideKeyboard();
    }  
  
   
    
  
    private class EditChangedListener implements TextWatcher {  
        @Override  
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {  
  
        }  
  
        @Override  
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {  
            if (!"".equals(charSequence.toString())) {  
                ivDelete.setVisibility(VISIBLE);  
            } else {
                ivDelete.setVisibility(GONE);  
            }
            //更新autoComplete数据
            if (mListener != null) {
                mListener.onRefreshAutoComplete(charSequence.toString());
            }
        }
  
        @Override  
        public void afterTextChanged(Editable editable) {  
        }  
    }  
  
    @Override  
    public void onClick(View view) {  
        switch (view.getId()) {  
            case R.id.search_et_input:  
                break;  
            case R.id.search_iv_delete:  
                etInput.setText("");  
                ivDelete.setVisibility(GONE);  
                break;  
            case R.id.btn_search:  
            	notifyStartSearching(etInput.getText().toString()); 
                break;  
        }  
    }  
  
    public String getLastSearchKey() {
		return mSearchKey;
	}
    
    public void setSearchKey(String key) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		mSearchKey = key;
		if (etInput != null) {
			 etInput.setText(mSearchKey);  
	         etInput.setSelection(mSearchKey.length());  
	          
//	         notifyStartSearching(mSearchKey);
		}
		 
	}
    
    public void hideKeyboard() {
    	try{
    	InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
    	imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
    	}catch(Exception e){
    		
    	}
	}
    /** 
     * search view回调方法 
     */  
    public interface SearchViewListener {  
  
        /** 
         * 更新自动补全内容 
         * 
         * @param text 传入补全后的文本 
         */  
        void onRefreshAutoComplete(String text);  
  
        /** 
         * 开始搜索 
         * 
         * @param text 传入输入框的文本 
         */  
        void onSearch(String text);  
  
//        /**  
//         * 提示列表项点击时回调方法 (提示/自动补全)  
//         */  
//        void onTipsItemClick(String text);  
    }  
  
}