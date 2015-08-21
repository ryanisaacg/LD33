package com.ryanisaacg.ld33.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Textures
{
	private static final HashMap<String, Texture> textures;
	static
	{
		textures = new HashMap<String, Texture>();
	}
	private Textures() {}
	
	public static Texture get(String name)
	{
		if(textures.containsKey(name))
			return textures.get(name);
		else
		{
			Texture tex = new Texture(Gdx.files.internal(name + ".png"));
			textures.put(name, tex);
			return tex;
		}
	}
	
	public static void dispose()
	{
		for(Texture tex : textures.values())
			tex.dispose();
	}
}
