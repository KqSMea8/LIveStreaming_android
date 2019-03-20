package com.fanwe.libgame.dice.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.adapter.GameBetCoinsOptionAdapter;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;

import java.util.List;

/**
 * Created by yhz on 2017/6/23.
 */

public class DiceBetCoinsOptionAdapter extends GameBetCoinsOptionAdapter
{
    public DiceBetCoinsOptionAdapter(List<GameBetCoinsOptionModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_dice_bet_coins;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<GameBetCoinsOptionModel> holder, int position, GameBetCoinsOptionModel model)
    {
        ImageView iv_coins = holder.get(R.id.iv_coins);
        TextView tv_coins_number = holder.get(R.id.tv_coins_number);

        tv_coins_number.setText(String.valueOf(model.getCoins()));
        if (mUserCoins >= model.getCoins())
        {
            iv_coins.setSelected(model.isSelected());
        } else
        {
            iv_coins.setSelected(false);
        }
    }
}
