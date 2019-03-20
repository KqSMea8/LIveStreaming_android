package com.fanwe.shortvideo.videorecord;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.shortvideo.model.MusicSearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Link on 2016/9/14.
 */
public class SearchMusicView extends LinearLayout {

    static private final String TAG = SearchMusicView.class.getSimpleName();
    private TCAudioControl mAudioCtrl;
    private SDTitleSimple title;
    private EditText edit_search;
    private Context mContext;
    public MusicListView mMusicList;
    private List<TCAudioControl.MediaEntity> mMusicListData=new ArrayList<>();

    public SearchMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SearchMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public SearchMusicView(Context context) {
        super(context);
        mContext = context;
    }

    public void init(TCAudioControl audioControl) {
        mAudioCtrl = audioControl;
        LayoutInflater.from(mContext).inflate(R.layout.search_music_view, this);
        mMusicList = (MusicListView) findViewById(R.id.xml_music_list_view);
        mMusicList.setupList(LayoutInflater.from(mContext));
        mMusicList.getAdapter().setOnItemSelecetListener(new MusicListAdapter.ItemSelectListener() {
            @Override
            public void OnItemSelected(int position, String title, String path) {
                mAudioCtrl.playBGM(title, path, position);
            }
        });
        edit_search = (EditText) findViewById(R.id.edit_search);
        title = (SDTitleSimple) findViewById(R.id.title);
        title.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        title.setMiddleTextBot("搜索歌曲");
        title.setmListener(new SDTitleSimple.SDTitleSimpleListener() {
            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
                SDKeyboardUtil.hideKeyboard(edit_search);
                SearchMusicView.this.setVisibility(GONE);
                mAudioCtrl.mMusicSelectView.setVisibility(GONE);
                mAudioCtrl.mMusicControlPart.setVisibility(VISIBLE);
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

            }

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
            }
        });
        findViewById(R.id.tv_search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKeyboardUtil.hideKeyboard(edit_search);
                requestData(edit_search.getText().toString());
            }
        });

    }
    protected void requestData(String songStr) {

        CommonInterface.requestSearchMusicList(songStr,new AppRequestCallback<MusicSearchModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel != null && actModel.song != null && actModel.song.size() !=0) {
                    for (MusicSearchModel. SongListModel model:actModel.song) {
                        TCAudioControl control=new TCAudioControl(mContext);
                        TCAudioControl.MediaEntity entity = control.new MediaEntity();
                        entity.id = Integer.valueOf(model.songid);
                        entity.title = model.songname;
                        entity.singer = model.artistname;
                        for (TCAudioControl.MediaEntity mediaEntity : mAudioCtrl.mMusicListData) {
                            if (mediaEntity.title.equals(model.songname)) {
                                entity.isDownLoad = 1;
                            }
                        }
                        mMusicListData.add(entity);
                    }
                    mMusicList.getAdapter().setData(mMusicListData);
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });

    }
}
