package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabText;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.fragment.LiveUserHomeBaseFragment;
import com.fanwe.live.fragment.LiveUserHomeCenterFragment;
import com.fanwe.live.fragment.LiveUserHomeLeftFragment;
import com.fanwe.live.fragment.LiveUserHomeRightFragment;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.App_user_homeActModel;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.User_set_blackActModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;
import com.fanwe.live.view.SlideToBottomScrollView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-11 上午11:52:46 类说明
 */
public class LiveUserHomeActivity extends BaseActivity {
    public static final String EXTRA_USER_ID = "extra_user_id";
    public static final String EXTRA_USER_IMG_URL = "extra_user_img_url";
    /**
     * 家族列表跳转隐藏关注，拉黑，私信栏
     */
    public static final String EXTRA_FAMILY = "extra_family";

    @ViewInject(R.id.lsv)
    private SlideToBottomScrollView lsv;

    public SlideToBottomScrollView getLsv() {
        return lsv;
    }

    @ViewInject(R.id.ll_close)
    private View ll_close;

    @ViewInject(R.id.tab_left)
    private SDTabText tab_left;

    @ViewInject(R.id.tab_center)
    private SDTabText tab_center;

    @ViewInject(R.id.tab_right)
    private SDTabText tab_right;

    @ViewInject(R.id.tv_use_diamonds)
    private TextView tv_use_diamonds;// 消费秀豆

    @ViewInject(R.id.ll_user_id)
    private LinearLayout ll_user_id;

    @ViewInject(R.id.tv_user_id)
    private TextView tv_user_id;

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;// 头像

    @ViewInject(R.id.bg_img_head_bur)
    private ImageView bg_img_head_bur;// 头像

    @ViewInject(R.id.iv_level)
    private ImageView iv_level;// 等级

    @ViewInject(R.id.tv_nick_name)
    private TextView tv_nick_name;// 昵称

    @ViewInject(R.id.iv_global_male)
    private ImageView iv_global_male;

    @ViewInject(R.id.iv_rank)
    private ImageView iv_rank;// 等级

    @ViewInject(R.id.iv_vip)
    private ImageView iv_vip;

    @ViewInject(R.id.ll_my_focus)
    private LinearLayout ll_my_focus;// 关注布局

    @ViewInject(R.id.tv_focus_count)
    private TextView tv_focus_count;// 关注数量

    @ViewInject(R.id.ll_my_fans)
    private LinearLayout ll_my_fans;// 粉丝布局

    @ViewInject(R.id.tv_fans_count)
    private TextView tv_fans_count;// 粉丝数量

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;// 个人简介

    @ViewInject(R.id.tv_v_explain)
    private TextView tv_v_explain;// 认证信息
    @ViewInject(R.id.ll_v_explain)
    private LinearLayout ll_v_explain;

    @ViewInject(R.id.ll_follow)
    private LinearLayout ll_follow;// 关注
    @ViewInject(R.id.tv_follow)
    private TextView tv_follow;

    @ViewInject(R.id.ll_letter)
    private LinearLayout ll_letter;// 私信

    @ViewInject(R.id.ll_set_black)
    private LinearLayout ll_set_black;// 拉黑
    @ViewInject(R.id.tv_set_black)
    private TextView tv_set_black;

    @ViewInject(R.id.ll_function_layout)
    private LinearLayout ll_function_layout;

    @ViewInject(R.id.ll_broadcast_entrance)
    private View ll_broadcast_entrance;//正在直播入口
    @ViewInject(R.id.tv_broadcast_entrance)
    private TextView tv_broadcast_entrance;
    @ViewInject(R.id.iv_meda1)
    private ImageView iv_meda1;
    @ViewInject(R.id.iv_meda2)
    private ImageView iv_meda2;
    @ViewInject(R.id.iv_meda3)
    private ImageView iv_meda3;
    private SDSelectViewManager<SDTabText> mSelectManager = new SDSelectViewManager<SDTabText>();

    private int mSelectTabIndex = 0;

    private String user_id;

    private App_user_homeActModel app_user_homeActModel;

    private String familyStr;

