package pres.pingyiqing.learning.libgdx.drop.simple;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * 雨滴小游戏  基础版
 * 屏幕设置 1280 * 720
 */
public class Drop implements ApplicationListener {
    private Texture dropImage;
    private Texture bucketImage;

    private Sound dropSound;
    private Music rainMusic;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    // 桶实例
    private Rectangle bucket;
    // 雨滴实例列表
    private Array<Rectangle> raindrops;
    // 上次产生雨滴的时间
    private long lastDropTime;
    // 优化，三维向量实例，避免gc
    private Vector3 touchPos = new Vector3();

    @Override
    public void create() {
        // 加载droplet和bucket的图像， 64x64 像素
        dropImage = new Texture(Gdx.files.internal("drop/droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("drop/bucket.png"));
        // 加载雨滴音乐效果和雨背景音乐
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop/drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("drop/rain.mp3"));
        rainMusic.setLooping(true);
        // 立即开始播放背景音乐
        rainMusic.play();
        // 创建相机和SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        batch = new SpriteBatch();
        // 创建一个长方形来逻辑上表示水桶
        bucket = new Rectangle();
        bucket.x = 1280 / 2 - 64 / 2;// 水平居中
        bucket.y = 20;// 屏幕底部边缘以上20像素
        bucket.width = 64;
        bucket.height = 64;
        // 实例化雨滴阵列并产生我们的第一个雨滴
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    // 创建雨滴
    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 1280 - 64);
        raindrop.y = 720;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        // 1. 用深蓝色来清除屏幕
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // 2. 在我们的简单例子中，我们不改变相机的属性，但是每帧更新一次相机通常是一个好习惯
        camera.update();
        // 3. 让精灵批处理用相机的坐标系渲染
        batch.setProjectionMatrix(camera.combined);
        // 4. 开启一个新的批处理来画水桶和雨滴
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        batch.end();

        // 5. 处理用户输入
        // 5.1 使桶移动(触摸/鼠标)
        // 5.1.1 调用是否触摸屏幕（或按下鼠标按钮）
        if (Gdx.input.isTouched()) {
            // 5.1.2 将触摸/鼠标屏幕坐标转换为相机的坐标系
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            // 5.1.3 将桶的位置更改为以触摸/鼠标坐标为中心
            bucket.x = touchPos.x - 64 / 2;
        }
        // 5.2. 使桶移动（键盘）
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
        // 5.3 确保桶在界面范围内
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 1280 - 64) bucket.x = 1280 - 64;
        // 6. 检查是否需要创建一个新的雨滴。
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        // 7. 让雨滴以每秒200像素/单位的速度匀速移动。
        // 如果雨滴位于屏幕的下边缘之下，我们将其从阵列中移除。
        // 如果雨滴击中桶，我们想播放我们的滴声，并从阵列中删除雨滴。
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        //释放所有本地资源
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
        batch.dispose();
    }

}
