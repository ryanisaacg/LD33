package com.ryanisaacg.ld33.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class MusicSystem
{
	private final List<Music> music;
	private final OnCompletionListener complete;

	private MusicSystem(List<Music> music)
	{
		this.music = music;
		complete = track -> music.get((music.indexOf(track) + 1 ) % music.size()).play();
		this.music.forEach(track -> track.setOnCompletionListener(complete));
		this.music.get(0).play();
	}
	public MusicSystem(Music... music)
	{
		this(Arrays.asList(music));
	}
	
	public MusicSystem(String... files)
	{
		music = new ArrayList<Music>();
		Arrays.asList(files).forEach(filename -> music.add(Gdx.audio.newMusic(Gdx.files.internal(filename))));
		complete = track -> music.get((music.indexOf(track) + 1 ) % music.size()).play();
		this.music.forEach(track -> track.setOnCompletionListener(complete));
		this.music.get(0).play();
	}
}
