package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.business.FocusClickListener;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * 关注窗口
 */
public class LiveShowFocusDialog extends LiveBaseDialog
{
    public FocusClickListener listener;
    private ImageView imageView,iv_rank,iv_global_male,iv_focus;
    private TextView tv_name;
    public FocusClickListener getListener() {
        return listener;
    }

    public void setListener(FocusClickListener listener) {
        this.listener = listener;
    }
    private void requestUserInfo()
    {
        CommonInterface.requestUserInfo(getLiveActivity().getCreaterId(), to_user_id, getLiveActivity().getRoomId(), new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    UserModel to_user = actModel.getUser();
                    if (to_user.getSexResId() > 0)
                    {
                        iv_global_male.setImageResource(to_user.getSexResId());
                    } else
                    {
                        SDViewUtil.setGone(iv_global_male);
                    }
                    iv_rank.setImageResource(to_user.getLevelImageResId());
                    tv_name.setText(to_user.getNick_name());
                    GlideUtil.loadHeadImage(to_user.getHead_image()).into(imageView);
                }
            }
        });
    }
    String to_user_id;
    public LiveShowFocusDialog(Activity activity,String to_user_id,final FocusClickListener listener)
    {
        super(activity);
        init();
        this.to_user_id=to_user_id;
        this.listener=listener;
        imageView= (ImageView) findViewById(R.id.iv_head);
        iv_global_male= (ImageView) findViewById(R.id.iv_global_male);
        iv_focus= (ImageView) findViewById(R.id.iv_focus);
        iv_rank= (ImageView) findViewById(R.id.iv_rank);
        tv_name= (TextView) findViewById(R.id.tv_nick_name);
        iv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.focusClick();
            }
        });
        requestUserInfo();
    }
    private void init()
    {
        setContentView(R.layout.dialog_show_focus);
        setCanceledOnTouchOutside(true);
        setGrativity(Gravity.BOTTOM);
        padding(0,0,0,0);
    }
}
