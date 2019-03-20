package com.fanwe.baimei.appview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fanwe.baimei.util.ViewUtil;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.model.InitUpgradeModel;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 包名 com.fanwe.baimei.appview
 * 描述 主页底部导航
 * 作者 Su
 * 创建时间 2017/5/15 9:16
 **/
public class BMHomeBottomNavigationView extends BaseAppView {
    private LinearLayout mTabLiveCenterImageView, mTabGameCenterImageView, mLiveImageView, mTabAttentionImageView, mTabPersonCenterImageView;
    private SDSelectManager<LinearLayout> mSelectManager;
    private BMHomeBottomNavigationViewCallback mCallback;
    private Context context;


    public BMHomeBottomNavigationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initBMHomeBottomNavigationView(context);
    }

    public BMHomeBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initBMHomeBottomNavigationView(context);
    }

    public BMHomeBottomNavigationView(Context context) {
        super(context);
        this.context = context;
        initBMHomeBottomNavigationView(context);
    }

    private void initBMHomeBottomNavigationView(Context context) {
        setContentView(R.layout.bm_view_bottom_navigation_home);

        getTabLiveCenterImageView().setOnClickListener(this);
        getLiveImageView().setOnClickListener(this);
        getTabGameCenterImageView().setOnClickListener(this);
        getTabPersonCenterImageView().setOnClickListener(this);
        getTabAttentionCenterImageView().setOnClickListener(this);
        getSelectManager().setItems(new ArrayList<LinearLayout>(Arrays.asList(getTabLiveCenterImageView(), getTabAttentionCenterImageView(), getTabGameCenterImageView(), getTabPersonCenterImageView())));
        getSelectManager().setSelected(getTabLiveCenterImageView(), true);
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null) {
            InitUpgradeModel model = initActModel.getVersion();
            if (model != null) {
                if (SDTypeParseUtil.getInt(model.getServerVersion())==20170101){
                    getTabAttentionCenterImageView().setVisibility(GONE);
                    getTabGameCenterImageView().setVisibility(GONE);
                }else {
                    getTabAttentionCenterImageView().setVisibility(VISIBLE);
                    getTabGameCenterImageView().setVisibility(VISIBLE);
                }
            }
        }
    }

    private LinearLayout getTabLiveCenterImageView() {
        if (mTabLiveCenterImageView == null) {
            mTabLiveCenterImageView = (LinearLayout) findViewById(R.id.lv_tab_live_center);
        }
        return mTabLiveCenterImageView;
    }

    private LinearLayout getTabGameCenterImageView() {
        if (mTabGameCenterImageView == null) {
            mTabGameCenterImageView = (LinearLayout) findViewById(R.id.lv_game_center);
        }
        return mTabGameCenterImageView;
    }

    private LinearLayout getLiveImageView() {
        if (mLiveImageView == null) {
            mLiveImageView = (LinearLayout) findViewById(R.id.lv_tab_live);
        }
        return mLiveImageView;
    }

    private LinearLayout getTabPersonCenterImageView() {
        if (mTabPersonCenterImageView == null) {
            mTabPersonCenterImageView = (LinearLayout) findViewById(R.id.lv_person_center);
        }
        return mTabPersonCenterImageView;
    }

    private LinearLayout getTabAttentionCenterImageView() {
        if (mTabAttentionImageView == null) {
            mTabAttentionImageView = (LinearLayout) findViewById(R.id.lv_tab_attention);
        }
        return mTabAttentionImageView;
    }

    private SDSelectManager<LinearLayout> getSelectManager() {
        if (mSelectManager == null) {
            mSelectManager = new SDSelectManager<LinearLayout>();
            mSelectManager.setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);

            mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<LinearLayout>() {
                @Override
                public void onNormal(int index, LinearLayout item) {
                    //  item.setSelected(false);
                    if (item == getTabLiveCenterImageView()) {
                        item.findViewById(R.id.iv_tab_live_center).setSelected(false);
                        ((TextView) item.findViewById(R.id.tv_tab_live_center)).setTextColor(context.getResources().getColor(R.color.text_gray));
                    } else if (item == getTabGameCenterImageView()) {
                        item.findViewById(R.id.iv_tab_game_center).setSelected(false);
                        ((TextView) item.findViewById(R.id.tv_tab_game_center)).setTextColor(context.getResources().getColor(R.color.text_gray));
                    } else if (item == getTabAttentionCenterImageView()) {
                        item.findViewById(R.id.iv_tab_attention).setSelected(false);
                        ((TextView) item.findViewById(R.id.tv_tab_attention)).setTextColor(context.getResources().getColor(R.color.text_gray));
                    } else if (item == getTabPersonCenterImageView()) {
                        item.findViewById(R.id.iv_tab_person_center).setSelected(false);
                        ((TextView) item.findViewById(R.id.tv_tab_person_center)).setTextColor(context.getResources().getColor(R.color.text_gray));
                    }
                }

                @Override
                public void onSelected(int index, LinearLayout item) {
                    //  item.setSelected(true);

                    if (item == getTabLiveCenterImageView()) {
                        item.findViewById(R.id.iv_tab_live_center).setSelected(true);
                        ((TextView) item.findViewById(R.id.tv_tab_live_center)).setTextColor(context.getResources().getColor(R.color.main_color));
                        getCallback().onTabHomeSelected(item, index);
                    } else if (item == getTabGameCenterImageView()) {
                        item.findViewById(R.id.iv_tab_game_center).setSelected(true);
                        ((TextView) item.findViewById(R.id.tv_tab_game_center)).setTextColor(context.getResources().getColor(R.color.main_color));
                        getCallback().onTabGameSelected(item, index);
                    } else if (item == getTabAttentionCenterImageView()) {
                        item.findViewById(R.id.iv_tab_attention).setSelected(true);
                        ((TextView) item.findViewById(R.id.tv_tab_attention)).setTextColor(context.getResources().getColor(R.color.main_color));
                        getCallback().onTabAttentionSelected(item, index);
                    } else if (item == getTabPersonCenterImageView()) {
                        item.findViewById(R.id.iv_tab_person_center).setSelected(true);
                        ((TextView) item.findViewById(R.id.tv_tab_person_center)).setTextColor(context.getResources().getColor(R.color.main_color));
                        getCallback().onTabPersonSelected(item, index);
                    }
                }
            });
        }
        return mSelectManager;
    }

    public void setTabSelected(int index, boolean selected) {
        getSelectManager().setSelected(index, selected);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v == getTabLiveCenterImageView()) {
            getSelectManager().setSelected(getTabLiveCenterImageView(), true);
        } else if (v == getLiveImageView()) {
            getCallback().onLiveClick(v);
        } else if (v == getTabGameCenterImageView()) {
            getSelectManager().setSelected(getTabGameCenterImageView(), true);
        } else if (v == getTabPersonCenterImageView()) {
            getSelectManager().setSelected(getTabPersonCenterImageView(), true);
        } else if (v == getTabAttentionCenterImageView()) {
            getSelectManager().setSelected(getTabAttentionCenterImageView(), true);
        }

    }

    private BMHomeBottomNavigationViewCallback getCallback() {
        if (mCallback == null) {
            mCallback = new BMHomeBottomNavigationViewCallback() {
                @Override
                public void onTabHomeSelected(View v, int index) {

                }

                @Override
                public void onTabGameSelected(View v, int index) {

                }

                @Override
                public void onTabAttentionSelected(View v, int index) {

                }

                @Override
                public void onTabPersonSelected(View v, int index) {

                }

                @Override
                public void onLiveClick(View v) {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMHomeBottomNavigationViewCallback mCallback) {
        this.mCallback = mCallback;
    }

    public interface BMHomeBottomNavigationViewCallback {
        void onTabHomeSelected(View v, int index);

        void onTabGameSelected(View v, int index);

        void onTabAttentionSelected(View v, int index);

        void onTabPersonSelected(View v, int index);

        void onLiveClick(View v);
    }
}
