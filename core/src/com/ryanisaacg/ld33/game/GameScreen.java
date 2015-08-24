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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter
{
	private final Engine engine;
	private final SpriteBatch batch;
	private LevelLoader loader;
	private final int TILE = 32;
	private int level;
	private final GoalSystem goal;
	
	public GameScreen(int level)
	{
		this.level = level;
		FileHandle file = Gdx.files.internal("levels/lvl" + level);
		loader = new LevelLoader(getContents(file));
		batch = new SpriteBatch();
		engine = new Engine();
		loader.spawn(engine, TILE);
		
		engine.addSystem(new PhysicsSystem(loader.getTilemap(TILE)));
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new ControlSystem());
		engine.addSystem(new HurtSystem());
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new AISystem());
		engine.addSystem(new TrapSystem());
		engine.addSystem(goal = new GoalSystem());
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		engine.update(delta);
		batch.end();
		ImmutableArray<Entity> entities = engine.getEntities();
		for(Entity entity : entities)
			if(Maps.marked.get(entity) != null)
				if(Maps.control.get(entity) != null)
					restart();
				else
					engine.removeEntity(entity);
		if(goal.isFinished())
			next();
	}
		
	private void restart()
	{
		engine.removeAllEntities();
		loader.spawn(engine, TILE);
		engine.removeSystem(engine.getSystem(PhysicsSystem.class));
		engine.addSystem(new PhysicsSystem(loader.getTilemap(TILE)));
	}
	
	private void next()
	{
		level += 1;
		FileHandle file = Gdx.files.internal("levels/lvl" + level);
		loader = new LevelLoader(getContents(file));
		restart();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void dispose()
	{
		Textures.dispose();
		batch.dispose();
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
