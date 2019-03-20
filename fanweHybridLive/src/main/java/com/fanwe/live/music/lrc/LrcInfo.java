package com.fanwe.live.music.lrc;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/16.
 */
public class LrcInfo {
    private String title;//music title
    private String artist;//artist name
    private String album;//album name
    private String bySomeBody;//the lrc maker
    private String offset;//the time delay or bring forward
    private List<LrcRow> rows;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getBySomeBody() {
        return bySomeBody;
    }
    public void setBySomeBody(String bySomeBody) {
        this.bySomeBody = bySomeBody;
    }
    public String getOffset() {
        return offset;
    }
    public void setOffset(String offset) {
        this.offset = offset;
    }

    public List<LrcRow> getRows() {
        return rows;
    }

    public void setRows(List<LrcRow> rows) {
        this.rows = rows;
    }
}
