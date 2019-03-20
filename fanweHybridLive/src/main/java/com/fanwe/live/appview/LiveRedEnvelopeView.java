package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * 未打开的红包view
 * Created by luodong on 2016/9/2.
 */
public class LiveRedEnvelopeView extends BaseAppView
{

    private ImageView iv_envelope_head;
    private TextView tv_envelope_name;
    private ImageView iv_open_redpacket;
    private View ll_close;

    public LiveRedEnvelopeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public LiveRedEnvelopeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LiveRedEnvelopeView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.include_live_red_envelope;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        iv_envelope_head = find(R.id.iv_envelope_head);
        tv_envelope_name = find(R.id.tv_envelope_name);
        iv_open_redpacket = find(R.id.iv_open_redpacket);
        ll_close = find(R.id.ll_close);

    }

    /**
     * 头像
     */
    public void setIvEnvelopeHead(String envelopeHead)
    {
        GlideUtil.loadHeadImage(envelopeHead).into(iv_envelope_head);
    }

    /**
     * 名字
     */
    public void setTvEnvelopeName(String envelopeName)
    {
        SDViewBinder.setTextView(tv_envelope_name, envelopeName);
    }

    /**
     * 头像点击监听
     *
     * @param envelopeHeadClickListener
     */
    public void setEnvelopeHeadClickListener(OnClickListener envelopeHeadClickListener)
    {
        iv_envelope_head.setOnClickListener(envelopeHeadClickListener);
    }

    /**
     * 红包点击监听
     *
     * @param openRedpacketClickListener
     */
    public void setOpenRedpacketClickListener(OnClickListener openRedpacketClickListener)
    {
        iv_open_redpacket.setOnClickListener(openRedpacketClickListener);
    }

    /**
     * 关闭点击监听
     *
     * @param closeClickListener
     */
    public void setCloseClickListener(OnClickListener closeClickListener)
    {
        ll_close.setOnClickListener(closeClickListener);
    }

}
