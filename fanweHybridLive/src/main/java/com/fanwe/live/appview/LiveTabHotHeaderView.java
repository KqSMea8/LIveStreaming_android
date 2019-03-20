package com.fanwe.live.appview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fanwe.baimei.adapter.BMHomeLiveCenterTabAdapter;
import com.fanwe.baimei.model.BMHomeLiveCenterTabModel;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.customview.SDSlidingPlayView;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewSizeLocker;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveTabHotBannerPagerAdapter;
import com.fanwe.live.adapter.LiveTabRocketAdapter;
import com.fanwe.live.adapter.LiveViewerListRecyclerAdapter;
import com.fanwe.live.dialog.LiveSelectLiveDialog;
import com.fanwe.live.event.ESelectLiveFinish;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.model.LiveTopicInfoModel;
import com.fanwe.live.model.RocketModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LiveTabHotHeaderView extends BaseAppView
{

    private static final String STRING_MORE = "[更多]";
    private static final String STRING_BACK = "[收回]";

    //百媚==================================================
    private static final int SHOW_NUM = 5;
    //百媚==================================================


    private View ll_topic_info;
    private ImageView iv_head;
    private TextView tv_topic;
    private TextView tv_desc;

    //百媚==================================================
    private HorizontalScrollView hsv_tab_game;
    private GridView grid_tab_game;
    //百媚==================================================

    private View ll_change_city;
    private TextView tv_city;
    private SDSlidingPlayView spv_content;
    private List<LiveBannerModel> listModel = new ArrayList<>();
    private LiveTabHotBannerPagerAdapter adapter;
    private List<BMHomeLiveCenterTabModel> mListModelCenter = new ArrayList<BMHomeLiveCenterTabModel>();

    private SDRecyclerView hlv_rocket;
    private LiveTabRocketAdapter adapter_rocket;

    private BMHomeLiveCenterTabAdapter mAdapterCenter;

    private boolean showAllDescMode = false;
    private String strDesc;
    private SDViewSizeLocker sizeLocker;
    private int lockHeight;
    public SDSlidingPlayView getSlidingPlayView()
    {
        return spv_content;
    }

    public LiveTabHotHeaderView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTabHotHeaderView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabHotHeaderView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_tab_hot_header);
        hlv_rocket=find(R.id.hlv_rocket);

        hlv_rocket.setLinearHorizontal();
        hlv_rocket.addItemDecoration(new SpaceItemDecoration(8));
        adapter_rocket = new LiveTabRocketAdapter(getActivity());
        hlv_rocket.setAdapter(adapter_rocket);

        spv_content = (SDSlidingPlayView) findViewById(R.id.spv_content);
        ll_topic_info = findViewById(R.id.ll_topic_info);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

        ll_topic_info.setVisibility(GONE);
        //百媚==================================================
        hsv_tab_game= (HorizontalScrollView) findViewById(R.id.hsv_tab_game);
        grid_tab_game = (GridView) findViewById(R.id.grid_tab_game);
        //百媚==================================================
        hsv_tab_game.setVisibility(GONE);
        ll_change_city = findViewById(R.id.ll_change_city);
        tv_city = (TextView) findViewById(R.id.tv_city);
        spv_content.setNormalImageResId(R.drawable.ic_point_normal_dark);
        spv_content.setSelectedImageResId(R.drawable.ic_point_selected_main_color);
        ll_change_city.setVisibility(GONE);

        adapter = new LiveTabHotBannerPagerAdapter(listModel, getActivity());
        spv_content.setAdapter(adapter);

        //百媚==================================================
        mAdapterCenter = new BMHomeLiveCenterTabAdapter(mListModelCenter, getActivity());
        grid_tab_game.setAdapter(mAdapterCenter);
        //百媚==================================================

        sizeLocker = new SDViewSizeLocker(spv_content);

        ll_change_city.setOnClickListener(this);
        updateCity();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == ll_change_city)
        {
            LiveSelectLiveDialog selectDialog = new LiveSelectLiveDialog(getActivity());
            selectDialog.showBottom();
        }
    }
    //刷新火箭榜
    public void updataRocket(List<RocketModel> rocket_list){
        int rocket_size=0;
        rocket_size=rocket_list.size();
        do{
            rocket_list.add(new RocketModel("虚位以待"));
            rocket_size++;
        }while(rocket_size<5);
        adapter_rocket.updateData(rocket_list);
    }
    //刷新火箭榜
    public void initRocket(){
        List<RocketModel> rocket_list=new ArrayList<>();
        int rocket_size=0;
        do{
            rocket_list.add(new RocketModel("虚位以待"));
            rocket_size++;
        }while(rocket_size<5);
        adapter_rocket.updateData(rocket_list);
    }
    public void onEventMainThread(ESelectLiveFinish event)
    {
        updateCity();
    }

    private void updateCity()
    {
        String city = SDConfig.getInstance().getString(R.string.config_live_select_city, "");
        if (TextUtils.isEmpty(city))
        {
            city = "热门";
        }
        SDViewBinder.setTextView(tv_city, city);
    }

    public void setBannerItemClickCallback(SDItemClickCallback<LiveBannerModel> callback)
    {
        adapter.setItemClickCallback(callback);
    }

    public void setTopicInfoModel(final LiveTopicInfoModel model)
    {
        showAllDescMode = false;
        strDesc = null;
        if (model != null)
        {
            SDViewUtil.setVisible(ll_topic_info);
            GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head);
            SDViewBinder.setTextView(tv_topic, model.getTitle());
            iv_head.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                    intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, model.getUser_id());
                    intent.putExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL, model.getHead_image());
                    getActivity().startActivity(intent);
                }
            });
            strDesc = model.getDesc();
            if (TextUtils.isEmpty(strDesc))
            {
                SDViewUtil.setGone(tv_desc);
            } else
            {
                SDViewUtil.setVisible(tv_desc);

                postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setShowAllDescMode(false);
                    }
                }, 100);
            }
        } else
        {
            SDViewUtil.setGone(ll_topic_info);
        }
    }

    public void setShowAllDescMode(boolean showAllDescMode)
    {
        this.showAllDescMode = showAllDescMode;

        float length = SDViewUtil.measureText(tv_desc, strDesc);
        float maxLength = tv_desc.getWidth() * 2;
        float clickLength = SDViewUtil.measureText(tv_desc, STRING_MORE);
        if (length > maxLength)
        {
            SDSpannableStringBuilder sb = new SDSpannableStringBuilder();
            int color = SDResourcesUtil.getColor(R.color.main_color);
            ForegroundColorSpan span = new ForegroundColorSpan(color);

            if (showAllDescMode)
            {
                sb.append(strDesc);
                sb.appendSpan(span, STRING_BACK);
            } else
            {
                CharSequence ellipsizeDesc = TextUtils.ellipsize(strDesc, tv_desc.getPaint(), maxLength - clickLength, TextUtils.TruncateAt.END);
                sb.append(ellipsizeDesc);
                sb.appendSpan(span, STRING_MORE);
            }
            sb.coverSpan(span, clickableSpan);

            tv_desc.setText(sb);
        } else
        {
            tv_desc.setText(strDesc);
        }
    }

    private ClickableSpan clickableSpan = new ClickableSpan()
    {
        @Override
        public void updateDrawState(TextPaint ds)
        {
        }

        @Override
        public void onClick(View widget)
        {
            setShowAllDescMode(!showAllDescMode);
        }
    };


    public void setListLiveBannerModel(List<LiveBannerModel> listModel)
    {
        loadFirstImage(listModel);
        adapter.updateData(listModel);

        if (!adapter.getData().isEmpty())
        {
            SDViewUtil.setVisible(spv_content);
            spv_content.startPlay(5 * 1000);
        } else
        {
            SDViewUtil.setGone(spv_content);
        }
    }

    private void loadFirstImage(List<LiveBannerModel> listModel)
    {
        if (!SDCollectionUtil.isEmpty(listModel))
        {
            LiveBannerModel model = listModel.get(0);
            GlideUtil.load(model.getImage()).asBitmap().into(new SimpleTarget<Bitmap>()
            {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
                {
                    int height = SDViewUtil.getScaleHeight(resource.getWidth(), resource.getHeight(), SDViewUtil.getScreenWidth());
                    if (height > 0)
                    {
                        if (lockHeight != height)
                        {
                            lockHeight = height;
                            sizeLocker.lockHeight(lockHeight);
                        }
                    }
                }
            });
        }
    }


    @Override
    protected void onDetachedFromWindow()
    {
        spv_content.stopPlay();
        super.onDetachedFromWindow();
    }
    //==============================================百魅
    public void setGameTabData(List<BMHomeLiveCenterTabModel> tag_list)
    {
        if (SDCollectionUtil.isEmpty(tag_list))
        {
            SDViewUtil.setGone(hsv_tab_game);
            return;
        }

        //如果新列表和旧列表数量相同则不重新设置布局参数
        if (mListModelCenter.size() != tag_list.size())
        {
            this.mListModelCenter = tag_list;
            int showNum;
            if (mListModelCenter.size() <= SHOW_NUM)
            {
                showNum = mListModelCenter.size();
            } else
            {
                showNum = SHOW_NUM;
            }

            int itemWidth = SDViewUtil.getScreenWidth() / showNum;
            int gridWith = itemWidth * mListModelCenter.size();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWith
                    , LayoutParams.MATCH_PARENT);
            grid_tab_game.setLayoutParams(params);
            grid_tab_game.setColumnWidth(itemWidth); // 设置列表项宽
            grid_tab_game.setHorizontalSpacing(0); // 设置列表项水平间距
            grid_tab_game.setStretchMode(GridView.NO_STRETCH);
            grid_tab_game.setNumColumns(mListModelCenter.size()); // 设置列数量=列表集合数
        } else
        {
            this.mListModelCenter = tag_list;
        }

        mAdapterCenter.updateData(mListModelCenter);
    }
    //==============================================百魅
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
        int space;
        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
        }
    }
}
