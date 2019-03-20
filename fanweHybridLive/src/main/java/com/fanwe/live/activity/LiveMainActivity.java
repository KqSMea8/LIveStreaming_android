package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.service.AppUpgradeHelper;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LevelLoginFirstDialog;
import com.fanwe.live.dialog.LevelUpgradeDialog;
import com.fanwe.live.event.EIMLoginError;
import com.fanwe.live.event.EImOnForceOffline;
import com.fanwe.live.event.EReSelectTabLiveBottom;
import com.fanwe.live.fragment.LiveTabLiveFragment;
import com.fanwe.live.fragment.LiveTabMeNewFragment;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.LiveTabMainMenuView;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

public class LiveMainActivity extends BaseActivity
{
    @ViewInject(R.id.view_tab_live)
    private LiveTabMainMenuView view_tab_live;

    @ViewInject(R.id.iv_tab_create_live)
    private View view_tab_create_live;

    @ViewInject(R.id.view_tab_me)
    private LiveTabMainMenuView view_tab_me;

    private SDSelectViewManager<LiveTabMainMenuView> selectViewManager = new SDSelectViewManager<LiveTabMainMenuView>();

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_main;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        mIsExitApp = true;
        checkUpdate();
        AppRuntimeWorker.startContext();
        CommonInterface.requestUser_apns(null);
        CommonInterface.requestMyUserInfo(null);

        checkVideo();

        initTabs();

        initUpgradeDialog();
        initLoginfirstDialog();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        initUpgradeDialog();
        initLoginfirstDialog();
    }

    private void initUpgradeDialog()
    {
        LevelUpgradeDialog.check(this);
    }

    //首次登陆奖励和升级提示
    private void initLoginfirstDialog()
    {
        LevelLoginFirstDialog.check(this);
    }

    private void checkUpdate()
    {
        new AppUpgradeHelper(this).check(0);
    }

    private void initTabs()
    {

        view_tab_create_live.setOnClickListener(this);


        selectViewManager.setReSelectCallback(new SDSelectManager.ReSelectCallback<LiveTabMainMenuView>()
        {
            @Override
            public void onSelected(int index, LiveTabMainMenuView item)
            {
                EReSelectTabLiveBottom event = new EReSelectTabLiveBottom();
                event.index = index;
                SDEventManager.post(event);
            }
        });
        selectViewManager.addSelectCallback(new SDSelectViewManager.SelectCallback<LiveTabMainMenuView>()
        {

            @Override
            public void onNormal(int index, LiveTabMainMenuView item)
            {
                switch (index)
                {
                    case 0:
                        item.iv_tab_image.setImageResource(R.drawable.ic_live_tab_live_normal);
                        break;

                    case 1:
                        item.iv_tab_image.setImageResource(R.drawable.ic_live_tab_me_normal);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSelected(int index, LiveTabMainMenuView item)
            {
                switch (index)
                {
                    case 0:
                        clickTabLive();
                        item.iv_tab_image.setImageResource(R.drawable.ic_live_tab_live_selected);
                        break;
                    case 1:
                        clickTabMe();
                        item.iv_tab_image.setImageResource(R.drawable.ic_live_tab_me_selected);
                        break;

                    default:
                        break;
                }
            }
        });

        LiveTabMainMenuView[] items = new LiveTabMainMenuView[]{view_tab_live, view_tab_me};
        selectViewManager.setItems(items);

        selectViewManager.performClick(0);
    }

    @Override
    public void onClick(View v)
    {
        if (v == view_tab_create_live)
        {
            clickTabCreateLive();
        }
        super.onClick(v);
    }

    protected void clickTabLive()
    {
        getSDFragmentManager().toggle(R.id.fl_main_content, null, LiveTabLiveFragment.class);
    }

    protected void clickTabMe()
    {
        getSDFragmentManager().toggle(R.id.fl_main_content, null, LiveTabMeNewFragment.class);
    }

    private void clickTabCreateLive()
    {
        if (AppRuntimeWorker.isLogin(this))
        {
            final UserModel userModel = UserModelDao.query();
            if (userModel.getIs_agree() == 1)
            {
                Intent intent = new Intent(this, LiveCreateRoomActivity.class);
                startActivity(intent);
            } else
            {
                Intent intent = new Intent(this, LiveCreaterAgreementActivity.class);
                startActivity(intent);
            }
        }
    }

    public void onEventMainThread(EIMLoginError event)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        String content = "登录IM失败";
        if (!TextUtils.isEmpty(event.errMsg))
        {
            content = content + (event.errCode + event.errMsg);
        }
        dialog.setTextContent(content).setTextCancel("退出").setTextConfirm("重试");
        dialog.setCallback(new SDDialogCustom.SDDialogCustomCallback()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                App.getApplication().logout(false);
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                AppRuntimeWorker.startContext();
            }
        }).show();
    }

    /**
     * 异地登录
     *
     * @param event
     */
    public void onEventMainThread(EImOnForceOffline event)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextContent("你的帐号在另一台手机上登录");
        dialog.setTextCancel("退出");
        dialog.setTextConfirm("重新登录");
        dialog.setCallback(new SDDialogCustom.SDDialogCustomCallback()
        {
            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                AppRuntimeWorker.startContext();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                App.getApplication().logout(true);
            }
        });
        dialog.show();
    }
}
