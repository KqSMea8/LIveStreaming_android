package com.fanwe.live.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.adapter.SDFragmentPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.event.EFinishAdImg;
import com.fanwe.live.fragment.LiveRankingBaseFragment;
import com.fanwe.live.fragment.LiveRankingContributionFragment;
import com.fanwe.live.fragment.LiveRankingMeritsFragment;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * 功德榜、贡献榜
 *
 * @author luodong
 * @date 2016-10-10
 */
public class LiveRankingActivity extends BaseActivity
{
	@ViewInject(R.id.rl_back)
	private View rl_back;
	@ViewInject(R.id.tab_rank_merits)
	private SDTabUnderline tab_rank_merits;
	@ViewInject(R.id.tab_rank_contribution)
	private SDTabUnderline tab_rank_contribution;

	@ViewInject(R.id.vpg_content)
	private SDViewPager vpg_content;

	private SparseArray<LiveRankingBaseFragment> arrFragment = new SparseArray<>();

	private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();

	public static final String EXTRA_USER_ID="user_id";
	public static final String EXTRA_RANKING_TYPE="rank_type";
	public static final String EXTRA_CONTRIBUTION_TOTAL="contribution_total";

	private String rankingType;
	@Override
	protected int onCreateContentView()
	{
		return R.layout.act_live_ranking;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		super.init(savedInstanceState);
		getIntentData();
		initSDViewPager();
		initTabs();
		setSelectTags();
		rl_back.setOnClickListener(this);
	}

	private void setSelectTags() {
		if (rankingType!=null){
			switch (rankingType){
				case LiveRankingActivity.EXTRA_CONTRIBUTION_TOTAL:
					vpg_content.setCurrentItem(1);
					break;
				default:
					break;
			}
		}
	}

	private void getIntentData() {
		rankingType = getIntent().getStringExtra(EXTRA_RANKING_TYPE);
	}

	private void initSDViewPager()
	{
		vpg_content.setOffscreenPageLimit(2);
		List<String> listModel = new ArrayList<>();
		listModel.add("");
		listModel.add("");

		vpg_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int position)
			{
				if (selectViewManager.getSelectedIndex() != position)
				{
					selectViewManager.setSelected(position, true);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});
		vpg_content.setAdapter(new LivePagerAdapter(listModel, LiveRankingActivity.this, getSupportFragmentManager()));

	}

	private class LivePagerAdapter extends SDFragmentPagerAdapter<String>
	{

		public LivePagerAdapter(List<String> listModel, Activity activity, FragmentManager fm)
		{
			super(listModel, activity, fm);
		}

		@Override
		public Fragment getItemFragment(int position, String model)
		{
			LiveRankingBaseFragment fragment = null;
			switch (position)
			{
				case 0:
					fragment = new LiveRankingMeritsFragment();
					break;
				case 1:
					fragment = new LiveRankingContributionFragment();
					if(rankingType!=null)
						fragment.setRankingType(rankingType);
					break;

				default:
					break;
			}
			arrFragment.put(position, fragment);
			return fragment;
		}
	}

	private void initTabs()
	{
		tab_rank_merits.setTextTitle(SDResourcesUtil.getString(R.string.live_ranking_tab_merits_text));
		tab_rank_merits.getViewConfig(tab_rank_merits.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
				.setBackgroundColorSelected(Color.TRANSPARENT);
		tab_rank_merits.getViewConfig(tab_rank_merits.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
				.setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18));

		tab_rank_contribution.setTextTitle(SDResourcesUtil.getString(R.string.live_ranking_tab_contribution_text));
		tab_rank_contribution.getViewConfig(tab_rank_contribution.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(Color.TRANSPARENT);
		tab_rank_contribution.getViewConfig(tab_rank_contribution.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.text_title_bar)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color))
				.setTextSizeNormal(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18)).setTextSizeSelected(SDResourcesUtil.getDimensionPixelSize(R.dimen.base_textsize_18));

		SDTabUnderline[] items = new SDTabUnderline[]{tab_rank_merits, tab_rank_contribution};

		selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
		{

			@Override
			public void onNormal(int index, SDTabUnderline item)
			{
			}

			@Override
			public void onSelected(int index, SDTabUnderline item)
			{
				switch (index)
				{
					case 0:
						clickTabMerits();
						break;
					case 1:
						clickTabContribution();
						break;

					default:
						break;
				}
			}
		});
		selectViewManager.setItems(items);
		selectViewManager.setSelected(0, true);

	}

	protected void clickTabMerits()
	{
		vpg_content.setCurrentItem(0);
	}

	protected void clickTabContribution()
	{
		vpg_content.setCurrentItem(1);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()){
			case R.id.rl_back:
				onSendEvent();
				finish();
				break;
		}
	}

	@Override
	public void onBackPressed()
	{
		onSendEvent();
		super.onBackPressed();
	}

	/**
	 * 发送关闭事件
	 */
	private void onSendEvent() {
		EFinishAdImg event = new EFinishAdImg();
		SDEventManager.post(event);
	}

}
