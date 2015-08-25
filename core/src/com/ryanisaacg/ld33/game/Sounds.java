package com.ryanisaacg.ld33.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class Sounds
{
	private static final HashMap<String, Sound> sounds;
	
	static 
	{
		sounds = new HashMap<String, Sound>();
	}
	private Sounds() {}
	
	public static void play(String sound)
	{
		if(sounds.containsKey(sound))
			sounds.get(sound).play();
		else
		{
			FileHandle file = Gdx.files.internal("sounds/" + sound + ".mp3");
			if(!file.exists())
				file = Gdx.files.internal("sounds/" + sound + ".ogg");
			Sound snd = Gdx.audio.newSound(file);
			sounds.put(sound, snd);
			snd.play();
		}
	}
	
	public static void stopAll()
	{
		sounds.values().forEach(snd -> snd.stop());
	}
	
	public static void dispose()
	{
		sounds.values().forEach(snd -> snd.dispose());
	}
}
