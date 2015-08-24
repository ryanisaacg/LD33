package com.ryanisaacg.ld33;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.ryanisaacg.ld33.game.GameScreen;

public class LD33Game extends Game 
{
	
	@Override
	public void create () 
	{
		int level = 1;
		FileHandle file = Gdx.files.internal("lvl" + level);
		while(file.exists())
		{
			setScreen(new GameScreen(file));
			level += 1;
			file = Gdx.files.internal("lvl" + level);
		}
	}
}
