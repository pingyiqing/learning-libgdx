package pres.pingyiqing.learning.libgdx.drop.ultimate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import pres.pingyiqing.learning.libgdx.core.component.Display;
import pres.pingyiqing.learning.libgdx.core.component.Position;
import pres.pingyiqing.learning.libgdx.core.component.Velocity;
import pres.pingyiqing.learning.libgdx.core.system.RenderingSystem;
import pres.pingyiqing.learning.libgdx.drop.ultimate.entity.Bucket;
import pres.pingyiqing.learning.libgdx.drop.ultimate.entity.Droplet;
import pres.pingyiqing.learning.libgdx.drop.ultimate.entity.Score;

import java.util.Iterator;

/**
 * @Description:
 * @Author: Ping Yiqing
 * @Date: 2018/12/26
 * @Version: V1.0
 * @Modified By:
 * @Modified Date:
 * @Why:
 */
public class UltimateDropGameScreen implements Screen {
    private final UltimateDrop game;

    private Texture dropletImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;

    OrthographicCamera camera;

    Bucket bucket;
    Array<Droplet> raindrops;
    long lastDropTime;
    Score score;

    public UltimateDropGameScreen(final UltimateDrop game) {
        this.game = game;
        System.out.println("进入游戏界面");

        // 加载droplet和bucket的图像， 64x64 像素
        dropletImage = game.assetManager.get("drop/droplet.png", Texture.class);
        bucketImage = game.assetManager.get("drop/bucket.png", Texture.class);
        // 加载雨滴音乐效果和雨背景音乐
        dropSound = game.assetManager.get("drop/drop.wav", Sound.class);
        rainMusic = game.assetManager.get("drop/rain.mp3", Music.class);
        rainMusic.setLooping(true);
        // 创建相机
        camera = game.engine.getSystem(RenderingSystem.class).camera;
        // 创建计分板
        score = new Score(0, new Position(0, game.windowHeight));
        game.engine.addEntity(score);
        // 创建水桶，水平居中，屏幕底部边缘以上20像素
        bucket = new Bucket(new Position(game.windowWidth / 2 - 64f / 2, 20), new Velocity(), new Display(bucketImage, 64, 64));
        game.engine.addEntity(bucket);
        // 实例化雨滴阵列并产生我们的第一个雨滴
        raindrops = new Array<Droplet>();
        spawnRaindrop();
    }

    /* 生成雨滴 */
    private void spawnRaindrop() {
        Droplet raindrop = new Droplet(new Position(MathUtils.random(0, game.windowWidth - 64), game.windowHeight), new Velocity(0, -200), new Display(dropletImage, 64, 64));
        game.engine.addEntity(raindrop);
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    /* 移除雨滴 */
    private void removeRaindrop(Iterator<Droplet> iter, Droplet droplet) {
        iter.remove();
        game.engine.removeEntity(droplet);
    }

    private boolean overlaps(float x, float y, float width, float height, float rx, float ry, float rwidth, float rheight) {
        return x < rx + rwidth && x + width > rx && y < ry + rheight && y + height > ry;
    }

    @Override
    public void show() {
        // 显示时开始播放背景音乐
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        game.engine.update(delta);

        // 5. 处理用户输入
        // 5.1 使桶移动(触摸/鼠标)
        // 5.1.1 调用是否触摸屏幕（或按下鼠标按钮）
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.position.x = touchPos.x - 64 / 2;
        }
        // 5.2. 使桶移动（键盘）
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.position.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.position.x += 200 * Gdx.graphics.getDeltaTime();
        // 5.3 确保桶在界面范围内
        if (bucket.position.x < 0)
            bucket.position.x = 0;
        if (bucket.position.x > game.windowWidth - 64)
            bucket.position.x = game.windowWidth - 64;
        // 6. 检查是否需要创建一个新的雨滴。
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        //雨滴检测
        Iterator<Droplet> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Droplet raindrop = iter.next();
            if (raindrop.position.y + 64 < 0) {
                removeRaindrop(iter, raindrop);
            }
            Display rd = raindrop.display;
            Display bd = bucket.display;
            if (overlaps(raindrop.position.x, raindrop.position.y, rd.width, rd.height,
                    bucket.position.x, bucket.position.y, bd.width, bd.height)) {
                score.addScore();
                dropSound.play();
                removeRaindrop(iter, raindrop);
            }
        }
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
