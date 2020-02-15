package com.cescristorey;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Goomba extends Image {
    TextureRegion muere, jump;
    Animation walk;

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

    public Goomba() {
        final float width = 16;
        final float height = 16;
        this.setSize(1, height / width);

        Texture koalaTexture = new Texture("goomba.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);

       muere = grid[0][2];
//        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][0], grid[0][1]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public void act(float delta) {
        time = time + delta;
        if (this.die==false){
            if (!isFacingRight) {
                xVelocity = -1 * MAX_VELOCITY;
                isFacingRight = false; 
                if (layer.getCell(Math.round(this.getX()-1),Math.round(this.getY())) != null) {
                    isFacingRight = true;
                }
            }else if (isFacingRight) {
                xVelocity = MAX_VELOCITY;
                isFacingRight = true;
                if (layer.getCell(Math.round(this.getX()+1),Math.round(this.getY())) != null) {
                    isFacingRight = false;
                }
            }
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

            this.setPosition(x + xChange, y + yChange);

            xVelocity = xVelocity * DAMPING;
            if (Math.abs(xVelocity) < 0.5f) {
                xVelocity = 0;
            }
        }
        
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (this.die==false) {
            frame = (TextureRegion) walk.getKeyFrame(time);
        } else {
            frame = muere;
            xVelocity=0;
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    if (shouldDestroy) {
                       // layer.setCell(x, y, null);
                    }
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}