package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.live.R;
import com.fanwe.live.fragment.LiveUpLoadImageOssBaseFragment;
import com.fanwe.live.fragment.LiveUpLoadImageOssFragment;
import com.fanwe.live.fragment.LiveUpLoadUserImageOssFragment;

import org.xutils.view.annotation.ViewInject;

import static com.fanwe.live.activity.LiveUpLoadImageOssActivity.ExtraType.EXTRA_UPLOADD_EFAULT_IMAGE;
import static com.fanwe.live.activity.LiveUpLoadImageOssActivity.ExtraType.EXTRA_UPLOAD_USER_IMAGE;

/**
 * Created by Administrator on 2016/10/19.
 */

public class LiveUpLoadImageOssActivity extends BaseTitleActivity
{
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    public static final String EXTRA_START_TYPE = "EXTRA_START_TYPE";

    @ViewInject(R.id.ll_content)
    private LinearLayout ll_content;

    public static final class ExtraType
    {
        public static String EXTRA_UPLOADD_EFAULT_IMAGE = "extrauploaddefaultimage";
        public static String EXTRA_UPLOAD_USER_IMAGE = "extrauploaduserimage";
    }

    public static final String EXTRA_TITILE = "extra_titile";
    public static final String EXTRA_RIGHT_TEXT = "extra_right_text";
    private String mStrTitle = "上传图片";
    private String mStrRightText = "完成";

    private String mStartType = EXTRA_UPLOADD_EFAULT_IMAGE;

    private LiveUpLoadImageOssBaseFragment liveUpLoadImageOssBaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_up_load_image_oss);
        init();
    }

    private void init()
    {
        initTitle();
        getIntentData();
        initUpLoadFragment();
    }

    private void getIntentData()
    {
        mStrTitle = getIntent().getStringExtra(EXTRA_TITILE);
        mStrRightText = getIntent().getStringExtra(EXTRA_RIGHT_TEXT);
        mStartType = getIntent().getStringExtra(EXTRA_START_TYPE);
        if (TextUtils.isEmpty(mStartType))
        {
            mStartType = EXTRA_UPLOADD_EFAULT_IMAGE;
        }
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop(mStrTitle);
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot(mStrRightText);
    }

    private void initUpLoadFragment()
    {
        if (EXTRA_UPLOADD_EFAULT_IMAGE.equals(mStartType))
        {
            liveUpLoadImageOssBaseFragment = new LiveUpLoadImageOssFragment();
        } else if (EXTRA_UPLOAD_USER_IMAGE.equals(mStartType))
        {
            liveUpLoadImageOssBaseFragment = new LiveUpLoadUserImageOssFragment();
        }else
        {
            liveUpLoadImageOssBaseFragment = new LiveUpLoadImageOssFragment();
        }
        getSDFragmentManager().toggle(R.id.ll_content, null, liveUpLoadImageOssBaseFragment);
    }


    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        liveUpLoadImageOssBaseFragment.upLoadImage();
    }
}
