package com.fanwe.live.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * 未打开的红包view
 * Created by luodong on 2016/9/2.
 */
public class LiveRedEnvelopeOpenView extends BaseAppView
{
    private ImageView iv_envelope_open_head;
    private TextView tv_envelope_open_name;
    private TextView tv_info;
    private TextView tv_user_red_envelope;
    private ListView list_red;
    private View ll_open_close;
    private View ll_open_top;

    public LiveRedEnvelopeOpenView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public LiveRedEnvelopeOpenView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LiveRedEnvelopeOpenView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.include_live_red_envelope_open;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        iv_envelope_open_head = find(R.id.iv_envelope_open_head);
        tv_envelope_open_name = find(R.id.tv_envelope_open_name);
        tv_info = find(R.id.tv_info);
        tv_user_red_envelope = find(R.id.tv_user_red_envelope);
        list_red = find(R.id.list_red);
        ll_open_close = find(R.id.ll_open_close);
        ll_open_top = find(R.id.ll_open_top);

    }

    /**
     * 设置视图的显示与隐藏
     */
    public void setViewShowOrHide()
    {
        SDViewUtil.setGone(tv_user_red_envelope);
        SDViewUtil.setVisible(list_red);
        SDViewUtil.setVisible(ll_open_top);
    }

    /**
     * 获取ListView
     */
    public ListView getListView()
    {
        return list_red;
    }

    /**
     * 头像
     */
    public void setIvEnvelopeOpenHead(String envelopeOpenHead)
    {
        GlideUtil.loadHeadImage(envelopeOpenHead).into(iv_envelope_open_head);
    }

    /**
     * 名字
     */
    public void setTvEnvelopeOpenName(String envelopeOpenName)
    {
        SDViewBinder.setTextView(tv_envelope_open_name, envelopeOpenName);
    }

    /**
     * 信息
     */
    public void setTvInfo(String info)
    {
        if (!TextUtils.isEmpty(info))
        {
            SDViewBinder.setTextView(tv_info, info);
        }
    }

    /**
     * 头像点击监听
     *
     * @param onHeadClickListener
     */
    public void setOnHeadClickListener(OnClickListener onHeadClickListener)
    {
        iv_envelope_open_head.setOnClickListener(onHeadClickListener);
    }

    /**
     * 查看手气点击监听
     *
     * @param onUserClickListener
     */
    public void setOnUserClickListener(OnClickListener onUserClickListener)
    {
        tv_user_red_envelope.setOnClickListener(onUserClickListener);
    }

    /**
     * 关闭点击监听
     *
     * @param closeClickListener
     */
    public void setCloseClickListener(OnClickListener closeClickListener)
    {
        ll_open_close.setOnClickListener(closeClickListener);
    }

}
