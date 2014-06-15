
package com.badlogic.gradletest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.Stage;


public class HelloApp extends ApplicationAdapter {

    Stage stage;
	
	@Override
	public void create () {
        stage = new Stage();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //Shift drawing responsibilities to Stage
        stage.draw();

        //Update
        float dt = Gdx.graphics.getDeltaTime();
        stage.update(dt);

	}
}
