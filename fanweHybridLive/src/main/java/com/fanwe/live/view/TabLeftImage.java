package com.fanwe.live.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.select.SDSelectViewAuto;
import com.fanwe.live.R;

public class TabLeftImage extends SDSelectViewAuto
{

	public TextView mTvTitle;
	public ImageView mIvLeft;

	public TabLeftImage(Context context)
	{
		super(context);
		init();
	}

	public TabLeftImage(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	protected void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.view_tab_left_image, this, true);

		mTvTitle = (TextView) findViewById(R.id.tv_title);
		mIvLeft = (ImageView) findViewById(R.id.iv_left);
		addAutoView(mIvLeft, mTvTitle);

		setDefaultConfig();
		onNormal();
	}

	@Override
	public void setDefaultConfig()
	{
		getViewConfig(mTvTitle).setTextColorNormal(Color.GRAY).setTextColorSelected(getLibraryConfig().getColorMain());
		getViewConfig(mIvLeft).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(getLibraryConfig().getColorMain());
		super.setDefaultConfig();
	}

	public void setTextTitle(String title)
	{
		SDViewBinder.setTextView(mTvTitle, title);
	}

}
