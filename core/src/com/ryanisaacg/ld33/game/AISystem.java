package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ryanisaacg.ld33.game.Components.Velocity.CollideBehavior;

public class AISystem extends IteratingSystem
{
	private Engine engine;
	private Family playerFilter;
	
	public AISystem()
	{
		super(Family.all(Components.AI.class).get());
		playerFilter = Family.all(Components.Control.class).get();
	}

	@Override
	public void addedToEngine(Engine engine)
	{
		super.addedToEngine(engine);
		this.engine = engine;
	}
	
	private Entity bullet(float x, float y, float width, float height, float xspeed, float yspeed)
	{
		Components.Velocity velocity = new Components.Velocity(xspeed, yspeed, CollideBehavior.DIE);
		velocity.lockDirection = true;
		return new Entity()
		.add(new Components.Geom(x, y, width, height))
		.add(velocity)
		.add(new Components.Draw(new TextureRegion(Textures.get("bullet"))))
		.add(new Components.Hurt(Family.all(Components.Geom.class, Components.Control.class).
				exclude(Components.Jump.class, Components.AI.class)))
		;
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Components.AI aiType = Maps.ai.get(entity);
		Components.Geom aiGeom = Maps.geom.get(entity);
		Components.Velocity aiSpeed = Maps.velocity.get(entity);
		Entity player = engine.getEntitiesFor(playerFilter).first();
		Components.Geom geom = Maps.geom.get(player);
		switch(aiType.type)
		{
		case TURRET:
			if(Components.distance(aiGeom, geom) < 500 && aiType.delay <= 0)
			{
				float x, y, len;
				x = geom.x - aiGeom.x;
				y = geom.y - aiGeom.y;
				len = (float)Math.sqrt(x * x + y * y);
				x = x / len * 10;
				y = y / len * 10;
				engine.addEntity(bullet(aiGeom.x + aiGeom.width / 2, aiGeom.y + aiGeom.height / 2, 8, 4, x, y));
				aiType.delay = 60;
				Sounds.play("laser-gun");
			}
			aiType.delay -= 1;
			Components.Draw img = Maps.draw.get(entity);
			img.rotation = (float)Math.toDegrees(Math.atan2(geom.y - aiGeom.y, geom.x - aiGeom.x));
			break;
		case HUNTER:
			float x, y, len;
			x = geom.x - aiGeom.x;
			y = geom.y - aiGeom.y;
			len = (float)Math.sqrt(x * x + y * y);
			x = x / len * 3;
			y = y / len * 3;
			aiSpeed.x = x;
			aiSpeed.y = y;
			break;
		case HUNTER_KILLER:
			if(aiType.delay > 0)
			{
				if(geom.x > aiGeom.x + 32)
					aiSpeed.x = 1;
				else if(geom.x < aiGeom.x - 32)
					aiSpeed.x = -1;
				else if(geom.y > aiGeom.y + 32)
					aiSpeed.y = 1;
				else if(geom.y < aiGeom.y - 32)
					aiSpeed.y = -1;
				aiType.delay --;
			}
			else
			{
				x = geom.x - aiGeom.x;
				y = geom.y - aiGeom.y;
				len = (float)Math.sqrt(x * x + y * y);
				x = x / len * 10;
				y = y / len * 10;
				engine.addEntity(bullet(aiGeom.x + aiGeom.width / 2, aiGeom.y + aiGeom.height / 2, 8, 4, x, y));
				aiType.delay = 60;
				Sounds.play("laser-gun");
			}
			img = Maps.draw.get(entity);
			img.rotation = (float)Math.toDegrees(Math.atan2(geom.y - aiGeom.y, geom.x - aiGeom.x));
			break;
		}
	}
}
