package com.mygdx.game.martianrun.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.martianrun.stages.GameStage;

/**
 * @author swanf
 * date 17-11-9 下午5:12
 */

public class GameScreen implements Screen{
    private GameStage stage;

    public GameScreen() {
        stage = new GameStage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // 清屏
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 刷新舞台数据
        stage.draw();
        stage.act(delta);
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
