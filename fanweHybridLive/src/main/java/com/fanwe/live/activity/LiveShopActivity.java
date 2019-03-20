package com.fanwe.live.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.fanwe.baimei.fragment.BMPopularityFragment;
import com.fanwe.baimei.fragment.BMTyrantFragment;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.fragment.LiveShopMyPackFragment;
import com.fanwe.live.fragment.LiveShopStoreFragment;
import com.fanwe.live.fragment.LiveTabLiveFragment;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-12 下午8:33:19 类说明
 */
public class LiveShopActivity extends BaseTitleActivity {
    @ViewInject(R.id.tab_popularity)
    private SDTabText tab_popularity;
    @ViewInject(R.id.tab_tyrant)
    private SDTabText tab_tyrant;
    @ViewInject(R.id.fl_content_anchor)
    private ViewPager fl_content_anchor;
    @ViewInject(R.id.fragment_bmp)
    private BMPopularityFragment fragment_bmp;
    @ViewInject(R.id.fragment_bmt)
    private BMTyrantFragment fragment_bmt;
    @ViewInject(R.id.lv_bmp)
    private LinearLayout lv_bmp;
    @ViewInject(R.id.lv_bmt)
    private LinearLayout lv_bmt;
    private Context context;
    private Fragment fragment;
    private LiveTabLiveFragment fragmentManager;
    private SDSelectViewManager<SDTabText> selectViewManager = new SDSelectViewManager<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_shop);
        x.view().inject(this);
        init();
    }
    private void initTabs()
    {
        tab_popularity.setTextTitle("坐骑商城");
        tab_popularity.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_popularity.getViewConfig(tab_popularity.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        tab_tyrant.mTv_title.setPadding(SDViewUtil.dp2px(20), SDViewUtil.dp2px(2), SDViewUtil.dp2px(20), SDViewUtil.dp2px(2));
        tab_tyrant.setTextTitle("我的背包");
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setBackgroundNormal(SDResourcesUtil.getDrawable(R.drawable.bm_bg_no_color_corner_5dp))
                .setBackgroundSelected(SDResourcesUtil.getDrawable(R.drawable.bm_bg_main_color_corner_5dp));
        tab_tyrant.getViewConfig(tab_tyrant.mTv_title).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.white))
                .setTextSizeNormal(SDViewUtil.dp2px(13)).setTextSizeSelected(SDViewUtil.dp2px(13));

        SDTabText[] items = new SDTabText[]{tab_popularity, tab_tyrant};

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabText>() {

            @Override
            public void onNormal(int index, SDTabText item) {
            }

            @Override
            public void onSelected(int index, SDTabText item) {
                switch (index) {
                    case 0:
                        clickTabShopList();
                        break;
                    case 1:
                        clickTabMyPack();
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);
    }
    private void init() {
        mTitle.setMiddleTextTop("商城");
        initTabs();

    }protected void clickTabShopList()
    {
        lv_bmp.setVisibility(View.VISIBLE);
        lv_bmt.setVisibility(View.GONE);
    }

    protected void clickTabMyPack()
    {
        lv_bmt.setVisibility(View.VISIBLE);
        lv_bmp.setVisibility(View.GONE);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fg=null;

            if(position==0){
                fg= new LiveShopStoreFragment();

            } else if (position==1) {
                fg = new LiveShopMyPackFragment();
            }
            return fg;
        }
        @Override
        public int getCount() {
            return  2;
        }
    }
}
