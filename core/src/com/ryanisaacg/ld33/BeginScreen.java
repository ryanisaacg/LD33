package com.ryanisaacg.ld33;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ryanisaacg.ld33.game.GameScreen;

public class BeginScreen extends ScreenAdapter
{
	private BitmapFont font;
	private SpriteBatch batch;
	private Game game;
	
	public BeginScreen(Game game)
	{
		font = new BitmapFont();
		batch = new SpriteBatch();
		font.setColor(Color.WHITE);
		this.game = game;
	}
	
	@Override
	public void render(float deltaTime)
	{
		Gdx.gl20.glClear(GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		batch.begin();
		int y = 120;
		font.draw(batch, "You're a genetic experiment for a weapons manufacturing company.", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2 + y);
		y -= 30;
		font.draw(batch, "Escape the facility they've locked you in.", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2+ y);
		y -= 30;
		font.draw(batch, "Some rooms have an escape hatch, others you complete by killing the other experiments.", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2+ y);
		y -= 30;
		font.draw(batch, "Good luck.", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2+ y);
		y -= 30;
		font.draw(batch, "Press space to continue.", Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 2+ y);
		batch.end();
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			dispose();
			game.setScreen(new GameScreen(1, game));
		}
	}
	
	@Override
	public void dispose()
	{
		font.dispose();
		batch.dispose();
	}
}
