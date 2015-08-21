package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter
{
	private Engine engine;
	private SpriteBatch batch;
	private Texture block, red;
	private Viewport viewport;
	private OrthographicCamera camera;
	private final int TILE = 32;
	
	public GameScreen(char[][] level)
	{
		camera = new OrthographicCamera();
		viewport = new FitViewport(level.length * TILE, level[0].length * TILE, camera);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		engine = new Engine();
		engine.addSystem(new PhysicsSystem(new TileMap(new boolean[20][15], 32)));
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new ControlSystem());
		engine.addSystem(new HurtSystem());
		block = new Texture(Gdx.files.internal("badlogic.jpg"));
		red = new Texture(Gdx.files.internal("enemy.png"));
		Entity entity = new Entity();
		entity.add(new Components.Geom(0, 32, 32, 32))
		.add(new Components.Draw(new TextureRegion(block)))
		.add(new Components.Control(Keys.D, Keys.W, Keys.A, Keys.S, Keys.SPACE))
		.add(new Components.Velocity(0, 2, Components.Velocity.CollideBehavior.STOP))
		.add(new Components.Health(3, 60))
		.add(new Components.Priority(1));
		engine.addEntity(entity);
		engine.addEntity(new Entity().add(new Components.Geom(0, 0, 16, 16)).
				add(new Components.Draw(new TextureRegion(red))).
				add(new Components.Hurt(Family.all(Components.Geom.class))).
				add(new Components.Priority(0)));
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
		block.dispose();
		red.dispose();
		batch.dispose();
	}

}
