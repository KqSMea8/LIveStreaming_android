package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30 0030.
 */

public class SpeechToText {
    /**
     * corpus_no : 6433214037620997779
     * err_msg : success.
     * err_no : 0
     * result : ["北京科技馆，"]
     * sn : 371191073711497849365
     */

    private String corpus_no;
    private String err_msg;
    private int err_no;
    private String sn;
    private List<String> result;

    @Override
    public String toString() {
        return "SpeechToText{" +
                "corpus_no='" + corpus_no + '\'' +
                ", err_msg='" + err_msg + '\'' +
                ", err_no=" + err_no +
                ", sn='" + sn + '\'' +
                ", result=" + result +
                '}';
    }

    public String getCorpus_no() {
        return corpus_no;
    }

    public void setCorpus_no(String corpus_no) {
        this.corpus_no = corpus_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
