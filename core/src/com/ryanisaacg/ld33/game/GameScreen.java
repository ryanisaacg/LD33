package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Input.Keys;

public class GameScreen implements Screen
{
	private Engine engine;
	private SpriteBatch batch;
	private Entity thing;
	private Texture block, player, enemy;
	
	public GameScreen()
	{
		batch = new SpriteBatch();
		engine = new Engine();
		engine.addSystem(new PhysicsSystem(new TileMap(new boolean[20][15], 32)));
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new ControlSystem());
		block = new Texture(Gdx.files.internal("badlogic.jpg"));
		Entity entity = new Entity();
		entity.add(new Components.Geom(0, 32, 32, 32));
		entity.add(new Components.Draw(new TextureRegion(block)));
		entity.add(new Components.Control(Keys.D, Keys.W, Keys.A, Keys.S));
		engine.addEntity(entity);
	}
	
	@Override
	public void show()
	{
		
	}

	@Override
	public void render(float delta)
	{
		batch.begin();
		engine.update(delta);
		batch.end();
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
