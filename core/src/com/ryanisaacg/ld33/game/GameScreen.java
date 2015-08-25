package com.ryanisaacg.ld33.game;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.ryanisaacg.ld33.EndScreen;

public class GameScreen extends ScreenAdapter
{
	private final Engine engine;
	private final SpriteBatch batch;
	private LevelLoader loader;
	private final int TILE = 32, WIDTH, HEIGHT;
	private int level;
	private final GoalSystem goal;
	private ShapeRenderer shapes;
	private float r;
	private Game game;
	private int countdown;
	private EntitySystem system, physics;
	
	public GameScreen(int level, Game game)
	{
		this.level = level;
		FileHandle file = Gdx.files.internal("levels/lvl" + level);
		loader = new LevelLoader(getContents(file));
		batch = new SpriteBatch();
		engine = new Engine();
		shapes = new ShapeRenderer();
		
		loader.spawn(engine, TILE);
		TileMap map = loader.getTilemap(TILE);
		engine.addSystem(new PhysicsSystem(map));
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new ControlSystem());
		engine.addSystem(new HurtSystem());
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new AISystem());
		engine.addSystem(new TrapSystem());
		engine.addSystem(goal = new GoalSystem());
		new MusicSystem("sounds/track1.mp3", "sounds/track2.mp3", "sounds/track3.mp3");
		
		WIDTH = map.width;
		HEIGHT = map.height;
		r = 1;
		this.game = game;
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		r = (r + 0.01f) % 2;
		Gdx.gl20.glClearColor(Math.abs(1 - r), 0.0f, 0.0f, 1);
		shapes.setColor(0.1f, 0.0f, 0.0f, 0.0f);
		shapes.begin(ShapeType.Filled);
		shapes.rect(12, 12, WIDTH, HEIGHT);
		shapes.end();
		batch.begin();
		engine.update(delta);
		batch.end();
		ImmutableArray<Entity> entities = engine.getEntities();
		for(Entity entity : entities)
			if(Maps.marked.get(entity) != null)
				if(Maps.control.get(entity) != null)
				{
					if(countdown == 0) restart(false);
				}
				else
					engine.removeEntity(entity);
		if(countdown == 0 && (goal.isFinished() || Gdx.input.isKeyJustPressed(Keys.PAGE_UP)))
			next();
		if(countdown > 0)
		{
			countdown--;
			if(countdown == 1)
			{
				trueRestart();
				countdown = 0;
			}
		}
	}
	
	private void restart(boolean win)
	{
		Sounds.play(win ? "success" : "failure");
		countdown = 60;
		system = engine.getSystem(ControlSystem.class);
		engine.removeSystem(system);
		physics = engine.getSystem(PhysicsSystem.class);
		engine.removeSystem(physics);
		engine.removeSystem(goal);
	}
		
	private void trueRestart()
	{
		engine.addSystem(system);
		engine.addSystem(physics);
		engine.addSystem(goal);
		engine.removeAllEntities();
		loader.spawn(engine, TILE);
		engine.removeSystem(engine.getSystem(PhysicsSystem.class));
		engine.addSystem(new PhysicsSystem(loader.getTilemap(TILE)));
		Sounds.stopAll();
	}
	
	private void next()
	{
		level += 1;
		FileHandle file = Gdx.files.internal("levels/lvl" + level);
		if(file.exists())
		{
			loader = new LevelLoader(getContents(file));
			restart(true);
		}
		else
		{
			dispose();
			game.setScreen(new EndScreen());
		}
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void dispose()
	{
		Textures.dispose();
		Sounds.dispose();
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
