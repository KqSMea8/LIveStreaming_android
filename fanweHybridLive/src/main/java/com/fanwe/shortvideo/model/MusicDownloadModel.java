package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 音乐下载地址
 *
 * @author wxy
 */
public class MusicDownloadModel extends BaseActModel {
    public MusicModel data;

    public static class MusicModel {
        public List<SongModel> songList;
    }
    public static class SongModel {
     public long songId;
     public String songName;//无问西东
     public String artistName;//王菲
     public String songLink;//http://zhangmenshiting.qianqian.com/data2/music/b008dff3c9df62831582ddea13f64921/569080852/5690808291518292861128.mp3?xcode=c9058fe4e4a67e6daca186416cbdae39
    }
}

