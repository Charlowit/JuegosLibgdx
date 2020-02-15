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
public class Seta extends Image{
    public static Texture seta;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    boolean die= false;
    TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 5f;
    final float DAMPING = 0.87f;


    public Seta() {
        final float width = 16;
        final float height = 16;
        this.setSize(1, height / width);

        
        seta = new Texture(Gdx.files.internal("mushroom.png"));

        
    }
    
    public static Texture loadTexture (String file) {
            return new Texture(Gdx.files.internal(file));
    }
    public void act(float delta) {
        time = time + delta;

//        yVelocity = yVelocity + MAX_VELOCITY;
        xVelocity = MAX_VELOCITY;

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }


        if(isFacingRight==true){
            if (x + xChange < 26){
               xChange = xVelocity * delta;
               this.setPosition(x + xChange, y + yChange); 
            }else {
                isFacingRight = false;
            }
        }else{
            if(x+xChange > 5){
               xChange = -1*xVelocity * delta;
               this.setPosition(x +xChange, y + yChange);
            }else {
                isFacingRight = true;
            }
        }
        if (y + yChange <= 0 ){
            this.setPosition(x +xChange, 1);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        
        batch.draw(seta , this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
    }
    
    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();
        int y = (int) startY;
        shouldDestroy=false;
        while (y < endY) {

            int x = (int) startX;
            while (x < endX) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                        layer.setCell(x, y, null);
                    }
                    return false;
                }
                x = x + 1;
            }
            y = y + 1;
        }
        return true;
    }
}
