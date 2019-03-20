package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by luodong on 2016/10/13.
 */
public abstract class LevelBaseDialog extends SDDialogBase
{
    @ViewInject(R.id.iv_tip_image)
    protected ImageView iv_tip_image;
    @ViewInject(R.id.tv_title)
    protected TextView tv_title;
    @ViewInject(R.id.tv_content)
    protected TextView tv_content;
    @ViewInject(R.id.btn_confirm)
    protected Button btn_confirm;

    public LevelBaseDialog(Activity activity) {
        super(activity);
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_level_base);
        paddings(0);
        x.view().inject(this, getContentView());
        register();
    }

    private void register() {
        btn_confirm.setOnClickListener(this);
    }

    protected void setImageResource(int resId) {
        iv_tip_image.setImageResource(resId);
    }

    public void setTitle(String text) {
        tv_title.setText(text);
    }

    public void setContent(String text) {
        tv_content.setText(text);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_confirm:
                dismiss();
                break;
        }
    }

}
