package com.mygdx.game.martianrun.box2d;

import com.mygdx.game.martianrun.enums.UserDataType;

/**
 * @author swanf
 * date 17-11-9 下午8:39
 */

public class GroundUserData extends UserData {
    public GroundUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.GROUND;
    }
}
