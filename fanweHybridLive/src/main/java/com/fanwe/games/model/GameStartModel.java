package com.fanwe.games.model;

/**
 * Created by shibx on 2016/11/23.
 * 调用开始游戏的回调
 */

public class GameStartModel {
    private int game_id;
    private long create_time;
    private long long_time;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getLong_time() {
        return long_time;
    }

    public void setLong_time(long long_time) {
        this.long_time = long_time;
    }
}
