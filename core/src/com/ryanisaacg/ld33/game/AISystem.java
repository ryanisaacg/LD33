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
		return new Entity()
		.add(new Components.Geom(x, y, width, height))
		.add(new Components.Velocity(xspeed, yspeed, CollideBehavior.DIE))
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
		switch(aiType.type)
		{
			case TURRET:
				Entity player = engine.getEntitiesFor(playerFilter).first();
				Components.Geom geom = Maps.geom.get(player);
				if(Components.distance(aiGeom, geom) < 500 && aiType.delay <= 0)
				{
					float x, y, len;
					x = geom.x - aiGeom.x;
					y = geom.y - aiGeom.y;
					len = (float)Math.sqrt(x * x + y * y);
					x /= len;
					y /= len;
					engine.addEntity(bullet(aiGeom.x + aiGeom.width / 2, aiGeom.y + aiGeom.height / 2, 8, 4, x, y));
					aiType.delay = 60;
				}
				aiType.delay -= 1;
				break;
			case HUNTER:
				break;
			case HUNTER_KILLER:
				break;
		}
	}
}
