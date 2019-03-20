package com.fanwe.live.model;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class BodyParams {

    private String format;
    private int rate;
    private int dev_pid;
    private int channel;
    private String token;
    private String cuid;
    private long len;
    private String speech;

    public BodyParams(String format, int rate, int dev_pid, int channel, String token, String cuid, long len, String speech) {
        this.format = format;
        this.rate = rate;
        this.dev_pid = dev_pid;
        this.channel = channel;
        this.token = token;
        this.cuid = cuid;
        this.len = len;
        this.speech = speech;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getDev_pid() {
        return dev_pid;
    }

    public void setDev_pid(int dev_pid) {
        this.dev_pid = dev_pid;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }
}
