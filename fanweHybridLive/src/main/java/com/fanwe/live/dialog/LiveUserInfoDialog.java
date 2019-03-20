package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveAdminActivity;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.App_forbid_send_msgActModel;
import com.fanwe.live.model.App_set_adminActModel;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgLiveMsg;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;
import com.fanwe.live.view.CornerTextView;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Random;

import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-25 下午3:12:49 类说明
 */
public class LiveUserInfoDialog extends LiveBaseDialog {
    public static final int NO_TALKING_TIME = 120;// 禁言秒数
    public static final int NO_FORBID = 0;
    @ViewInject(R.id.tv_tipoff)
    private TextView tv_tipoff;// 举报

    @ViewInject(R.id.ll_close)
    private View ll_close;// 关闭

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;// 头像

    @ViewInject(R.id.iv_level)
    private ImageView iv_level;//认证图标

    @ViewInject(R.id.iv_pic)
    private ImageView iv_pic;// 贡献最多

    @ViewInject(R.id.ll_pic)
    private LinearLayout ll_pic;// 贡献最多

    @ViewInject(R.id.tv_nick_name)
    private TextView tv_nick_name;// 用户名

    @ViewInject(R.id.iv_global_male)
    private ImageView iv_global_male;// 性别

    @ViewInject(R.id.iv_rank)
    private ImageView iv_rank;// 等级

    @ViewInject(R.id.tv_number)
    private TextView tv_number;// 帐号

    @ViewInject(R.id.tv_city)
    private TextView tv_city;// 所在城市

    @ViewInject(R.id.ll_v_explain)
    private LinearLayout ll_v_explain;// 认证等级

    @ViewInject(R.id.tv_level_name)
    private TextView tv_level_name;// 认证等级

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;// 个人简介

    @ViewInject(R.id.tv_follow)
    private TextView tv_follow;// 关注人数

    @ViewInject(R.id.tv_fans)
    private TextView tv_fans;// 粉丝人数

    @ViewInject(R.id.tv_brick)
    private TextView tv_brick;// 黄钻数

    @ViewInject(R.id.tv_ticket)
    private TextView tv_ticket;// 钱票数

    @ViewInject(R.id.ll_btn)
    private LinearLayout ll_btn;// 按钮组

    @ViewInject(R.id.btn_follow)
    private Button btn_follow;

    @ViewInject(R.id.btn_letter)
    private Button btn_letter;

    @ViewInject(R.id.btn_reply)
    private Button btn_reply;

    @ViewInject(R.id.btn_mainpage)
    private Button btn_mainpage;
    @ViewInject(R.id.tv_add_memory)
    private TextView tv_add_memory;
    @ViewInject(R.id.tv_memory1)
    private CornerTextView tv_memory1;
    @ViewInject(R.id.tv_memory2)
    private CornerTextView tv_memory2;
    @ViewInject(R.id.tv_memory3)
    private CornerTextView tv_memory3;
    private String to_user_id;

    private String db_user_id;

    private String identifierCreater_id;

    private String group_id;

    private App_userinfoActModel app_userinfoActModel;

    public LiveUserInfoDialog(Activity activity, String to_user_id) {
        super(activity);
        init_id(to_user_id);
        init();
    }

    private void init_id(String user_id) {
        this.to_user_id = user_id;
        UserModel user = UserModelDao.query();
        if (user != null) {
            this.db_user_id = user.getUser_id();
        } else {
            SDToast.showToast("未找到本地用户信息");
        }
        identifierCreater_id = getLiveActivity().getCreaterId();
        if (TextUtils.isEmpty(identifierCreater_id)) {
            SDToast.showToast("主播ID不存在");
        }
        group_id = getLiveActivity().getGroupId();
        if (TextUtils.isEmpty(group_id)) {
            SDToast.showToast("群组group_id不存在");
        }
    }

