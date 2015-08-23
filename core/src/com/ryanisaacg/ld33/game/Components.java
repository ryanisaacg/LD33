package com.ryanisaacg.ld33.game;

import java.util.HashMap;
import java.util.function.Function;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.Family.Builder;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

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
		public boolean overlaps(Geom other)
		{
			Rectangle.tmp.set(x, y, width, height);
			Rectangle.tmp2.set(other.x, other.y, other.width, other.height);
			return Rectangle.tmp.overlaps(Rectangle.tmp2);
		}
	}
	
	public static class Velocity implements Component
	{
		public float x, y;
		public Velocity(float x, float y)
		{
			this.x = x;
			this.y = y;
			lockDirection = false;
		}
		public Velocity(float x, float y, CollideBehavior collision)
		{
			this(x, y);
			behavior = collision;
		}
		public enum CollideBehavior { IGNORE, STOP, DIE };
		public CollideBehavior behavior = CollideBehavior.IGNORE;
		public boolean lockDirection;
	}
	
	public static class Friction implements Component
	{
		public float friction;
		public Friction(float amt)
		{
			friction = amt;
		}
	}
	
	public static class Health implements Component
	{
		public int health = 1, countdown;
		public final int maxCountdown;
		public Health(int health, int maxCountdown)
		{
			this.health = health;
			countdown = 0;
			this.maxCountdown = maxCountdown;
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
		public final int RIGHT, UP, LEFT, DOWN, JUMP;
		public Control(int right, int up, int left, int down, int jump)
		{
			this.RIGHT = right;
			this.LEFT = left;
			this.UP = up;
			this.DOWN = down;
			this.JUMP = jump;
		}
	}
	
	public static class MarkedForDeath implements Component
	{
		/*
		 * This class exists to tell the engine to remove the Entity
		 * It has no other purpose
		 */
		public final static MarkedForDeath mark = new MarkedForDeath();
		private MarkedForDeath() {}
	}
	
	public static class Jump implements Component
	{
		public float duration;
		public final float maxDuration;
		public Jump(float timeInAir)
		{
			duration = timeInAir;
			maxDuration = duration;
		}
	}
	
	public static class Hurt implements Component
	{
		public final Family target, current;
		public Hurt(Builder hurtEntities)
		{
			this(hurtEntities, null);
		}
		
		public Hurt(Builder hurtEntities, Family statusToHurt)
		{
			target = hurtEntities.get();
			current = statusToHurt;
		}
	}
	
	public static class Priority implements Component
	{
		public int priority;
		public Priority(int priority)
		{
			this.priority = priority;
		}
	}
	
	public static class Animation implements Component
	{
		public final HashMap<String, TextureRegion[]> animations;
		public final HashMap<Function<Entity, Boolean>, String> triggers;
		public int frame;
		public String current;
		
		public Animation()
		{
			animations = new HashMap<String, TextureRegion[]>();
			triggers = new HashMap<Function<Entity, Boolean>, String>();
			frame = 0;
		}
		
		public void add(String name, Function<Entity, Boolean> trigger, TextureRegion... textures)
		{
			triggers.put(trigger, name);
			animations.put(name, textures);
		}
	}
	
	public static class Follow implements Component
	{
		private Follow() {}
		public final static Follow instance = new Follow();
	}
	
	public static class AI implements Component
	{
		public enum Type { TURRET, HUNTER, HUNTER_KILLER };
		public Type type;
		public int delay;
		
		public AI(Type type)
		{
			this.type = type;
		}
	}

	public static double distance(Geom g1, Geom g2)
	{
		return Math.sqrt(Math.pow(g1.x + g1.width / 2 - g2.x - g2.width / 2, 2) 
				+ Math.pow(g1.y + g1.height / 2 - g2.y - g2.height / 2, 2));
	}
}
