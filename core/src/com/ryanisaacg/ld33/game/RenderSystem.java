package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderSystem extends IteratingSystem
{
	private SpriteBatch batch;
	
	public RenderSystem(SpriteBatch batch)
	{
		super(Family.all(Components.Draw.class).get());
		this.batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Components.Geom geom = Maps.geom.get(entity);
		Components.Draw tex = Maps.draw.get(entity);
		float x = 0, y = 0, width = tex.region.getRegionWidth(), height = tex.region.getRegionHeight();
		if(geom != null)
		{
			x = geom.x;
			y = geom.y;
			width = geom.width;
			height = geom.height;
		}
		batch.draw(tex.region, x, y, tex.originX, tex.originY, width, height, tex.scaleX, tex.scaleY, tex.rotation);
	}

}
