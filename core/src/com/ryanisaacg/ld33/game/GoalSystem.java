package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class GoalSystem extends EntitySystem
{
	private Engine engine;
	private Family goals;
	private boolean finished = false;
	private Family playerFilter;
	public GoalSystem()
	{
		super();
		playerFilter = Family.all(Components.Control.class).get();
		goals = Family.all(Components.Goal.class).get();
	}
	
	@Override
	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
	}
	
	public void update(float deltaTime)
	{
		finished = true;
		ImmutableArray<Entity> goalList = engine.getEntitiesFor(goals);
		for(Entity e : goalList)
		{
			switch(Maps.goal.get(e).type)
			{
			case DESTROY:
				finished = false;
				break;
			case REACH:
				Entity player = engine.getEntitiesFor(playerFilter).first();
				Components.Geom goalBox = Maps.geom.get(e);
				Components.Geom playerbox = Maps.geom.get(player);
				if(!goalBox.overlaps(playerbox))
					finished = false;
				break;
			}
		}
	}
	
	public boolean isFinished()
	{
		return finished;
	}
}
