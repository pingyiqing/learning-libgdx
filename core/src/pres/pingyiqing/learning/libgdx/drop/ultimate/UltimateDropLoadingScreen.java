package pres.pingyiqing.learning.libgdx.drop.ultimate;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * @Description:
 * @Author: Ping Yiqing
 * @Date: 2018/12/26
 * @Version: V1.0
 * @Modified By:
 * @Modified Date:
 * @Why:
 */
public class UltimateDropLoadingScreen implements Screen {
    private final UltimateDrop game;
    private final AssetManager assetManager;

    public UltimateDropLoadingScreen(UltimateDrop game) {
        this.game = game;
        this.assetManager = game.assetManager;
        System.out.println("进入资源加载界面");
    }

    @Override
    public void show() {
        assetManager.load("drop/droplet.png", Texture.class);
        assetManager.load("drop/bucket.png", Texture.class);
        assetManager.load("drop/drop.wav", Sound.class);
        assetManager.load("drop/rain.mp3", Music.class);
    }

    @Override
    public void render(float delta) {
        //显示资源加载进度，返回一个介于0和1之间的数字，表示到目前为止加载的资产的百分比
        float progress = assetManager.getProgress();
        System.out.println("资源记载进度：" + progress);

        if (assetManager.update()) {
            //已完成资源加载
            game.setScreen(new UltimateDropGameScreen(game));
            dispose();
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
