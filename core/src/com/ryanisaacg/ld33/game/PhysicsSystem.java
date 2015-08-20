package com.ryanisaacg.ld33.game;

import static com.ryanisaacg.ld33.game.Maps.geom;
import static com.ryanisaacg.ld33.game.Maps.velocity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ryanisaacg.ld33.game.Components.Geom;
import com.ryanisaacg.ld33.game.Components.MarkedForDeath;
import com.ryanisaacg.ld33.game.Components.Velocity;

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
