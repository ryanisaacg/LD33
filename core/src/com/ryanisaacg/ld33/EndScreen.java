package com.ryanisaacg.ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndScreen extends ScreenAdapter
{
	private BitmapFont font;
	private SpriteBatch batch;
	
	public EndScreen()
	{
		font = new BitmapFont();
		batch = new SpriteBatch();
		font.setColor(Color.WHITE);
	}
	
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		batch.begin();
		font.draw(batch, "You escaped! Freedom at last...", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2);
		batch.end();
	}
}
