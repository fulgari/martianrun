package com.mygdx.game.martianrun.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.martianrun.box2d.UserData;
import com.mygdx.game.martianrun.utils.Constants;

/**
 * @author swanf
 * date 17-11-9 下午8:28
 */

public abstract class GameActor extends Actor {
    protected Body body;
    protected UserData userData;
    protected Rectangle screenRectangle;

    // 都需要一个body是因为要确保Actor都有对应的物理Body类去渲染和刷新
    public GameActor(Body body) {
        this.body = body;
        // 加上UserData确保每个演员类都能够在这个enum指定的行为之内，确保不会产生不可遇见的后果
        this.userData = (UserData) body.getUserData();
        screenRectangle = new Rectangle();  // 恭喜又找到一个bug，忘记初始化
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (body.getUserData() != null) {
            updateRectangle();
        } else {
            remove();
        }
    }

    private void updateRectangle() {
        screenRectangle.x = transformToScreen(body.getPosition().x - userData.getWidth() / 2);
        screenRectangle.y = transformToScreen(body.getPosition().y - userData.getHeight() / 2);
        screenRectangle.width = transformToScreen(userData.getWidth());
        screenRectangle.height = transformToScreen(userData.getHeight());
    }

    protected float transformToScreen(float n) {
        return Constants.WORLD_TO_SCREEN * n;
    }


    public abstract UserData getUserData();
}
