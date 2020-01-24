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
import com.badlogic.gdx.graphics.Texture;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

/**
 *
 * @author Charlowit
 */
public class MainMenuScreen implements Screen {
    	
        final Gota2 game;
        int highscore;
        Texture imagenfondo;
	OrthographicCamera camera;

	public MainMenuScreen(final Gota2 game) throws FileNotFoundException, IOException {
		this.game = game;
                imagenfondo = new Texture(Gdx.files.internal("Fondoinicio.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                
                File ficheroScore = new File("Highscore.dat");
                ObjectInputStream dataIS = new ObjectInputStream(new FileInputStream(ficheroScore));
                try {
                    while (true) {
                        this.highscore =  dataIS.readInt();
                    }
                } catch (EOFException eo) {
                } catch (StreamCorruptedException x) {
                }
                dataIS.close();
                
              
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
                game.batch.draw(imagenfondo, 0,0, 800, 480);
		game.font.draw(game.batch, "Wellcome to Flappy Lowit ", 100, 200);
                game.font.draw(game.batch, "The highscore is " + this.highscore, 100, 150);
		game.font.draw(game.batch, "Press any key to start the game", 100, 100);
		game.batch.end();
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game, highscore));
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
