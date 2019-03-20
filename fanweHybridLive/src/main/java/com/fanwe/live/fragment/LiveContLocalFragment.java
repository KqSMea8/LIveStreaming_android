package com.fanwe.live.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.appview.room.RoomContributionView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_ContActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * 贡献榜-本次直播
 *
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-3 上午10:53:50 类说明
 */
public class LiveContLocalFragment extends LiveContBaseFragment
{
    public static final String TAG = "LiveContLocalFragment";

    @ViewInject(R.id.ll_do_not_contribute)
    private LinearLayout ll_do_not_contribute;

    private ImageView civ_head_me;
    private ImageView v_icon;
    private TextView tv_me;
    private TextView tv_me_number;

    protected int room_id;

    @Override
    protected void init() {
        getIntentExtra();
        super.init();
    }

    private void getIntentExtra() {
        room_id = getArguments().getInt(RoomContributionView.EXTRA_ROOM_ID);
    }

    @Override
    protected void register() {
        super.register();

        SDViewUtil.setGone(ll_do_not_contribute);

        View view = getActivity().getLayoutInflater().inflate(R.layout.frag_cont_head, null);
        civ_head_me = (ImageView) view.findViewById(R.id.civ_head_me);
        v_icon = (ImageView) view.findViewById(R.id.v_icon);
        tv_me = (TextView) view.findViewById(R.id.tv_me);
        tv_me_number = (TextView) view.findViewById(R.id.tv_me_number);

        view.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (app_ContActModel != null)
                {
                    UserModel user = app_ContActModel.getUser();
                    if (user != null)
                    {
                        String userid = user.getUser_id();
                        if (!TextUtils.isEmpty(userid))
                        {
                            Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                            intent.putExtra(RoomContributionView.EXTRA_USER_ID, user.getUser_id());
                            startActivity(intent);
                        } else
                        {
                            SDToast.showToast("userid为空");
                        }
                    }
                }
            }
        });

        list.getRefreshableView().addHeaderView(view);
    }

    @Override
    protected void requestCont(final boolean isLoadMore)
    {
        CommonInterface.requestCont(room_id, "", page, new AppRequestCallback<App_ContActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    app_ContActModel = actModel;
                    bindheadData(actModel);
                    SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                }
            }

            private void bindheadData(App_ContActModel actModel)
            {
                if (actModel != null)
                {
                    UserModel user = actModel.getUser();
                    if (user != null)
                    {
                        GlideUtil.loadHeadImage(user.getHead_image()).into(civ_head_me);
                        SDViewBinder.setTextView(tv_me, user.getNick_name());
                        if(!TextUtils.isEmpty(user.getV_icon())) {
                            SDViewUtil.setVisible(v_icon);
                            GlideUtil.load(user.getV_icon()) .into(v_icon);
                        } else {
                            SDViewUtil.setInvisible(v_icon);
                        }
                    }
                    SDViewBinder.setTextView(tv_me_number, actModel.getTotal_num() + "");
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                list.onRefreshComplete();
            }
        });
    }
}
