package com.cescristorey;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charlowit
 */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cescristorey.Level1;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarioBros extends Game {
    
        public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
                 batch = new SpriteBatch();
                //Use LibGDX's default Arial font.
                //font = new BitmapFont();
                font = new BitmapFont(Gdx.files.internal("fuente.fnt"));
            try {
                this.setScreen(new Start_Screen(this));
            } catch (IOException ex) {
                Logger.getLogger(MarioBros.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
