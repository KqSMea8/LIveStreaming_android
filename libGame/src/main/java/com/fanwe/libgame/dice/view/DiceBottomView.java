package com.fanwe.libgame.dice.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.dice.adapter.DiceBetCoinsOptionAdapter;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.view.GameBottomView;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.view.SDRecyclerView;

/**
 * Created by yhz on 2017/6/23.
 */

public class DiceBottomView extends GameBottomView
{
    public DiceBottomView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public DiceBottomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public DiceBottomView(Context context)
    {
        super(context);
    }

    @Override
    protected void initLayout()
    {
        setContentView(R.layout.view_dice_bottom);
    }

    @Override
    protected void initAdapter()
    {
        mAdapter = new DiceBetCoinsOptionAdapter(null, (Activity) getContext());
        mAdapter.setItemClickCallback(new SDItemClickCallback<GameBetCoinsOptionModel>()
        {
            @Override
            public void onItemClick(int position, GameBetCoinsOptionModel item, View view)
            {
                performSelect(position);
            }
        });
        rv_coins.setAdapter(mAdapter);
    }
}
