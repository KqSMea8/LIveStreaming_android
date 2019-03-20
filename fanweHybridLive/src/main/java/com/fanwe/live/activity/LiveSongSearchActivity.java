package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveSongDownloadManager;
import com.fanwe.live.LiveSongSearchHistoryManager;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveSongListAdapter;
import com.fanwe.live.adapter.LiveSongListAdapter.OnXItemClickListener;
import com.fanwe.live.adapter.LiveSongSearchHistoryAdapter;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.ELiveSongDownload;
import com.fanwe.live.event.EPlayMusic;
import com.fanwe.live.model.LiveSongModel;
import com.fanwe.live.model.Music_downurlActModel;
import com.fanwe.live.model.Music_searchActModel;
import com.fanwe.live.view.LiveSongSearchView;
import com.fanwe.live.view.LiveSongSearchView.SearchViewListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import de.greenrobot.event.EventBus;

public class LiveSongSearchActivity extends BaseActivity implements SearchViewListener, OnXItemClickListener{

	private ListView mHistoryListView;
	private PullToRefreshListView mResultListView;
	private LiveSongSearchHistoryAdapter mHistoryAdapter;
	private LiveSongSearchHistoryManager mHistoryManager;
	private LiveSongListAdapter mSongAdapter;
	private View mEmptyView;
	private LiveSongSearchView mSearchView;
	private Music_searchActModel mActModel;
	private int mPage;
	private String mKeyword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_live_song_search);
		
		mEmptyView = findViewById(R.id.rl_empty);
		mSearchView = (LiveSongSearchView)findViewById(R.id.sv_song);
		mSearchView.setSearchViewListener(this);
		findViewById(R.id.rl_back).setOnClickListener(this);
		initSearchHistory();
		initResultList();
		
		findViewById(R.id.v_xx).setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
					mSearchView.hideKeyboard();
				}else {
					
				}
				return false;
			}
		});
	}
	
	protected void initSearchHistory() {
		mHistoryListView = (ListView)findViewById(R.id.lv_search_history);
		mHistoryAdapter = new LiveSongSearchHistoryAdapter(this);
		mHistoryManager = LiveSongSearchHistoryManager.getInstance();
		if (mHistoryManager.isEmpty()) {
			mHistoryListView.setVisibility(View.GONE);
			mEmptyView.setVisibility(View.VISIBLE);
		}else {
			mHistoryAdapter.setData(mHistoryManager.getDatas());
			mHistoryListView.setAdapter(mHistoryAdapter);
			mEmptyView.setVisibility(View.GONE);
			mHistoryListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mSearchView.setSearchKey(mHistoryAdapter.getItem(arg2));
					mHistoryListView.setVisibility(View.GONE);
				}
			});
		}
		
	}
	
	protected void initResultList() {
		mResultListView = (PullToRefreshListView)findViewById(R.id.lv_search_result);
		mSongAdapter = new LiveSongListAdapter(this);
		
		mResultListView.setAdapter(mSongAdapter);
		
		mResultListView.setMode(Mode.PULL_FROM_END);
		mSongAdapter.setXItemClickListener(this);
		mResultListView.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				reloadData(false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				if (mActModel != null && mActModel.hasNext()) {
					loadData();
				}else {
					mResultListView.onRefreshComplete();
					SDToast.showToast("没有更多");
				}
				
			}
		});

		mResultListView.setAdapter(mSongAdapter);
		
//		mResultListView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				mSearchView.hideKeyboard();
//				return false;
//			}
//		});
	}
	
	protected void loadAudioUrlSilent(final LiveSongModel model) {
		CommonInterface.requestMusic_downurl(model.getAudio_id(), new AppRequestCallback<Music_downurlActModel>() {

			@Override
			protected void onSuccess(SDResponse resp) {
				if (actModel.isOk()) {
					model.setAudio_link(actModel.getAudio().getAudio_link());
					model.setTime_len(actModel.getAudio().getTime_len());
					model.setLrc_content(actModel.getAudio().getLrc_content());
					LiveSongDownloadManager.getInstance().addUserSongSilent(model);
				}
			}
		});
		
	}
	protected void loadAudioUrl(final LiveSongModel model) {
		showProgressDialog("");
		CommonInterface.requestMusic_downurl(model.getAudio_id(), new AppRequestCallback<Music_downurlActModel>() {

			@Override
			protected void onSuccess(SDResponse resp) {
				if (actModel.isOk()) {
					model.setAudio_link(actModel.getAudio().getAudio_link());
					model.setTime_len(actModel.getAudio().getTime_len());
					model.setLrc_content(actModel.getAudio().getLrc_content());
					LiveSongDownloadManager.getInstance().addTask(model);
				}
			}
			@Override
			protected void onError(SDResponse resp) {
				super.onError(resp);
				SDToast.showToast(resp.getResult());
				
			}
			
			@Override
			protected void onFinish(SDResponse resp) {
				super.onFinish(resp);
				dismissProgressDialog();
			}
		});
	}
	protected void reloadData(boolean showLoading) {
		mPage = 1;
		if(showLoading){
			showProgressDialog("");
		}
		loadData();
	}
	protected void loadData() {

		CommonInterface.requestMusic_search(mPage, mKeyword, new AppRequestCallback<Music_searchActModel>() {
			
			@Override
			protected void onSuccess(SDResponse resp) {
				
				if (actModel.isOk()) {
					mHistoryListView.setVisibility(View.GONE);
					if (mPage == 1) {
						mSongAdapter.updateData(actModel.getList());
					}else {
						mSongAdapter.appendData(actModel.getList());
					}
					mPage += 1;
					mActModel = actModel;
					
					if (mSongAdapter.getCount() > 0) {
						mEmptyView.setVisibility(View.GONE);
					}else {
						mEmptyView.setVisibility(View.VISIBLE);
					}
				}else {
					if (!TextUtils.isEmpty(actModel.getError())) {
						SDToast.showToast(actModel.getError());
					}
					
				}
				
				
			}
			
			@Override
			protected void onError(SDResponse resp) {
				super.onError(resp);
				SDToast.showToast(resp.getResult());
			}
			
			@Override
			protected void onFinish(SDResponse resp) {
				super.onFinish(resp);
				mResultListView.onRefreshComplete();
				dismissProgressDialog();
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onRefreshAutoComplete(String text) {
		mKeyword = text;
		reloadData(false);
	}

	@Override
	public void onSearch(String text) {
		mKeyword = text;
		reloadData(true);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHistoryManager.add(mSearchView.getLastSearchKey());
	}
	
	public void onEventMainThread(ELiveSongDownload event)
	{
		mSongAdapter.updateSongItem(event);
	}

	@Override
	public void onXItemLongClick(int position, View convertView,
			ViewGroup parent, LiveSongModel model) {
	}

	@Override
	public void onXItemClick(int position, View convertView, ViewGroup parent,
			LiveSongModel model) {

		if (model.getStatus() == 1) {
			// 下载中
			return;
		}
		
		if (model.isMusicExist()) {
			// 跳转到直接UI
			EventBus.getDefault().post(new EPlayMusic(model));
			AppRuntimeWorker.openLiveCreaterActivity(LiveSongSearchActivity.this);
			loadAudioUrlSilent(model);
		}else {
			loadAudioUrl(model);
		}

	}
}
