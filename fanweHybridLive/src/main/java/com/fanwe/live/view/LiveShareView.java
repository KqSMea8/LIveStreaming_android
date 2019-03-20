package com.fanwe.live.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享view
 */
public class LiveShareView extends SDAppView
{
    private static final int PLATFORM_WX = 1;
    private static final int PLATFORM_WX_CIRCLE = 2;
    private static final int PLATFORM_QQ = 3;
    private static final int PLATFORM_QZONE = 4;
    private static final int PLATFORM_SINA = 5;

    private SDGridLinearLayout ll_platform;
    private List<PlatformModel> listPlatform = new ArrayList<>();

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public LiveShareView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveShareView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveShareView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_share);

        ll_platform = find(R.id.ll_platform);
        ll_platform.setColNumber(4);
    }

    public void bindData(Activity activity)
    {
        listPlatform.clear();
        addWeixin();
        addWeixinCircle();
        addQQ();
        addQzone();
        addSina();

        ll_platform.setAdapter(new SDSimpleAdapter<PlatformModel>(listPlatform, activity)
        {
            @Override
            public int getLayoutId(int position, View convertView, ViewGroup parent)
            {
                return R.layout.item_share_view;
            }

            @Override
            public void bindData(int position, View convertView, ViewGroup parent, final PlatformModel model)
            {
                ImageView iv_image = get(R.id.iv_image, convertView);
                TextView tv_name = get(R.id.tv_name, convertView);
                View ll_share = get(R.id.ll_share, convertView);

                iv_image.setImageResource(model.imageResId);
                tv_name.setText(model.name);

                ll_share.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        switch (model.platform)
                        {
                            case PLATFORM_WX:
                                onClickWx(v);
                                break;
                            case PLATFORM_WX_CIRCLE:
                                onClickWxCircle(v);
                                break;
                            case PLATFORM_QQ:
                                onClickQQ(v);
                                break;
                            case PLATFORM_QZONE:
                                onClickQzone(v);
                                break;
                            case PLATFORM_SINA:
                                onClickSina(v);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }


        });
    }

    private void onClickWx(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClickWx(v);
        }
    }

    private void onClickWxCircle(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClickWxCircle(v);
        }
    }

    private void onClickQQ(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClickQQ(v);
        }
    }

    private void onClickQzone(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClickQzone(v);
        }
    }

    private void onClickSina(View v)
    {
        if (clickListener != null)
        {
            clickListener.onClickSina(v);
        }
    }

    private void addWeixin()
    {
        if (UmengSocialManager.isWeixinEnable())
        {
            PlatformModel model = new PlatformModel(PLATFORM_WX, "微信", R.drawable.umeng_socialize_wechat);
            listPlatform.add(model);
        }
    }

    private void addWeixinCircle()
    {
        if (UmengSocialManager.isWeixinEnable())
        {
            PlatformModel model = new PlatformModel(PLATFORM_WX_CIRCLE, "微信朋友圈", R.drawable.umeng_socialize_wxcircle);
            listPlatform.add(model);
        }
    }

    private void addQQ()
    {
        if (UmengSocialManager.isQQEnable())
        {
            PlatformModel model = new PlatformModel(PLATFORM_QQ, "QQ", R.drawable.umeng_socialize_qq);
            listPlatform.add(model);
        }
    }

    private void addQzone()
    {
        if (UmengSocialManager.isQQEnable())
        {
            PlatformModel model = new PlatformModel(PLATFORM_QZONE, "QQ空间", R.drawable.umeng_socialize_qzone);
            listPlatform.add(model);
        }
    }

    private void addSina()
    {
        if (UmengSocialManager.isSinaEnable())
        {
            PlatformModel model = new PlatformModel(PLATFORM_SINA, "新浪", R.drawable.umeng_socialize_sina);
            listPlatform.add(model);
        }
    }

    private class PlatformModel
    {
        public int platform;
        public String name;
        public int imageResId;

        public PlatformModel(int platform, String name, int imageResId)
        {
            this.platform = platform;
            this.name = name;
            this.imageResId = imageResId;
        }
    }

    public interface ClickListener
    {
        void onClickWx(View v);

        void onClickWxCircle(View v);

        void onClickQQ(View v);

        void onClickQzone(View v);

        void onClickSina(View v);
    }

}
