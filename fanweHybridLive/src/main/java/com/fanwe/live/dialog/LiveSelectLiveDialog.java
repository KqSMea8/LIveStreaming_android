package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.event.ESelectLiveFinish;
import com.fanwe.live.view.LiveSelectLiveView;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by HSH on 2016/7/25.
 */
public class LiveSelectLiveDialog extends LiveBaseDialog
{
    public LiveSelectLiveView hotView;

    public LiveSelectLiveDialog(Activity activity)
    {
        super(activity);

        init();
    }

    private void init()
    {
        hotView = new LiveSelectLiveView(getOwnerActivity());
        hotView.setListener(new LiveSelectLiveView.LiveTabHotViewListener()
        {
            @Override
            public void success(int sex, String city)
            {
                ESelectLiveFinish event = new ESelectLiveFinish();
                SDEventManager.post(event);
                dismiss();
            }
        });

        setContentView(hotView);
        setFullScreen();
        hotView.initSelected();
    }
}
