package com.fanwe.games.model;

import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/11/30.
 */

public class App_getGamesActModel extends BaseActModel {

    private GameMsgModel data;
    private int game_id;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public GameMsgModel getData() {
        return data;
    }

    public void setData(GameMsgModel data) {
        this.data = data;
    }
}
