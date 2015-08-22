package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ryanisaacg.ld33.game.Components.Control;
import com.ryanisaacg.ld33.game.Components.Draw;
import com.ryanisaacg.ld33.game.Components.Follow;
import com.ryanisaacg.ld33.game.Components.Geom;
import com.ryanisaacg.ld33.game.Components.Health;
import com.ryanisaacg.ld33.game.Components.Hurt;
import com.ryanisaacg.ld33.game.Components.Jump;
import com.ryanisaacg.ld33.game.Components.Priority;
import com.ryanisaacg.ld33.game.Components.Velocity;
public class LevelLoader
{
	private final char[][] characters;
	
	public LevelLoader(String dat)
	{
		String[] lines = dat.split("\n");
		characters = new char[lines.length][lines[0].length()];
		for(int i = 0; i < lines.length; i++)
			for(int j = 0; j < lines[i].length(); j++)
				characters[i][j] = lines[i].charAt(j);
	}
	
	public Engine spawn(Engine engine, int tile)
	{
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				switch(characters[i][j])
				{
					//player
					case 'P':
						engine.addEntity(new Entity()
						.add(new Control(Keys.RIGHT, Keys.UP, Keys.LEFT, Keys.DOWN, Keys.SPACE))
						.add(new Geom(i * tile, j * tile, tile, tile))
						.add(new Velocity(0, 0))
						.add(new Health(1, 0))
						.add(new Priority(0))
						.add(new Draw(new TextureRegion(Textures.get("player"))))
						.add(Follow.instance)
						);
						break;
					//pit
					case '0':
						engine.addEntity(new Entity()
						.add(new Geom(i * tile, j * tile, tile, tile))
						.add(new Hurt(Family.all(Geom.class, Health.class).exclude(Jump.class)))
						.add(new Draw(new TextureRegion(Textures.get("enemy"))))
						);
						break;
					//light
					case 'L':
						//TODO: Light image
						engine.addEntity(new Entity()
						.add(new Geom(i * tile, j * tile, tile, tile))
						.add(new Hurt(Family.all(Geom.class, Health.class, Control.class)))
						);
				}
		return engine;
	}
	
	public Viewport getViewport(Camera camera, int tile)
	{
		return new FitViewport(characters.length * tile, characters[0].length * tile, camera);
	}
}
