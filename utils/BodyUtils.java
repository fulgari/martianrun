package com.mygdx.game.martianrun.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.martianrun.box2d.UserData;
import com.mygdx.game.martianrun.enums.UserDataType;

/**
 * @author swanf
 * date 17-11-9 下午9:08
 */

// 这里是帮助类用来判断body是runner还是ground
public class BodyUtils {

    public static boolean bodyInBounds(Body body) {
        UserData userData = (UserData) body.getUserData();
        switch (userData.getUserDataType()) {
            case RUNNER:
            case ENEMY:
                return body.getPosition().x + userData.getWidth() / 2 > 0;
        }
        return true;
    }
    public static boolean bodyIsRunner(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
    }

    public static boolean bodyIsGround(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.GROUND;
    }
    public static boolean bodyIsEnemy(Body body) {
        UserData userData = (UserData) body.getUserData();
        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }
}
