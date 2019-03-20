package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EIsFinishLiveSociatyDetails;
import com.fanwe.live.model.App_sociaty_indexActModel;
import com.fanwe.live.model.App_sociaty_user_logoutActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 公会详情
 * Created by Administrator on 2016/9/27.
 */

public class LiveSociatyDetailsActivity extends BaseTitleActivity
{
    @ViewInject(R.id.iv_head_img)
    private CircleImageView iv_head_img;//公会头像
    @ViewInject(R.id.txv_edt_head)
    private TextView txv_edt_head;//编辑头像提示
    @ViewInject(R.id.et_fam_name)
    private EditText et_fam_name;//编辑公会名称
    @ViewInject(R.id.txv_fam_name)
    private TextView txv_fam_name;//公会名称
    @ViewInject(R.id.tv_sociaty)
    private TextView tv_sociaty;
    @ViewInject(R.id.ll_fam_nick)
    private LinearLayout ll_fam_nick;
    @ViewInject(R.id.txv_fam_nick)
    private TextView txv_fam_nick;//公会长
    @ViewInject(R.id.tv_sociaty_num)
    private TextView tv_sociaty_num;
    @ViewInject(R.id.ll_fam_num)
    private LinearLayout ll_fam_num;
    @ViewInject(R.id.txv_fam_num)
    private TextView txv_fam_num;//公会人数
    @ViewInject(R.id.et_fam_decl)
    private EditText et_fam_decl;//公会宣言
    @ViewInject(R.id.txv_sure)
    private TextView txv_sure;//公会成员
    @ViewInject(R.id.ll_family)
    private LinearLayout ll_family;
    @ViewInject(R.id.txv_edt)
    private TextView txv_edt;//编辑资料
    @ViewInject(R.id.txv_manage)
    private TextView txv_manage;//成员管理
    @ViewInject(R.id.ll_family2)
    private LinearLayout ll_family2;
    @ViewInject(R.id.txv_menb)
    private TextView txv_menb;//公会成员
    @ViewInject(R.id.txv_exit)
    private TextView txv_exit;//成员管理

    private String sociaty_logo;//公会头像
    private String sociaty_name;//公会名称
    private String sociaty_decl;//公会宣言
    private String sociaty_nick;//公会长
    private int sociaty_num;//人数

