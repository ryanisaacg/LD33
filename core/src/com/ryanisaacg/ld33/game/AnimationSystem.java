package com.ryanisaacg.ld33.game;

import java.util.function.Function;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class AnimationSystem extends IteratingSystem
{
	public AnimationSystem()
	{
		super(Family.all(Components.Draw.class, Components.Animation.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Components.Animation anim = Maps.animation.get(entity);
		anim.frame += 1;
		for(Function<Entity, Boolean> trigger : anim.triggers.keySet())
			if(anim.current != anim.triggers.get(trigger) && trigger.apply(entity))
			{
				anim.frame = 0;
				anim.current = anim.triggers.get(trigger);
			}
		Components.Draw image = Maps.draw.get(entity);
		image.region = anim.animations.get(anim.current)[anim.frame];
	}
}
