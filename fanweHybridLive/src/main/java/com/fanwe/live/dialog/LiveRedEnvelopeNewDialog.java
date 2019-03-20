package com.fanwe.live.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveRedEnvelopeAdapter;
import com.fanwe.live.appview.LiveRedEnvelopeOpenView;
import com.fanwe.live.appview.LiveRedEnvelopeView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_red_envelopeActModel;
import com.fanwe.live.model.App_user_red_envelopeActModel;
import com.fanwe.live.model.App_user_red_envelopeModel;
import com.fanwe.live.model.custommsg.CustomMsgRedEnvelope;

import org.xutils.x;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-27 下午7:27:15 类说明
 */
public class LiveRedEnvelopeNewDialog extends LiveBaseDialog
{
    private LiveRedEnvelopeAdapter adapter;
    private CustomMsgRedEnvelope customMsgRedEnvelope;

    private FrameLayout fl_content;// 红包列表
    private LiveRedEnvelopeOpenView liveRedEnvelopeOpenView;
    private LiveRedEnvelopeView liveRedEnvelopeView;

    public LiveRedEnvelopeNewDialog(Activity activity, CustomMsgRedEnvelope msg)
    {
        super(activity);
        this.customMsgRedEnvelope = msg;
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_live_red_envelope);
        setCanceledOnTouchOutside(true);
        paddingLeft(80);
        paddingRight(80);

        initView();
        initEvent();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //是否打开过红包
        if (!TextUtils.isEmpty(customMsgRedEnvelope.getRed_info()) && customMsgRedEnvelope.isOnclicked())
        {
            //打开
            createLiveRedEnvelopeOpenView();
        } else
        {
            //未打开
            createLiveRedEnvelopeView();
        }
    }

    /**
     * 创建未打开红包的view
     */
    private void createLiveRedEnvelopeView() {
        liveRedEnvelopeView = new LiveRedEnvelopeView(getContext());
        liveRedEnvelopeView.setIvEnvelopeHead(customMsgRedEnvelope.getSender().getHead_image());//头像
        liveRedEnvelopeView.setTvEnvelopeName(customMsgRedEnvelope.getSender().getNick_name());//昵称
        liveRedEnvelopeView.setOpenRedpacketClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDismissRunnable();
                request_red_envelope();//抢红包
            }
        });
        liveRedEnvelopeView.setEnvelopeHeadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击头像
            }
        });
        liveRedEnvelopeView.setCloseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveRedEnvelopeNewDialog.this.dismiss();//关闭
            }
        });
        fl_content.addView(liveRedEnvelopeView);
    }

    /**
     * 创建打开红包的view
     */
    private void createLiveRedEnvelopeOpenView() {
        liveRedEnvelopeOpenView = new LiveRedEnvelopeOpenView(getContext());
        liveRedEnvelopeOpenView.setIvEnvelopeOpenHead(customMsgRedEnvelope.getSender().getHead_image());//头像
        liveRedEnvelopeOpenView.setTvEnvelopeOpenName(customMsgRedEnvelope.getSender().getNick_name());//昵称
        if (!TextUtils.isEmpty(customMsgRedEnvelope.getRed_info()))
        {
            liveRedEnvelopeOpenView.setTvInfo(customMsgRedEnvelope.getRed_info());//信息
        }
        liveRedEnvelopeOpenView.setCloseClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveRedEnvelopeNewDialog.this.dismiss();//关闭
            }
        });
        liveRedEnvelopeOpenView.setOnHeadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击头像
            }
        });
        liveRedEnvelopeOpenView.setOnUserClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDismissRunnable();
                request_user_red_envelope();//查看手气
            }
        });
        fl_content.addView(liveRedEnvelopeOpenView);
    }

    /**
     *     抢红包
     */
    private void request_red_envelope()
    {
        CommonInterface.requestRed_envelope(customMsgRedEnvelope.getUser_prop_id(), new AppRequestCallback<App_red_envelopeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                String red_info;
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getDiamonds() > 0)
                    {
                        CommonInterface.requestMyUserInfo(null);
                        red_info = "恭喜您抢到" + actModel.getDiamonds() + "个秀豆";
                    } else
                    {
                        red_info = "手太慢，已发完";
                    }
                } else
                {
                    red_info = "手太慢，已发完";
                }
                customMsgRedEnvelope.setRed_info(red_info);
                customMsgRedEnvelope.setOnclicked(true);
                fl_content.removeAllViews();//移除fl中的未打开红包视图
                createLiveRedEnvelopeOpenView();//创建打开红包的view
            }
        });
    }

    /**
     * 查看手气
     */
    private void request_user_red_envelope()
    {
        CommonInterface.requestUser_red_envelope(customMsgRedEnvelope.getUser_prop_id(), new AppRequestCallback<App_user_red_envelopeActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    liveRedEnvelopeOpenView.setViewShowOrHide();//设置试图的显示与隐藏

                    List<App_user_red_envelopeModel> list = actModel.getList();
                    if (list != null && list.size() > 0)
                    {
                        bindData(list);
                    }
                }
            }

            private void bindData(List<App_user_red_envelopeModel> list)
            {
                adapter = new LiveRedEnvelopeAdapter(list, getOwnerActivity());
                //获取打开红包View里面的ListView,并设置adapter
                liveRedEnvelopeOpenView.getListView().setAdapter(adapter);
            }
        });
    }

    @Override
    public void show()
    {
        startDismissRunnable(6 * 1000);
        super.show();
    }
}
