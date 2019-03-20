package com.fanwe.games.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

import java.util.List;

/**
 * 历史记录适配器
 *
 * @author luodong
 * @version 创建时间：2016-12-6
 */
public class GameLogAdapter extends SDSimpleAdapter<Integer>
{
    public GameLogAdapter(List<Integer> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_game_log;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final Integer model)
    {
        ImageView iv_game_log_new = get(R.id.iv_game_log_new, convertView);

        ImageView iv_result_0 = get(R.id.iv_result_0, convertView);
        ImageView iv_result_1 = get(R.id.iv_result_1, convertView);
        ImageView iv_result_2 = get(R.id.iv_result_2, convertView);

        iv_result_0.setImageResource(R.drawable.ic_game_log_lost);
        iv_result_1.setImageResource(R.drawable.ic_game_log_lost);
        iv_result_2.setImageResource(R.drawable.ic_game_log_lost);

        switch (model)
        {
            case 1:
                iv_result_0.setImageResource(R.drawable.ic_game_log_win);
                break;
            case 2:
                iv_result_1.setImageResource(R.drawable.ic_game_log_win);
                break;
            case 3:
                iv_result_2.setImageResource(R.drawable.ic_game_log_win);
                break;
            default:
                break;
        }

        if (position == 0)
        {
            SDViewUtil.setVisible(iv_game_log_new);
        } else
        {
            SDViewUtil.setInvisible(iv_game_log_new);
        }
    }
}
