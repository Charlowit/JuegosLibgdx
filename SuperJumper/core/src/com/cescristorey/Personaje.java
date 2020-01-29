/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.cescristorey;


public class Personaje extends DynamicGameObject {
	public static final float COIN_WIDTH = 0.5f;
	public static final float COIN_HEIGHT = 0.8f;
	public static final int COIN_SCORE = 10;
        public static final float COIN_VELOCITY = 1.5f;

	float stateTime;

	public Personaje (float x, float y) {
		super(x, y, COIN_WIDTH, COIN_HEIGHT);
                velocity.set(COIN_VELOCITY, 0);
	}

	public void update (float deltaTime) {
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - COIN_WIDTH / 2;
		bounds.y = position.y - COIN_HEIGHT / 2;

		if (position.x < COIN_WIDTH / 2) {
			position.x = COIN_WIDTH / 2;
			velocity.x = COIN_VELOCITY;
		}
		if (position.x > World.WORLD_WIDTH - COIN_WIDTH / 2) {
			position.x = World.WORLD_WIDTH - COIN_WIDTH / 2;
			velocity.x = -COIN_VELOCITY;
		}
		stateTime += deltaTime;
	}
}
