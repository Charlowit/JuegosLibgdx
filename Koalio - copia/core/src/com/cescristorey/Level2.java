/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cescristorey;

/**
 *
 * @author Charlowit
 */
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.scenes.scene2d.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Level2 implements Screen {
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Mario mario;
    ArrayList<Goomba> goombas;
    ArrayList <Moneda> monedas;
    ArrayList <Turtle> tortugas;
    ArrayList <Intelligent_Turtle> tortuguitas;
    MarioBros game;
    Seta seta;
    int score=0;
    TiledMapTileLayer CapaSeta;

    Level2(MarioBros game) {
        this.game= game;
    }
    
    public void loadMoneda(float startX, float startY){
        TiledMapTileLayer monedas=(TiledMapTileLayer) map.getLayers().get("Monedas");
        float endX=startX + monedas.getWidth();
        float endY=startY + monedas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (monedas.getCell(x, y) != null) {
                    if (monedas.getProperties().get("visible", Boolean.class)==true) {
                        monedas.setCell(x, y, null);
                        this.spawMoneda(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    public void spawMoneda(float x, float y){
        Moneda moneda= new Moneda();
        moneda.setPosition(x, y);
        stage.addActor(moneda);
        monedas.add(moneda);
    }
    
     public void loadSeta(float startX, float startY){
        TiledMapTileLayer setas=(TiledMapTileLayer) map.getLayers().get("Seta");
        float endX=startX + setas.getWidth();
        float endY=startY + setas.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (setas.getCell(x, y) != null) {
                    if (setas.isVisible()==true) {
                        setas.setCell(x, y, null);
                        this.spawSeta(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    public void spawSeta(float x, float y){
        seta= new Seta();
        seta.layer = (TiledMapTileLayer) map.getLayers().get("Seta"); 
        seta.setPosition(x, y);
        stage.addActor(seta);
    }

    public void show() {
        map = new TmxMapLoader().load("mapa2.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);

        mario = new Mario();
        mario.layer = (TiledMapTileLayer) map.getLayers().get("Plataformas");
        mario.setPosition(20, 10);
        stage.addActor(mario);
        
        
        goombas = new ArrayList();
        for (int i=0; i<5; i++){
            Goomba goomba = new Goomba();
            goomba.layer = (TiledMapTileLayer) map.getLayers().get("Plataformas"); 
            goomba.setPosition((float) (Math.random()*100+40), 13);
            stage.addActor(goomba);
            goombas.add(goomba);
        }
        tortugas = new ArrayList();
        for (int i=0; i<5; i++){
            Turtle turtle = new Turtle();
            turtle.layer = (TiledMapTileLayer) map.getLayers().get("Plataformas"); 
            turtle.setPosition((float) (Math.random()*100+40), 13);
            stage.addActor(turtle);
            tortugas.add(turtle);
        }
        tortuguitas = new ArrayList();
        for(int i = 0 ; i < 7; i++){
            Intelligent_Turtle tortu =  new Intelligent_Turtle();
            tortu.layer = (TiledMapTileLayer) map.getLayers().get("Plataformas"); 
            tortu.setPosition((float) (Math.random()*100+40), 1);

            stage.addActor(tortu);
            tortuguitas.add(tortu);
        }
        
        monedas= new ArrayList<>();
        this.loadMoneda(0,0);

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(mario.getX()> 13 && mario.getX()<222){
            camera.position.x=mario.getX();
        }
        if(mario.getX()<0){
            mario.setPosition(2, mario.getY());
        }
        if (mario.getX()>230){
            game.setScreen(new Level2(game));
            dispose();
        }
        if (mario.getY()<0){
            game.setScreen(new GameOver(game));
            dispose();
        }
        
        for(Intelligent_Turtle tortu : tortuguitas){
            
            if(mario.getX() >= tortu.getX()+2){
                tortu.start_move = true;
            }
            if(mario.getX() > tortu.getX()+ 40){
                tortu.start_move = false;
            }
            if(mario.getX() > tortu.getX()-10 && tortu.getX() > mario.getX()){
                tortu.isFacingRight = false;
            }
            else if(tortu.getX() < mario.getX()){
                tortu.isFacingRight = true;
            }
        
        }
        
        
        camera.update();

        renderer.setView(camera);
        renderer.render();

        stage.act(delta);
        stage.draw();
        
        
        
        this.overlapsMonedas();
        this.overlapsCajasMonedas();
        this.overlapsGoombas();
       this.overlapsCajasDestruir();
        this.overlapsCajaSeta();
        this.overlapsTurtles();
        this.overlapsTurtlesInt();
        
        if(this.seta!=null){
            this.overlapsSetas();
        }
        
        
        
        this.game.batch.begin();
        this.game.font.draw(game.batch, "Puntuacion: " + score, 0, 420);
        this.game.batch.end();
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, 16 * width / height, 13);
    }

    public void resume() {
    }
    
    public void overlapsMonedas(){
        for (Moneda moneda:monedas){
            if (moneda.getX()+1.5f > mario.getX() && moneda.getY()+1.5f > mario.getY() && moneda.getX()-1.5f < mario.getX() && moneda.getY()-1.5f < mario.getY()){
               score+=100;
               moneda.setY(500);
               moneda.remove();
            }
        }
    }
    
    public void overlapsCajasMonedas(){
        float startX=0;
        float startY=0;
        TiledMapTileLayer cajitaMoneda=(TiledMapTileLayer) map.getLayers().get("Cajas_de_Monedas");
        float endX=startX + cajitaMoneda.getWidth();
        float endY=startY + cajitaMoneda.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (cajitaMoneda.getCell(x, y) != null) {
                    if (cajitaMoneda.getProperties().get("visible", Boolean.class)==true) {
                       if (x+0.6f > mario.getX() && x-0.6f < mario.getX()){ 
                           if(y+0.5f > mario.getY() && y-1.5f < mario.getY()){
                                if(mario.yVelocity>0 && !mario.canJump)
                                    cajitaMoneda.setCell(x, y, null);
                        //this.spawMoneda(x,y);
                           }
                       }
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
     public void overlapsCajaSeta(){
        float startX=0;
        float startY=0;
        TiledMapTileLayer cajaSeta=(TiledMapTileLayer) map.getLayers().get("CajaSeta");
        float endX=startX + cajaSeta.getWidth();
        float endY=startY + cajaSeta.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (cajaSeta.getCell(x, y) != null) {
                    if (cajaSeta.getProperties().get("visible", Boolean.class)==true) {
                       if (x+0.6f > mario.getX() && x-0.6f < mario.getX()){ 
                           if(y+0.5f > mario.getY() && y-1.5f < mario.getY()){
                                if(mario.yVelocity>0 && !mario.canJump){
                                    cajaSeta.setCell(x, y, null);
                                    CapaSeta=(TiledMapTileLayer) map.getLayers().get("Seta");
                                    CapaSeta.setVisible(true);
                                    this.loadSeta(0,0);
                                }
                                    
                        //this.spawMoneda(x,y);
                           }
                       }
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
    
    public void overlapsGoombas() {
        for(Goomba goomba : goombas){
            if (mario.getX()+0.5 > goomba.getX() && mario.getX()-0.5 < goomba.getX() && mario.getY() > goomba.getY()+0.5f && mario.getY() < goomba.getY()+1.5f){
                mario.xVelocity = 50;
                mario.yVelocity = 20;

                if (goomba.die==true){
                    goomba.remove();
                }else {
                    goomba.die=true;
                    goomba.setX(goomba.getX()-0.8f);
                }
            }
            
            else if(mario.getX()+0.5 > goomba.getX() && mario.getY()+ 0.5 > goomba.getY() && mario.getX()-0.5 < goomba.getX() && mario.getY()-0.5 < goomba.getY()){
                game.setScreen(new GameOver(game));
                dispose();
            }
            
        }   
    }
    public void overlapsTurtles() {
        for(Turtle turtle : tortugas){
            if (mario.getX()+0.5 > turtle.getX() && mario.getX()-0.5 < turtle.getX() && mario.getY() > turtle.getY()+0.5f && mario.getY() < turtle.getY()+1.5f){
                mario.xVelocity = 50;
                mario.yVelocity = 20;

                if (turtle.die==true){
                    turtle.remove();
                }else {
                    turtle.die=true;
                    turtle.setX(turtle.getX()-0.8f);
                }
            }
            
            else if(mario.getX()+0.5 > turtle.getX() && mario.getY()+ 0.5 > turtle.getY() && mario.getX()-0.5 < turtle.getX() && mario.getY()-0.5 < turtle.getY()){
                game.setScreen(new GameOver(game));
                dispose();
            }
            
        }   
    }
        public void overlapsTurtlesInt() {
        for(Intelligent_Turtle tortu : tortuguitas){
            if (mario.getX()+0.5 > tortu.getX() && mario.getX()-0.5 < tortu.getX() && mario.getY() > tortu.getY()+0.5f && mario.getY() < tortu.getY()+1.5f){
                mario.xVelocity = 50;
                mario.yVelocity = 20;

                if (tortu.die==true){
                    tortu.remove();
                }else {
                    tortu.die=true;
                    tortu.setX(tortu.getX()-0.8f);
                }
            }
            
            else if(mario.getX()+0.5 > tortu.getX() && mario.getY()+ 0.5 > tortu.getY() && mario.getX()-0.5 < tortu.getX() && mario.getY()-0.5 < tortu.getY()){
                game.setScreen(new GameOver(game));
                dispose();
            }
            
        }   
    }
     public void overlapsSetas() {
       
            if(mario.getX()+0.5 > seta.getX() && mario.getY()+ 0.5 > seta.getY() && mario.getX()-0.5 < seta.getX() && mario.getY()-0.5 < seta.getY()){
                seta.setX(-9999);
                seta.remove();
                this.CapaSeta.setVisible(false);
                score+=500;
            }
              
    }
    
    public void overlapsCajasDestruir(){
        float startX=0;
        float startY=0;
        TiledMapTileLayer cajaDestruir=(TiledMapTileLayer) map.getLayers().get("Caja_Destruir");
        float endX=startX + cajaDestruir.getWidth();
        float endY=startY + cajaDestruir.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (cajaDestruir.getCell(x, y) != null) {
                    if (cajaDestruir.getProperties().get("destruir", Boolean.class)==true) {
                       if (x+0.3f > mario.getX() && x-0.3f < mario.getX() && y+1.5f > mario.getY() && y-1.5f < mario.getY()){
                            if(mario.yVelocity>0 && !mario.canJump){
                                cajaDestruir.setCell(x, y, null);
                                mario.yVelocity=y;
                            }
                        }         
                        //this.spawMoneda(x,y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }
}