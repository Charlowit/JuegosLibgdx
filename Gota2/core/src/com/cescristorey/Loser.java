/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 *
 * @author Charlowit
 */
public class Loser implements Screen{
        
    
        final Gota2 game;
        private int gotas_recogidas;

	OrthographicCamera camera;

	public Loser(final Gota2 game, int gotas_recogidas) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                this.gotas_recogidas = gotas_recogidas;

	}

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        	
                Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "HAS PERDIDO AMIGO MIO HAS CONSEGUIDO " + this.gotas_recogidas + " GOTAS!", 100, 150);
		game.font.draw(game.batch, "PULSA EN CUALQUIER PARTE PARA PODER INICIAR EL JUEGO", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