    private SDDialogConfirm mDialog;
    private boolean examine;//审核状态 正在审核 或 通过 false  被拒 true
    private UserModel dao = UserModelDao.query();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_family_data_update_edit);
        initView();
    }

    private void initView()
    {
        initTitle();

        requestSociatyIndex(dao.getSociety_id());

        txv_edt_head.setVisibility(View.GONE);
        et_fam_name.setVisibility(View.GONE);
        ll_fam_nick.setVisibility(View.VISIBLE);
        ll_fam_num.setVisibility(View.VISIBLE);
        txv_sure.setVisibility(View.GONE);
        txv_fam_name.setVisibility(View.VISIBLE);
        et_fam_decl.setFocusable(false);

        SDViewBinder.setTextView(txv_fam_name,"请输入公会名称");
        SDViewBinder.setTextView(tv_sociaty,"公会长:");
        SDViewBinder.setTextView(tv_sociaty_num,"公会人数:");
        et_fam_decl.setHint("请输入公会宣言");
        SDViewBinder.setTextView(txv_menb,"公会成员");
        SDViewBinder.setTextView(txv_exit,"退出公会");

        if (dao.getSociety_chieftain() == 1)//是否公会长；0：否、1：是
        {
            ll_family.setVisibility(View.VISIBLE);
            ll_family2.setVisibility(View.GONE);
        }else
        {
            ll_family.setVisibility(View.GONE);
            ll_family2.setVisibility(View.VISIBLE);
        }

        txv_menb.setOnClickListener(this);
        txv_exit.setOnClickListener(this);
        txv_edt.setOnClickListener(this);
        txv_manage.setOnClickListener(this);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("公会详情");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.txv_menb:
                clickMemberList();
                break;
            case R.id.txv_exit:
                showExitDialog("是否退出该公会？","确定","取消");
                break;
            case R.id.txv_edt:
                clickEdit();
                break;
            case R.id.txv_manage:
                clickMemberList();
                break;
        }
    }

    /**
     * 成员列表
     */
    private void clickMemberList()
    {
        Intent intent = new Intent(this, LiveSociatyMembersListActivity.class);
        startActivity(intent);
    }

    /**
     * 编辑资料
     */
    private void clickEdit()
    {
        Intent intent = new Intent(this, LiveSociatyUpdateEditActivity.class);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_UPDATE,LiveSociatyUpdateEditActivity.EXTRA_UPDATE_DATA);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_EXAMINE,examine);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_SOCIATY_LOGO,sociaty_logo);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_SOCIATY_NAME,sociaty_name);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_SOCIATY_NICK,sociaty_nick);
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_SOCIATY_NUM,sociaty_num + "");
        intent.putExtra(LiveSociatyUpdateEditActivity.EXTRA_SOCIATY_DECL,sociaty_decl);
        startActivity(intent);
    }

    /**
     * 退出公会对话框
     * @param content 内容提示
     * @param confirmText 确认
     * @param cancelText 取消
     */
    private void showExitDialog(String content,String confirmText,String cancelText)
    {
        if(mDialog == null)
        {
            mDialog = new SDDialogConfirm(this);
            mDialog.setTextGravity(Gravity.CENTER);
            mDialog.setCancelable(false);
            mDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
            {
                @Override
                public void onClickCancel(View v, SDDialogCustom dialog)
                {

                }

                @Override
                public void onClickConfirm(View v, SDDialogCustom dialog)
                {
                    requestSociatyLogout();
                }

                @Override
                public void onDismiss(SDDialogCustom dialog)
                {

                }
            });
        }
        mDialog.setTextContent(content);
        mDialog.setTextConfirm(confirmText);
        mDialog.setTextCancel(cancelText);
        mDialog.show();
    }

    /**
     * 获取公会主页数据
     * @param society_id
     */
    private void requestSociatyIndex(int society_id)
    {
        showProgressDialog("");
        CommonInterface.requestSociatyIndex(society_id, new AppRequestCallback<App_sociaty_indexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                sociaty_logo = actModel.getSociety_info().getLogo();
                sociaty_name = actModel.getSociety_info().getName();
                sociaty_nick = actModel.getSociety_info().getNick_name();
                sociaty_num = actModel.getSociety_info().getUser_count();
                sociaty_decl = actModel.getSociety_info().getManifesto();
                GlideUtil.loadHeadImage(sociaty_logo).into(iv_head_img);
                SDViewBinder.setTextView(txv_fam_name,sociaty_name);
                SDViewBinder.setTextView(txv_fam_nick,sociaty_nick);
                SDViewBinder.setTextView(txv_fam_num,sociaty_num + "人");
                SDViewBinder.setTextView(et_fam_decl,sociaty_decl);

                if (actModel.getStatus() == 0)//未审核，正在审核
                {
                    if (dao.getSociety_chieftain() == 1)//是否公会长；0：否、1：是
                    {
                        txv_edt.setEnabled(false);
                        txv_edt.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                        txv_manage.setEnabled(false);
                        txv_manage.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                    }else
                    {
                        txv_menb.setEnabled(false);
                        txv_menb.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                    }
                }else if (actModel.getStatus() == 2)//审核被拒
                {
                    examine = true;
                    txv_manage.setEnabled(false);
                    txv_manage.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);

                    if (dao.getSociety_chieftain() != 1)//是否公会长；0：否、1：是
                    {
                        txv_menb.setEnabled(false);
                        txv_menb.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 退出公会
     */
    private void requestSociatyLogout()
    {
        CommonInterface.requestSociatyLogout(new AppRequestCallback<App_sociaty_user_logoutActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast(actModel.getError().toString());
                    finish();
                } else
                    SDToast.showToast(actModel.getError().toString());
            }
        });
    }

    public void onEventMainThread(EIsFinishLiveSociatyDetails event)
    {
        finish();
    }
}
