package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen
{
	private Engine engine;
	private Entity thing;
	
	public GameScreen()
	{
		engine = new Engine();
		thing = new Entity();
		thing.add(new Components.Draw());
		Gdx.app.log("TEST", "" + thing.getComponent(Components.Geom.class));
	}
	
	@Override
	public void show()
	{
		
	}

	@Override
	public void render(float delta)
	{
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}

	@Override
	public void hide()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

}
