package com.ryanisaacg.ld33.game;

import static com.ryanisaacg.ld33.game.Maps.*;
import static com.ryanisaacg.ld33.game.Components.*;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


public class PhysicsSystem extends IteratingSystem
{
	private TileMap map;
	
	public PhysicsSystem(TileMap map)
	{
		super(Family.all(Geom.class, Velocity.class).get());
		this.map = map;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Geom hitbox = geom.get(entity);
		Velocity speed = velocity.get(entity);
		Jump entityJump = jump.get(entity);
		if(entityJump != null)
		{
			entityJump.duration -= 1;
			if(entityJump.duration == 0)
				entity.remove(Jump.class);
		}
		switch(speed.behavior)
		{
			case STOP:
				if(!map.free(hitbox.x + speed.x, hitbox.y + speed.y, hitbox.width, hitbox.height))
				{
					speed.x = 0;
					speed.y = 0;
				}
				break;
			case DIE:
				if(!map.free(hitbox.x + speed.x, hitbox.y + speed.y, hitbox.width, hitbox.height))
					entity.add(MarkedForDeath.mark);
				break;
			default: break;
		}
		hitbox.x += speed.x;
		hitbox.y += speed.y;
		Friction fric = friction.get(entity);
		if(fric != null)
		{
			speed.x *= 1 - fric.friction;
			speed.y *= 1 - fric.friction;
		}
	}
}
