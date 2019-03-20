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
import com.fanwe.live.model.App_family_createActModel;
import com.fanwe.live.model.App_family_indexActModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 家族信息展示
 * Created by Administrator on 2016/10/9.
 */

public class LiveFamilyInformationActivity extends BaseTitleActivity
{
    /**
     * 家族ID
     */
    public static final String EXTRA_FAMILY_ID = "extra_family_id";
    /**
     * 1：已提交、0：未提交2：已加入家族
     */
    public static final String EXTRA_FAMILY_IS_APPLY = "extra_family_is_apply";
    /**
     * true：已提交、false：未提交
     */
    public static final String EXTRA_FAMILY_IS_CHECK = "extra_family_is_check";
    /**
     * 加入家族按钮是否可以点击
     */
    public static final String EXTRA_FAMILY_IS_CLICK = "extra_family_is_click";

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
    private TextView txv_sure;//加入家族

    private String family_id;

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

        family_id = getIntent().getStringExtra(EXTRA_FAMILY_ID);
        String family_is_apply = getIntent().getStringExtra(EXTRA_FAMILY_IS_APPLY);
        boolean isCheck = getIntent().getBooleanExtra(EXTRA_FAMILY_IS_CHECK,true);
        boolean isClick = getIntent().getBooleanExtra(EXTRA_FAMILY_IS_CLICK,true);
        requestFamilyIndex(Integer.parseInt(family_id));
        if (Integer.parseInt(family_is_apply) == 1 || isCheck)//1：已提交、0：未提交  true：已提交、false：未提交
        {
            txv_sure.setText("申请中");
            txv_sure.setBackgroundResource(R.drawable.bg_grey_rectangle_radius);
            txv_sure.setEnabled(false);
        }
        else if (Integer.parseInt(family_is_apply) == 0)
        {
            txv_sure.setText("加入家族");
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
        mTitle.setMiddleTextTop("家族详情");
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
                applyJoinFamily(Integer.parseInt(family_id));
                break;
        }
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
                if (actModel.getStatus() == 1)
                {
                    String family_logo = actModel.getFamily_info().getFamily_logo();
                    String family_name = actModel.getFamily_info().getFamily_name();
                    String family_nick = actModel.getFamily_info().getNick_name();
                    int family_num = actModel.getFamily_info().getUser_count();
                    String family_decl = actModel.getFamily_info().getFamily_manifesto();
                    GlideUtil.loadHeadImage(family_logo).into(iv_head_img);
                    SDViewBinder.setTextView(txv_fam_name,family_name);
                    SDViewBinder.setTextView(txv_fam_nick,family_nick);
                    SDViewBinder.setTextView(txv_fam_num,family_num + "人");
                    SDViewBinder.setTextView(et_fam_decl,family_decl);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    /**
     * 申请加入家族
     * @param id
     */
    private void applyJoinFamily(int id)
    {
        CommonInterface.requestApplyJoinFamily(id, new AppRequestCallback<App_family_createActModel>()
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
