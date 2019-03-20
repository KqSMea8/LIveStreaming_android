package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveKSYSetBeautyFilterAdapter;
import com.fanwe.live.control.KSYPushSDK;
import com.fanwe.live.model.LiveKSYBeautyConfig;
import com.fanwe.live.model.LiveKSYBeautyFilterModel;

import java.util.List;

/**
 * 金山sdk设置美颜窗口
 */
public class LiveKSYSetBeautyDialog extends LiveBaseDialog
{

    private KSYPushSDK mPushSDK = KSYPushSDK.getInstance();
    private SDGridLinearLayout ll_beauty_filter;

    //磨皮
    private View ll_grind;
    private SeekBar sb_grind;
    private TextView tv_grind_percent;

    //美白
    private View ll_whiten;
    private SeekBar sb_whiten;
    private TextView tv_whiten_percent;

    //红润
    private View ll_ruddy;
    private SeekBar sb_ruddy;
    private TextView tv_ruddy_percent;

    private LiveKSYSetBeautyFilterAdapter mAdapter;

    private LiveKSYBeautyConfig mBeautyConfig = LiveKSYBeautyConfig.get();
    private LiveKSYBeautyFilterModel mCurrentBeautyFilter;

    public LiveKSYSetBeautyDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_ksy_set_beauty);
        paddings(0);
        setCanceledOnTouchOutside(true);

        //磨皮
        ll_grind = findViewById(R.id.ll_grind);
        sb_grind = (SeekBar) findViewById(R.id.sb_grind);
        tv_grind_percent = (TextView) findViewById(R.id.tv_grind_percent);

        //美白
        ll_whiten = findViewById(R.id.ll_whiten);
        sb_whiten = (SeekBar) findViewById(R.id.sb_whiten);
        tv_whiten_percent = (TextView) findViewById(R.id.tv_whiten_percent);

        //红润
        ll_ruddy = findViewById(R.id.ll_ruddy);
        sb_ruddy = (SeekBar) findViewById(R.id.sb_ruddy);
        tv_ruddy_percent = (TextView) findViewById(R.id.tv_ruddy_percent);

        ll_beauty_filter = (SDGridLinearLayout) findViewById(R.id.ll_beauty_filter);

        initSeekbar();
        initBeautyFilter();
    }

    private void initBeautyFilter()
    {
        ll_beauty_filter.setColNumber(3);
        List<LiveKSYBeautyFilterModel> listType = mBeautyConfig.getListFilter();

        int savedFilter = LiveKSYBeautyConfig.get().getBeautyFilter();
        for (LiveKSYBeautyFilterModel item : listType)
        {
            if (savedFilter == item.getBeautyFilter())
            {
                mCurrentBeautyFilter = item;
                break;
            }
        }

        mAdapter = new LiveKSYSetBeautyFilterAdapter(listType, getOwnerActivity());
        mAdapter.setItemClickCallback(new SDItemClickCallback<LiveKSYBeautyFilterModel>()
        {
            @Override
            public void onItemClick(int position, LiveKSYBeautyFilterModel item, View view)
            {
                selectBeautyFilter(item);
                mAdapter.getSelectManager().performClick(item);
            }
        });
        ll_beauty_filter.setAdapter(mAdapter);

        if (mCurrentBeautyFilter != null)
        {
            mAdapter.getSelectManager().setSelected(mCurrentBeautyFilter, true);
            selectBeautyFilter(mCurrentBeautyFilter);
        }
    }

    private void selectBeautyFilter(LiveKSYBeautyFilterModel item)
    {
        mCurrentBeautyFilter = item;
        mPushSDK.setBeautyFilter(item.getBeautyFilter());
        mBeautyConfig.setBeautyFilter(item.getBeautyFilter());

        //磨皮
        if (mPushSDK.isGrindSupported())
        {
            SDViewUtil.setVisible(ll_grind);
            int progress = item.getGrindProgress();
            if (progress >= 0)
            {
                mPushSDK.setGrindProgress(progress);
            } else
            {
                progress = mPushSDK.getGrindProgress();
                item.setGrindProgress(progress);
            }
            sb_grind.setProgress(progress);
            updateTextPercent(tv_grind_percent, progress);
        } else
        {
            SDViewUtil.setGone(ll_grind);
        }

        //美白
        if (mPushSDK.isWhitenSupported())
        {
            SDViewUtil.setVisible(ll_whiten);
            int progress = item.getWhitenProgress();
            if (progress >= 0)
            {
                mPushSDK.setWhitenProgress(progress);
            } else
            {
                progress = mPushSDK.getWhitenProgress();
                item.setWhitenProgress(progress);
            }
            sb_whiten.setProgress(progress);
            updateTextPercent(tv_whiten_percent, progress);
        } else
        {
            SDViewUtil.setGone(ll_whiten);
        }

        //红润
        if (mPushSDK.isRuddySupported())
        {
            SDViewUtil.setVisible(ll_ruddy);
            int progress = item.getRuddyProgress();
            if (progress >= 0)
            {
                mPushSDK.setRuddyProgress(progress);
            } else
            {
                progress = mPushSDK.getRuddyProgress();
                item.setRuddyProgress(progress);
            }
            sb_ruddy.setProgress(progress);
            updateTextPercent(tv_ruddy_percent, progress);
        } else
        {
            SDViewUtil.setGone(ll_ruddy);
        }
    }

    private void initSeekbar()
    {
        //磨皮
        sb_grind.setMax(100);
        sb_grind.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPushSDK.setGrindProgress(progress);
                mCurrentBeautyFilter.setGrindProgress(progress);
                updateTextPercent(tv_grind_percent, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        //美白
        sb_whiten.setMax(100);
        sb_whiten.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPushSDK.setWhitenProgress(progress);
                mCurrentBeautyFilter.setWhitenProgress(progress);
                updateTextPercent(tv_whiten_percent, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        //美白
        sb_ruddy.setMax(100);
        sb_ruddy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPushSDK.setRuddyProgress(progress);
                mCurrentBeautyFilter.setRuddyProgress(progress);
                updateTextPercent(tv_ruddy_percent, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    private void updateTextPercent(TextView textView, int percent)
    {
        textView.setText(String.valueOf(percent) + "%");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mBeautyConfig.save();
    }
}
