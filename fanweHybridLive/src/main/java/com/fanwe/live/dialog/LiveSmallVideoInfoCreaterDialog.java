package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.utils.GlideUtil;

public class LiveSmallVideoInfoCreaterDialog extends LiveBaseDialog
{

    private ImageView iv_head;
    private TextView tv_nickname;
    private View ll_close;
    private View view_close_video;

    private String userId;
    private ClickListener clickListener;

    public LiveSmallVideoInfoCreaterDialog(Activity activity, String userId)
    {
        super(activity);
        this.userId = userId;
        init();
    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_small_video_info_creater);

        iv_head = (ImageView) getContentView().findViewById(R.id.iv_head);
        tv_nickname = (TextView) getContentView().findViewById(R.id.tv_nickname);
        ll_close = getContentView().findViewById(R.id.ll_close);
        view_close_video = getContentView().findViewById(R.id.view_close_video);

        register();
        requestData();
    }

    private void register()
    {
        ll_close.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        view_close_video.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (clickListener != null)
                {
                    clickListener.onClickCloseVideo(v, userId);
                }
                dismiss();
            }
        });
    }

    private void requestData()
    {
        CommonInterface.requestUserInfo(getLiveActivity().getCreaterId(), userId, new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    bindData(actModel);
                }
            }
        });
    }

    protected void bindData(App_userinfoActModel actModel)
    {
        GlideUtil.loadHeadImage(actModel.getUser().getHead_image()).into(iv_head);
        SDViewBinder.setTextView(tv_nickname, actModel.getUser().getNick_name());
    }

    public interface ClickListener
    {
        void onClickCloseVideo(View v, String userId);
    }

}
