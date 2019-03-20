package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by shibx on 2016/11/23.
 */

public class App_startGameActModel extends BaseActModel {
    private GameStartModel data;
    private int game_log_id;        //游戏id(游戏轮数id)

    public int getGame_log_id() {
        return game_log_id;
    }

    public void setGame_log_id(int game_log_id) {
        this.game_log_id = game_log_id;
    }

    public GameStartModel getData() {
        return data;
    }

    public void setData(GameStartModel data) {
        this.data = data;
    }
}
