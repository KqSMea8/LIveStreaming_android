package com.fanwe.live.model;

import java.io.File;

import android.R.bool;
import android.text.TextUtils;

import com.fanwe.live.LiveSongDownloadManager;


/**
 * 背景音乐的item实体
 * 
 * @author ldh
 * @date 2016-5-18 上午11:03:21
 */
public class LiveSongModel 
{
	private String audio_id; // ID
	private String audio_link; // 音乐下载链接
	private String lrc_link;  // 歌词下载链接
	private String lrc_content; // 歌词内容
	private String audio_name; // 歌曲名
	private String artist_name; // 演唱者
	private long time_len; // 时长（秒）

	// 自定义字段
	private int progress;
	private int status; // 状态：0 正常  1下载中 2 下载成功 -1 下载失败
	private boolean isCached;
	
	
	public String getAudio_id() {
		return audio_id;
	}
	public void setAudio_id(String audio_id) {
		this.audio_id = audio_id;
	}
	public long getTime_len() {
		return time_len;
	}
	public void setTime_len(long time_len) {
		this.time_len = time_len;
	}

	public String getAudio_link() {
		return audio_link;
	}
	public void setAudio_link(String audio_link) {
		this.audio_link = audio_link;
	}
	public String getLrc_link() {
		return lrc_link;
	}
	public void setLrc_link(String lrc_link) {
		this.lrc_link = lrc_link;
	}
	public String getAudio_name() {
		return audio_name;
	}
	public void setAudio_name(String audio_name) {
		this.audio_name = audio_name;
	}
	public String getArtist_name() {
		return artist_name;
	}
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	
	
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isCached() {
		return isCached;
	}
	public void setCached(boolean isCached) {
		this.isCached = isCached;
	}
	
	public String getLrc_content() {
		return lrc_content;
	}
	public void setLrc_content(String lrc_content) {
		this.lrc_content = lrc_content;
	}
	
	public String getMusicPath() {
		return LiveSongDownloadManager.getInstance().getSongFilePath(this);
	}
	
	public String getLrcPath() {
		return LiveSongDownloadManager.getInstance().getLrcFilePath(this);
	}
	
	public boolean isMusicExist() {
		String path = getMusicPath();
		if (TextUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
		return file.exists();
	}
	
}
