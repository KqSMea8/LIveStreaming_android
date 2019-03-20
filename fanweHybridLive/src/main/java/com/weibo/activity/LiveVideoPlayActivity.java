package com.weibo.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.weibo.model.UpdateFavorite;
import com.weibo.model.XRAddVideoPlayCountResponseModel;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2017/10/23.
 */

public class LiveVideoPlayActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView mStartPreview,iv_back;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video_play);
        getIntentData();
        initData();
    }
    String url;
    int weibo_id;
    private void getIntentData()
    {
        mStartPreview=find(R.id.iv_status);
        iv_back=find(R.id.iv_back);
        videoView = (VideoView)findViewById(R.id.video_view);
        mStartPreview.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        url = getIntent().getStringExtra("video_url");
        weibo_id = getIntent().getIntExtra("weibo_id",0);
    }
    private void initData()
    {
        addPlaycount();
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mStartPreview.setBackgroundResource(R.drawable.icon_record_start);
                mStartPreview.setVisibility(View.VISIBLE);
                is_finish=true;
            }
        });
        videoView.setOnClickListener(this);
    }
    private void addPlaycount(){
        CommonInterface.requestAddVideoPlayCount(weibo_id, new AppRequestCallback<XRAddVideoPlayCountResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    UpdateFavorite updateFavorite=new UpdateFavorite();
                    updateFavorite.setWeibo_id(weibo_id);
                    updateFavorite.setVideo_count(Integer.parseInt(actModel.getVideo_count()));
                    updateFavorite.setType(UpdateFavorite.VIDEO_COUNT);
                    EventBus.getDefault().post(updateFavorite);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }
    boolean is_finish;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_status:
                videoView.start();
                mStartPreview.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.video_view:
                if(videoView.isPlaying()){
                    mStartPreview.setVisibility(View.VISIBLE);
                    mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
                }
                break;
        }
    }
}
