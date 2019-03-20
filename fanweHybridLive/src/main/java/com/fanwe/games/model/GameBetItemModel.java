package com.fanwe.games.model;

import java.util.List;

/**
 * Created by shibx on 2016/11/25.
 */

public class GameBetItemModel {

    private List<Integer> bet;//总投注
    private List<Integer> user_bet;//自己投注

    public List<Integer> getBet() {
        return bet;
    }

    public void setBet(List<Integer> bet) {
        this.bet = bet;
    }

    public List<Integer> getUser_bet() {
        return user_bet;
    }

    public void setUser_bet(List<Integer> user_bet) {
        this.user_bet = user_bet;
    }
}
