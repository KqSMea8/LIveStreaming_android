package com.fanwe.hybrid.dialog;

import com.fanwe.live.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * 
 * @author yhz
 * @create time 2014-11-17
 */
public class BasePopupBottomView extends PopupWindow
{
	protected Activity mActivity;
	protected LayoutInflater mInflater;

	public BasePopupBottomView(Activity activity)
	{
		super(activity);
		this.mActivity = activity;
		this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void setContentView(View contentView)
	{
		super.setContentView(contentView);
		baseinit();
	}

	private void baseinit()
	{
		// 设置SelectPicPopupWindow弹出窗体可点击
		setFocusable(true);
		setmBackgroundDrawable(null);
	}

	// 设置SelectPicPopupWindow弹出窗体动画效果
	public void setmAnimationStyle(int style)
	{
		if (style <= 0)
		{
			this.setAnimationStyle(R.style.path_popwindow_anim_enterorout_window);
		} else
		{
			this.setAnimationStyle(style);
		}
	}

	// 设置SelectPicPopupWindow弹出窗体的背景
	public void setmBackgroundDrawable(Drawable drawable)
	{
		if (drawable == null)
		{
			ColorDrawable dw = new ColorDrawable(R.color.half_trans_color);
			this.setBackgroundDrawable(dw);
		} else
		{
			this.setBackgroundDrawable(drawable);
		}

	}

	public void initPopupView(final View mMenuView)
	{
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		setmAnimationStyle(0);
		baseinit();
	}

}
