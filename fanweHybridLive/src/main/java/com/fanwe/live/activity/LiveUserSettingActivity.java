package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.service.AppUpgradeHelper;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;

import static com.fanwe.live.common.AppRuntimeWorker.getDistribution_module;

/**
 * Created by Administrator on 2016/7/11.
 */
public class LiveUserSettingActivity extends BaseTitleActivity
{

    @ViewInject(R.id.ll_account_and_safety)
    private LinearLayout ll_account_and_safety;
    @ViewInject(R.id.ll_black_list)
    private LinearLayout ll_black_list;
    @ViewInject(R.id.ll_push_manage)
    private LinearLayout ll_push_manage;
    @ViewInject(R.id.ll_recommend)
    private LinearLayout ll_recommend;
    @ViewInject(R.id.ll_help_and_feedback)
    private LinearLayout ll_help_and_feedback;
    @ViewInject(R.id.ll_update)
    private LinearLayout ll_update;
    @ViewInject(R.id.ll_about_us)
    private LinearLayout ll_about_us;
    @ViewInject(R.id.tv_logout)
    private TextView tv_logout;
    @ViewInject(R.id.tv_version_name)
    private TextView tv_version_name;

    @ViewInject(R.id.tv_start_test_activity)
    private TextView tv_start_test_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_user_setting);
        initView();
    }

    private void initView()
    {
        initTitle();
        ll_account_and_safety.setOnClickListener(this);
        ll_black_list.setOnClickListener(this);
        ll_push_manage.setOnClickListener(this);
        ll_recommend.setOnClickListener(this);
        if (getDistribution_module() == 1)
        {
            SDViewUtil.setVisible(ll_recommend);
        } else
        {
            SDViewUtil.setGone(ll_recommend);
        }
        if (AppRuntimeWorker.getIsOpenWebviewMain())
        {
            SDViewUtil.setGone(tv_logout);
        }

        ll_help_and_feedback.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        ll_about_us.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

        getLocalVersion();
        setDebugThings();
    }

    private void setDebugThings()
    {
        if (ApkConstant.DEBUG)
        {
            tv_start_test_activity.setOnClickListener(this);
            SDViewUtil.setVisible(tv_start_test_activity);
        }
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("设置");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    /**
     * 获取本地版本号
     */
    private void getLocalVersion()
    {
        tv_version_name.setText(SDPackageUtil.getVersionName());
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ll_account_and_safety:
                openAccountCenter();
                break;
            case R.id.ll_black_list:
                openBlackList();
                break;
            case R.id.ll_push_manage:
                openPushManage();
                break;
            case R.id.ll_recommend:
                clickllRecommend();
                break;
            case R.id.ll_help_and_feedback:
                openHelpAndFeedback();
                break;
            case R.id.ll_update:
                checkVersion();
                break;
            case R.id.ll_about_us:
                openAboutUs();
                break;
            case R.id.tv_logout:
                clickLogout();
                break;
            case R.id.tv_start_test_activity:
                clickTvStartTestActivity();
                break;
            default:
                break;
        }
    }

    private void clickLogout()
    {
        App.getApplication().logout(true);
    }

    private void openAboutUs()
    {
        Intent intent = new Intent(this, LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_about_us());
        startActivity(intent);
    }

    private void openHelpAndFeedback()
    {
        Intent intent = new Intent(this, LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_help_feedback());
        startActivity(intent);
    }

    /**
     * 打开账号安全中心
     */
    private void openAccountCenter()
    {
        Intent intent = new Intent(this, LiveAccountCenterActivity.class);
        startActivity(intent);
    }

    /**
     * 前往黑名单界面
     */
    private void openBlackList()
    {
        Intent intent = new Intent(this, LiveBlackListActivity.class);
        startActivity(intent);
    }

    /**
     * 检查版本
     */
    private void checkVersion()
    {
        new AppUpgradeHelper(this).check(1);
    }

    /**
     * 打开推送管理界面
     */
    private void openPushManage()
    {
        Intent intent = new Intent(this, LivePushManageActivity.class);
        startActivity(intent);
    }

    /**
     * 打开推荐人页面
     */
    private void clickllRecommend()
    {
        Intent intent = new Intent(this, LiveRecommendActivity.class);
        startActivity(intent);
    }

    /*测试页面*/
    private void clickTvStartTestActivity()
    {

    }
}
