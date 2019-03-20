package com.fanwe.hybrid.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.fanwe.baimei.activity.BMHomeActivity;
import com.fanwe.hybrid.adapter.ViewPagerAdapter;
import com.fanwe.hybrid.constant.Constant.CommonSharePTag;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.live.R;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.activity.LiveLoginActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.BitmapFactoryUtils;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author yangyu 功能描述：主程序入口类
 */
public class InitAdvListActivity extends BaseActivity implements OnClickListener, OnPageChangeListener, OnTouchListener
{
    public static final String EXTRA_ARRAY = "extra_array";

    // 定义ViewPager对象
    @ViewInject(R.id.viewpager)
    private ViewPager mViewPager;
    @ViewInject(R.id.ll)
    private LinearLayout mLl;

    // 定义ViewPager适配器
    private ViewPagerAdapter mVpAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> mViews;
    // 底部小点的图片
    private ImageView[] mPoints;
    // 记录当前选中位置
    private int mCurrentIndex;
    private int mLastX;
    private ArrayList<String> mArrayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setFullScreen(true);
        setContentView(R.layout.act_init_adv_list);
        x.view().inject(this);
        init();
    }

    private void init()
    {
        SDConfig.getInstance().setInt(CommonSharePTag.IS_FIRST_OPEN_APP, 1);
        initIntent();
        bindAdapter();
        initData();
    }

    private void initIntent()
    {
        mArrayUrl = getIntent().getStringArrayListExtra(EXTRA_ARRAY);
    }

    private void bindAdapter()
    {
        // 实例化ArrayList对象
        mViews = new ArrayList<View>();
        // 实例化ViewPager适配器
        mVpAdapter = new ViewPagerAdapter(mViews);
    }

    /**
     * 初始化数据
     */
    private void initData()
    {
        if (mArrayUrl != null && mArrayUrl.size() > 0)
        {
            // 定义一个布局并设置参数
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            // 初始化引导图片列表
            for (int i = 0; i < mArrayUrl.size(); i++)
            {
                ImageView iv = new ImageView(this);
                iv.setScaleType(ScaleType.FIT_XY);
                iv.setLayoutParams(mParams);
                String url = mArrayUrl.get(i);
                if (url.contains("http"))
                {
                    GlideUtil.load(url).into(iv);
                } else
                {
                    int resID = getResources().getIdentifier(url, "drawable", SDPackageUtil.getCurrentPackageInfo().packageName);
//                    iv.setImageResource(resID);
                    try
                    {//大图片加载，防止内存溢出
                        BitmapFactoryUtils.getBitmapForImgResourse(InitAdvListActivity.this, resID, iv);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                mViews.add(iv);
            }

            // 设置数据
            mViewPager.setAdapter(mVpAdapter);
            // 设置监听
            mViewPager.setOnPageChangeListener(this);
            mViewPager.setOnTouchListener(this);
            // 初始化底部小点
            initPoint();
        }
    }

    /**
     * 初始化底部小点
     */
    private void initPoint()
    {
        mPoints = new ImageView[mArrayUrl.size()];

        // 循环取得小点图片
        for (int i = 0; i < mArrayUrl.size(); i++)
        {
            ImageView iv = (ImageView) getLayoutInflater().inflate(R.layout.item_iv_act_init_adv_list, null);
            // 得到一个LinearLayout下面的每一个子元素
            mPoints[i] = iv;
            // 默认都设为灰色
            mPoints[i].setEnabled(true);
            // 给每个小点设置监听
            mPoints[i].setOnClickListener(this);
            // 设置位置tag，方便取出与当前位置对应
            mPoints[i].setTag(i);
            mLl.addView(mPoints[i]);
        }

        // 设置当面默认的位置
        mCurrentIndex = 0;
        // 设置为白色，即选中状态״̬
        mPoints[mCurrentIndex].setEnabled(false);
    }

    /**
     * 当滑动状态改变时调用
     */
    @Override
    public void onPageScrollStateChanged(int arg0)
    {
    }

    /**
     * 当当前页面被滑动时调用
     */

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {
    }

    /**
     * 当新的页面被选中时调用
     */

    @Override
    public void onPageSelected(int position)
    {
        // 设置底部小点选中状态״̬
        setCurDot(position);
    }

    /**
     * 通过点击事件来切换当前的页面
     */
    @Override
    public void onClick(View v)
    {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= mArrayUrl.size())
        {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > mArrayUrl.size() - 1 || mCurrentIndex == positon)
        {
            return;
        }
        mPoints[positon].setEnabled(false);
        mPoints[mCurrentIndex].setEnabled(true);
        mCurrentIndex = positon;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if ((mLastX - event.getX()) > 100 && (mCurrentIndex == mViews.size() - 1))
                {
                    startLiveMainActivity();
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void startLiveMainActivity()
    {
        InitBusiness.startMainOrLogin(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}
