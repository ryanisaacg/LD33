package com.ryanisaacg.ld33.game;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter
{
	private Engine engine;
	private SpriteBatch batch;
	private Viewport viewport;
	private OrthographicCamera camera;
	private final int TILE = 32;
	
	public GameScreen(FileHandle file)
	{
		LevelLoader loader = new LevelLoader(getContents(file));
		camera = new OrthographicCamera();
		viewport = loader.getViewport(camera, TILE);
		Gdx.app.log("DIMENSIONS", viewport.getWorldWidth() + " x " + viewport.getWorldHeight());
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		engine = new Engine();
		loader.spawn(engine, TILE);
		
		engine.addSystem(new PhysicsSystem(new TileMap(new boolean[20][15], 32)));
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new ControlSystem());
		engine.addSystem(new HurtSystem());
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.setProjectionMatrix(camera.projection);
		batch.begin();
		engine.update(delta);
		batch.end();
		ImmutableArray<Entity> entities = engine.getEntities();
		for(Entity entity : entities)
			if(Maps.marked.get(entity) != null)
				engine.removeEntity(entity);
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height);
	}

	@Override
	public void dispose()
	{
		Textures.dispose();
	}
	
	private String getContents(FileHandle file)
	{
		BufferedReader reader = new BufferedReader(file.reader());
		String contents = "", line;
		try
		{
			while((line = reader.readLine()) != null)
				contents += line + "\n";
			reader.close();
		} catch (IOException e)
		{
			Gdx.app.log("ERR", e.getMessage());
		}
		return contents;
	}
}
