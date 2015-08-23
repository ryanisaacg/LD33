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
	public static final ComponentMapper<Jump> jump;
	public static final ComponentMapper<Hurt> hurt;
	public static final ComponentMapper<MarkedForDeath> marked;
	public static final ComponentMapper<Priority> priority;
	public static final ComponentMapper<Animation> animation;
	public static final ComponentMapper<Follow> follow;
	public static final ComponentMapper<AI> ai;
	
	static 
	{
		geom = ComponentMapper.getFor(Geom.class);
		velocity = ComponentMapper.getFor(Velocity.class);
		health = ComponentMapper.getFor(Health.class);
		draw = ComponentMapper.getFor(Draw.class);
		control = ComponentMapper.getFor(Control.class);
		friction = ComponentMapper.getFor(Friction.class);
		jump = ComponentMapper.getFor(Jump.class);
		hurt = ComponentMapper.getFor(Hurt.class);
		marked = ComponentMapper.getFor(MarkedForDeath.class);
		priority = ComponentMapper.getFor(Priority.class);
		animation = ComponentMapper.getFor(Animation.class);
		follow = ComponentMapper.getFor(Follow.class);
		ai = ComponentMapper.getFor(AI.class);
	}
}