    private void dropUser(String drop_userid) {
        CommonInterface.requestPrivateDropUser(getLiveActivity().getRoomId(), drop_userid, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("踢出直播间成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    //超管 禁言
    private void managerForbidMessage(String to_userid, String sconds) {
        CommonInterface.requestManagerForbidMessage(to_userid, getLiveActivity().getGroupId(), sconds, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("禁言成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    //超管 踢人
    private void managerDropUser(String drop_userid) {
        CommonInterface.requestManagerDropUser(getLiveActivity().getRoomId(), drop_userid, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("踢出直播间成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    //超管 封设备
    private void managerForbidDevice(String drop_userid) {
        CommonInterface.requestManagerForbidDevice(getLiveActivity().getRoomId() + "", new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("封设备成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    //超管 封号
    private void managerForbidAccount(String to_userid) {
        CommonInterface.requestManagerForbidAccount(to_userid, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("封号成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    //超管 关闭直播间
    private void managerCloseRoom() {
        CommonInterface.requestManagerCloseRoom(getLiveActivity().getRoomId(), new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("关闭直播间成功！");
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    private void init() {
        // 点击头像id和本地登录ID一样
        if (!TextUtils.isEmpty(to_user_id) && to_user_id.equals(db_user_id)) {
            // 点击头像id和主播ID一样
            if (to_user_id.equals(identifierCreater_id)) {
                setContentView(R.layout.dialog_user_info_page_three);
            } else
                setContentView(R.layout.dialog_user_info_page_two);
            {
            }
        } else {
            setContentView(R.layout.dialog_user_info_page_one);
        }

        setCanceledOnTouchOutside(true);
        paddingLeft(80);
        paddingRight(80);
        x.view().inject(this, getContentView());
        register();
        requestUserInfo();
    }

    private void register() {
        iv_head.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startMainPage();
            }
        });
        tv_tipoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app_userinfoActModel != null) {
                    if(1==UserModelDao.query().getIs_admin()&&!app_userinfoActModel.getUser().getUser_id().equals(UserModelDao.query().getUser_id())){
                        open_show_super_admin(app_userinfoActModel.getIs_forbid());
                    }
                    else if (app_userinfoActModel.getShow_tipoff() == 1) {
                        showTipoff_typeDialog();
                    } else if (app_userinfoActModel.getShow_admin() == 1 || app_userinfoActModel.getShow_admin() == 2 ) {
                        showManageDialog(app_userinfoActModel.getShow_admin(), app_userinfoActModel.getHas_admin(), app_userinfoActModel.getIs_forbid());
                    }
                }
            }

            private void showManageDialog(int show_admin, int has_admin, int is_forbid) {
                if (show_admin == 1) {
                    open_show_admin_1(is_forbid);
                } else if (show_admin == 2) {
                    open_show_admin_2(has_admin, is_forbid);
                }
            }
        });
        tv_add_memory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LiveAddMemoryDialog(getOwnerActivity(), to_user_id).show();
                dismiss();
            }
        });
        ll_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                requestFollow();
            }
        });

        btn_letter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LivePrivateChatDialog dialog = new LivePrivateChatDialog(getOwnerActivity(), to_user_id);
                dialog.showBottom();
                dismiss();
            }
        });

        btn_reply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (app_userinfoActModel != null) {
                    UserModel user = app_userinfoActModel.getUser();
                    if (user != null) {
                        String at = "@" + user.getNick_name() + "　";
                        getLiveActivity().openSendMsg(at);
                        dismiss();
                    }
                }
            }
        });

        btn_mainpage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startMainPage();
            }
        });
    }

    private void startMainPage() {
        if (app_userinfoActModel != null) {
            UserModel user = app_userinfoActModel.getUser();
            if (user != null) {
                Intent intent = new Intent(getOwnerActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, user.getUser_id());
                getOwnerActivity().startActivity(intent);
                dismiss();
            }
        }
    }

    private void bindData(App_userinfoActModel actModel) {
        UserModel to_user = actModel.getUser();
        if (to_user != null) {
            if (TextUtils.isEmpty(to_user.getUser_id())) {
                dealErrorInfo();
            } else if (!to_user.getUser_id().equals(to_user_id)) {
                dealErrorInfo();
            }

            if (!TextUtils.isEmpty(to_user.getV_explain())) {
                SDViewUtil.setVisible(ll_v_explain);
                SDViewBinder.setTextView(tv_level_name, to_user.getV_explain());
            } else {
                SDViewUtil.setGone(ll_v_explain);
            }

            GlideUtil.loadHeadImage(to_user.getHead_image()).into(iv_head);

            if (!TextUtils.isEmpty(to_user.getV_icon())) {
                GlideUtil.load(to_user.getV_icon()).into(iv_level);
            }


            SDViewBinder.setTextView(tv_nick_name, to_user.getNick_name(), "您未设置昵称");

            if (to_user.getSexResId() > 0) {
                iv_global_male.setImageResource(to_user.getSexResId());
            } else {
                SDViewUtil.setGone(iv_global_male);
            }
            if (null != actModel.getUser().getTags()) {
                updateSelectedTag(actModel.getUser().getTags());
            }

            iv_rank.setImageResource(to_user.getLevelImageResId());

            SDViewBinder.setTextView(tv_number, to_user.getShowId());

            SDViewBinder.setTextView(tv_city, to_user.getCity(), "难道在火星?");

            // SDViewBinder.setTextView(tv_level_name, model.getIdentifier());

            SDViewBinder.setTextView(tv_introduce, to_user.getSignature(), "TA好像忘记写签名了");

            SDViewBinder.setTextView(tv_follow, to_user.getFocus_count() + "");
            SDViewBinder.setTextView(tv_fans, to_user.getFans_count() + "");

            SDViewBinder.setTextView(tv_brick, to_user.getUse_diamonds() + "");

            SDViewBinder.setTextView(tv_ticket, to_user.getTicket() + "");
//            SDViewBinder.setTextView(tv_fans, LiveUtils.getFormatNumber(to_user.getFans_count()));
//
//            SDViewBinder.setTextView(tv_brick, LiveUtils.getFormatNumber(to_user.getUse_diamonds()));
//
//            SDViewBinder.setTextView(tv_ticket, LiveUtils.getFormatNumber(to_user.getTicket()));

        }
        // iv_pic
       model = actModel.getCuser();
        if (model != null) {
            GlideUtil.loadHeadImage(model.getHead_image()).into(iv_pic);
        }

        setBtnFollow(actModel.getHas_focus());
        if(1==UserModelDao.query().getIs_admin()&&!app_userinfoActModel.getUser().getUser_id().equals(UserModelDao.query().getUser_id())){
            SDViewBinder.setTextView(tv_tipoff, "管理");
        }
        else if (actModel.getShow_tipoff() == 1) {
            SDViewBinder.setTextView(tv_tipoff, "举报");
        } else if (actModel.getShow_admin() == 1 || actModel.getShow_admin() == 2) {
            SDViewBinder.setTextView(tv_tipoff, "管理");
        } else {
            SDViewBinder.setTextView(tv_tipoff, "");
            tv_tipoff.setOnClickListener(null);
        }
    }
    UserModel model;
    //随机产生颜色RGB
    public int generateRandomColorCode() {
        Random random = new Random();
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private void updateSelectedTag(List<UserModel.TagsBean> list) {
        if (list.size() <= 0) {
            tv_memory1.setVisibility(View.GONE);
            tv_memory2.setVisibility(View.GONE);
            tv_memory3.setVisibility(View.GONE);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                tv_memory1.setVisibility(View.VISIBLE);
                tv_memory1.setText(list.get(i).getTag_name());
                tv_memory1.setfilColor(generateRandomColorCode());
            } else if (i == 1) {
                tv_memory2.setVisibility(View.VISIBLE);
                tv_memory2.setText(list.get(i).getTag_name());
                tv_memory2.setfilColor(generateRandomColorCode());
            } else if (i == 2) {
                tv_memory3.setVisibility(View.VISIBLE);
                tv_memory3.setText(list.get(i).getTag_name());
                tv_memory3.setfilColor(generateRandomColorCode());
            }
        }
    }

    // 设置个人信息关注按钮
    private void setBtnFollow(int has_focus) {
        if (has_focus == 1) {
            btn_follow.setClickable(false);
            btn_follow.setText("已关注");
            btn_follow.setTextColor(SDResourcesUtil.getColor(R.color.text_gray));
        } else {
            btn_follow.setText("关注");
            btn_follow.setTextColor(SDResourcesUtil.getColor(R.color.main_color_second));
        }
    }
    // 弹出管理员权限底部框
    private void open_show_super_admin(int is_forbid) {
        String is_forbid_text;
        if (is_forbid == 1) {
            is_forbid_text = "取消禁言";
        } else {
            is_forbid_text = "禁言";
        }
        String[] arrOption = new String[]
                    {is_forbid_text, "踢出直播间", "封终端", "封号", "关闭直播间"};
        SDDialogMenu dialog = new SDDialogMenu(getOwnerActivity());

        dialog.setItems(arrOption);
        dialog.setmListener(new SDDialogMenuListener() {
            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog) {
                    switch (index) {
                        case 0:
                            if (app_userinfoActModel.getIs_forbid() == 1) {
                                requestforbid_send_msg(NO_FORBID);
                            } else
                                requestforbid_send_msg(NO_TALKING_TIME);
                            break;
                        case 1: // 禁言/取消禁言
                            managerDropUser(to_user_id);
                            break;
                        case 2://踢出直播间
                            managerForbidDevice(to_user_id);
                            break;
                        case 3://踢出直播间
                            managerForbidAccount(to_user_id);
                            break;
                        case 4://踢出直播间
                            if(to_user_id.equals(getLiveActivity().getCreaterId())){
                                managerCloseRoom();
                            }
                            break;
                        default:
                            break;
                    }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog) {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog) {
            }
        });
        dialog.showBottom();
    }
    // 弹出管理员权限底部框
    private void open_show_admin_1(int is_forbid) {
        String is_forbid_text;
        if (is_forbid == 1) {
            is_forbid_text = "取消禁言";
        } else {
            is_forbid_text = "禁言";
        }
        String[] arrOption = new String[]
                    {"举报", is_forbid_text, "踢出直播间"};
        SDDialogMenu dialog = new SDDialogMenu(getOwnerActivity());

        dialog.setItems(arrOption);
        dialog.setmListener(new SDDialogMenuListener() {
            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog) {
                    switch (index) {
                        case 0: // 举报
                            showTipoff_typeDialog();
                            break;
                        case 1: // 禁言/取消禁言
                            if (app_userinfoActModel.getIs_forbid() == 1) {
                                requestforbid_send_msg(NO_FORBID);
                            } else
                                requestforbid_send_msg(NO_TALKING_TIME);
                            break;
                        case 2://踢出直播间
                            dropUser(to_user_id);
                        default:
                            break;
                    }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog) {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog) {
            }
        });
        dialog.showBottom();
    }

    // 弹出主播权限底部框
    private void open_show_admin_2( int has_admin, int is_forbid) {
        String is_forbid_text;
        if (is_forbid == 1) {
            is_forbid_text = "取消禁言";
        } else {
            is_forbid_text = "禁言";
        }
        String[] arrOption;
         if (has_admin == 1) {
            arrOption = new String[]
                    {"取消管理员", "管理员列表", is_forbid_text, "踢出直播间"};
        } else {
            arrOption = new String[]
                    {"设置管理员", "管理员列表", is_forbid_text, "踢出直播间"};
        }

        SDDialogMenu dialog = new SDDialogMenu(getOwnerActivity());

        dialog.setItems(arrOption);
        dialog.setmListener(new SDDialogMenuListener() {
            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog) {
                    switch (index) {
                        case 0: // 设置管理员或者取消
                            requestset_admin();
                            break;
                        case 1: // 管理员列表
                            Intent intent = new Intent(getOwnerActivity(), LiveAdminActivity.class);
                            getOwnerActivity().startActivity(intent);
                            LiveUserInfoDialog.this.dismiss();
                            break;
                        case 2:// 禁言/取消禁言
                            if (app_userinfoActModel.getIs_forbid() == 1) {
                                requestforbid_send_msg(NO_FORBID);
                            } else
                                requestforbid_send_msg(NO_TALKING_TIME);
                            break;
                        case 3://踢出直播间
                            dropUser(to_user_id);
                            break;
                        default:
                            break;
                    }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog) {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog) {
            }
        });
        dialog.showBottom();
    }

    private void showTipoff_typeDialog() {
        dismiss();
        Intent intent = new Intent(getOwnerActivity(), LiveTipoffTypeActivity.class);
        intent.putExtra("roomid", getLiveActivity().getRoomId());
        intent.putExtra("to_user_id", to_user_id);
        getOwnerActivity().startActivity(intent);
    }

    private void requestUserInfo() {
        CommonInterface.requestUserInfo(getLiveActivity().getCreaterId(), to_user_id, getLiveActivity().getRoomId(), new AppRequestCallback<App_userinfoActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    app_userinfoActModel = actModel;
                    bindData(actModel);
                }
            }
        });
    }

    // 关注某人
    private void requestFollow() {
        CommonInterface.requestFollow(to_user_id, getLiveActivity().getRoomId(), new AppRequestCallback<App_followActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    setBtnFollow(actModel.getHas_focus());
                }
            }
        });
    }

    // 设置管理员
    private void requestset_admin() {
        CommonInterface.requestSet_admin(to_user_id, new AppRequestCallback<App_set_adminActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    final CustomMsgLiveMsg liveMsg = new CustomMsgLiveMsg();
                    String nickName = app_userinfoActModel.getUser().getNick_name();

                    if (actModel.getHas_admin() == 1) {
                        app_userinfoActModel.setHas_admin(1);
                        SDToast.showToast("设置管理员成功");
                        liveMsg.setDesc(nickName + " 被设置为管理员");
                    } else {
                        app_userinfoActModel.setHas_admin(0);
                        SDToast.showToast("取消管理员成功");
                        liveMsg.setDesc(nickName + " 管理员被取消");
                    }
                    final String groupId = getLiveActivity().getGroupId();
                    IMHelper.sendMsgGroup(groupId, liveMsg, new TIMValueCallBack<TIMMessage>() {
                        @Override
                        public void onError(int i, String s) {
                        }

                        @Override
                        public void onSuccess(TIMMessage timMessage) {
                            IMHelper.postMsgLocal(liveMsg, groupId);
                        }
                    });
                }
            }
        });
    }

    // 禁言
    private void requestforbid_send_msg(int second) {
        CommonInterface.requestForbidSendMsg(group_id, to_user_id, second, new AppRequestCallback<App_forbid_send_msgActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (app_userinfoActModel != null) {
                    app_userinfoActModel.setIs_forbid(actModel.getIs_forbid());
                }
                if (actModel.getStatus() == 1) {
                    if (actModel.getIs_forbid() == 0) {
                        SDToast.showToast("取消禁言成功！");
                    } else {
                        SDToast.showToast("禁言成功！");
                    }
                } else if (actModel.getStatus() == 0) {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    private void dealErrorInfo() {
        SDToast.showToast("服务器数据加载异常");
        iv_head.setOnClickListener(null);
        tv_tipoff.setOnClickListener(null);
        btn_follow.setOnClickListener(null);
        btn_letter.setOnClickListener(null);
        btn_mainpage.setOnClickListener(null);
        btn_reply.setOnClickListener(null);
    }
}
