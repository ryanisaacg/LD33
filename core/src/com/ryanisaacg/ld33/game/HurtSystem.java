package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public class HurtSystem extends EntitySystem
{
	private Engine engine;
	private final Family targets;
	private final Family hazards;
	
	public HurtSystem()
	{
		targets = Family.all(Components.Geom.class, Components.Health.class).get();
		hazards = Family.all(Components.Geom.class, Components.Hurt.class).get();
	}

	public void addedToEngine(Engine engine)
	{
		this.engine = engine;
	}
	
    public void removedFromEngine(Engine engine)
    {
    	this.engine = null;
    }
    
    public void update(float deltaTime)
    {
    	ImmutableArray<Entity> targetList = engine.getEntitiesFor(targets);
    	ImmutableArray<Entity> hazardList = engine.getEntitiesFor(hazards);
    	for(Entity target : targetList)
    		for(Entity hazard : hazardList)
    			if(Maps.hurt.get(hazard).target.matches(target) && Maps.geom.get(target).overlaps(Maps.geom.get(hazard)))
    				target.add(Components.MarkedForDeath.mark);
    }
    
    public boolean checkProcessing()
    {
    	return true;
    }
    
    public void setProcessing(boolean processing)
    {
    	return;
    }
}