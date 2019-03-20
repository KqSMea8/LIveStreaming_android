package com.fanwe.shortvideo.editor.bgm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.live.R;
import com.fanwe.shortvideo.editor.TCVideoEditerWrapper;
import com.fanwe.shortvideo.editor.bgm.utils.TCBGMInfo;
import com.fanwe.shortvideo.editor.bgm.utils.TCMusicManager;
import com.fanwe.shortvideo.editor.bgm.view.TCReversalSeekBar;
import com.fanwe.shortvideo.editor.common.widget.BaseRecyclerAdapter;
import com.fanwe.shortvideo.view.DialogUtil;
import com.fanwe.shortvideo.view.RangeSlider;
import com.tencent.ugc.TXVideoEditer;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by hans on 2017/11/6.
 * <p>
 * bgm设置的fragment
 */
public class TCBGMSettingFragment extends Fragment implements RangeSlider.OnRangeChangeListener, BaseRecyclerAdapter.OnItemClickListener {
    private View mContentView;

    /**
     * 音乐列表相关
     */
    private RecyclerView mRecyclerView;
    private RelativeLayout mRlEmpty, mRlLoading, mRlRoot;
    private TCMusicAdapter mMusicListAdapter;
    private List<TCBGMInfo> mMusicList;


    /**
     * 控制面板相关
     */
    private TCReversalSeekBar mTCReversalSeekBar;
    private TextView mTvDelete, mTvMusicName;
    private LinearLayout mLlMainPanel;
    private RelativeLayout mRlMusicInfo;
    private RelativeLayout mRlChoseMusic;
    private RangeSlider mRangeSlider;


    private long mDuration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bgm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentView = view;
        initMusicListPanel(view);
        initControlPanel(view);
    }

    /**
     * ==============================================SDK设置相关==============================================
     */


    /**
     * 音量修改
     */
    private void onBGMVolumeChange(float progress) {
        TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
        editer.setBGMVolume(progress);
        editer.setVideoVolume(1 - progress);
    }

    /**
     * BGM信息配置
     *
     * @param info 为null不设置BGM
     */
    private boolean onSetBGMInfo(TCBGMInfo info) {
        TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
        if (info == null) {
            editer.setBGM(null); //不设置BGM
            return true;
        } else {
            String bgmPath = info.getPath();
            if (!TextUtils.isEmpty(bgmPath)) {
                int result = editer.setBGM(bgmPath);
                if (result != 0) {
                    DialogUtil.showDialog(getContext(), "视频编辑失败", "背景音仅支持MP3格式或M4A音频");
                }
                return result == 0;//设置成功
            }
        }
        return false;
    }

    /**
     * bgm 播放时间区间设置
     */
    private void onSetBGMStartTime(long startTime, long endTime) {
        TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
        editer.setBGMStartTime(startTime, endTime);
    }


    /**
     * ==============================================音乐列表相关==============================================
     */

    private void initMusicListPanel(View view) {
        mRlRoot = (RelativeLayout) view.findViewById(R.id.chose_rl_root);
        mRlEmpty = (RelativeLayout) view.findViewById(R.id.chose_rl_empty);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.chose_rv_music);
        mRlLoading = (RelativeLayout) view.findViewById(R.id.chose_rl_loading_music);
        initMusicList();
    }


    private void initMusicList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMusicList = new ArrayList<>();
        mMusicListAdapter = new TCMusicAdapter(mMusicList);
        mMusicListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMusicListAdapter);
        mRlLoading.setVisibility(View.VISIBLE);
        //延迟500ms在进行歌曲加载， 避免与外部线程竞争
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadMusicAndSetAdapter();
            }
        }, 500);
    }

    /**
     * 开启子线程获取音频列表
     */
    private void loadMusicAndSetAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMusicList.clear();
                mMusicList.addAll(TCMusicManager.getInstance(getContext()).getAllMusic());
                //切换到主线程
                mContentView.post(new Runnable() {
                    @Override
                    public void run() {
                        mRlLoading.setVisibility(View.GONE);
                        if (mMusicList != null && mMusicList.size() > 0) {
                            mMusicListAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(mMusicListAdapter);
                        } else {
                            mRlEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onItemClick(View view, int position) {
        boolean success = setBGMInfo(mMusicList.get(position));
        // 设置成功，切换到BGM控制面板
        if (success) {
            mRlChoseMusic.setVisibility(GONE);
            mLlMainPanel.setVisibility(VISIBLE);
        }
    }

    /**
     * ==============================================控制面板相关==============================================
     */
    private void initControlPanel(View view) {
        mTvMusicName = (TextView) view.findViewById(R.id.bgm_tv_music_name);
        mTvDelete = (TextView) view.findViewById(R.id.bgm_tv_delete);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlMainPanel.setVisibility(GONE);
                mRlChoseMusic.setVisibility(VISIBLE);
                onSetBGMInfo(null);
            }
        });
        mRlMusicInfo = (RelativeLayout) view.findViewById(R.id.bgm_rl_bgm_info);
        mRlMusicInfo.setVisibility(GONE);

        mRangeSlider = (RangeSlider) view.findViewById(R.id.bgm_range_slider);
        mRangeSlider.setRangeChangeListener(this);

        mLlMainPanel = (LinearLayout) view.findViewById(R.id.bgm_ll_main_panel);
        mRlChoseMusic = (RelativeLayout) view.findViewById(R.id.bgm_rl_chose);


        mTCReversalSeekBar = (TCReversalSeekBar) view.findViewById(R.id.bgm_sb_voice);
        mTCReversalSeekBar.setOnSeekProgressListener(new TCReversalSeekBar.OnSeekProgressListener() {
            @Override
            public void onSeekDown() {

            }

            @Override
            public void onSeekUp() {

            }

            @Override
            public void onSeekProgress(float progress) {
                onBGMVolumeChange(progress);
            }
        });
    }

    private boolean setBGMInfo(TCBGMInfo bgmInfo) {
        if (bgmInfo == null) {
            return false;
        }
        mRlMusicInfo.setVisibility(VISIBLE);
        mDuration = bgmInfo.getDuration();
        mTvMusicName.setText(bgmInfo.getSongName() + " — " + bgmInfo.getSingerName() + "   " + bgmInfo.getFormatDuration());

        mRangeSlider.resetRangePos();
        return onSetBGMInfo(bgmInfo);
    }

    @Override
    public void onKeyDown(int type) {
    }


    @Override
    public void onKeyUp(int type, int leftPinIndex, int rightPinIndex) {
        long leftTime = mDuration * leftPinIndex / 100; //ms
        long rightTime = mDuration * rightPinIndex / 100;

        onSetBGMStartTime(leftTime, rightTime);
    }

}
