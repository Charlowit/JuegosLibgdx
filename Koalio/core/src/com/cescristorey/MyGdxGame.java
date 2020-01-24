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
import com.cescristorey.MainScreen;

public class MyGdxGame extends Game {
	public void create() {
		this.setScreen(new MainScreen());
	}
}
