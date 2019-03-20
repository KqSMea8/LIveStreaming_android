package com.fanwe.libgame.adapter;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class GameBetCoinsOptionAdapter extends SDSimpleRecyclerAdapter<GameBetCoinsOptionModel>
{
    protected long mUserCoins;

    public GameBetCoinsOptionAdapter(List<GameBetCoinsOptionModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<GameBetCoinsOptionModel> holder, int position, GameBetCoinsOptionModel model)
    {
        ImageView iv_coins = holder.get(R.id.iv_coins);
        ImageView iv_coins_selected = holder.get(R.id.iv_coins_selected);
        TextView tv_coins_number = holder.get(R.id.tv_coins_number);

        tv_coins_number.setText(String.valueOf(model.getCoins()));
        if (mUserCoins >= model.getCoins())
        {
            iv_coins.setImageResource(R.drawable.bg_bet_coins);
            SDViewUtil.setVisibleOrGone(iv_coins_selected, model.isSelected());
            SDViewUtil.setTextViewColorResId(tv_coins_number, R.color.text_poker_bet_coins_number_enable);
        } else
        {
            iv_coins.setImageResource(R.drawable.layer_bet_coins_disabled);
            SDViewUtil.setInvisible(iv_coins_selected);
            SDViewUtil.setTextViewColorResId(tv_coins_number, R.color.text_poker_bet_coins_number_disabled);
        }
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType)
    {
        return R.layout.item_poker_bet_coins;
    }

    public void setUserCoins(long userCoins)
    {
        mUserCoins = userCoins;
        notifyDataSetChanged();
    }
}
