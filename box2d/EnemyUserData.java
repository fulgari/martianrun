package com.mygdx.game.martianrun.box2d;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.martianrun.enums.UserDataType;
import com.mygdx.game.martianrun.utils.Constants;

/**
 * @author swanf
 * date 17-11-9 下午10:16
 */

public class EnemyUserData extends UserData {
    private Vector2 linearVelocity;
    private String[] textureRegions;

    public EnemyUserData(float width, float height, String[] textureRegions) {
        super(width, height);
        this.textureRegions = textureRegions;
        userDataType = UserDataType.ENEMY;
        linearVelocity = Constants.ENEMY_LINEAR_VELOCITY;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public String[] getTextureRegions() {
        return textureRegions;
    }
}
