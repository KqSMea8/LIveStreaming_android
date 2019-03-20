package com.fanwe.games.dialog;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ListView;

import com.fanwe.games.adapter.GameLogAdapter;
import com.fanwe.games.constant.GameType;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.List;

/**
 * 游戏历史记录
 */
public class GameLogDialog extends SDDialogBase
{
    private ListView lv_log;
    private ImageView iv_star_0;
    private ImageView iv_star_1;
    private ImageView iv_star_2;

    private GameLogAdapter mAdapter;

    public GameLogDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_game_log);
        lv_log = (ListView) findViewById(R.id.lv_log);
        iv_star_0 = (ImageView) findViewById(R.id.iv_star_0);
        iv_star_1 = (ImageView) findViewById(R.id.iv_star_1);
        iv_star_2 = (ImageView) findViewById(R.id.iv_star_2);
        paddingLeft(SDViewUtil.dp2px(50));
        paddingRight(SDViewUtil.dp2px(50));
        paddingTop(0).paddingBottom(0);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setAdapter();
    }

    private void setImageRes(int res_1, int res_2, int res_3)
    {
        iv_star_0.setImageResource(res_1);
        iv_star_1.setImageResource(res_2);
        iv_star_2.setImageResource(res_3);
    }

    public void setGameId(int gameId)
    {
        if (gameId == GameType.GOLD_FLOWER)
        {
            setImageRes(R.drawable.ic_goldflower_star_0,
                    R.drawable.ic_goldflower_star_1,
                    R.drawable.ic_goldflower_star_2);
        } else if (gameId == GameType.BULL)
        {
            setImageRes(R.drawable.ic_bull_star_0,
                    R.drawable.ic_bull_star_1,
                    R.drawable.ic_bull_star_2);
        }
    }

    private void setAdapter()
    {
        mAdapter = new GameLogAdapter(null, getOwnerActivity());
        lv_log.setAdapter(mAdapter);
    }

    public void setData(List<Integer> listModel)
    {
        mAdapter.updateData(listModel);
    }
}
