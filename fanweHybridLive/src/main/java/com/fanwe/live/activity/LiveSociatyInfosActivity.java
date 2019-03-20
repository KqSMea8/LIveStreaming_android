package com.fanwe.live.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_sociaty_indexActModel;
import com.fanwe.live.model.App_sociaty_joinActModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 公会信息展示
 * Created by Administrator on 2016/10/9.
 */

public class LiveSociatyInfosActivity extends BaseTitleActivity
{
    /**
     * 公会ID
     */
    public static final String EXTRA_SOCIATY_ID = "extra_sociaty_id";
    /**
     * 1：已提交、0：未提交2：已加入公会
     */
    public static final String EXTRA_SOCIATY_IS_APPLY = "extra_sociaty_is_apply";
    /**
     * true：已提交、false：未提交
     */
    public static final String EXTRA_SOCIATY_IS_CHECK = "extra_sociaty_is_check";
    /**
     * 加入公会按钮是否可以点击
     */
    public static final String EXTRA_SOCIATY_IS_CLICK = "extra_sociaty_is_click";

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
    @ViewInject(R.id.ll_fam_num)
    private LinearLayout ll_fam_num;
    @ViewInject(R.id.tv_sociaty_num)
    private TextView tv_sociaty_num;
    @ViewInject(R.id.txv_fam_num)
    private TextView txv_fam_num;//公会人数
    @ViewInject(R.id.et_fam_decl)
    private EditText et_fam_decl;//公会宣言
    @ViewInject(R.id.txv_sure)
    private TextView txv_sure;//加入公会

    private String sociaty_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_family_data_update_edit);
        initData();
    }

    private void initData()
    {
        initTitle();

        txv_edt_head.setVisibility(View.GONE);
        et_fam_name.setVisibility(View.GONE);
        txv_fam_name.setVisibility(View.VISIBLE);
        ll_fam_nick.setVisibility(View.VISIBLE);
        ll_fam_num.setVisibility(View.VISIBLE);
        et_fam_decl.setFocusable(false);
        SDViewBinder.setTextView(txv_fam_name,"请输入公会名称");
        SDViewBinder.setTextView(tv_sociaty,"公会长:");
        SDViewBinder.setTextView(tv_sociaty_num,"公会人数:");
        et_fam_decl.setHint("请输入公会宣言");

        sociaty_id = getIntent().getStringExtra(EXTRA_SOCIATY_ID);
        String family_is_apply = getIntent().getStringExtra(EXTRA_SOCIATY_IS_APPLY);
        boolean isCheck = getIntent().getBooleanExtra(EXTRA_SOCIATY_IS_CHECK,true);
        boolean isClick = getIntent().getBooleanExtra(EXTRA_SOCIATY_IS_CLICK,true);
        requestSociatyIndex(Integer.parseInt(sociaty_id));
        if (Integer.parseInt(family_is_apply) == 1 || isCheck)//1：已提交、0：未提交  true：已提交、false：未提交
        {
            txv_sure.setText("申请中");
            txv_sure.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
            txv_sure.setEnabled(false);
        }
        else if (Integer.parseInt(family_is_apply) == 0)
        {
            txv_sure.setText("加入公会");
            txv_sure.setEnabled(isClick);
        }
        else if (Integer.parseInt(family_is_apply) == 2)
        {
            txv_sure.setText("已加入");
            txv_sure.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
            txv_sure.setEnabled(false);
        }

        txv_sure.setOnClickListener(this);
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("公会详情");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.txv_sure:
                applyJoinSociaty(Integer.parseInt(sociaty_id));
                break;
        }
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
                if (actModel.getStatus() == 1)
                {
                    String sociaty_logo = actModel.getSociety_info().getLogo();
                    String sociaty_name = actModel.getSociety_info().getName();
                    String sociaty_nick = actModel.getSociety_info().getNick_name();
                    int sociaty_num = actModel.getSociety_info().getUser_count();
                    String sociaty_decl = actModel.getSociety_info().getManifesto();
                    GlideUtil.loadHeadImage(sociaty_logo).into(iv_head_img);
                    SDViewBinder.setTextView(txv_fam_name,sociaty_name);
                    SDViewBinder.setTextView(txv_fam_nick,sociaty_nick);
                    SDViewBinder.setTextView(txv_fam_num,sociaty_num + "人");
                    SDViewBinder.setTextView(et_fam_decl,sociaty_decl);
                }
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
     * 申请加入公会
     * @param id
     */
    private void applyJoinSociaty(int id)
    {
        CommonInterface.requestApplyJoinSociaty(id, new AppRequestCallback<App_sociaty_joinActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast(actModel.getError().toString());
                    txv_sure.setText("申请中");
                    txv_sure.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
                    txv_sure.setEnabled(false);
                }
            }
        });
    }

}
