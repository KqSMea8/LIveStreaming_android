package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 音乐下载地址
 *
 * @author wxy
 */
public class MusicSearchModel extends BaseActModel {
    public List<SongListModel> song;

    public static class SongListModel {
     public String songid;
     public String songname;//无问西东
     public String artistname;//王菲
    }
}

