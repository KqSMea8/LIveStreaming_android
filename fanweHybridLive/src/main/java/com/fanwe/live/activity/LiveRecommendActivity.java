package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_get_p_user_idActModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by yhz on 2017/1/12.推荐人
 */

public class LiveRecommendActivity extends BaseTitleActivity
{
    @ViewInject(R.id.et_recommend_id)
    private EditText et_recommend_id;

    @ViewInject(R.id.btn_submit)
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_recommend);
        x.view().inject(this);
        init();
    }

    private void init()
    {
        initTitle();
        register();
        requestGet_p_user_id();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("推荐人ID");
    }

    protected void register()
    {
        btn_submit.setOnClickListener(this);
        et_recommend_id.setFocusable(false);
    }

    protected void requestGet_p_user_id()
    {
        CommonInterface.requestGet_p_user_id(new AppRequestCallback<App_get_p_user_idActModel>()
        {
            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    int p_user_id = actModel.getP_user_id();
                    if (p_user_id>0)
                    {
                        et_recommend_id.setText(String.valueOf(p_user_id));
                        et_recommend_id.setFocusable(false);
                        SDViewUtil.setGone(btn_submit);
                    } else
                    {
                        et_recommend_id.setFocusable(true);
                        SDViewUtil.setVisible(btn_submit);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.btn_submit:
                clickBtnSubmit();
                break;
        }
    }

    private void clickBtnSubmit()
    {
        requestUpdata_p_user_id();
    }

    private void requestUpdata_p_user_id()
    {
        String update_p_user_id = et_recommend_id.getText().toString();
        if (TextUtils.isEmpty(update_p_user_id))
        {
            SDToast.showToast("请输入推荐人ID");
            return;
        }

        CommonInterface.requestUpdata_p_user_id(update_p_user_id, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }

            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("正在保存");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    finish();
                }
            }
        });
    }
}
