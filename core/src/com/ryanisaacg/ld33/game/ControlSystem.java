package com.ryanisaacg.ld33.game;

import static com.ryanisaacg.ld33.game.Maps.control;
import static com.ryanisaacg.ld33.game.Maps.velocity;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.ryanisaacg.ld33.game.Components.Control;
import com.ryanisaacg.ld33.game.Components.Velocity;

public class ControlSystem extends IteratingSystem
{
	private final float diagConstant = 1 / (float)Math.sqrt(2);
	private final Camera camera;
	
	public ControlSystem(Camera camera)
	{
		super(Family.all(Control.class, Velocity.class).get());
		this.camera = camera;
	}
	
	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		Control keys = control.get(entity);
		Velocity speed = velocity.get(entity);
		
		if(Gdx.input.isKeyPressed(keys.UP) && !Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = MathUtils.clamp(speed.y + 0.2f, -1f, 4f);
		else if(!Gdx.input.isKeyPressed(keys.UP) && Gdx.input.isKeyPressed(keys.DOWN))
			speed.y = MathUtils.clamp(speed.y - 0.2f, -4f, 1f);
		else
			speed.y *= (Math.abs(speed.y) > 1) ? 0.75f : 0;
		
		if(Gdx.input.isKeyPressed(keys.LEFT) && !Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = MathUtils.clamp(speed.x - 0.2f, -4f, 1f);
		else if(!Gdx.input.isKeyPressed(keys.LEFT) && Gdx.input.isKeyPressed(keys.RIGHT))
			speed.x = MathUtils.clamp(speed.x + 0.2f, -1f, 4f);
		else
			speed.x *= (Math.abs(speed.x) > 1) ? 0.75f : 0;
		if(Math.sqrt(speed.x * speed.x + speed.y * speed.y) > 4)
		{
			/*
			 * If you move at a speed of <1,1>, you move a distance of sqrt(2)
			 * To counteract this, both speeds are divided by sqrt(2)
			 */
			speed.x *= diagConstant;
			speed.y *= diagConstant;
		}
		if(Gdx.input.isKeyJustPressed(keys.JUMP) && Maps.jump.get(entity) == null)
		{
			entity.add(new Components.Jump(60));
			Components.Priority p = Maps.priority.get(entity);
			if(p != null) p.priority ++;
		}
		camera.position.x += speed.x;
		camera.position.y += speed.y;
	}
}