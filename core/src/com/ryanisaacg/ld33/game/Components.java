package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Components
{
	public static class Geom implements Component
	{
		public float x, y, width, height;
		public Geom(float x, float y, float width, float height)
		{
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
	public static class Velocity implements Component
	{
		public float x, y;
		public Velocity(float x, float y)
		{
			this.x = x;
			this.y = y;
		}
		public Velocity(float x, float y, CollideBehavior collision)
		{
			this(x, y);
			behavior = collision;
		}
		public enum CollideBehavior { IGNORE, STOP, DIE };
		public CollideBehavior behavior = CollideBehavior.IGNORE;
	}
	public static class Health implements Component
	{
		public int health = 1;
		public Health(int health)
		{
			this.health = health;
		}
	}
	public static class Draw implements Component
	{
		public TextureRegion region;
		public float rotation, alpha = 1.0f, scaleX = 1, scaleY = 1, originX = 0, originY = 0;
		public Draw(TextureRegion region)
		{
			this.region = region;
		}
	}
	public static class Control implements Component
	{
		public final int RIGHT, UP, LEFT, DOWN;
		public Control(int right, int up, int left, int down)
		{
			this.RIGHT = right;
			this.LEFT = left;
			this.UP = up;
			this.DOWN = down;
		}
	}
	public static class MarkedForDeath implements Component
	{
		/*
		 * This class exists to tell the engine to remove the Entity
		 * It has no other purpose
		 */
		public final static MarkedForDeath mark = new MarkedForDeath();
	}
}