    private boolean isSelf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_home);
        x.view().inject(this);
        init();
    }

    private void init() {
        getIntentExtra();
        register();
        setHideUserIdView();
        requestUser_home(false);
    }

    private void setHideUserIdView() {
        SDViewUtil.setGone(ll_user_id);
    }

    private void getIntentExtra() {
        user_id = getIntent().getStringExtra(EXTRA_USER_ID);
        if (TextUtils.isEmpty(user_id)) {
            user_id = "";
        }
        familyStr = getIntent().getStringExtra(EXTRA_FAMILY);
        if (TextUtils.isEmpty(familyStr)) {
            familyStr = "";
        }
    }

    private void register() {
        UserModel user = UserModelDao.query();
        if (user != null) {
            String localUserId = user.getUser_id();
            if (!TextUtils.isEmpty(localUserId)) {
                if (TextUtils.equals(user_id, localUserId)) {
                    isSelf = true;
                    SDViewUtil.setGone(ll_function_layout);
                }
            }
        }

        if (familyStr.equals(EXTRA_FAMILY)) {
            SDViewUtil.setGone(ll_function_layout);
        }

        ll_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_my_focus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (app_user_homeActModel != null && app_user_homeActModel.getUser() != null) {
                    UserModel userModel = app_user_homeActModel.getUser();
                    Intent intent = new Intent(LiveUserHomeActivity.this, LiveFollowActivity.class);
                    intent.putExtra(LiveMyFocusActivity.EXTRA_USER_ID, userModel.getUser_id());
                    LiveUserHomeActivity.this.startActivity(intent);
                }
            }
        });

        ll_my_fans.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (app_user_homeActModel != null && app_user_homeActModel.getUser() != null) {
                    UserModel userModel = app_user_homeActModel.getUser();
                    Intent intent = new Intent(LiveUserHomeActivity.this, LiveMyFocusActivity.class);
                    intent.putExtra(LiveMyFocusActivity.EXTRA_USER_ID, userModel.getUser_id());
                    LiveUserHomeActivity.this.startActivity(intent);
                }
            }
        });

        ll_follow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                requestFollow();
            }
        });
        ll_letter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (app_user_homeActModel != null) {
                    UserModel local_user = UserModelDao.query();
                    UserModel to_user = app_user_homeActModel.getUser();
                    if (local_user != null && to_user != null) {
                        Intent intent = new Intent(getActivity(), LivePrivateChatActivity.class);
                        intent.putExtra(LivePrivateChatActivity.EXTRA_USER_ID, to_user.getUser_id());
                        startActivity(intent);
                    }
                }
            }
        });
        ll_set_black.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                requestSet_black();
            }
        });
        iv_head.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (app_user_homeActModel != null && app_user_homeActModel.getUser() != null) {
                    Intent intent = new Intent(LiveUserHomeActivity.this, LiveUserHeadImageActivity.class);
                    intent.putExtra(EXTRA_USER_ID, app_user_homeActModel.getUser().getUser_id());
                    intent.putExtra(EXTRA_USER_IMG_URL, app_user_homeActModel.getUser().getHead_image());
                    LiveUserHomeActivity.this.startActivity(intent);
                }
            }
        });
        ll_broadcast_entrance.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入直播间
                joinRoom();
            }
        });
        //是否打开vip标识
