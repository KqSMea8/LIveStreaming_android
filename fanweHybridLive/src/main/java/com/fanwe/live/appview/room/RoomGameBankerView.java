package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.model.GameBankerModel;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by shibx on 2017/2/24.
 * 庄家信息
 */

public class RoomGameBankerView extends RoomView
{

    private ImageView iv_user_img;
    private TextView tv_banker_nickname;
    private TextView tv_banker_coins;

    private GameBankerModel mBanker;

    public RoomGameBankerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomGameBankerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomGameBankerView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_game_banker;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        iv_user_img = find(R.id.iv_user_img);
        tv_banker_nickname = find(R.id.tv_banker_nickname);
        tv_banker_coins = find(R.id.tv_banker_coins);
    }

    public void setBnaker(GameBankerModel banker)
    {
        this.mBanker = banker;
        GlideUtil.loadHeadImage(mBanker.getBanker_img()).into(iv_user_img);
        tv_banker_nickname.setText(mBanker.getBanker_name());
        tv_banker_coins.setText("剩余底金:" + mBanker.getCoin());
    }
}
