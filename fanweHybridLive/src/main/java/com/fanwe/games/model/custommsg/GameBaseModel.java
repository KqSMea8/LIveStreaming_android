package com.fanwe.games.model.custommsg;

import com.fanwe.live.model.custommsg.CustomMsg;

import java.util.List;

/**
 * Created by shibx on 2016/11/30.
 * 游戏相关数据对象，推送数据与接口数据共用
 */
public class GameBaseModel extends CustomMsg
{
    private int room_id;//房间号
    private String desc;//描述
    private int time;//剩余时长(秒)
    private int game_id;//游戏id
    private int game_log_id;//游戏轮数id
    private int game_status;//游戏状态，1：游戏开始，2：游戏结束
    private int game_action;//游戏操作，不同游戏略有不同：开始：1；下注：2；停止：3；结算：4；翻牌：5；
    private int auto_start; //游戏是否服务端自动开始1-是；0-否(仅请求游戏信息接口返回的时候此字段有效)
    private double timestamp; //服务端生成这条消息的时间戳
    private List<Integer> bet_option;//投注金额选项
    private List<String> option; //投注倍数

    public int getAuto_start()
    {
        return auto_start;
    }

    public void setAuto_start(int auto_start)
    {
        this.auto_start = auto_start;
    }

    public double getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(double timestamp)
    {
        this.timestamp = timestamp;
    }

    public int getGame_status()
    {
        return game_status;
    }

    public void setGame_status(int game_status)
    {
        this.game_status = game_status;
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public int getGame_id()
    {
        return game_id;
    }

    public void setGame_id(int game_id)
    {
        this.game_id = game_id;
    }

    public int getGame_log_id()
    {
        return game_log_id;
    }

    public void setGame_log_id(int game_log_id)
    {
        this.game_log_id = game_log_id;
    }

    public int getGame_action()
    {
        return game_action;
    }

    public void setGame_action(int game_action)
    {
        this.game_action = game_action;
    }

    public List<Integer> getBet_option()
    {
        return bet_option;
    }

    public void setBet_option(List<Integer> bet_option)
    {
        this.bet_option = bet_option;
    }

    public List<String> getOption()
    {
        return option;
    }

    public void setOption(List<String> option)
    {
        this.option = option;
    }
}
