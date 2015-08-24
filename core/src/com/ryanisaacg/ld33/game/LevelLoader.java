package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ryanisaacg.ld33.game.Components.Trap.Type;

import static com.ryanisaacg.ld33.game.Components.*;
public class LevelLoader
{
	private final char[][] characters;
	private String solids = "1A";
	
	public LevelLoader(String dat)
	{
		String[] lines = dat.split("\n");
		characters = new char[lines.length][lines[0].length()];
		for(int i = 0; i < lines.length && i < characters.length; i++)
			for(int j = 0; j < lines[i].length() && j < characters[i].length; j++)
				characters[i][j] = lines[i].charAt(j);
	}
	
	public Engine spawn(Engine engine, int tile)
	{
		Draw draw;
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				switch(characters[i][j])
				{
				//player
				case 'P':
					draw = new Draw(new TextureRegion(Textures.get("player")));
					draw.originX = 16;
					draw.originY = 16;
					Velocity speed = new Velocity(0, 0, Velocity.CollideBehavior.STOP);
					speed.lockDirection = true;
					engine.addEntity(new Entity()
					.add(new Control(Keys.RIGHT, Keys.UP, Keys.LEFT, Keys.DOWN, Keys.SPACE))
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(speed)
					.add(draw)
					.add(new Health(1, 0))
					.add(new Priority(0))
					.add(Follow.instance)
					);
					break;
				//spikes
				case 'X':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Hurt(Family.all(Geom.class).exclude(Jump.class)))
					.add(new Draw(new TextureRegion(Textures.get("spikes"))))
					);
					break;
				//turret
				case 'T':
					draw = new Draw(new TextureRegion(Textures.get("turret")));
					draw.originX = draw.region.getRegionWidth() / 2;
					draw.originY = draw.region.getRegionHeight() / 2;
					engine.addEntity(new Entity() 
					.add(new Geom(i * tile, j * tile, draw.region.getRegionWidth(), draw.region.getRegionHeight()))
					.add(new AI(AI.Type.TURRET))
					.add(draw)
					.add(new Health(1, 0))
					);
					break;
				//hunter
				case 'H':
					draw = new Draw(new TextureRegion(Textures.get("hunter")));
					draw.originX = 16;
					draw.originY = 16;
					speed = new Velocity(0, 0, Velocity.CollideBehavior.STOP);
					speed.lockDirection = true;
					engine.addEntity(new Entity() 
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(speed)
					.add(new AI(AI.Type.HUNTER))
					.add(draw)
					.add(new Hurt(Family.all(Control.class).exclude(Jump.class)))
					.add(new Health(1, 0))
					);
					break;
				//arrow trap
				case 'A':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Trap(Type.ARROW))
					.add(new Draw(new TextureRegion(Textures.get("arrowTrap"))))
					);
					break;
					//arrow trap
				case 'S':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Velocity(0, 0, Velocity.CollideBehavior.STOP))
					.add(new Trap(Type.SMASH))
					.add(new Draw(new TextureRegion(Textures.get("smash"))))
					.add(new Hurt(Family.all(Health.class, Geom.class, Velocity.class).exclude(Trap.class, Jump.class)))
					);
					break;
				//Controls- 1
				case 'C':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, 48, tile))
					.add(new Draw(new TextureRegion(Textures.get("arrowControls"))))
					);
					break;
				case 'c':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, 64, tile))
					.add(new Draw(new TextureRegion(Textures.get("space"))))
					);
					break;
				//Goal
				case 'G':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Goal(Goal.Type.REACH))
					.add(new Draw(new TextureRegion(Textures.get("goal"))))
					);
					break;
				//Mine
				case 'M':
					draw = new Draw(new TextureRegion(Textures.get("mines")));
					draw.originX = 8;
					draw.originY = 8;
					engine.addEntity(new Entity()
					.add(new Geom(i * tile + 16, j * tile + 16, 16, 16))
					.add(draw)
					.add(new Trap(Trap.Type.MINE))
					);
					break;
				//Wall
				case '1':
					engine.addEntity(new Entity()
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Draw(new TextureRegion(Textures.get("wall"))))
					);
					break;
				}
		boolean hasGoal = engine.getEntitiesFor(Family.all(Goal.class).get()).size() != 0;
		if(!hasGoal)
			for(Entity e : engine.getEntitiesFor(Family.all(AI.class, Hurt.class).get()))
				e.add(new Goal(Goal.Type.DESTROY));
		return engine;
	}
	
	public Viewport getViewport(Camera camera, int tile)
	{
		return new FitViewport(characters.length * tile, characters[0].length * tile, camera);
	}
	
	public TileMap getTilemap(int tile)
	{
		boolean[][] tiles = new boolean[characters.length][characters[0].length];
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				tiles[i][j] = solids.contains("" + characters[i][j]);
		return new TileMap(tiles, tile);
	}
}