//        if(AppRuntimeWorker.isOpenVip()) {
//            SDViewUtil.setVisible(iv_vip);
//        } else {
//            SDViewUtil.setGone(iv_vip);
//        }

        addTab();
    }

    private void addTab() {
        tab_left.setTextTitle("主页");
        tab_left.getViewConfig(tab_left.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_left.setTextSizeTitleSp(15);
        tab_center.setTextTitle("直播");
        tab_center.getViewConfig(tab_center.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_center.setTextSizeTitleSp(15);
        tab_right.setTextTitle("小视频");
        tab_right.getViewConfig(tab_right.mTv_title).setTextColorNormalResId(R.color.black).setTextColorSelectedResId(R.color.main_color);
        tab_right.setTextSizeTitleSp(15);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabText>() {

            @Override
            public void onNormal(int index, SDTabText item) {
            }

            @Override
            public void onSelected(int index, SDTabText item) {
                switch (index) {
                    case 0:
                        click0();
                        break;
                    case 1:
                        click1();
                        break;
                    case 2:
                        click2();
                        break;
                    default:
                        break;
                }
            }

        });

        mSelectManager.setItems(new SDTabText[]
                {tab_left, tab_center, tab_right});
    }

    private void joinRoom() {
        LiveRoomModel video = app_user_homeActModel.getVideo();
        if (video == null) {
            return;
        }
        AppRuntimeWorker.joinRoom(video, this);
    }

    /**
     * 主页
     */
    protected void click0() {
        if (app_user_homeActModel != null) {
            Bundle b = new Bundle();
            b.putSerializable(LiveUserHomeBaseFragment.EXTRA_OBJ, app_user_homeActModel);
            getSDFragmentManager().toggle(R.id.ll_content, null, LiveUserHomeLeftFragment.class, b);
        }
    }

    /**
     * 直播
     */
    protected void click1() {
        if (app_user_homeActModel != null) {
            Bundle b = new Bundle();
            b.putSerializable(LiveUserHomeBaseFragment.EXTRA_OBJ, app_user_homeActModel);
            getSDFragmentManager().toggle(R.id.ll_content, null, LiveUserHomeCenterFragment.class, b);
        }
    }
    /**
     * 直播
     */
    protected void click2() {
        if (app_user_homeActModel != null) {
            Bundle b = new Bundle();
            b.putSerializable(LiveUserHomeBaseFragment.EXTRA_OBJ, app_user_homeActModel);
            getSDFragmentManager().toggle(R.id.ll_content, null, LiveUserHomeRightFragment.class, b);
        }
    }

    private void requestFollow() {
        CommonInterface.requestFollow(user_id, 0, new AppRequestCallback<App_followActModel>() {

            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    // 已关注则刷新接口
                    if (actModel.getHas_focus() == 1) {
                        requestUser_home(true);
                    }
                    setIsFollow(actModel.getHas_focus());
                }
            }
        });
    }

    private void requestSet_black() {
        CommonInterface.requestSet_black(user_id, new AppRequestCallback<User_set_blackActModel>() {

            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    // 已拉黑则刷新接口
                    if (actModel.getHas_black() == 1) {
                        requestUser_home(true);
                    }
                    setIsSet_black(actModel.getHas_black());
                }
            }
        });
    }

    // 设置个人信息关注按钮
    private void setIsFollow(int has_focus) {
        if (has_focus == 1) {
            tv_follow.setText("已关注");
        } else {
            tv_follow.setText("关注");
        }
    }

    // 设置个人信息拉黑按钮
    private void setIsSet_black(int has_black) {
        if (has_black == 1) {
            // 拉黑则默认无法关注
            // ll_follow.setClickable(false);
            tv_set_black.setText("解除拉黑");
        } else {
            // ll_follow.setClickable(true);
            tv_set_black.setText("拉黑");
        }
    }

    // isRefresh是刷新就不切换Fragment
    private void requestUser_home(final boolean isRefresh) {
        CommonInterface.requestUser_home(user_id, new AppRequestCallback<App_user_homeActModel>() {

            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    app_user_homeActModel = actModel;

                    if (!isRefresh) {
                        mSelectManager.performClick(mSelectTabIndex);
                    }

                    setIsFollow(actModel.getHas_focus());
                    setIsSet_black(actModel.getHas_black());
                    bindData(actModel);
                }
            }
        });
    }

    private void bindData(App_user_homeActModel actModel) {
        UserModel user = actModel.getUser();
        if (user != null) {
            long use_diamonds = user.getUse_diamonds();
            String str_use_diamonds = String.valueOf(use_diamonds);
            SDViewBinder.setTextView(tv_use_diamonds, str_use_diamonds);
            GlideUtil.loadHeadImage(user.getHead_image()).into(iv_head);

            Glide.with(this).load(user.getHead_image()).bitmapTransform(new BlurTransformation(this.getActivity(), 20)).into(bg_img_head_bur);

            if (!TextUtils.isEmpty(user.getV_icon())) {
                GlideUtil.load(user.getV_icon()).into(iv_level);
            } else {
                iv_level.setVisibility(View.GONE);
            }

            SDViewBinder.setTextView(tv_nick_name, user.getNick_name());

            if (user.getSexResId() > 0) {
                SDViewUtil.setVisible(iv_global_male);
                iv_global_male.setImageResource(user.getSexResId());
            } else {
                SDViewUtil.setGone(iv_global_male);
            }

            iv_rank.setImageResource(user.getLevelImageResId());

            long focus_count = user.getFocus_count();
            String str_focus_count = String.valueOf(focus_count);
            SDViewBinder.setTextView(tv_focus_count, str_focus_count);

            long fans_count = user.getFans_count();
            String str_fans_count = String.valueOf(fans_count);
//            SDViewBinder.setTextView(tv_fans_count, LiveUtils.getFormatNumber(fans_count));
            SDViewBinder.setTextView(tv_fans_count, fans_count+"");
            SDViewBinder.setTextView(tv_introduce, user.getSignature(), "TA好像忘记写签名了");

            if (!TextUtils.isEmpty(user.getV_explain())) {
                SDViewUtil.setVisible(ll_v_explain);
                SDViewBinder.setTextView(tv_v_explain, user.getV_explain());
            } else {
                ll_v_explain.setVisibility(View.GONE);
            }

            if (!isSelf) {
                if (actModel.getVideo() != null) {
                    SDViewUtil.setVisible(ll_broadcast_entrance);
                    if (actModel.getVideo().getLive_in() == 1) {
                        tv_broadcast_entrance.setText("正在直播");
                    } else if (actModel.getVideo().getLive_in() == 3) {
                        tv_broadcast_entrance.setText("正在回播");
                    }
                } else
                    SDViewUtil.setInvisible(ll_broadcast_entrance);
            }

            if (user.getIs_vip() == 1) {
                SDViewUtil.setVisible(iv_vip);
//                GlideUtil.load(R.drawable.ic_is_vip).into(iv_vip);
            } else {
                SDViewUtil.setGone(iv_vip);
//                GlideUtil.load(R.drawable.ic_not_vip).into(iv_vip);
            }
        }

        if(null!=actModel.getUser().getMedals()){
            for(int i=0;i<actModel.getUser().getMedals().size();i++){
                switch (i){
                    case 0:
                        iv_meda1.setVisibility(View.VISIBLE);
                        GlideUtil.load(actModel.getUser().getMedals().get(i)).into(iv_meda1);
                        break;
                    case 1:
                        iv_meda2.setVisibility(View.VISIBLE);
                        GlideUtil.load(actModel.getUser().getMedals().get(i)).into(iv_meda2);
                        break;
                    case 2:
                        iv_meda3.setVisibility(View.VISIBLE);
                        GlideUtil.load(actModel.getUser().getMedals().get(i)).into(iv_meda3);
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestUser_home(true);
    }

}
