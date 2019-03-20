package com.fanwe.live.model;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class ServerData {

    /**
     * servertime : 1533808556630
     * act : servertime
     * ctl : app
     */

    private String servertime;
    private String act;
    private String ctl;

    @Override
    public String toString() {
        return "ServerData{" +
                "servertime='" + servertime + '\'' +
                ", act='" + act + '\'' +
                ", ctl='" + ctl + '\'' +
                '}';
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCtl() {
        return ctl;
    }

    public void setCtl(String ctl) {
        this.ctl = ctl;
    }
}
