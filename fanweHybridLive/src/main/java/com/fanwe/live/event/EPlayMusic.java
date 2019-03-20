package com.fanwe.live.event;

import com.fanwe.live.model.LiveSongModel;

public class EPlayMusic
{
	public LiveSongModel songModel;
	public EPlayMusic(LiveSongModel model) {
		songModel = model;
	}
	public EPlayMusic() {
		songModel = null;
	}
}
