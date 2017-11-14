package com.mygdx.game.martianrun.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.martianrun.utils.Constants;

/**
 * @author swanf
 * date 17-11-10 上午9:32
 */

// 设置背景演员类，它实际上是两张长方形的背景图片的交替，然后反复render（渲染）他们的交替
// 它会在x轴上以一定频率更新2个长方形，当长方形边界接触到屏幕末尾，reset它们的位置
    // 这里只继承了Actor而没有继承GameActor是由于背景不需要碰撞检测（box2d元素）
public class Background extends Actor {
    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 100;

    // 初始化的时候，左长方形的右边一半与右长方形的左边一半组合成为当前画面（因为背景图片是连续的，也可以有别的选择）
    public Background() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH)));
        textureRegionBounds1 = new Rectangle(0 - Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.APP_WIDTH / 2, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds1.x, textureRegionBounds1.y, Constants.APP_WIDTH, Constants.APP_HEIGHT);
        batch.draw(textureRegion, textureRegionBounds2.x, textureRegionBounds2.y, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }

    @Override
    public void act(float delta) {  // delta = 最后一个画面到现在的时间(time in seconds since the last frame)
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    // 刷新边界：当右长方形的x到达屏幕最左时，当前长方形成为左长方形，以屏幕最右为x创建新的右长方形
    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        // 这里长方形的x为Constant.APP_WIDTH，我之前写成它的一半了，导致出现了背景的中间断开
        textureRegionBounds2 = new Rectangle(Constants.APP_WIDTH, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    }
}
