package com.mygdx.game.martianrun.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mygdx.game.martianrun.actors.Background;
import com.mygdx.game.martianrun.actors.Enemy;
import com.mygdx.game.martianrun.actors.Ground;
import com.mygdx.game.martianrun.actors.Runner;
import com.mygdx.game.martianrun.utils.BodyUtils;
import com.mygdx.game.martianrun.utils.Constants;
import com.mygdx.game.martianrun.utils.WorldUtils;

/**
 * @author swanf
 * date 17-11-9 下午5:30
 */

/*
* 添加舞台到Screen里面使用，一个Stage实际上是screen2d里面的一部分
* 并且能够接受输入、负责演员类的绘制和输入事件
* */

// 通过ContactListener监听器用来检测runner和ground的碰撞，撞了就通知runner去调用landed（）
public class GameStage extends Stage implements ContactListener{

    // 这将是我们在debug渲染器中的测量数值
    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;  // 世界相机
    private Box2DDebugRenderer renderer;    // 渲染器

    private Rectangle screenLeftSide;   // 左侧区域
    private Rectangle screenRightSide;  // 右侧区域
    private Vector3 touchPoint;     // 人物和地面碰撞点

    public GameStage() {
        super(new ScalingViewport(Scaling.stretchX, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        setupCamera();
        setUpWorld();
        setupTouchControlAreas();
        renderer = new Box2DDebugRenderer();
    }

    private void setupTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0,
                getCamera().viewportWidth / 2, getCamera().viewportHeight);
        Gdx.input.setInputProcessor(this);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpBackground();
        setUpGround();
        setUpRunner();
        createEnemy();
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        addActor(enemy);
    }

    private void setUpBackground() {
        addActor(new Background());
    }


    private void setUpRunner() {
        if (runner != null) {
            runner.remove();
        }
        // 恭喜你发现一个bug的位置：天哪！！！我闪比了吗！！竟然用WorldUtils.createGround(world)创建Runnder。惊叹
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // 需要知道确切的坐标
        translateScreenToWorldCoordinates(screenX, screenY);

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();
        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
            runner.dodge();
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    /* Another bug found
    * 原文里面说要把GameStage中的draw方法去掉（我没看到！），找了好久才发现这个区别
    * 看来我需要更加细心>.<
    * */
   /* @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }*/

    @Override
    public void act(float delta) {
        super.act(delta);

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // 让时间间距保持恒定
        accumulator += delta;

        // 使得每次的时间间隔都控制在某个值以下，不会出现跳帧动作
        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body)) {
            if (BodyUtils.bodyIsEnemy(body) && !runner.isHit()) {
                createEnemy();
            }
            world.destroyBody(body);
        }
    }

    // 一个帮助类，转化触摸坐标为相机（世界）坐标
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (runner.isDodging()) {
            runner.stopDodge();
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b)) ||
                (BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b))) {
            runner.hit();
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
