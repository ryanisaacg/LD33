package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input.Keys;

import static com.ryanisaacg.ld33.game.Components.*;
public class LevelLoader
{
	private final String csv;
	private final String[][] characters;
	
	public LevelLoader(String csv)
	{
		this.csv = csv.replace("\r", "");
		String[] lines = this.csv.split("\n");
		characters = new String[lines.length][lines[0].length()];
		
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				characters[i][j] = lines[i].split(",")[j];
	}
	
	public Engine spawn(Engine engine, int tile)
	{
		for(int i = 0; i < characters.length; i++)
			for(int j = 0; j < characters[i].length; j++)
				switch(characters[i][j])
				{
					case "Player":
						engine.addEntity(new Entity()
						.add(new Control(Keys.RIGHT, Keys.UP, Keys.LEFT, Keys.DOWN, Keys.SPACE))
						.add(new Geom(i * tile, j * tile, tile, tile))
						.add(new Velocity(0, 0))
						.add(new Health(1, 0))
						.add(new Priority(0))
						
						);
				}
		return engine;
	}
}
