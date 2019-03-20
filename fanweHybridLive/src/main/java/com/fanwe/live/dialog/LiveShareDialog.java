package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;
import com.fanwe.live.view.LiveShareView;

/**
 * 分享窗口
 */
public class LiveShareDialog extends SDDialogBase
{
    private LiveShareView view_share;

    public LiveShareDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_share);
        paddings(0);
        setCanceledOnTouchOutside(true);
        view_share = (LiveShareView) findViewById(R.id.view_share);
        view_share.bindData(getOwnerActivity());
    }

    public void setClickListener(LiveShareView.ClickListener clickListener)
    {
        view_share.setClickListener(clickListener);
    }

}
