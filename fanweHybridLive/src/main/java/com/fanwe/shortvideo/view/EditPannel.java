package com.fanwe.shortvideo.view;//package com.tencent.liteav.demo.shortvideo.view;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.tencent.liteav.demo.R;
//import com.tencent.liteav.demo.shortvideo.editor.utils.Edit;
//import com.tencent.liteav.demo.shortvideo.editor.TCVideoEditerActivity;
//import com.tencent.liteav.demo.shortvideo.editor.bgm.utils.TCBGMInfo;
//import com.tencent.liteav.demo.shortvideo.editor.motion.view.TCMotionEditView;
//import com.tencent.liteav.demo.shortvideo.editor.paster.view.TCPasterEditView;
//import com.tencent.liteav.demo.shortvideo.editor.bubble.TCWordEditView;
//
//import java.util.ArrayList;
//
///**
// * 总的控制面盘
// * <p>
// * 包含：裁剪 时间特效 滤镜 背景音 字幕
// */
//public class EditPannel extends LinearLayout implements View.OnClickListener {
//
//    /***********************各个接口的回调************************/
//    private Edit.OnCutChangeListener mOnCutChangeListener;
//    private Edit.OnSpeedChangeListener mOnSpeedChangeListener;
//    private Edit.OnFilterChangeListener mOnFilterChangeListener;
//    private Edit.OnBGMChangeListener mOnBGMChangeListener;
//    private Edit.OnWordChangeListener mOnWordChangeListener;
//    private Edit.OnReverseChangeListener mRevertClickListener;
//    private Edit.OnRepeatChangeListener mRepeateChangeListener;
//    private Edit.OnOriginChangeListener mOriginChangeListener;
//
//    private TCTimeEffectView mTimeEffectView;
//    private TCVideoEditView mCutView;                           // 裁剪模块的View
//    private TCBGMEditView mBGMEditView;                         // BGM模块的View
//    private TCWordEditView mWordEditView;                       // 添加字幕的View
//    private TCHorizontalScrollView mFilterSV;                   // 滤镜的View
//    private ArrayAdapter<Integer> mFilterAdapter;               // 滤镜的Adapter
//    private TCPasterEditView mPasterEditView;                   // 贴纸View
//    private TCMotionEditView mMotionEditView;                   // 动效View
//
//    private LinearLayout mCutLL, mFilterLL, mBgmLL, mWordLL, mTimeEffectLL, mPasterLL, mMotionLL;    // 上述各个View的载体 用于控制显示隐藏
//
//    private ImageButton mCutBtn, mFilterBtn, mBgmBtn, mWordBtn, mTimeEffectBtn, mPasterBtn, mMotion; // 底部的按钮
//
//    private Context mContext;
//    private float mSpeedLevel;
//
//    public EditPannel(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        init();
//    }
//
//    private void init() {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_pannel, this);
//        mTimeEffectLL = (LinearLayout) view.findViewById(R.id.time_effect_ll);
//        mCutLL = (LinearLayout) view.findViewById(R.id.cut_ll);
//        mFilterLL = (LinearLayout) view.findViewById(R.id.filter_ll);
//        mBgmLL = (LinearLayout) view.findViewById(R.id.bgm_ll);
//        mWordLL = (LinearLayout) view.findViewById(R.id.word_ll);
//        mPasterLL = (LinearLayout) view.findViewById(R.id.paster_ll);
//        mMotionLL = (LinearLayout) view.findViewById(R.id.motion_ll);
//
//        mTimeEffectView = (TCTimeEffectView) view.findViewById(R.id.timeEffectView);
//        mBGMEditView = (TCBGMEditView) view.findViewById(R.id.panel_bgm_edit);
//        mWordEditView = (TCWordEditView) view.findViewById(R.id.panel_word_edit);
//        mCutView = (TCVideoEditView) view.findViewById(R.id.editView);
//        mFilterSV = (TCHorizontalScrollView) view.findViewById(R.id.filter_sv);
//        mPasterEditView = (TCPasterEditView) view.findViewById(R.id.panel_paster_edit);
//        mMotionEditView = (TCMotionEditView) view.findViewById(R.id.panel_motion_edit);
//
//        mTimeEffectBtn = (ImageButton) view.findViewById(R.id.btn_time_effect);
//        mCutBtn = (ImageButton) view.findViewById(R.id.btn_cut);
//        mBgmBtn = (ImageButton) view.findViewById(R.id.btn_music);
//        mFilterBtn = (ImageButton) view.findViewById(R.id.btn_filter);
//        mWordBtn = (ImageButton) view.findViewById(R.id.btn_word);
//        mPasterBtn = (ImageButton) view.findViewById(R.id.btn_paster);
//        mMotion = (ImageButton) view.findViewById(R.id.btn_motion_filter);
//
//        mTimeEffectBtn.setOnClickListener(this);
//        mCutBtn.setOnClickListener(this);
//        mFilterBtn.setOnClickListener(this);
//        mBgmBtn.setOnClickListener(this);
//        mWordBtn.setOnClickListener(this);
//        mPasterBtn.setOnClickListener(this);
//        mMotion.setOnClickListener(this);
//
//        initFilter();
//    }
//
//    private void initFilter() {
//        final ArrayList<Integer> filterIDList = new ArrayList<Integer>();
//        filterIDList.add(R.drawable.orginal);
//        filterIDList.add(R.drawable.langman);
//        filterIDList.add(R.drawable.qingxin);
//        filterIDList.add(R.drawable.weimei);
//        filterIDList.add(R.drawable.fennen);
//        filterIDList.add(R.drawable.huaijiu);
//        filterIDList.add(R.drawable.landiao);
//        filterIDList.add(R.drawable.qingliang);
//        filterIDList.add(R.drawable.rixi);
//        mFilterAdapter = new ArrayAdapter<Integer>(mContext, 0, filterIDList) {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    LayoutInflater inflater = LayoutInflater.from(getContext());
//                    convertView = inflater.inflate(R.layout.filter_layout, null);
//                }
//                ImageView view = (ImageView) convertView.findViewById(R.id.filter_image);
//                if (position == 0) {
//                    ImageView view_tint = (ImageView) convertView.findViewById(R.id.filter_image_tint);
//                    if (view_tint != null)
//                        view_tint.setVisibility(View.VISIBLE);
//                }
//                view.setTag(position);
//                view.setImageDrawable(getResources().getDrawable(getItem(position)));
//                view.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int index = (int) view.getTag();
//                        selectFilter(index);
//                        if (mOnFilterChangeListener != null) {
//                            Bitmap bmp = getFilterBitmap(index);
//                            mOnFilterChangeListener.onFilterChange(bmp);
//                        }
//                    }
//                });
//                return convertView;
//            }
//        };
//        mFilterSV.setAdapter(mFilterAdapter);
//    }
//
//    public float getBGMVolumeProgress() {
//        return mBGMEditView.getProgress();
//    }
//
//    /**
//     * 设置裁剪Listener
//     *
//     * @param listener
//     */
//    public void setCutChangeListener(Edit.OnCutChangeListener listener) {
//        mOnCutChangeListener = listener;
//        mCutView.setCutChangeListener(listener);
//    }
//
//    /**
//     * 设置加速Listener
//     *
//     * @param listener
//     */
//    public void setSpeedChangeListener(Edit.OnSpeedChangeListener listener) {
//        mOnSpeedChangeListener = listener;
//        mTimeEffectView.setSpeedChangeListener(listener);
//    }
//
//    /**
//     * 设置滤镜Listener
//     *
//     * @param listener
//     */
//    public void setFilterChangeListener(Edit.OnFilterChangeListener listener) {
//        mOnFilterChangeListener = listener;
//    }
//
//    /**
//     * 设置背景音Listener
//     *
//     * @param listener
//     */
//    public void setBGMChangeListener(Edit.OnBGMChangeListener listener) {
//        mOnBGMChangeListener = listener;
//        mBGMEditView.setIBGMPanelEventListener(listener);
//    }
//
//    /**
//     * 设置字幕Listener
//     *
//     * @param listener
//     */
//    public void setWordChangeListener(Edit.OnWordChangeListener listener) {
//        mOnWordChangeListener = listener;
//        mWordEditView.setIWordEventListener(listener);
//    }
//
//    public void setPasterChangeListener(Edit.OnPasterChangeListener listener) {
//        mPasterEditView.setOnPasterChangeListener(listener);
//    }
//
//    public void setMotionChangeListener(Edit.OnMotionChangeListener listener){
//        mMotionEditView.setOnMotionChangeListener(listener);
//    }
//
//    public void setBGMInfo(TCBGMInfo bgmInfo) {
//        mBGMEditView.setBGMInfo(bgmInfo);
//    }
//
//    /**
//     * 获取视频的裁剪起点
//     *
//     * @return
//     */
//    public long getSegmentFrom() {
//        return mCutView.getSegmentFrom();
//    }
//
//    /**
//     * 获取视频的裁剪终点
//     *
//     * @return
//     */
//    public long getSegmentTo() {
//        return mCutView.getSegmentTo();
//    }
//
//
//    public long getRepeatFrom() {
//        return mTimeEffectView.getRepeatFrom();
//    }
//
//    public long getRepeatTo() {
//        return mTimeEffectView.getRepeatTo();
//    }
//
//    /**
//     * 获取BGM的播放起点
//     *
//     * @return
//     */
//    public long getBGMSegmentFrom() {
//        return mBGMEditView.getSegmentFrom();
//    }
//
//    /**
//     * 获取BGM的播放终点
//     *
//     * @return
//     */
//    public long getBGMSegmentTo() {
//        return mBGMEditView.getSegmentTo();
//    }
//
//
//    /**
//     * 添加视频缩略图展示
//     *
//     * @param index
//     * @param bitmap
//     */
//    public void addBitmap(int index, Bitmap bitmap) {
//        mCutView.addBitmap(index, bitmap);
//        mTimeEffectView.addBitmap(index, bitmap);
//    }
//
//    public void clearAllBitmap() {
//        mCutView.clearAllBitmap();
//    }
//
//    public void setDuration(long duration) {
//        mCutView.setDuration(duration);
//        mTimeEffectView.setDuration(duration);
//    }
//
//    /**
//     * 选中滤镜
//     *
//     * @param index
//     */
//    private void selectFilter(int index) {
//        ViewGroup group = (ViewGroup) mFilterSV.getChildAt(0);
//        for (int i = 0; i < mFilterAdapter.getCount(); i++) {
//            View v = group.getChildAt(i);
//            ImageView IVTint = (ImageView) v.findViewById(R.id.filter_image_tint);
//            if (IVTint != null) {
//                if (i == index) {
//                    IVTint.setVisibility(View.VISIBLE);
//                } else {
//                    IVTint.setVisibility(View.INVISIBLE);
//                }
//            }
//        }
//    }
//
//    private static Bitmap decodeResource(Resources resources, int id) {
//        TypedValue value = new TypedValue();
//        resources.openRawResource(id, value);
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inTargetDensity = value.density;
//        return BitmapFactory.decodeResource(resources, id, opts);
//    }
//
//    private Bitmap getFilterBitmap(int index) {
//        Bitmap bmp = null;
//        switch (index) {
//            case 1:
//                bmp = decodeResource(getResources(), R.drawable.filter_langman);
//                break;
//            case 2:
//                bmp = decodeResource(getResources(), R.drawable.filter_qingxin);
//                break;
//            case 3:
//                bmp = decodeResource(getResources(), R.drawable.filter_weimei);
//                break;
//            case 4:
//                bmp = decodeResource(getResources(), R.drawable.filter_fennen);
//                break;
//            case 5:
//                bmp = decodeResource(getResources(), R.drawable.filter_huaijiu);
//                break;
//            case 6:
//                bmp = decodeResource(getResources(), R.drawable.filter_landiao);
//                break;
//            case 7:
//                bmp = decodeResource(getResources(), R.drawable.filter_qingliang);
//                break;
//            case 8:
//                bmp = decodeResource(getResources(), R.drawable.filter_rixi);
//                break;
//            default:
//                bmp = null;
//                break;
//        }
//        return bmp;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_time_effect:
//                changeToTimeEffectView();
//                break;
//            case R.id.btn_cut:
//                chagneToCutView();
//                break;
//            case R.id.btn_filter:
//                changeToFilterView();
//                break;
//            case R.id.btn_music:
//                changeToMusicView();
//                break;
//            case R.id.btn_word:
//                changeToWordView();
//                break;
//            case R.id.btn_paster:
//                changeToPasterView();
//                break;
//            case R.id.btn_motion_filter:
//                changeToMotionView();
//                break;
//        }
//    }
//
//    public void changeToNoTimeEffectView() {
//        changeToTimeEffectView();
//        mTimeEffectView.changeToNoEffectView();
//    }
//
//    private void changeToTimeEffectView() {
//        mTimeEffectLL.setVisibility(VISIBLE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(GONE);
//
//        mCutView.setCutChangeListener(null);
//        mTimeEffectView.setCutChangeListener(mOnCutChangeListener);
//        mTimeEffectView.setRepeatChangeListener(mRepeateChangeListener);
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_pressed);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//    }
//
//    private void changeToMotionView(){
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(VISIBLE);
//
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//        mMotion.setImageResource(R.drawable.ic_motion_pressed);
//    }
//
//    private void changeToPasterView() {
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(VISIBLE);
//        mMotionLL.setVisibility(GONE);
//
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster_pressed);
//        mMotion.setImageResource(R.drawable.ic_motion);
//    }
//
//    private void changeToWordView() {
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(VISIBLE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(GONE);
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word_press);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//        mMotion.setImageResource(R.drawable.ic_motion);
//    }
//
//    private void changeToMusicView() {
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(VISIBLE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(GONE);
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music_pressed);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//        mMotion.setImageResource(R.drawable.ic_motion);
//    }
//
//    private void changeToFilterView() {
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(GONE);
//        mFilterLL.setVisibility(VISIBLE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(GONE);
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful_press);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//        mMotion.setImageResource(R.drawable.ic_motion);
//    }
//
//    private void chagneToCutView() {
//        mTimeEffectLL.setVisibility(GONE);
//        mCutLL.setVisibility(VISIBLE);
//        mFilterLL.setVisibility(GONE);
//        mBgmLL.setVisibility(GONE);
//        mWordLL.setVisibility(GONE);
//        mPasterLL.setVisibility(GONE);
//        mMotionLL.setVisibility(GONE);
//
//        mTimeEffectBtn.setImageResource(R.drawable.ic_time_effect_normal);
//        mCutBtn.setImageResource(R.drawable.ic_cut_press);
//        mFilterBtn.setImageResource(R.drawable.ic_beautiful);
//        mBgmBtn.setImageResource(R.drawable.ic_music);
//        mWordBtn.setImageResource(R.drawable.ic_word);
//        mPasterBtn.setImageResource(R.drawable.ic_paster);
//        mMotion.setImageResource(R.drawable.ic_motion);
//
//        mCutView.setCutChangeListener(mOnCutChangeListener);
//        mTimeEffectView.setCutChangeListener(null);
//        mTimeEffectView.setRepeatChangeListener(null);
//        if (mOnCutChangeListener != null) {
//            mOnCutChangeListener.onCutClick();
//        }
//    }
//
//    public void setOnClickable(boolean onClickable) {
//        mTimeEffectLL.setClickable(onClickable);
//        mCutLL.setClickable(onClickable);
//        mFilterLL.setClickable(onClickable);
//        mBgmLL.setClickable(onClickable);
//        mWordLL.setClickable(onClickable);
//        mWordEditView.setOnClickable(onClickable);
//        mPasterLL.setClickable(onClickable);
//        mMotionLL.setClickable(onClickable);
//    }
//
//    public void setRevertClickListener(Edit.OnReverseChangeListener listener) {
//        mRevertClickListener = listener;
//        mTimeEffectView.setRevertChangeListener(listener);
//    }
//
//    public void setRepeatChangeListener(Edit.OnRepeatChangeListener listener) {
//        mRepeateChangeListener = listener;
//        mTimeEffectView.setRepeatChangeListener(listener);
//    }
//
//    public void setOriginChangeListener(Edit.OnOriginChangeListener listener) {
//        mOriginChangeListener = listener;
//        mTimeEffectView.setOriginChangeListener(listener);
//    }
//
//    public void setSpeedLevel(float speedLevel) {
//        mSpeedLevel = speedLevel;
//        mTimeEffectView.setSpeedLevel(speedLevel);
//    }
//
//    public void updateCutRange(long cutStartTime, long cutEndTime, int type) {
//        if (type == TCVideoEditerActivity.TYPE_CUT) {
//            mTimeEffectView.setCutRange(cutStartTime, cutEndTime);
//        } else if (type == TCVideoEditerActivity.TYPE_REPEAT) {
//            mCutView.setCutRange(cutStartTime, cutEndTime);
//        }
//    }
//}
