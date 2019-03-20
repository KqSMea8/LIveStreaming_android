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
import com.fanwe.live.model.App_family_indexActModel;
import com.fanwe.live.model.App_family_user_logoutActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 家族详情
 * Created by Administrator on 2016/9/27.
 */

public class LiveFamilyDetailsActivity extends BaseTitleActivity
{
    @ViewInject(R.id.iv_head_img)
    private CircleImageView iv_head_img;//家族头像
    @ViewInject(R.id.txv_edt_head)
    private TextView txv_edt_head;//编辑头像提示
    @ViewInject(R.id.et_fam_name)
    private EditText et_fam_name;//编辑家族名称
    @ViewInject(R.id.txv_fam_name)
    private TextView txv_fam_name;//家族名臣
    @ViewInject(R.id.ll_fam_nick)
    private LinearLayout ll_fam_nick;
    @ViewInject(R.id.txv_fam_nick)
    private TextView txv_fam_nick;//族长
    @ViewInject(R.id.ll_fam_num)
    private LinearLayout ll_fam_num;
    @ViewInject(R.id.txv_fam_num)
    private TextView txv_fam_num;//家族人数
    @ViewInject(R.id.et_fam_decl)
    private EditText et_fam_decl;//家族宣言
    @ViewInject(R.id.txv_sure)
    private TextView txv_sure;//家族成员
    @ViewInject(R.id.ll_family)
    private LinearLayout ll_family;
    @ViewInject(R.id.txv_edt)
    private TextView txv_edt;//编辑资料
    @ViewInject(R.id.txv_manage)
    private TextView txv_manage;//成员管理
    @ViewInject(R.id.ll_family2)
    private LinearLayout ll_family2;
    @ViewInject(R.id.txv_menb)
    private TextView txv_menb;//家族成员
    @ViewInject(R.id.txv_exit)
    private TextView txv_exit;//成员管理

    private String family_logo;//家族头像
    private String family_name;//家族名称
    private String family_decl;//家族宣言
    private String family_nick;//族长
    private int family_num;//人数

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

        requestFamilyIndex(dao.getFamily_id());

        txv_edt_head.setVisibility(View.GONE);
        et_fam_name.setVisibility(View.GONE);
        ll_fam_nick.setVisibility(View.VISIBLE);
        ll_fam_num.setVisibility(View.VISIBLE);
        txv_sure.setVisibility(View.GONE);
        txv_fam_name.setVisibility(View.VISIBLE);
        et_fam_decl.setFocusable(false);

        if (dao.getFamily_chieftain() == 1)//是否家族长；0：否、1：是
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
        mTitle.setMiddleTextTop("家族详情");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.txv_menb:
                Intent intentSure = new Intent(this, LiveFamilyMembersListActivity.class);
                startActivity(intentSure);
                break;
            case R.id.txv_exit:
                showExitDialog("是否退出该家族？","确定","取消");
                break;
            case R.id.txv_edt:
                Intent intent = new Intent(this, LiveFamilyUpdateEditActivity.class);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_UPDATE,LiveFamilyUpdateEditActivity.EXTRA_UPDATE_DATA);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_EXAMINE,examine);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_FAMILY_LOGO,family_logo);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_FAMILY_NAME,family_name);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_FAMILY_NICK,family_nick);
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_FAMILY_NUM,family_num + "");
                intent.putExtra(LiveFamilyUpdateEditActivity.EXTRA_FAMILY_DECL,family_decl);
                startActivity(intent);
                break;
            case R.id.txv_manage:
                Intent intentMem = new Intent(this, LiveFamilyMembersListActivity.class);
                startActivity(intentMem);
                break;
        }
    }

    /**
     * 退出家族对话框
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
                    requestFamilyLogout();
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
     * 获取家族主页数据
     * @param jid
     */
    private void requestFamilyIndex(int jid)
    {
        CommonInterface.requestFamilyIndex(jid, new AppRequestCallback<App_family_indexActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                family_logo = actModel.getFamily_info().getFamily_logo();
                family_name = actModel.getFamily_info().getFamily_name();
                family_nick = actModel.getFamily_info().getNick_name();
                family_num = actModel.getFamily_info().getUser_count();
                family_decl = actModel.getFamily_info().getFamily_manifesto();
                GlideUtil.loadHeadImage(family_logo).into(iv_head_img);
                SDViewBinder.setTextView(txv_fam_name,family_name);
                SDViewBinder.setTextView(txv_fam_nick,family_nick);
                SDViewBinder.setTextView(txv_fam_num,family_num + "人");
                SDViewBinder.setTextView(et_fam_decl,family_decl);

                if (actModel.getStatus() == 0)//未审核，正在审核
                {
                    if (dao.getFamily_chieftain() == 1)//是否家族长；0：否、1：是
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

                    if (dao.getFamily_chieftain() != 1)//是否家族长；0：否、1：是
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
        });
    }

    /**
     * 退出家族
     */
    private void requestFamilyLogout()
    {
        CommonInterface.requestFamilyLogout(new AppRequestCallback<App_family_user_logoutActModel>()
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
