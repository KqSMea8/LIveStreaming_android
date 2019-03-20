package com.fanwe.o2o.dialog;

import android.app.Activity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.o2o.appview.O2OShoppingPodCastView;
import com.fanwe.o2o.event.O2OEShoppingCartDialogShowing;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.greenrobot.event.EventBus;

/**
 * 购物直播商品列表Dialog
 * Created by Administrator on 2016/9/18.
 */
public class O2OShoppingPodCastDialog extends SDDialogBase
{
    @ViewInject(R.id.ll_pod_cast)
    private LinearLayout ll_pod_cast;
    private O2OShoppingPodCastView shoppingPodCastView;

    private String createrId;//主播Id

    public O2OShoppingPodCastDialog(Activity activity, String id)
    {
        super(activity);
        createrId = id;
        init();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_pod_cast);
        paddings(0);
        x.view().inject(this, getContentView());
        addPodCastView();
    }

    private void addPodCastView()
    {
        int screenHeight = (SDViewUtil.getScreenHeight() / 3 * 2);
        shoppingPodCastView = new O2OShoppingPodCastView(getOwnerActivity(), createrId, this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight);
        ll_pod_cast.addView(shoppingPodCastView, lp);
        eventDialogIsDismiss(true);
    }

    @Override
    public void showBottom()
    {
        super.showBottom();
        setScreenBgLight();
    }

    /**
     * 设置屏幕背景变亮
     */
    private void setScreenBgLight()
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        lp.dimAmount = 0f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        eventDialogIsDismiss(false);
    }

    /**
     * 发送event事件
     */
    private void eventDialogIsDismiss(boolean isShow)
    {
        O2OEShoppingCartDialogShowing event = new O2OEShoppingCartDialogShowing();
        event.isShowing = isShow;
        EventBus.getDefault().post(event);
    }
}
