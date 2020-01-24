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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Charlowit
 */
public class Loser implements Screen{
        
    
        final Gota2 game;
        private int puntuacion;
        private int highscore;
        Texture imagenfondo;

	OrthographicCamera camera;

	public Loser(final Gota2 game,int puntuacion, int highscore) throws FileNotFoundException, IOException {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                imagenfondo = new Texture(Gdx.files.internal("game-over.jpg"));
                this.puntuacion = puntuacion;
                this.highscore = highscore;
                if(this.highscore <= puntuacion){
                    this.highscore = puntuacion;
                }
                
                File ficheroScore = new File("Highscore.dat");
                FileOutputStream fileout = new FileOutputStream(ficheroScore);  //crea el flujo de salida
                //conecta el flujo de bytes al flujo de datos
                ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
                dataOS.writeInt(this.highscore);  
                dataOS.close();  //cerrar stream de salida

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
                game.font.draw(game.batch, "You lose!, you're score is " + this.puntuacion, 50, 180);
                if(this.puntuacion < this.highscore){
                    game.font.draw(game.batch, "The highscore is " + this.highscore, 50, 150 );
                }else {
                    game.font.draw(game.batch, "EY! You did the highscore!! " + this.highscore, 50, 150 );
                }
		game.font.draw(game.batch, "Press any key, to restart the game!", 50, 120);
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
