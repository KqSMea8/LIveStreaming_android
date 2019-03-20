package com.fanwe.live.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 查看聊天图片
 * Created by Administrator on 2016/8/30.
 */
public class PhotoViewActivity extends BaseTitleActivity
{

    /**
     * 获取图片url集合(list)
     */
    public static final String EXTRA_IMAGE_URLS = "extra_image_urls";

    /**
     * 获取图片当前位置(int)
     */
    public static final String EXTRA_POSITION = "extra_position";

    @ViewInject(R.id.viewPager)
    private SDViewPager viewPager;
    @ViewInject(R.id.txv_page)
    private TextView txv_page;//当前图片位置
    @ViewInject(R.id.iv_down_load)
    private ImageView iv_down_load;//下载
    /**
     * 获取前一个activity传过来的position
     */
    private int selectPosition;
    /**
     * 存储图片的url集合
     */
    private ArrayList<String> listImageUrl;
    private PhotoViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_view);
        initData();
    }

    private void initData()
    {
        initTitle();

        selectPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        listImageUrl = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        viewPager.addOnPageChangeListener(pageChangeListener);

        adapter = new PhotoViewPageAdapter(listImageUrl, this);
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin((int) getResources().getDimensionPixelOffset(R.dimen.height_photo_view));
        viewPager.setCurrentItem(selectPosition);

        updateSelected();
        iv_down_load.setOnClickListener(this);
    }

    private void initTitle()
    {

    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.iv_down_load:
                savePic();
                break;
        }
    }

    /**
     * 保存图片
     */
    private void savePic()
    {
        String url = SDCollectionUtil.get(listImageUrl, selectPosition);

        GlideUtil.load(url).downloadOnly(new SimpleTarget<File>()
        {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation)
            {
                if (resource != null)
                {
                    String cacheName = resource.getName();
                    String fromPath = resource.getAbsolutePath();
                    String toPath = SDFileUtil.getPicturesDir().getAbsolutePath() + File.separator + cacheName + ".jpg";

                    SDFileUtil.copy(fromPath, toPath);
                    SDToast.showToast("图片保存成功");
                }
            }
        });
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            selectPosition = position;
            updateSelected();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    private void updateSelected()
    {
        txv_page.setText((selectPosition + 1) + "/" + listImageUrl.size());
    }

    class PhotoViewPageAdapter extends SDPagerAdapter<String>
    {
        public PhotoViewPageAdapter(List<String> listModel, Activity activity)
        {
            super(listModel, activity);
        }

        @Override
        public View getView(ViewGroup container, int position)
        {
            String url = getData(position);
            PhotoView photoView = new PhotoView(getActivity());
            GlideUtil.load(url).into(photoView);
            return photoView;
        }
    }

}
