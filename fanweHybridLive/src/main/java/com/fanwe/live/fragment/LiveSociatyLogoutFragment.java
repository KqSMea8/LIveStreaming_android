package com.fanwe.live.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveFamilyApplyAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_sociaty_user_confirmActModel;
import com.fanwe.live.model.App_sociaty_user_listActModel;
import com.fanwe.live.model.PageModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 公会成员申请列表
 * Created by Administrator on 2016/9/26.
 */

public class LiveSociatyLogoutFragment extends BaseFragment
{
    @ViewInject(R.id.lv_fam_members)
    private SDProgressPullToRefreshListView lv_fam_members;

    private LiveFamilyApplyAdapter adapter;
    private List<UserModel> listModel;

    private PageModel pageModel = new PageModel();
    private int page = 1;

    private int is_agree = 1;//是否同意 （1：同意，2：拒绝）

    private SDTabUnderline tab_live_apply, tab_live_menb, tab_live_logout;
    private int quit_count;//公会成员退出申请人数
    private int state = 3;//0申请加入待审核、1加入申请通过、2 加入申请被拒绝，3申请退出公会待审核 4退出公会申请通过 5.退出公会申请被拒

    @Override
    protected int onCreateContentView()
    {
        return R.layout.frag_live_family_members;
    }

    @Override
    protected void init()
    {
        super.init();
        initData();
    }

    private void initData()
    {
        listModel = new ArrayList<>();
        adapter = new LiveFamilyApplyAdapter(listModel, getActivity());
        lv_fam_members.setAdapter(adapter);
        initPullToRefresh();

        /**
         * 用户详情
         */
        adapter.setItemClickCallback(new SDItemClickCallback<UserModel>()
        {
            @Override
            public void onItemClick(int position, UserModel item, View view)
            {
                Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
                intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, item.getUser_id());
                intent.putExtra(LiveUserHomeActivity.EXTRA_FAMILY, LiveUserHomeActivity.EXTRA_FAMILY);
                startActivity(intent);
            }
        });

        /**
         * 同意退出
         */
        adapter.setClickAgreeListener(new SDItemClickCallback<UserModel>()
        {
            @Override
            public void onItemClick(int position, UserModel item, View view)
            {
                is_agree = 1;
                int user_id = Integer.parseInt(item.getUser_id());
                sociatyMemberLogout(user_id, is_agree, item);
            }
        });

        /**
         * 拒绝退出公会
         */
        adapter.setClickRefuseListener(new SDItemClickCallback<UserModel>()
        {
            @Override
            public void onItemClick(int position, UserModel item, View view)
            {
                is_agree = 2;
                int user_id = Integer.parseInt(item.getUser_id());
                sociatyMemberLogout(user_id, is_agree, item);
            }
        });
    }

    private void initPullToRefresh()
    {
        lv_fam_members.setMode(PullToRefreshBase.Mode.BOTH);
        lv_fam_members.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                refreshViewer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                loadMoreViewer();
            }
        });
    }

    public void refreshViewer()
    {
        page = 1;
        requestFamilyMembersApplyList(false);
    }

    private void loadMoreViewer()
    {
        if (pageModel.getHas_next() == 1)
        {
            page++;
            requestFamilyMembersApplyList(true);
        } else
        {
            SDToast.showToast("没有更多数据了");
            lv_fam_members.onRefreshComplete();
        }
    }

    /**
     * 获取公会成员申请列表
     *
     * @param isLoadMore
     */
    private void requestFamilyMembersApplyList(final boolean isLoadMore)
    {
        UserModel dao = UserModelDao.query();
        CommonInterface.requestSociatyMembersList(dao.getSociety_id(), state, page, new AppRequestCallback<App_sociaty_user_listActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    quit_count = actModel.getQuit_count();
                    tab_live_apply.setTextTitle("成员申请(" + actModel.getApply_count() + ")");
                    tab_live_menb.setTextTitle("公会成员(" + actModel.getRs_count() + ")");
                    tab_live_logout.setTextTitle("退出申请(" + quit_count + ")");
                    pageModel = actModel.getPage();
                    SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                lv_fam_members.onRefreshComplete();
            }
        });
    }

    public void setLogoutRsCount(SDTabUnderline textView, SDTabUnderline textView2, SDTabUnderline textView3)
    {
        this.tab_live_apply = textView;
        this.tab_live_menb = textView2;
        this.tab_live_logout = textView3;
    }

    /**
     * 成员申请审核
     *
     * @param user_id
     * @param is_agree
     */
    private void sociatyMemberLogout(int user_id, int is_agree, final UserModel item)
    {
        CommonInterface.requestSociatyMemberLogout(user_id, is_agree, new AppRequestCallback<App_sociaty_user_confirmActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast(actModel.getError().toString());
                    adapter.removeData(item);
                    if (quit_count > 0)
                    {
                        quit_count = quit_count - 1;
                        tab_live_logout.setTextTitle("退出申请(" + quit_count + ")");
                    }
                }
            }
        });
    }
}
