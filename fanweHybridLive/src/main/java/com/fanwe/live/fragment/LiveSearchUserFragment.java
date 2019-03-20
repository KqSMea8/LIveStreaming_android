package com.fanwe.live.fragment;

import android.text.TextUtils;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveUserModelAdapter;
import com.fanwe.live.appview.LiveSearchUserView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by shibx on 2016/7/25.
 */
public class LiveSearchUserFragment extends LiveBaseSearchFragment {

    @ViewInject(R.id.view_user)
    private LiveSearchUserView view_user;

    private SDRequestHandler mHandler = null;
    private String keyWord = "";

    private LiveUserModelAdapter.OnItemClickListener mListener;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_live_search_user;
    }

    @Override
    protected void init() {
        super.init();
        view_user.setOnItemClickListener(mListener);
        view_user.search(keyWord);
    }

    public void setOnItemClickListener(LiveUserModelAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void search(String keyWord) {

        this.keyWord = keyWord;

        if (view_user == null)
        {
            return;
        }

        if(!TextUtils.isEmpty(keyWord) && TextUtils.equals(keyWord,view_user.keyword)) {
            return ;
        }
        if(mHandler != null) {
            mHandler.cancel();
        }
        mHandler = view_user.search(keyWord);
    }
}
