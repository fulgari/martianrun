package com.mygdx.game.martianrun.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.martianrun.box2d.GroundUserData;
import com.mygdx.game.martianrun.box2d.UserData;
import com.mygdx.game.martianrun.utils.Constants;

/**
 * @author swanf
 * date 17-11-9 下午8:29
 */

public class Ground extends GameActor {

    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 10;

    public Ground(Body body) {
        super(body);
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(Constants.GROUND_IMAGE_PATH)));
        textureRegionBounds1 = new Rectangle(0 - getUserData().getWidth() / 2, 0,
                getUserData().getWidth(), getUserData().getHeight());
        textureRegionBounds2 = new Rectangle( getUserData().getWidth() / 2, 0,
                getUserData().getWidth(), getUserData().getHeight());
    }

    @Override
    public GroundUserData getUserData() {
        return (GroundUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds1.x, screenRectangle.y,
                screenRectangle.getWidth(), screenRectangle.getHeight());
        batch.draw(textureRegion, textureRegionBounds2.x, screenRectangle.y,
                screenRectangle.getWidth(), screenRectangle.getHeight());
    }


    private void updateXBounds(float delta) {
        textureRegionBounds1.x += transformToScreen(delta * speed);
        textureRegionBounds2.x += transformToScreen(delta * speed);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - transformToScreen(delta * speed)) <= 0;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(textureRegionBounds1.x + screenRectangle.width, 0,
                screenRectangle.width, screenRectangle.height);
    }
}
