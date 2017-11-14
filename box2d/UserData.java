package com.mygdx.game.martianrun.box2d;

import com.mygdx.game.martianrun.enums.UserDataType;

/**
 * @author swanf
 * date 17-11-9 下午8:38
 */

public abstract class UserData {
    protected UserDataType userDataType;
    private float width;
    private float height;

    public UserData() {

    }

    public UserData(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public UserDataType getUserDataType() {
        return userDataType;
    }
}
