package com.fanwe.games.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

/**
 * 游戏胜利窗口
 */
public class GameWinDialog extends SDDialogBase
{
    private TextView tv_gain;
    private View iv_close;

    public GameWinDialog(Activity activity)
    {
        super(activity);

        setContentView(R.layout.dialog_game_win);
        paddingLeft(0).paddingRight(0);
        tv_gain = (TextView) findViewById(R.id.tv_gain);
        iv_close = findViewById(R.id.iv_close);

        iv_close.setOnClickListener(this);
    }

    public void setTextGain(String text)
    {
        tv_gain.setText(text);
    }

    @Override
    public void show()
    {
        super.show();
        startDismissRunnable(3000);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_close)
        {
            dismiss();
        }
    }
}
