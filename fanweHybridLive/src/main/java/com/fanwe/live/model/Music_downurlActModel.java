package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;
/**
 * 用户音乐列表
 * @author ldh
 *
 */
@SuppressWarnings("serial")
public class Music_downurlActModel extends BaseActModel{
	private LiveSongModel audio;

	public LiveSongModel getAudio() {
		return audio;
	}

	public void setAudio(LiveSongModel audio) {
		this.audio = audio;
	}
	
	
}
