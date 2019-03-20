package com.fanwe.hybrid.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.fanwe.live.R;
import com.fanwe.zxing.CaptureActivity;
import com.google.zxing.Result;

public class MyCaptureActivity extends CaptureActivity
{
    /**
     * 是否扫描成功后结束二维码扫描activity，0：否，1:是,值为字符串
     */
    public static final String EXTRA_IS_FINISH_ACTIVITY = "extra_is_finish_activity";
    /**
     * 扫描成功返回码
     */
    public static final int RESULT_CODE_SCAN_SUCCESS = 10;
    /**
     * 扫描成功，扫描activity结束后Intent中取扫描结果的key
     */
    public static final String EXTRA_RESULT_SUCCESS_STRING = "extra_result_success_string";

    private View rl_back;

    private boolean mIsStartByAdvs = false;
    private int mFinishActivityWhenScanFinish = 1;

    @Override
    protected void init()
    {
        initIntentData();
        setLayoutId(R.layout.include_title_qrcode);
        rl_back = findViewById(R.id.rl_back);
        registeClick();
    }

    private void initIntentData()
    {
        mFinishActivityWhenScanFinish = getIntent().getIntExtra(EXTRA_IS_FINISH_ACTIVITY, 1);
    }

    private void registeClick()
    {
        rl_back.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                finish();
            }
        });
    }

    @Override
    protected void onSuccessScanning(Result result)
    {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT_SUCCESS_STRING, result.getText());
        setResult(RESULT_CODE_SCAN_SUCCESS, intent);
        if (mFinishActivityWhenScanFinish == 1)
        {
            finish();
        }
    }

    @Override
    public void finish()
    {
        if (mIsStartByAdvs)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        super.finish();
    }
}
