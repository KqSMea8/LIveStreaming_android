package com.fanwe.live.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.SDAppView;
import com.fanwe.library.view.select.SDSelectViewAuto;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

public class MultyTabView extends LinearLayout implements View.OnClickListener
{

	private TextView tv_tab;
	private LinearLayout ll_tab_bg;
	private View view_left1,view_left2;
	int type_count;
	public MultyTabView(Context context,int type_count)
	{
		super(context);
		init();
		this.type_count=type_count;
	}

	public MultyTabView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public int getType_count() {
		return type_count;
	}

	public void setType_count(int type_count) {
		this.type_count = type_count;
	}

	//0 弹幕  1 普通 2喇叭
	private final String[]tab_text=new String[]{"普通","弹幕","喇叭"};
	private final int[]res=new int[]{R.color.gray,R.color.edit_green,R.color.btn_red};
	int tab=0;
	protected void init()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.multytabview, this, true);
		tv_tab = (TextView) findViewById(R.id.tv_tab);
		ll_tab_bg = (LinearLayout) findViewById(R.id.ll_tab_bg);
		view_left1=findViewById(R.id.empty_left1);
		view_left2=findViewById(R.id.empty_left2);
		tv_tab.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(type_count==3){
			if(tab==0){
				view_left1.setVisibility(VISIBLE);
				view_left2.setVisibility(GONE);
				ll_tab_bg.setBackgroundResource(R.drawable.btn_corner_5dp_green);
				tab=1;
			}else if(tab==1){
				view_left1.setVisibility(VISIBLE);
				view_left2.setVisibility(VISIBLE);
				ll_tab_bg.setBackgroundResource(R.drawable.btn_corner_5dp_red);
				tab=2;
			}else if(tab==2){
				view_left1.setVisibility(GONE);
				view_left2.setVisibility(GONE);
				ll_tab_bg.setBackgroundResource(R.drawable.btn_corner_5dp_gray);
				tab=0;
			}
		}else if(type_count==2){
			if(tab==0){
				view_left1.setVisibility(VISIBLE);
				view_left2.setVisibility(VISIBLE);
				ll_tab_bg.setBackgroundResource(R.drawable.btn_corner_5dp_green);
				tab=1;
			}else if(tab==1){
				view_left1.setVisibility(GONE);
				view_left2.setVisibility(GONE);
				ll_tab_bg.setBackgroundResource(R.drawable.btn_corner_5dp_gray);
				tab=0;
			}
		}
		tv_tab.setTextColor(getResources().getColor(res[tab]));
		tv_tab.setText(tab_text[tab]);
		tabChangeListener.tabChange(tab);
	}

	public TabChangeListener getTabChangeListener() {
		return tabChangeListener;
	}

	public void setTabChangeListener(TabChangeListener tabChangeListener) {
		this.tabChangeListener = tabChangeListener;
	}

	TabChangeListener tabChangeListener;
	public interface TabChangeListener{
		void tabChange(int index);
	}
}
