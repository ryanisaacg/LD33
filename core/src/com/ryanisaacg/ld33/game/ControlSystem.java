package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.Gdx;

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
		speed.x = 0;
		speed.y = 0;
		if(Gdx.input.isKeyPressed(keys.UP) && !Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = -2;
		if(!Gdx.input.isKeyPressed(keys.UP) && Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = 2;
		if(Gdx.input.isKeyPressed(keys.LEFT) && !Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = -2;
		if(!Gdx.input.isKeyPressed(keys.LEFT) && Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = 2;
	}
}