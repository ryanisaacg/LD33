package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import static com.ryanisaacg.ld33.game.Components.*;
import static com.ryanisaacg.ld33.game.Maps.*;

public class ControlSystem extends IteratingSystem
{
	public ControlSystem()
	{
		super(Family.all(Control.class, Velocity.class).get());
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Control keys = control.get(entity);
		Velocity speed = velocity.get(entity);
		
		if(Gdx.input.isKeyPressed(keys.UP) && !Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = MathUtils.clamp(speed.y + 0.2f, 0, 4f);
		else if(!Gdx.input.isKeyPressed(keys.UP) && Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = MathUtils.clamp(speed.y - 0.2f, -4f, 0);
		else
			speed.y = 0;
		
		if(Gdx.input.isKeyPressed(keys.LEFT) && !Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = MathUtils.clamp(speed.x - 0.2f, -4f, 0);
		else if(!Gdx.input.isKeyPressed(keys.LEFT) && Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = MathUtils.clamp(speed.x + 0.2f, 0, 4f);
		else
			speed.x = 0;
	}
}