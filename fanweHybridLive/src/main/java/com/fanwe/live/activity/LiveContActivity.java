package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomContributionView;
import com.fanwe.live.common.AppRuntimeWorker;

import org.xutils.view.annotation.ViewInject;

/**
 * 贡献榜，只作为页面容器
 * see {@link RoomContributionView}
 *
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-2 下午5:10:51 类说明
 */
public class LiveContActivity extends BaseTitleActivity {

    @ViewInject(R.id.view_room_cont)
    RoomContributionView roomSendMsgView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cont);
        init();
    }

    private void init() {
        initTitle();
        getExtraDatas();
    }

    private void initTitle() {
        mTitle.setMiddleTextTop(AppRuntimeWorker.getTicketName() + "贡献榜");
        if (isOpenRankingList()) {
            mTitle.initRightItem(1);
            mTitle.getItemRight(0).setTextTop("总榜");
            SDViewUtil.setTextViewColorResId(mTitle.getItemRight(0).mTvTop,R.color.main_color);
            SDViewUtil.setTextSizeSp(mTitle.getItemRight(0).mTvTop,18);
        }
    }

    private boolean isOpenRankingList() {
        InitActModel model = InitActModelDao.query();
        if (model != null) {
            if (model.getOpen_ranking_list() == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        if (isOpenRankingList())
        {
            Intent intent = new Intent(LiveContActivity.this, LiveRankingActivity.class);
            startActivity(intent);
        }
    }

    private void getExtraDatas() {
        Intent intent = getIntent();
        if(intent == null || !intent.hasExtra(RoomContributionView.EXTRA_USER_ID) || !intent.hasExtra(RoomContributionView.EXTRA_ROOM_ID)) {
            finish();
            return;
        }
        String user_id = intent.getStringExtra(RoomContributionView.EXTRA_USER_ID);
        int room_id = intent.getIntExtra(RoomContributionView.EXTRA_ROOM_ID,0);
        roomSendMsgView.setExtraDatas(user_id, room_id);
    }
}
