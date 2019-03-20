package com.fanwe.o2o.fragment;

import android.content.Intent;
import android.widget.LinearLayout;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.live.R;
import com.fanwe.live.activity.LivePrivateChatActivity;
import com.fanwe.live.appview.LiveChatC2CNewView;
import com.fanwe.live.model.LiveConversationListModel;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/10/31.
 */

public class O2OShoppingLiveImFragment extends BaseFragment
{
    @ViewInject(R.id.ll_content)
    private LinearLayout ll_content;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_chat_c2c;
    }

    @Override
    protected void init()
    {
        super.init();
        LiveChatC2CNewView view = new LiveChatC2CNewView(getActivity());
        view.hideRl_back();
        view.setClickListener(new LiveChatC2CNewView.ClickListener()
        {

            @Override
            public void onClickBack()
            {
                getActivity().finish();
            }
        });

        view.setOnChatItemClickListener(new LiveChatC2CNewView.OnChatItemClickListener()
        {
            @Override
            public void onChatItemClickListener(LiveConversationListModel itemLiveChatListModel)
            {
                Intent intent = new Intent(getActivity(), LivePrivateChatActivity.class);
                intent.putExtra(LivePrivateChatActivity.EXTRA_USER_ID, itemLiveChatListModel.getPeer());
                startActivity(intent);
            }
        });
        ll_content.addView(view);
        //传入数据
        view.requestData();
    }
}
