package com.ryanisaacg.ld33.game;

public class TileMap
{
	private boolean[][] map;
	public int tileSize;
	
	public TileMap(boolean[][] map, int tileSize)
	{
		this.map = map.clone();
		this.tileSize = tileSize;
	}
	
	public TileMap(float width, float height, int tileSize)
	{
		this(new boolean [(int)width / tileSize] [(int)height / tileSize], tileSize);
	}
	
	public int width()
	{
		return map.length * tileSize;
	}
	
	public int height()
	{
		return (map.length > 0) ? map[0].length * tileSize : 0; 
	}
	
	public boolean valid(float x, float y)
	{
		return x >= 0 && (int)x < width() && y >= 0 && (int)y < height();
	}
	
	public boolean free(float x, float y)
	{
		return valid(x, y) && map[(int)(x / tileSize)][(int)(y / tileSize)];
	}
	
	public boolean free(float x, float y, float width, float height)
	{
		boolean free = true;
		int i, j = 0;
		for(i = (int)x; free && i < x + width; i += tileSize)
			for(j = (int)y; free && j < y + height; j += tileSize)
				free = free(i, j);
		free &= free(x + width, y + height);
		return free;
	}
}
