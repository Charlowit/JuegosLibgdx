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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

/**
 *
 * @author Noelia
 */
public class Start_Screen implements Screen{
        final MarioBros game;
	OrthographicCamera camera;
        Texture fondo;
        TextureRegion background;

    public Start_Screen(MarioBros game) throws FileNotFoundException, IOException {
        this.game = game;
    }
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);

        fondo = new Texture(Gdx.files.internal("background2.png"));
        background =new TextureRegion(fondo,0,0,800,480);  
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
        game.font.draw(game.batch, "Bienvenido", 50, 150);
        game.font.draw(game.batch, "Toca para jugar", 50, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
                game.setScreen(new Level2(game));
                dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        
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
