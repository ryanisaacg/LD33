package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.ryanisaacg.ld33.game.Components.*;

public class Maps
{
	public static final ComponentMapper<Geom> geom;
	public static final ComponentMapper<Velocity> velocity;
	public static final ComponentMapper<Health> health;
	public static final ComponentMapper<Draw> draw;
	public static final ComponentMapper<Control> control;
	public static final ComponentMapper<Friction> friction;
	
	static 
	{
		geom = ComponentMapper.getFor(Geom.class);
		velocity = ComponentMapper.getFor(Velocity.class);
		health = ComponentMapper.getFor(Health.class);
		draw = ComponentMapper.getFor(Draw.class);
		control = ComponentMapper.getFor(Control.class);
		friction = ComponentMapper.getFor(Friction.class);
	}
}
