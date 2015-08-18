package com.ryanisaacg.ld33.game;

import com.badlogic.ashley.core.ComponentMapper;
import com.ryanisaacg.ld33.game.Components.*;

public class Maps
{
	public static final ComponentMapper<Geom> geom;
	public static final ComponentMapper<Velocity> velocity;
	public static final ComponentMapper<Health> health;
	public static final ComponentMapper<Draw> draw;
	
	static 
	{
		geom = ComponentMapper.getFor(Geom.class);
		velocity = ComponentMapper.getFor(Velocity.class);
		health = ComponentMapper.getFor(Health.class);
		draw = ComponentMapper.getFor(Draw.class);
	}
}
