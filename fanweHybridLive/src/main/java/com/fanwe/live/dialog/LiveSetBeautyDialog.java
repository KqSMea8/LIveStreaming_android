package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveSetBeautyFilterAdapter;
import com.fanwe.live.control.IPushSDK;
import com.fanwe.live.control.LivePushSDK;
import com.fanwe.live.model.LiveBeautyConfig;
import com.fanwe.live.model.LiveBeautyFilterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置美颜窗口
 */
public class LiveSetBeautyDialog extends LiveBaseDialog
{

    //美颜
    private SeekBar sb_beauty;
    private TextView tv_beauty_percent;

    //美白
    private SeekBar sb_whiten;
    private TextView tv_whiten_percent;

    private SDGridLinearLayout ll_filter;
    private LiveSetBeautyFilterAdapter filterAdapter;

    private IPushSDK mPushSDK = LivePushSDK.getInstance();
    private LiveBeautyConfig mBeautyConfig = LiveBeautyConfig.get();

    public LiveSetBeautyDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_set_beauty);
        setCanceledOnTouchOutside(true);
        paddings(0);

        sb_beauty = (SeekBar) getContentView().findViewById(R.id.sb_beauty);
        tv_beauty_percent = (TextView) getContentView().findViewById(R.id.tv_beauty_percent);

        sb_whiten = (SeekBar) getContentView().findViewById(R.id.sb_whiten);
        tv_whiten_percent = (TextView) getContentView().findViewById(R.id.tv_whiten_percent);

        ll_filter = (SDGridLinearLayout) findViewById(R.id.ll_filter);

        float widthTvPercent = SDViewUtil.measureText(tv_beauty_percent, "100%");
        SDViewUtil.setWidth(tv_beauty_percent, (int) widthTvPercent);
        SDViewUtil.setWidth(tv_whiten_percent, (int) widthTvPercent);

        initSeekBar();
        initBeautyFilter();
    }

    private void initSeekBar()
    {
        //美颜
        int beautyProgress = mBeautyConfig.getBeautyProgress();
        updateTextPercent(tv_beauty_percent, beautyProgress);
        sb_beauty.setMax(100);
        sb_beauty.setProgress(beautyProgress);
        sb_beauty.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPushSDK.setBeautyProgress(progress);
                mBeautyConfig.setBeautyProgress(progress);
                updateTextPercent(tv_beauty_percent, progress);
            }
        });

        //美白
        int whitenProgress = mBeautyConfig.getWhitenProgress();
        updateTextPercent(tv_whiten_percent, whitenProgress);
        sb_whiten.setMax(100);
        sb_whiten.setProgress(whitenProgress);
        sb_whiten.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPushSDK.setWhitenProgress(progress);
                mBeautyConfig.setWhitenProgress(progress);
                updateTextPercent(tv_whiten_percent, progress);
            }
        });
    }

    private void updateTextPercent(TextView textView, int percent)
    {
        textView.setText(String.valueOf(percent) + "%");
    }

    private void initBeautyFilter()
    {
        ll_filter.setColNumber(3);

        List<LiveBeautyFilterModel> listFilter = new ArrayList<>();

        LiveBeautyFilterModel none = new LiveBeautyFilterModel();
        none.setName("无滤镜");

        LiveBeautyFilterModel langman = new LiveBeautyFilterModel();
        langman.setName("浪漫");
        langman.setBeautyFilter(R.drawable.live_filter_langman);

        LiveBeautyFilterModel qingxin = new LiveBeautyFilterModel();
        qingxin.setName("清新");
        qingxin.setBeautyFilter(R.drawable.live_filter_qingxin);

        LiveBeautyFilterModel weimei = new LiveBeautyFilterModel();
        weimei.setName("唯美");
        weimei.setBeautyFilter(R.drawable.live_filter_weimei);

        LiveBeautyFilterModel fennen = new LiveBeautyFilterModel();
        fennen.setName("粉嫩");
        fennen.setBeautyFilter(R.drawable.live_filter_fennen);

        LiveBeautyFilterModel huaijiu = new LiveBeautyFilterModel();
        huaijiu.setName("怀旧");
        huaijiu.setBeautyFilter(R.drawable.live_filter_huaijiu);

        LiveBeautyFilterModel landiao = new LiveBeautyFilterModel();
        landiao.setName("蓝调");
        landiao.setBeautyFilter(R.drawable.live_filter_landiao);

        LiveBeautyFilterModel qingliang = new LiveBeautyFilterModel();
        qingliang.setName("清凉");
        qingliang.setBeautyFilter(R.drawable.live_filter_qingliang);

        LiveBeautyFilterModel rixi = new LiveBeautyFilterModel();
        rixi.setName("日系");
        rixi.setBeautyFilter(R.drawable.live_filter_rixi);

        listFilter.add(none);
        listFilter.add(langman);
        listFilter.add(qingxin);
        listFilter.add(weimei);
        listFilter.add(fennen);
        listFilter.add(huaijiu);
        listFilter.add(landiao);
        listFilter.add(qingliang);
        listFilter.add(rixi);

        filterAdapter = new LiveSetBeautyFilterAdapter(listFilter, getOwnerActivity());
        filterAdapter.setItemClickCallback(new SDItemClickCallback<LiveBeautyFilterModel>()
        {
            @Override
            public void onItemClick(int position, LiveBeautyFilterModel item, View view)
            {
                int beautyFilter = item.getBeautyFilter();
                mPushSDK.setBeautyFilter(beautyFilter);
                mBeautyConfig.setBeautyFilter(beautyFilter);
            }
        });
        ll_filter.setAdapter(filterAdapter);

        int savedBeautyFilter = LiveBeautyConfig.get().getBeautyFilter();
        int index = 0;
        for (LiveBeautyFilterModel item : listFilter)
        {
            if (item.getBeautyFilter() == savedBeautyFilter)
            {
                break;
            }
            index++;
        }
        filterAdapter.getSelectManager().setSelected(index, true);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mBeautyConfig.save();
    }
}
