package com.mygdx.game.martianrun;

import com.badlogic.gdx.Game;
import com.mygdx.game.martianrun.screens.GameScreen;

/**
 * @author swanf
 * date 17-11-9 下午5:10
 */

// 继承Game便于以后多屏幕的操作
public class MartianRun extends Game{
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
