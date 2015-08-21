package com.ryanisaacg.ld33;

import com.badlogic.gdx.Game;
import com.ryanisaacg.ld33.game.GameScreen;

public class LD33Game extends Game 
{
	
	@Override
	public void create () 
	{
		setScreen(new GameScreen(new char[20][15]));
	}
}
