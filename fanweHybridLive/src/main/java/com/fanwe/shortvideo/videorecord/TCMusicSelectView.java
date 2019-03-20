package com.fanwe.shortvideo.videorecord;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;

/**
 * Created by Link on 2016/9/14.
 */
public class TCMusicSelectView extends FrameLayout {

    static private final String TAG = TCMusicSelectView.class.getSimpleName();
    private TCAudioControl mAudioCtrl;
    private SDTitleSimple title;
    private Context mContext;
    public MusicListView mMusicList;
    public SearchMusicView mSearchMusicView;
    private SDTabText tab_left;
    private SDTabText tab_center;
    private SDTabText tab_right;
    public SDSelectViewManager<SDTabText> mSelectManager = new SDSelectViewManager<SDTabText>();

    public TCMusicSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public TCMusicSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public TCMusicSelectView(Context context) {
        super(context);
        mContext = context;
    }

    public void init(TCAudioControl audioControl) {
        mAudioCtrl = audioControl;
        LayoutInflater.from(mContext).inflate(R.layout.audio_ctrl_music_list, this);
        mMusicList = (MusicListView) findViewById(R.id.xml_music_list_view);
        mSearchMusicView = (SearchMusicView) findViewById(R.id.search_music_view);
        mSearchMusicView.init(audioControl);
        tab_left = (SDTabText) findViewById(R.id.tab_left);
        tab_center = (SDTabText) findViewById(R.id.tab_center);
        tab_right = (SDTabText) findViewById(R.id.tab_right);
        title = (SDTitleSimple) findViewById(R.id.title);
        title.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        title.initRightItem(1);
        title.setMiddleTextBot("音乐列表");
        title.getItemRight(0).setImageRight(R.drawable.ic_edit_search_gray);
        title.setmListener(new SDTitleSimple.SDTitleSimpleListener() {
            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
                mAudioCtrl.mMusicSelectView.setVisibility(GONE);
                mAudioCtrl.mMusicControlPart.setVisibility(VISIBLE);
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

            }

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {
                mSearchMusicView.setVisibility(VISIBLE);
            }
        });
        addTab();

    }

    private void addTab() {
        tab_left.setTextTitle("本地音乐");
        tab_left.getViewConfig(tab_left.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_left.setTextSizeTitleSp(15);
        tab_center.setTextTitle("在线音乐");
        tab_center.getViewConfig(tab_center.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_center.setTextSizeTitleSp(15);
        tab_right.setTextTitle("已下载");
        tab_right.getViewConfig(tab_right.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_right.setTextSizeTitleSp(15);
        mSelectManager.setItems(new SDTabText[]
                {tab_left, tab_center, tab_right});
        mSelectManager.performClick(0);
    }

    public void setReturnListener(OnClickListener onClickListener) {
        mAudioCtrl.mMusicSelectView.setVisibility(GONE);
        mAudioCtrl.mMusicControlPart.setVisibility(VISIBLE);
    }
}
