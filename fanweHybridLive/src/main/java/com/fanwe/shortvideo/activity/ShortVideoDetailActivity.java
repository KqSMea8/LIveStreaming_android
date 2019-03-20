package com.fanwe.shortvideo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.fragment.VideoDetailContainerFragment;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.weibo.model.UpdateFavorite;
import com.weibo.model.XRAddVideoPlayCountResponseModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;


public class ShortVideoDetailActivity extends BaseActivity implements VideoDetailContainerFragment.CallBackValue {
    private static String TAG = "ShortVideoDetailActivity";

    @ViewInject(R.id.vertical_viewpager)
    private VerticalViewPager mViewPager;
    private int mCurrentItem;
    public TXVodPlayer mVodPlayer;
    private TXCloudVideoView mVideoView;
    private RelativeLayout mRoomContainer;
    private ImageView mStartPreview;
    private PagerAdapter mPagerAdapter;
    private boolean mInit = false;
    private FrameLayout mFragmentContainer;
    private FragmentManager mFragmentManager;
    private ArrayList<String> mVideoIdList;
    private ArrayList<String> mVideoImgList;
    private VideoDetailContainerFragment mRoomFragment = VideoDetailContainerFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_short_video_detail);
        getIntentData();
        initView();
        initListener();
        mViewPager.setCurrentItem(mCurrentItem);
        requestData(mVideoIdList.get(mCurrentItem));

    }

    private void initView() {
        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_room_container, null);
        mStartPreview = (ImageView) mRoomContainer.findViewById(R.id.record_preview);
        mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
        mStartPreview.postDelayed(new Runnable() {
            @Override
            public void run() {
                mStartPreview.setVisibility(View.GONE);
            }
        }, 500);
        mVideoView = (TXCloudVideoView) mRoomContainer.findViewById(R.id.texture_view);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mFragmentManager = getSupportFragmentManager();

        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mPagerAdapter = new PagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentItem = position;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
                mStartPreview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mStartPreview.setVisibility(View.GONE);
                    }
                }, 500);
                has_add=false;
                requestData(mVideoIdList.get(position));
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup);
                }
            }
        });
    }

    boolean has_add;
    protected void requestData(final String svId) {
        CommonInterface.requestShortVideoDetails(svId, new AppRequestCallback<ShortVideoDetailModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    loadVideo(actModel.video.get(0).sv_url);
                    mRoomFragment.updateData(actModel.video.get(0));
                    if(!has_add){
                        addPlaycount(Integer.parseInt(svId));
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }

    /**
     * @param viewGroup
     */
    @SuppressLint("ResourceType")
    private void loadVideoAndChatRoom(ViewGroup viewGroup) {
        //聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
        viewGroup.addView(mRoomContainer);
    }

    private void loadVideo(String videoUrl) {
        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        //关键player对象与界面view
        mVodPlayer.setPlayerView(mVideoView);
        mVodPlayer.startPlay(videoUrl);
    }

    private void addPlaycount(int svId) {
        CommonInterface.requestAddVideo(svId, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onStart() {
                super.onStart();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    has_add=true;
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVodPlayer.resume();
        mVideoView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        mVideoView.onDestroy();
    }

    public void getIntentData() {
        mCurrentItem = getIntent().getIntExtra("position", 0);
        mVideoIdList = getIntent().getStringArrayListExtra("video_id_list");
        mVideoImgList = getIntent().getStringArrayListExtra("video_img_list");

    }

    @Override
    public void SendMessageValue() {
        if (mVodPlayer != null && mVodPlayer.isPlaying()) {
            mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
            mStartPreview.setBackgroundResource(R.drawable.icon_record_start);
            mStartPreview.setVisibility(View.VISIBLE);
        } else {
            requestData(mVideoIdList.get(mCurrentItem));
            mStartPreview.setBackgroundResource(R.drawable.icon_record_pause);
            mStartPreview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStartPreview.setVisibility(View.GONE);
                }
            }, 500);

        }

    }


    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return mVideoIdList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_room_item, null);
            ImageView anchor_img = (ImageView) view.findViewById(R.id.anchor_img);
            GlideUtil.load(mVideoImgList.get(position)).into(anchor_img);
            view.setId(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.findViewById(position));
        }
    }
}
