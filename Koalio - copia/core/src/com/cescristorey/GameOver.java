/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Noelia
 */
public class GameOver implements Screen {

	final MarioBros game;
//        int total_de_puntos;
	OrthographicCamera camera;
        Texture fondo;
        TextureRegion background;

	public GameOver(final MarioBros game) {
		this.game = game;
//                this.total_de_puntos=total;
	}
        @Override
        public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                game.batch.begin();
                game.batch.draw(background,0,0);
                game.batch.end();

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.font.draw(game.batch, "Game over,", 100, 150);
//		game.font.draw(game.batch, "Game over, tus puntos son: " +total_de_puntos, 100, 150);
		game.font.draw(game.batch, "Touch to play again", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new Level1(game));
			dispose();
		}
	}

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        fondo = new Texture(Gdx.files.internal("background2.png"));
        background =new TextureRegion(fondo,0,0,800,480);  
    }

    @Override
    public void resize(int i, int i1) {
       
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        
    }

}
