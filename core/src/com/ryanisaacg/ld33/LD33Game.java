package com.ryanisaacg.ld33;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ryanisaacg.ld33.game.GameScreen;

public class LD33Game extends Game 
{
	
	@Override
	public void create () 
	{
		setScreen(new GameScreen(Gdx.files.internal("lvl1")));
	}
}
