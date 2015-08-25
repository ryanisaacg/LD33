package com.ryanisaacg.ld33;

import com.badlogic.gdx.Game;

public class LD33Game extends Game 
{
	
	@Override
	public void create () 
	{
		setScreen(new BeginScreen(this));
	}
}
