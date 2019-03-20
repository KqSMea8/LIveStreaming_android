package com.fanwe.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.RelativeLayout;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTopicListAdapter;
import com.fanwe.live.adapter.LiveUserModelAdapter;
import com.fanwe.live.fragment.LiveBaseSearchFragment;
import com.fanwe.live.fragment.LiveSearchTopicFragment;
import com.fanwe.live.fragment.LiveSearchUserFragment;
import com.fanwe.live.model.LiveTopicModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.LiveSongSearchView;
import com.fanwe.live.view.LiveSongSearchView.SearchViewListener;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-17 上午9:23:16 类说明
 */
public class LiveSearchUserActivity extends BaseActivity implements SearchViewListener {

    public static final String TAG = "LiveSearchUserActivity";

    @ViewInject(R.id.rl_back)
    private RelativeLayout rl_back;
    @ViewInject(R.id.sv_song)
    private LiveSongSearchView sv_song;

    @ViewInject(R.id.vpg_content)
    private SDViewPager vpg_content;
    @ViewInject(R.id.tab_live_user)
    private SDTabUnderline tab_live_user;
    @ViewInject(R.id.tab_live_topic)
    private SDTabUnderline tab_live_topic;

    private SDSelectViewManager<SDTabUnderline> selectViewManager;
    private SparseArray<LiveBaseSearchFragment> arrFragment;

    private LiveSearchUserFragment fragUser;
    private LiveSearchTopicFragment fragTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_search);
        init();
    }

    private void init() {
        fragUser = new LiveSearchUserFragment();
        fragTopic = new LiveSearchTopicFragment();
        selectViewManager = new SDSelectViewManager<>();
        arrFragment = new SparseArray<>();
        arrFragment.put(0,fragUser);
        arrFragment.put(1,fragTopic);
        rl_back.setOnClickListener(this);
        sv_song.setSearchViewListener(this);
        sv_song.getEtInput().setHint("搜索用户或话题");
        fragTopic.setOnTopicClickListener(new LiveTopicListAdapter.TopicClickListener() {
            @Override
            public void onTopicClick(LiveTopicModel model) {
                Intent intent = new Intent(getApplicationContext(), LiveTopicRoomActivity.class);
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_ID, model.getCate_id());
                intent.putExtra(LiveTopicRoomActivity.EXTRA_TOPIC_TITLE, model.getTitle());
                startActivity(intent);
            }
        });
        fragUser.setOnItemClickListener(new LiveUserModelAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(UserModel user, int position) {
                if (user != null) {
                    Intent intent = new Intent(LiveSearchUserActivity.this, LiveUserHomeActivity.class);
                    intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, user.getUser_id());
                    startActivity(intent);
                }
            }
        });
        initSDViewPager();
        initTabs();
    }
    private void initSDViewPager() {
        vpg_content.setOffscreenPageLimit(1);
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");

        vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                selectViewManager.performClick(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        vpg_content.setAdapter(new LivePagerAdapter(listModel, this, getSupportFragmentManager()));

    }
    private void initTabs() {
        tab_live_user.setTextTitle("用户");
        tab_live_user.getViewConfig(tab_live_user.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_live_user.getViewConfig(tab_live_user.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_gray)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color));

        tab_live_topic.setTextTitle("话题");
        tab_live_topic.getViewConfig(tab_live_topic.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_live_topic.getViewConfig(tab_live_topic.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_gray)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color));

        SDTabUnderline[] items = new SDTabUnderline[]{tab_live_user, tab_live_topic};

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>() {

            @Override
            public void onNormal(int index, SDTabUnderline item) {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item) {
                switch (index) {
                    case 0:
                        vpg_content.setCurrentItem(0);
                        onSearch(getText());
                        break;
                    case 1:
                        vpg_content.setCurrentItem(1);
                        onSearch(getText());
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }

    private class LivePagerAdapter extends SDFragmentPagerAdapter<String> {

        public LivePagerAdapter(List<String> listModel, Activity activity, FragmentManager fm) {
            super(listModel, activity, fm);
        }

        @Override
        public Fragment getItemFragment(int position, String model) {
            return arrFragment.get(position);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    private String getText() {
        return sv_song.getEtInput().getText().toString();
    }

    @Override
    public void onRefreshAutoComplete(String text) {
        arrFragment.get(vpg_content.getCurrentItem()).search(text);
    }

    @Override
    public void onSearch(String text) {
        arrFragment.get(vpg_content.getCurrentItem()).search(text);
    }
}
