package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Components
{
	public static class Geom implements Component
	{
		public float x, y, width, height;
	}
	public static class Velocity implements Component
	{
		public float x, y;
	}
	public static class Health implements Component
	{
		public int health = 1;
	}
	public static class Draw implements Component
	{
		public TextureRegion region;
		public float rotation, alpha = 1.0f, scaleX = 1, scaleY = 1, originX = 0, originY = 0;
	}
}
