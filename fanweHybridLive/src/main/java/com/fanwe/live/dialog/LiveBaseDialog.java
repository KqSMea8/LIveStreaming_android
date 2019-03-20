package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.ILiveActivity;

public class LiveBaseDialog extends SDDialogBase
{
    public LiveBaseDialog(Activity activity)
    {
        super(activity, R.style.dialogBase);
    }

    public ILiveActivity getLiveActivity()
    {
        if (getOwnerActivity() instanceof ILiveActivity)
        {
            return (ILiveActivity) getOwnerActivity();
        } else
        {
            return null;
        }
    }
}
