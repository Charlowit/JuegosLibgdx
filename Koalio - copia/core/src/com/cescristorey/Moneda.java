/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author Noelia
 */
public class Moneda extends Image{
    public static Animation coinAnim;

    float time = 0;


    public Moneda() {
        final float width = 18;
        final float height = 26;
        this.setSize(1, height / width);

       Texture items;
       items = loadTexture("items.png");
        
        coinAnim = new Animation(0.2f, new TextureRegion(items, 128, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32),
			new TextureRegion(items, 192, 32, 32, 32), new TextureRegion(items, 160, 32, 32, 32));

        
    }
    
    public static Texture loadTexture (String file) {
            return new Texture(Gdx.files.internal(file));
    }
    public void act(float delta) {
        time = time + delta;
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = (TextureRegion) coinAnim.getKeyFrame(time);
        batch.draw(frame , this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
    }
    
    
}
