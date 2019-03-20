package com.fanwe.live.event;

import com.fanwe.live.model.LiveSongModel;


public class ELiveSongDownload {
	public LiveSongModel songModel;

	
	public ELiveSongDownload() {
		songModel = null;
	}
	
	public  ELiveSongDownload(LiveSongModel songModel) {
		this.songModel = songModel;
		
	}
}
