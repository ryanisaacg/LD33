package com.ryanisaacg.ld33.game;


public class TileMap
{
	private boolean[][] map;
	public final int tileSize, width, height;
	
	public TileMap(boolean[][] map, int tileSize)
	{
		super();
		this.map = map;
		this.tileSize = tileSize;
		width = map.length * tileSize;
		height = (map.length > 0) ? map[0].length * tileSize : 0;
	}
	
	public boolean valid(float x, float y)
	{
		return x >= 0 
				&& (int)x < width 
				&& y >= 0 
				&& (int)y < height;
	}
	
	public boolean free(float x, float y)
	{
		return valid(x, y) && !map[(int)(x / tileSize)][(int)(y / tileSize)];
	}
	
	public boolean free(float x, float y, float width, float height)
	{
		boolean free = true;
		for(int i = (int)x; free && i < x + width; i += tileSize)
		{
			for(int j = (int)y; free && j < y + height; j += tileSize)
			{
				free &= free(i, j);
			}
		}
		free &= free(x + width, y + height);
		return free;
	}
}
