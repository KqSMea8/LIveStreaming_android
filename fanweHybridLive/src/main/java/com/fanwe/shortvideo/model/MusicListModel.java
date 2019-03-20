package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 在线音乐
 *
 * @author wxy
 */
public class MusicListModel extends BaseActModel {
    public List<MusicItemModel> song_list;

    public static class MusicItemModel {
        public String title;
        public String author;
        public String song_id;
        public String lrclink;}

}

