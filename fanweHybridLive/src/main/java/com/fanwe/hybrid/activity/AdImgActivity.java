package com.fanwe.hybrid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.fanwe.baimei.activity.BMHomeActivity;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveLoginActivity;
import com.fanwe.live.activity.LiveMainActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EFinishAdImg;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.model.LiveBannerModel;
import com.fanwe.live.utils.GlideUtil;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-2-23 上午11:35:31 类说明
 */
public class AdImgActivity extends BaseActivity implements OnClickListener
{
    private ImageView mIvAdImg;

    private String mImgUrl;
    private LiveBannerModel mBannerModel;
   private Button btn_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ad_img);
        setFullScreen(true);
        init();
    }

    private void init()
    {
        mIvAdImg = find(R.id.iv_ad_img);
        btn_skip= find(R.id.btn_skip);
        InitActModel model = InitActModelDao.query();
        if (model == null)
        {
            return;
        }
        if (SDCollectionUtil.isEmpty(model.getStart_diagram()))
        {
            return;
        }
        mBannerModel = model.getStart_diagram().get(0);
        if (mBannerModel == null)
        {
            return;
        }
        mImgUrl = mBannerModel.getImage();
        if (TextUtils.isEmpty(mImgUrl))
        {
            return;
        }

        GlideUtil.load(mImgUrl).into(mIvAdImg);
        mIvAdImg.setOnClickListener(this);
        btn_skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == mIvAdImg)
        {
            Intent intent = mBannerModel.parseType(this);
            if (intent != null)
            {
                startActivity(intent);
            }
        }
        if (v == btn_skip)
        {
            InitBusiness.startMainOrLogin(AdImgActivity.this);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        startMainDelayRunnable();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        stopMainDelayRunnable();
    }

    private void startMainDelayRunnable()
    {
        LogUtil.i("----------startMainDelayRunnable");
        int delayTime = getResources().getInteger(R.integer.ad_delayed_time);
        mStartMainRunnable.runDelay(delayTime);
    }

    private void stopMainDelayRunnable()
    {
        LogUtil.i("----------stopMainDelayRunnable");
        mStartMainRunnable.removeDelay();
    }

    private SDDelayRunnable mStartMainRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            LogUtil.i("----------runnable run");
            mIvAdImg.setOnClickListener(null);
            InitBusiness.startMainOrLogin(AdImgActivity.this);
        }
    };
}
