package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import static com.ryanisaacg.ld33.game.Components.*;
import static com.ryanisaacg.ld33.game.Maps.*;

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
		switch(speed.behavior)
		{
			case STOP:
				if(!map.free(hitbox.x + speed.x, hitbox.y + speed.y, hitbox.width, hitbox.height))
					speed.x = 0;
					speed.y = 0;
				break;
			case DIE:
				if(!map.free(hitbox.x + speed.x, hitbox.y + speed.y, hitbox.width, hitbox.height))
					entity.add(MarkedForDeath.mark);
				break;
			default: break;
		}
		hitbox.x += speed.x;
		hitbox.y += speed.y;
	}
}
