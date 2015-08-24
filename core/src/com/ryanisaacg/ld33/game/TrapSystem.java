package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.ryanisaacg.ld33.game.Components.Velocity.CollideBehavior;

public class TrapSystem extends EntitySystem
{
	private Engine engine;
	private Family trap, geom;
	private final int RNG = 250, SPD = 12, SRNG = 400;
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
		trap = Family.all(Components.Trap.class).get();
		geom = Family.all(Components.Geom.class, Components.Velocity.class).get();
	}
	
	private Entity bullet(float x, float y, float width, float height, float xspeed, float yspeed)
	{
		Components.Velocity velocity = new Components.Velocity(xspeed, yspeed, CollideBehavior.DIE);
		velocity.lockDirection = true;
		return new Entity()
		.add(new Components.Geom(x, y, width, height))
		.add(velocity)
		.add(new Components.Draw(new TextureRegion(Textures.get("bullet"))))
		.add(new Components.Hurt(Family.all(Components.Geom.class, Components.Health.class).
				exclude(Components.Jump.class, Components.Trap.class)))
		;
	}
	
	@Override
	public void update(float deltaTime)
	{
		ImmutableArray<Entity> traps = engine.getEntitiesFor(trap);
		ImmutableArray<Entity> targets = engine.getEntitiesFor(geom);
		for(Entity e : traps)
		{
			Components.Trap trapType = Maps.trap.get(e);
			if(trapType.delay > 0 && trapType.type != Components.Trap.Type.SENSOR)
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
					//Rectangle tmp = Rectangle.tmp.set(region.x, region.y, region.width, region.height);
					if(Components.distance(region, trapRegion) <= RNG)
							/*Old system for targeting: buggy, but I don't know why
					tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, trapRegion.width + RNG, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, trapRegion.width, trapRegion.height + RNG))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x - RNG, trapRegion.y, trapRegion.width, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y - RNG, trapRegion.width, trapRegion.height)))*/
					{
						engine.addEntity(bullet(trapRegion.x + trapRegion.width, trapRegion.y + trapRegion.height/2, 8, 4, SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height, 8, 4, 0, SPD));
						engine.addEntity(bullet(trapRegion.x, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y, 8, 4, 0, -SPD));
						Sounds.play("laser-gun");
						trapType.delay = 30;
					}
				}
				break;
			case MINE:
				for(Entity target : targets)
				{
					if(Maps.velocity.get(target).behavior == Components.Velocity.CollideBehavior.DIE) continue;
					Components.Geom region = Maps.geom.get(target);
					Components.Geom trapRegion = Maps.geom.get(e);
					//Rectangle tmp = Rectangle.tmp.set(region.x, region.y, region.width, region.height);
					if(Components.distance(region, trapRegion) <= RNG /2)
					{
						engine.addEntity(bullet(trapRegion.x + trapRegion.width, trapRegion.y + trapRegion.height/2, 8, 4, SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height, 8, 4, 0, SPD));
						engine.addEntity(bullet(trapRegion.x, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, 0));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y, 8, 4, 0, -SPD));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, SPD, SPD));
						engine.addEntity(bullet(trapRegion.x - trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, SPD));
						engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y - trapRegion.height/2, 8, 4, SPD, -SPD));
						engine.addEntity(bullet(trapRegion.x - trapRegion.width/2, trapRegion.y - trapRegion.height/2, 8, 4, -SPD, -SPD));
						Sounds.play("explosion");
						trapType.delay = 30;
						e.add(Components.MarkedForDeath.mark);
					}
				}
				break;
			case SMASH:
				Components.Velocity velocity = Maps.velocity.get(e);
				for(Entity target : targets)
				{
					if(target == e || velocity.x != 0 || velocity.y != 0)
						continue;
					Components.Geom region = Maps.geom.get(target);
					Components.Geom trapRegion = Maps.geom.get(e);
					Rectangle tmp = Rectangle.tmp.set(region.x, region.y, region.width, region.height);
					if(tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, SRNG, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y, trapRegion.width, SRNG))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x - SRNG, trapRegion.y, trapRegion.width, trapRegion.height))
							|| tmp.overlaps(Rectangle.tmp2.set(trapRegion.x, trapRegion.y - SRNG, trapRegion.width, trapRegion.height)))
					{
						float x, y, len;
						x = region.x - trapRegion.x;
						y = region.y - trapRegion.y;
						len = (float)Math.sqrt(x * x + y * y);
						velocity.x = x / len * SPD;
						velocity.y = y / len * SPD;
					}
				}
				break;
			case SENSOR:
				Components.Geom trapRegion = Maps.geom.get(e);
				Gdx.app.log("TRAP", "" + trapType.delay);
				if(trapType.delay < 120)
				{
					trapType.delay--;
					Components.Draw img = Maps.draw.get(e);
					if(trapType.delay < 60)
						if(img.region.getTexture() == Textures.get("sensor"))
							img.region.setTexture(Textures.get("sensor_red"));
						else if(trapType.delay <= 0)
						{
							engine.addEntity(bullet(trapRegion.x + trapRegion.width, trapRegion.y + trapRegion.height/2, 8, 4, SPD, 0));
							engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height, 8, 4, 0, SPD));
							engine.addEntity(bullet(trapRegion.x, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, 0));
							engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y, 8, 4, 0, -SPD));
							engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, SPD, SPD));
							engine.addEntity(bullet(trapRegion.x - trapRegion.width/2, trapRegion.y + trapRegion.height/2, 8, 4, -SPD, SPD));
							engine.addEntity(bullet(trapRegion.x + trapRegion.width/2, trapRegion.y - trapRegion.height/2, 8, 4, SPD, -SPD));
							engine.addEntity(bullet(trapRegion.x - trapRegion.width/2, trapRegion.y - trapRegion.height/2, 8, 4, -SPD, -SPD));
							Sounds.play("explosion");
							trapType.delay = 120;
							img.region.setTexture(Textures.get("sensor"));
						}
				}
				else
					for(Entity target : targets)
					{
						if(Maps.trap.get(target) != null || Maps.hurt.get(target) != null) continue;
						Components.Geom region = Maps.geom.get(target);
						if(region.overlaps(trapRegion))
							trapType.delay--;
					}
			}
		}
	}
}
