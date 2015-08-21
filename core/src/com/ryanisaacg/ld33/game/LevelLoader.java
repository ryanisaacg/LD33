package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ryanisaacg.ld33.game.Components.Control;
import com.ryanisaacg.ld33.game.Components.Draw;
import com.ryanisaacg.ld33.game.Components.Geom;
import com.ryanisaacg.ld33.game.Components.Health;
import com.ryanisaacg.ld33.game.Components.Priority;
import com.ryanisaacg.ld33.game.Components.Velocity;
public class LevelLoader
{
	private final String csv;
	private final String[][] characters;
	
	public LevelLoader(String csv)
	{
		this.csv = csv.replace("\r", "\n");
		String[] lines = this.csv.split("\n");
		characters = new String[lines.length][lines[0].split(",").length];
		for(int i = 0; i < characters.length && i < lines.length; i++)
			for(int j = 0; j < characters[i].length && j < lines[i].split(",").length; j++)
				characters[i][j] = lines[i].split(",")[j];
	}
	
	public Engine spawn(Engine engine, int tile)
	{
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				if(characters[i][j] != null && characters[i][j].equals("Player"))
				{
					engine.addEntity(new Entity()
					.add(new Control(Keys.RIGHT, Keys.UP, Keys.LEFT, Keys.DOWN, Keys.SPACE))
					.add(new Geom(i * tile, j * tile, tile, tile))
					.add(new Velocity(0, 0))
					.add(new Health(1, 0))
					.add(new Priority(0))
					.add(new Draw(new TextureRegion(Textures.get("player"))))
					);
				}
		return engine;
	}
	
	public Viewport getViewport(Camera camera, int tile)
	{
		return new FitViewport(characters.length * tile, characters[0].length * tile, camera);
	}
}
