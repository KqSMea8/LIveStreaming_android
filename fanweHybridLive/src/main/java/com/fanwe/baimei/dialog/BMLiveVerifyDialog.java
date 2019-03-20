package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fanwe.baimei.appview.SecurityCodeView;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMCheckVerifyActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * 登录输入验证码窗口
 */

public class BMLiveVerifyDialog extends SDDialogCustom {

    private FrameLayout fl_progress;

    private SecurityCodeView et_verify;

    private ImageView iv_logout;

    private ProgressBar progress;

    private TextView tv_failed;
    private TextView tv_tip;

    private boolean isFailed;

    public BMLiveVerifyDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.bm_dialog_verify);
        setFullScreen();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initDialog();
        requestVerifyStatus();
    }

    private void initDialog() {
        fl_progress = (FrameLayout) findViewById(R.id.fl_progress);
        et_verify = (SecurityCodeView) findViewById(R.id.et_verify);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        progress = (ProgressBar) findViewById(R.id.progress);
        tv_failed = (TextView) findViewById(R.id.tv_failed);
        tv_tip = (TextView) findViewById(R.id.tip);
        tv_tip.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        ((TextView) findViewById(R.id.tip1)).setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        iv_logout.setOnClickListener(this);
        fl_progress.setOnClickListener(this);
//        et_verify.setInputCompleteListener(new SecurityCodeView.InputCompleteListener() {
//            @Override
//            public void inputComplete() {
//                requestCommitVerify();
//            }
//
//            @Override
//            public void deleteContent(boolean isDelete) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == iv_logout) {
            dismiss();
//            requestCommitVerify();
        } else if (v == fl_progress) {
            if (isFailed) {
//                requestVerifyStatus();
            }
        }
    }

    private void requestVerifyStatus() {
        isFailed = false;
        SDViewUtil.setVisible(fl_progress);
        SDViewUtil.setVisible(progress);
        SDViewUtil.setGone(tv_failed);
        BMCommonInterface.requestVerifyStatus(new AppRequestCallback<BMCheckVerifyActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    SDViewUtil.setGone(fl_progress);
                    isFailed = false;
                    int status = actModel.getHas_invitation_code();//1：填写过邀请码，0：未填写邀请码
                    et_verify.setEdtiContent(actModel.getDefault_code());
                    if (status == 1) {
                        dismiss();
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
                isFailed = true;
                SDViewUtil.setVisible(tv_failed);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                SDViewUtil.setGone(progress);
            }
        });
    }

//    private void requestCommitVerify() {
//        String code = et_verify.getEditContent().toString().trim();
//        if (TextUtils.isEmpty(code)) {
//            code="AFSXU";
//        }
//        SDViewUtil.setVisible(fl_progress);
//        BMCommonInterface.requestVerify(code, new AppRequestCallback<BaseActModel>() {
//            @Override
//            protected void onSuccess(SDResponse sdResponse) {
//                if (actModel.isOk()) {
//                    dismiss();
//                }
//            }
//
//            @Override
//            protected void onFinish(SDResponse resp) {
//                super.onFinish(resp);
//                SDViewUtil.setGone(fl_progress);
//            }
//        });
//    }

    public void setTip(String name) {
        tv_tip.setText(name);
    }

}
