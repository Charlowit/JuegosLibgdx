/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.text.Font.font;
/**
 *
 * @author Charlowit
 */
public class GameScreen implements Screen {

    
        final Gota2 game;
	Texture tubebottom, tubetop, imagenfondo;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic, tuberiasonido;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> tuboabajo;
        Array<Rectangle> tuboarriba;
	long lastDropTime;
        int puntuacion, highscore;
        
        final float GRAVITY = -18f;
        float MAX_VELOCITY = 3.35f;
        final int SALTO = 100;
        final float DAMPING = 0.87f;
        final float CELLSPACING = 550;
        
        
	public GameScreen(final Gota2 game, int highscore) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
                tubebottom = new Texture(Gdx.files.internal("tuberiabuena2.png"));
                tubetop = new Texture(Gdx.files.internal("tuberiabuena.png"));
                imagenfondo = new Texture(Gdx.files.internal("Fondojuego.png"));
		bucketImage = new Texture(Gdx.files.internal("flappy2.png"));

		// load the drop sound effect and the rain background "music"
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
                tuberiasonido = Gdx.audio.newMusic(Gdx.files.internal("mario_moneda.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = 64; // center the bucket horizontally
		bucket.y = 480 / 2 - 64 / 2; // bottom left corner of the bucket is 20 pixels above
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		tuboabajo = new Array<Rectangle>();
                tuboarriba = new Array<Rectangle>();
		spawnRaindrop();
                
                this.puntuacion = 0;
                this.highscore = highscore;

	}

	private void spawnRaindrop() {
            
            
		Rectangle raindrop = new Rectangle();
		raindrop.y =  MathUtils.random(-200,0);
		raindrop.x = 800;
		raindrop.width = 64;
		raindrop.height =300;
		tuboabajo.add(raindrop);
                
                
                Rectangle raindrop2 = new Rectangle();
		raindrop2.y = raindrop.y + CELLSPACING;
		raindrop2.x = 800;
		raindrop2.width = 64;
		raindrop2.height = 300;
		tuboarriba.add(raindrop2);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
              
               
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		// all drops
		game.batch.begin();
                game.batch.draw(imagenfondo, 0,0, 800, 480);
                game.font.draw(game.batch, "Puntuacion: " + this.puntuacion, 0, 480);
                game.font.draw(game.batch, "Highscore: " + this.highscore, 200, 480);
		game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
                
                // Dibujando los tubos
		for (Rectangle raindrop : tuboarriba) {
                    
                        game.batch.draw(tubebottom, raindrop.x, raindrop.y);
		}
                for (Rectangle raindrop : tuboabajo) {
                    
                        game.batch.draw(tubetop, raindrop.x, raindrop.y);
		}
                
		game.batch.end();

		// Salto y gravedad.
                boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
              
                       bucket.y  += SALTO * Gdx.graphics.getDeltaTime();
                       MAX_VELOCITY = 350;
                    
                }
                
                MAX_VELOCITY = MAX_VELOCITY + GRAVITY;
                float y = bucket.getY();
                
                float yChange = MAX_VELOCITY * delta;
                bucket.setPosition(bucket.x, y + yChange);
                
                //
                
                // Si el pajaro llega a menor de la posicion y de 0 morirá
		if (bucket.y < 0){
                    try {
                        game.setScreen(new Loser(game, this.puntuacion, this.highscore));
                    } catch (IOException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispose();	
                }
                        
		if (bucket.y > 480 - 64){
			bucket.y = 480 - 64;
                }
               
		if (1500000000 <= TimeUtils.nanoTime() - lastDropTime) {
                    spawnRaindrop();
                }

		// Iteraciones de los tubos
		
		Iterator<Rectangle> iter = tuboabajo.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
                        raindrop.x -= 200 * Gdx.graphics.getDeltaTime();
                        
			if (raindrop.overlaps(bucket)) {
                            try {
                                game.setScreen(new Loser(game,this.puntuacion,this.highscore));
                            } catch (IOException ex) {
                                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                dispose();	
			}
                        if(raindrop.x + 64 < 0){
                            iter.remove();
                            tuberiasonido.play();
                            this.puntuacion++;
                        }
                        
		}
                Iterator<Rectangle> iter2 = tuboarriba.iterator();
		while (iter2.hasNext()) {
			Rectangle raindrop = iter2.next();
                        raindrop.x -= 200 * Gdx.graphics.getDeltaTime();
                        
                        
			if (raindrop.overlaps(bucket)) {
                            try {
                                game.setScreen(new Loser(game, this.puntuacion,this.highscore));
                            } catch (IOException ex) {
                                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                dispose();
				
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
                tubebottom.dispose();
		bucketImage.dispose();
		rainMusic.dispose();
	}

}

