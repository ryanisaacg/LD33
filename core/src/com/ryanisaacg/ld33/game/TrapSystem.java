package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import static com.ryanisaacg.ld33.game.AISystem.bullet;

public class TrapSystem extends EntitySystem
{
	private Engine engine;
	private Family trap, geom;
	private final int RNG = 250, SPD = 8;
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
		trap = Family.all(Components.Trap.class).get();
		geom = Family.all(Components.Geom.class, Components.Velocity.class).get();
	}
	
	@Override
	public void update(float deltaTime)
	{
		ImmutableArray<Entity> traps = engine.getEntitiesFor(trap);
		ImmutableArray<Entity> targets = engine.getEntitiesFor(geom);
		for(Entity e : traps)
		{
			Components.Trap trapType = Maps.trap.get(e);
			if(trapType.delay > 0)
			{
				trapType.delay--;
				continue;
			}
			switch(trapType.type)
			{
			case ARROW:
				for(Entity target : targets)
				{
					Components.Geom region = Maps.geom.get(target);
					Components.Geom trapRegion = Maps.geom.get(e);
					Rectangle tmp = Rectangle.tmp.set(region.x, region.y, region.width, region.height);
					if(tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, RNG, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, trapRegion.width, RNG))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x - RNG, trapRegion.y, trapRegion.width, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y - RNG, trapRegion.width, trapRegion.height)))
					{
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, 0, SPD));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, 0, -SPD));
						trapType.delay = 60;
					}
				}
				break;
			}
		}
	}
}
