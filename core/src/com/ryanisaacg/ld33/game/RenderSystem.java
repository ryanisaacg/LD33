package com.ryanisaacg.ld33.game;


import java.util.Comparator;
import java.util.PriorityQueue;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderSystem extends EntitySystem
{
	private Engine engine;
	private final SpriteBatch batch;
	private final OrthographicCamera camera;
	private final PriorityQueue<Entity> render;
	private final Family allowable;
	
	public RenderSystem(SpriteBatch batch, OrthographicCamera camera)
	{
		this.batch = batch;
		this.camera = camera;
		render = new PriorityQueue<Entity>(500, new Comparator<Entity>() {
			public int compare(Entity e1, Entity e2)
			{
				Components.Priority p1 = Maps.priority.get(e1), p2 = Maps.priority.get(e2);
				if(p1 == null && p2 != null)
					return -1;
				else if(p1 != null && p2 == null)
					return 1;
				else if(p1 != null && p2 != null)
					return p1.priority - p2.priority;
				else
					return 0;
			}
			public boolean equals(Object o) { return false; }
		});
		allowable = Family.all(Components.Draw.class).get();
	}

	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
	}
	
    public void removedFromEngine(Engine engine)
    {
    	this.engine = null;
    }
	
	protected void processEntity(Entity entity, float deltaTime)
	{
		Components.Geom geom = Maps.geom.get(entity);
		Components.Draw tex = Maps.draw.get(entity);
		Components.Jump jump = Maps.jump.get(entity);
		Components.Follow follow = Maps.follow.get(entity);
		float x = 0, y = 0, width = tex.region.getRegionWidth(), height = tex.region.getRegionHeight();
		if(geom != null)
		{
			x = geom.x;
			y = geom.y;
			width = geom.width;
			height = geom.height;
		}
		if(jump != null)
		{
			float peak = jump.maxDuration / 2;
			float shift = 10 * (1 - (Math.abs(jump.duration - peak) / peak));
			x -= shift;
			y -= shift;
			width += shift * 2;
			height += shift * 2;
		}
		if(follow != null)
		{
			camera.position.x = geom.x + geom.width / 2;
			camera.position.y = geom.y + geom.height / 2;
		}
		batch.draw(tex.region, x, y, tex.originX, tex.originY, width, height, tex.scaleX, tex.scaleY, tex.rotation);
	}
	
	public void update(float deltaTime)
    {
		ImmutableArray<Entity> canDraw = engine.getEntitiesFor(allowable);
		for(Entity entity : canDraw)
			render.add(entity);
		Entity draw = null;
		while((draw = render.poll()) != null)
			processEntity(draw, deltaTime);
    }
}
